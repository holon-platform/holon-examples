/*
 * Copyright 2000-2017 Holon TDCN.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.holonplatform.example.datastore.jdbc.test;

import static com.holonplatform.example.datastore.jdbc.Product.CATEGORY;
import static com.holonplatform.example.datastore.jdbc.Product.DESCRIPTION;
import static com.holonplatform.example.datastore.jdbc.Product.ID;
import static com.holonplatform.example.datastore.jdbc.Product.PRODUCT;
import static com.holonplatform.example.datastore.jdbc.Product.SKU;
import static com.holonplatform.example.datastore.jdbc.Product.TARGET;
import static com.holonplatform.example.datastore.jdbc.Product.UNIT_PRICE;
import static com.holonplatform.example.datastore.jdbc.Product.WITHDRAWN;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.holonplatform.core.datastore.Datastore;
import com.holonplatform.core.datastore.Datastore.OperationResult;
import com.holonplatform.core.datastore.DefaultWriteOption;
import com.holonplatform.core.property.PropertyBox;

@SpringBootTest
public class TestDatastore {

	@Autowired
	private Datastore datastore;

	@Test
	public void testDatastore() {
		testSave();
		testQuery();
		testOperations();
		testBulk();
	}

	public void testSave() {

		// Create a new product
		PropertyBox product = PropertyBox.builder(PRODUCT).set(SKU, "prod1-sku").set(DESCRIPTION, "The first product")
				.set(CATEGORY, "C1").set(UNIT_PRICE, 10.90).build();
		// store the product
		OperationResult result = datastore.save(TARGET, product);

		// check to operation succeded
		assertEquals(1, result.getAffectedCount());

		// get the created product ID (the ID column is configured as auto-increment)
		Long productId = result.getInsertedKey(ID).orElse(null);
		assertEquals(Long.valueOf(1), productId);

		// Create another product
		PropertyBox product2 = product.cloneBox();
		product2.setValue(SKU, "prod2-sku");
		product2.setValue(UNIT_PRICE, 12.90);
		product2.setValue(DESCRIPTION, "The second product");

		// store the product using the BRING_BACK_GENERATED_IDS option
		datastore.save(TARGET, product2, DefaultWriteOption.BRING_BACK_GENERATED_IDS);

		// the generated id now is stored in the PropertyBox by the Datastore
		Long productId2 = product2.getValue(ID);
		assertEquals(Long.valueOf(2), productId2);
	}

	public void testQuery() {

		// get all products (as a Stream) and print the description
		datastore.query(TARGET).stream(PRODUCT).map(p -> p.getValue(DESCRIPTION)).forEach(description -> {
			System.out.println(description);
		});

		// get a product by id
		PropertyBox product = datastore.query(TARGET).filter(ID.eq(1L)).findOne(PRODUCT).orElse(null);
		assertNotNull(product);

		// get all product ids, ordered by SKU
		List<Long> ids = datastore.query(TARGET).sort(SKU.asc()).stream(ID).collect(Collectors.toList());

		assertEquals(2, ids.size());

		// get only the ID and DESCRITION product property values
		Stream<PropertyBox> productIdDescription = datastore.query(TARGET).stream(ID, DESCRIPTION);

		assertEquals(2, productIdDescription.count());

		// get the products with a price greater then 11
		List<PropertyBox> products = datastore.query(TARGET).filter(UNIT_PRICE.gt(11.00)).list(PRODUCT);

		assertEquals(1, products.size());
		assertEquals(Long.valueOf(2), products.get(0).getValue(ID));

		// get the products with a price greater then 11 and description starting with "The"
		products = datastore.query(TARGET).filter(UNIT_PRICE.gt(11.00).and(DESCRIPTION.startsWith("The")))
				.list(PRODUCT);
		assertEquals(1, products.size());

		// get the max price grouping by category
		Double maxPrice = datastore.query(TARGET).aggregate(CATEGORY).stream(UNIT_PRICE.max()).findFirst().orElse(null);
		assertEquals(Double.valueOf(12.90), maxPrice);
	}

	public void testOperations() {

		// update the WITHDRAWN status for product 1
		datastore.query(TARGET).filter(ID.eq(1L)).findOne(PRODUCT).ifPresent(product -> {
			// update the product
			product.setValue(WITHDRAWN, true);
			datastore.update(TARGET, product);
		});

		assertTrue(datastore.query(TARGET).filter(ID.eq(1L)).findOne(WITHDRAWN).orElse(false));

		// insert a new product
		PropertyBox third = PropertyBox.builder(PRODUCT).set(SKU, "prod3-sku").set(DESCRIPTION, "The third product")
				.build();
		datastore.insert(TARGET, third, DefaultWriteOption.BRING_BACK_GENERATED_IDS);

		// remove the product
		OperationResult result = datastore.delete(TARGET, third);
		assertEquals(1, result.getAffectedCount());
	}

	public void testBulk() {

		// bulk update: update all the products with category C1 to category C2
		OperationResult result = datastore.bulkUpdate(TARGET).set(CATEGORY, "C2").filter(CATEGORY.eq("C1")).execute();

		assertEquals(2, result.getAffectedCount());

		// bulk delete: delete all the products with category C2
		result = datastore.bulkDelete(TARGET).filter(CATEGORY.eq("C2")).execute();

		assertEquals(2, result.getAffectedCount());
	}

}
