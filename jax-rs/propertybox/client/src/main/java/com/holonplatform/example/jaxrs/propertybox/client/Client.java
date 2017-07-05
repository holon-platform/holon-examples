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
import java.util.Optional;

import com.holonplatform.core.property.PropertyBox;
import com.holonplatform.example.model.MProduct;
import com.holonplatform.http.exceptions.UnsuccessfulResponseException;
import com.holonplatform.http.rest.RequestEntity;
import com.holonplatform.http.rest.RestClient;
import com.holonplatform.jaxrs.client.JaxrsRestClient;

public class Client {

	public static void main(String[] args) {

		RestClient client = JaxrsRestClient.create().defaultTarget(URI.create("http://localhost:8080/api/"));

		PropertyBox product = PropertyBox.builder(MProduct.PRODUCT).set(MProduct.DESCRIPTION, "Product 1")
				.set(MProduct.SKU, "abc-123-xyz").set(MProduct.UNIT_PRICE, 9.99).build();

		// Add a product
		try {
			Optional<URI> location = client.request().path("products")
					//.propertySet(MProduct.PRODUCT)
					.postForLocation(RequestEntity.json(product));
			System.out.println("Created: " + location.orElse(null));
		} catch (UnsuccessfulResponseException e) {
			System.err.println(e.getMessage());
			e.getResponse().as(String.class).ifPresent(r -> System.err.println(r));
		}
		

	}

}
