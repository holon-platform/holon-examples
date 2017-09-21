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
package com.holonplatform.example.jaxrs.swagger;

import static com.holonplatform.example.jaxrs.swagger.Product.ID;
import static com.holonplatform.example.jaxrs.swagger.Product.PRODUCT;
import static com.holonplatform.example.jaxrs.swagger.Product.TARGET;

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

import com.holonplatform.core.datastore.Datastore;
import com.holonplatform.core.datastore.DefaultWriteOption;
import com.holonplatform.core.property.PropertyBox;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api("Product management")
@Component
@Path("/products")
public class ProductEndpoint {
	
	@Inject
	private Datastore datastore;
	
	/*
	 * Get a list of products PropertyBox in JSON.
	 */
	@ApiOperation("Get all the products")
	@ProductModel
	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public List<PropertyBox> getProducts() {
		return datastore.query().target(TARGET).list(PRODUCT);
	}

	/*
	 * Get a product PropertyBox in JSON.
	 */
	@ApiOperation("Get a product by id")
	@ApiResponses({ @ApiResponse(code = 200, message = "OK", response = PropertyBox.class) })
	@ProductModel
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getProduct(@ApiParam("The product id") @PathParam("id") Long id) {
		return datastore.query().target(TARGET).filter(ID.eq(id)).findOne(PRODUCT).map(p -> Response.ok(p).build())
				.orElse(Response.status(Status.NOT_FOUND).build());
	}

	/*
	 * Create a product. The @PropertySetRef must be used to declare the request PropertyBox property set.
	 */
	@ApiOperation("Create a product")
	@ApiResponses({ @ApiResponse(code = 201, message = "Product created") })
	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addProduct(@ProductModel PropertyBox product) {
		if (product == null) {
			return Response.status(Status.BAD_REQUEST).entity("Missing product").build();
		}
		datastore.save(TARGET, product, DefaultWriteOption.BRING_BACK_GENERATED_IDS);
		return Response.created(URI.create("/api/products/" + product.getValue(ID))).build();
	}

	/*
	 * Update a product. The @PropertySetRef must be used to declare the request PropertyBox property set.
	 */
	@ApiOperation("Update a product")
	@ApiResponses({ @ApiResponse(code = 204, message = "Product updated"),
			@ApiResponse(code = 404, message = "Product not found") })
	@PUT
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateProduct(@ProductModel PropertyBox product) {
		if (product == null) {
			return Response.status(Status.BAD_REQUEST).entity("Missing product").build();
		}
		if (!product.getValueIfPresent(ID).isPresent()) {
			return Response.status(Status.BAD_REQUEST).entity("Missing product id").build();
		}
		return datastore.query().target(TARGET).filter(ID.eq(product.getValue(ID))).findOne(PRODUCT).map(p -> {
			datastore.save(TARGET, product);
			return Response.noContent().build();
		}).orElse(Response.status(Status.NOT_FOUND).build());
	}

	/*
	 * Delete a product by id.
	 */
	@ApiOperation("Delete a product")
	@ApiResponses({ @ApiResponse(code = 204, message = "Product deleted") })
	@DELETE
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response deleteProduct(@ApiParam("The product id to delete") @PathParam("id") Long id) {
		datastore.bulkDelete(TARGET).filter(ID.eq(id)).execute();
		return Response.noContent().build();
	}

}
