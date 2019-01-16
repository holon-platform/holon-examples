/*
 * Copyright 2000-2019 Holon TDCN.
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
package com.holonplatform.example.datastore.mongo.test;

import static com.holonplatform.example.datastore.mongo.Product.CATEGORY;
import static com.holonplatform.example.datastore.mongo.Product.DESCRIPTION;
import static com.holonplatform.example.datastore.mongo.Product.ID;
import static com.holonplatform.example.datastore.mongo.Product.PRODUCT;
import static com.holonplatform.example.datastore.mongo.Product.SKU;
import static com.holonplatform.example.datastore.mongo.Product.TARGET;
import static com.holonplatform.example.datastore.mongo.Product.UNIT_PRICE;
import static com.holonplatform.example.datastore.mongo.Product.WITHDRAWN;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.holonplatform.core.datastore.Datastore;
import com.holonplatform.core.datastore.Datastore.OperationResult;
import com.holonplatform.core.datastore.DefaultWriteOption;
import com.holonplatform.core.property.PropertyBox;

import de.flapdoodle.embed.mongo.Command;
import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodProcess;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.MongodConfigBuilder;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.config.RuntimeConfigBuilder;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.config.IRuntimeConfig;
import de.flapdoodle.embed.process.runtime.Network;

@SpringBootTest
public class TestDatastore {

	private static final Logger logger = LoggerFactory.getLogger(TestDatastore.class);
	private static final IRuntimeConfig runtimeConfig = new RuntimeConfigBuilder()
			.defaultsWithLogger(Command.MongoD, logger).build();

	private static MongodExecutable _mongodExe;
	private static MongodProcess _mongod;

	@BeforeAll
	public static void setUp() throws Exception {
		_mongodExe = MongodStarter.getInstance(runtimeConfig).prepare(new MongodConfigBuilder()
				.version(Version.Main.PRODUCTION).net(new Net("localhost", 12345, Network.localhostIsIPv6())).build());
		_mongod = _mongodExe.start();
	}

	@AfterAll
	public static void tearDown() throws Exception {
		_mongod.stop();
		_mongodExe.stop();
	}

	@Autowired
	private Datastore datastore;

	@Test
	public void testDatastore() {

		// Create a new product
		PropertyBox product = PropertyBox.builder(PRODUCT).set(SKU, "prod1-sku").set(DESCRIPTION, "The first product")
				.set(CATEGORY, "C1").set(UNIT_PRICE, 10.90).build();
		// store the product
		OperationResult result = datastore.save(TARGET, product);

		// check to operation succeded
		assertEquals(1, result.getAffectedCount());

		// get the created product ID
		String productId = result.getInsertedKey(ID).orElse(null);
		assertNotNull(productId);

		// get a product by id
		product = datastore.query(TARGET).filter(ID.eq(productId)).findOne(PRODUCT).orElse(null);
		assertNotNull(product);

		// Create another product
		PropertyBox product2 = PropertyBox.builder(PRODUCT).set(SKU, "prod2-sku").set(DESCRIPTION, "The second product")
				.set(CATEGORY, "C1").set(UNIT_PRICE, 12.90).build();

		// store the product using the BRING_BACK_GENERATED_IDS option
		datastore.save(TARGET, product2, DefaultWriteOption.BRING_BACK_GENERATED_IDS);

		// the generated id now is stored in the PropertyBox by the Datastore
		String productId2 = product2.getValue(ID);
		assertNotNull(productId2);

		// get all products (as a Stream) and print the description
		datastore.query(TARGET).stream(PRODUCT).map(p -> p.getValue(DESCRIPTION)).forEach(description -> {
			System.out.println(description);
		});

		// get all product ids, ordered by SKU
		List<String> ids = datastore.query(TARGET).sort(SKU.asc()).stream(ID).collect(Collectors.toList());

		assertEquals(2, ids.size());

		// get only the ID and DESCRITION product property values
		Stream<PropertyBox> productIdDescription = datastore.query(TARGET).stream(ID, DESCRIPTION);

		assertEquals(2, productIdDescription.count());

		// get the products with a price greater then 11
		List<PropertyBox> products = datastore.query(TARGET).filter(UNIT_PRICE.gt(11.00)).list(PRODUCT);
		assertEquals(1, products.size());

		// get the products with a price greater then 11 and description starting with "The"
		products = datastore.query(TARGET).filter(UNIT_PRICE.gt(11.00).and(DESCRIPTION.startsWith("The")))
				.list(PRODUCT);
		assertEquals(1, products.size());

		// get the max price grouping by category
		Double maxPrice = datastore.query(TARGET).aggregate(CATEGORY).stream(UNIT_PRICE.max()).findFirst().orElse(null);
		assertEquals(Double.valueOf(12.90), maxPrice);

		// update the WITHDRAWN status for product 1
		datastore.query(TARGET).filter(ID.eq(productId)).findOne(PRODUCT).ifPresent(p -> {
			// update the product
			p.setValue(WITHDRAWN, true);
			datastore.update(TARGET, p);
		});

		// insert a new product
		PropertyBox third = PropertyBox.builder(PRODUCT).set(SKU, "prod3-sku").set(DESCRIPTION, "The third product")
				.build();
		datastore.insert(TARGET, third, DefaultWriteOption.BRING_BACK_GENERATED_IDS);

		// remove the product
		result = datastore.delete(TARGET, third);
		assertEquals(1, result.getAffectedCount());

		// bulk update: update all the products with category C1 to category C2
		result = datastore.bulkUpdate(TARGET).set(CATEGORY, "C2").filter(CATEGORY.eq("C1")).execute();

		assertEquals(2, result.getAffectedCount());

		// bulk delete: delete all the products with category C2
		result = datastore.bulkDelete(TARGET).filter(CATEGORY.eq("C2")).execute();

		assertEquals(2, result.getAffectedCount());
	}

}
