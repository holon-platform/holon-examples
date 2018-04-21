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
package com.holonplatform.example.jaxrs.propertybox.client;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import com.holonplatform.core.property.PropertyBox;
import com.holonplatform.example.jaxrs.propertybox.model.Product;
import com.holonplatform.http.exceptions.UnsuccessfulResponseException;
import com.holonplatform.http.rest.RequestEntity;
import com.holonplatform.http.rest.RestClient;

/**
 * Client main class
 */
public class Client {

	public static void main(String[] args) {

		RestClient client = RestClient.forTarget("http://localhost:8080/api/");

		try {

			// Product PropertyBox
			PropertyBox product = PropertyBox.builder(Product.PRODUCT).set(Product.DESCRIPTION, "Product 1")
					.set(Product.SKU, "abc-123-xyz").set(Product.UNIT_PRICE, 9.99).build();

			// add using POST
			URI location = client.request().path("products").postForLocation(RequestEntity.json(product))
					.orElseThrow(() -> new RuntimeException("Missing URI"));

			System.out.println("Created URI: " + location);

			// get the product
			PropertyBox created = client.request().target(location).propertySet(Product.PRODUCT)
					.getForEntity(PropertyBox.class).orElseThrow(() -> new RuntimeException("Missing product"));

			System.out.println("Created id: " + created.getValue(Product.ID));

			// update product
			created.setValue(Product.DESCRIPTION, "Updated");

			client.request().path("products/{id}").resolve("id", created.getValue(Product.ID))
					.put(RequestEntity.json(created));

			// read again
			PropertyBox updated = client.request().path("products/{id}").resolve("id", created.getValue(Product.ID))
					.propertySet(Product.PRODUCT).getForEntity(PropertyBox.class)
					.orElseThrow(() -> new RuntimeException("Missing product"));

			System.out.println("Updated description: " + updated.getValue(Product.DESCRIPTION));

			// created another product
			product = PropertyBox.builder(Product.PRODUCT).set(Product.DESCRIPTION, "Product 2")
					.set(Product.SKU, "abc-456-xyz").set(Product.UNIT_PRICE, 19.99).build();

			location = client.request().path("products").postForLocation(RequestEntity.json(product))
					.orElseThrow(() -> new RuntimeException("Missing URI"));

			System.out.println("Created URI: " + location);

			// get all products as List
			List<PropertyBox> values = client.request().path("products").propertySet(Product.PRODUCT)
					.getAsList(PropertyBox.class);

			System.out.println("Products: "
					+ values.stream().map(pb -> pb.getValue(Product.ID) + " - " + pb.getValue(Product.DESCRIPTION))
							.collect(Collectors.joining("; ")));

			// delete al products
			values.forEach(pb -> {
				client.request().path("products/{id}").resolve("id", pb.getValue(Product.ID)).deleteOrFail();

				System.out.println("Deleted product with id: " + pb.getValue(Product.ID));
			});

			// get all products again
			values = client.request().path("products").propertySet(Product.PRODUCT).getAsList(PropertyBox.class);

			// size should be 0 now
			System.out.println("Products count: " + values.size());

		} catch (UnsuccessfulResponseException e) {
			// print exception message
			System.err.println(e.getMessage());
			// print the response body as String, if present
			e.getResponse().as(String.class).ifPresent(r -> System.err.println(r));
		}

	}

}
