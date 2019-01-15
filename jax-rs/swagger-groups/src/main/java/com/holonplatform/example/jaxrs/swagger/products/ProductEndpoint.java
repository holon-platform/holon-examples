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
package com.holonplatform.example.jaxrs.swagger.products;

import java.net.URI;
import java.util.List;

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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Product management")
@Component
@Path("/products")
public class ProductEndpoint {

	/*
	 * Get a list of products PropertyBox in JSON.
	 */
	@Operation(summary = "Get all the products")
	@ProductModel
	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public List<PropertyBox> getProducts() {
		return getProductStore().getAll();
	}

	/*
	 * Get a product PropertyBox in JSON.
	 */
	@Operation(summary = "Get a product by id")
	@ProductModel
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getProduct(@Parameter(name = "The product id") @PathParam("id") Long id) {
		return getProductStore().get(id).map(p -> Response.ok(p).build())
				.orElse(Response.status(Status.NOT_FOUND).build());
	}

	/*
	 * Create a product. The @PropertySetRef must be used to declare the request PropertyBox property set.
	 */
	@Operation(summary = "Create a product")
	@ApiResponses(@ApiResponse(responseCode = "201", description = "Product created"))
	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addProduct(@ProductModel PropertyBox product) {
		if (product == null) {
			return Response.status(Status.BAD_REQUEST).entity("Missing product").build();
		}
		// set id
		long nextId = getProductStore().nextId();
		product.setValue(Product.ID, nextId);
		getProductStore().put(product);
		return Response.created(URI.create("/api/products/" + nextId)).build();
	}

	/*
	 * Update a product. The @PropertySetRef must be used to declare the request PropertyBox property set.
	 */
	@Operation(summary = "Update a product")
	@ApiResponses({ @ApiResponse(responseCode = "204", description = "Product updated"),
			@ApiResponse(responseCode = "404", description = "Product not found") })
	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateProduct(@ProductModel PropertyBox product) {
		if (product == null) {
			return Response.status(Status.BAD_REQUEST).entity("Missing product").build();
		}
		if (!product.getValueIfPresent(Product.ID).isPresent()) {
			return Response.status(Status.BAD_REQUEST).entity("Missing product id").build();
		}
		return getProductStore().get(product.getValue(Product.ID)).map(p -> {
			getProductStore().put(product);
			return Response.noContent().build();
		}).orElse(Response.status(Status.NOT_FOUND).build());
	}

	/*
	 * Delete a product by id.
	 */
	@Operation(summary = "Delete a product")
	@ApiResponses(@ApiResponse(responseCode = "204", description = "Product deleted"))
	@DELETE
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response deleteProduct(@Parameter(name = "The product id to delete") @PathParam("id") Long id) {
		getProductStore().remove(id);
		return Response.noContent().build();
	}

	private static ProductStore getProductStore() {
		return ProductStore.INSTANCE;
	}

}
