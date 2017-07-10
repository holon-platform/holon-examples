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
package com.holonplatform.example.jaxrs.springboot.datastore;

import java.net.URI;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.springframework.stereotype.Component;

import com.holonplatform.core.property.PropertyBox;
import com.holonplatform.core.property.PropertySetRef;
import com.holonplatform.example.model.MProduct;

@Component
@Path("/api")
public class ProductEndpoint {

	@Inject
	private ProductStore productStore;
	
	/*
	 * Get a list of products PropertyBox in JSON.
	 */
	@GET
	@Path("/products")
	@Produces(MediaType.APPLICATION_JSON)
	public List<PropertyBox> getProducts() {
		return getProductStore().getAll();
	}

	/*
	 * Get a product PropertyBox in JSON.
	 */
	@GET
	@Path("/products/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getProduct(@PathParam("id") Long id) {
		return getProductStore().get(id).map(p -> Response.ok(p).build())
				.orElse(Response.status(Status.NOT_FOUND).build());
	}

	/*
	 * Create a product. The @PropertySetRef must be used to declare the request PropertyBox property set.
	 */
	@POST
	@Path("/products")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addProduct(@PropertySetRef(MProduct.class) PropertyBox product) {
		if (product == null) {
			return Response.status(Status.BAD_REQUEST).entity("Missing product").build();
		}
		// set id
		long nextId = getProductStore().nextId();
		product.setValue(MProduct.ID, nextId);
		getProductStore().put(product);
		return Response.created(URI.create("/api/products/" + nextId)).build();
	}

	/*
	 * Update a product. The @PropertySetRef must be used to declare the request PropertyBox property set.
	 */
	@PUT
	@Path("/products/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateProduct(@PropertySetRef(MProduct.class) PropertyBox product) {
		if (product == null) {
			return Response.status(Status.BAD_REQUEST).entity("Missing product").build();
		}
		if (!product.getValueIfPresent(MProduct.ID).isPresent()) {
			return Response.status(Status.BAD_REQUEST).entity("Missing product id").build();
		}
		return getProductStore().get(product.getValue(MProduct.ID)).map(p -> {
			getProductStore().put(product);
			return Response.noContent().build();
		}).orElse(Response.status(Status.NOT_FOUND).build());
	}

	/*
	 * Delete a product by id.
	 */
	@DELETE
	@Path("/products/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response deleteProduct(@PathParam("id") Long id) {
		getProductStore().remove(id);
		return Response.noContent().build();
	}

	private ProductStore getProductStore() {
		return productStore;
	}

}
