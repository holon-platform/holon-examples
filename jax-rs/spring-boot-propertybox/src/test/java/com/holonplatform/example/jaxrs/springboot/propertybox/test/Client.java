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
package com.holonplatform.example.jaxrs.springboot.propertybox.test;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.holonplatform.core.property.PropertyBox;
import com.holonplatform.example.model.MProduct;
import com.holonplatform.http.rest.RequestEntity;
import com.holonplatform.http.rest.RestClient;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class Client {

	@LocalServerPort
	private int serverPort;

	@Test
	public void testClient() {

		RestClient client = RestClient.forTarget("http://localhost:" + serverPort + "/api/");

		// Product PropertyBox
		PropertyBox product = PropertyBox.builder(MProduct.PRODUCT).set(MProduct.DESCRIPTION, "Product 1")
				.set(MProduct.SKU, "abc-123-xyz").set(MProduct.UNIT_PRICE, 9.99).build();

		// add using POST
		URI location = client.request().path("products").postForLocation(RequestEntity.json(product))
				.orElseThrow(() -> new RuntimeException("Missing URI"));

		System.out.println("Created URI: " + location);

		// get the product
		PropertyBox created = client.request().target(location).propertySet(MProduct.PRODUCT)
				.getForEntity(PropertyBox.class).orElseThrow(() -> new RuntimeException("Missing product"));

		System.out.println("Created id: " + created.getValue(MProduct.ID));

		// update product
		created.setValue(MProduct.DESCRIPTION, "Updated");

		client.request().path("products/{id}").resolve("id", created.getValue(MProduct.ID))
				.put(RequestEntity.json(created));

		// read again
		PropertyBox updated = client.request().path("products/{id}").resolve("id", created.getValue(MProduct.ID))
				.propertySet(MProduct.PRODUCT).getForEntity(PropertyBox.class)
				.orElseThrow(() -> new RuntimeException("Missing product"));

		System.out.println("Updated description: " + updated.getValue(MProduct.DESCRIPTION));

		// created another product
		product = PropertyBox.builder(MProduct.PRODUCT).set(MProduct.DESCRIPTION, "Product 2")
				.set(MProduct.SKU, "abc-456-xyz").set(MProduct.UNIT_PRICE, 19.99).build();

		location = client.request().path("products").postForLocation(RequestEntity.json(product))
				.orElseThrow(() -> new RuntimeException("Missing URI"));

		System.out.println("Created URI: " + location);

		// get all products as List
		List<PropertyBox> values = client.request().path("products").propertySet(MProduct.PRODUCT)
				.getAsList(PropertyBox.class);

		System.out.println("Products: "
				+ values.stream().map(pb -> pb.getValue(MProduct.ID) + " - " + pb.getValue(MProduct.DESCRIPTION))
						.collect(Collectors.joining("; ")));

		// delete al products
		values.forEach(pb -> {
			client.request().path("products/{id}").resolve("id", pb.getValue(MProduct.ID)).deleteOrFail();

			System.out.println("Deleted product with id: " + pb.getValue(MProduct.ID));
		});

		// get all products again
		values = client.request().path("products").propertySet(MProduct.PRODUCT).getAsList(PropertyBox.class);

		// size should be 0 now
		System.out.println("Products count: " + values.size());

	}

}
