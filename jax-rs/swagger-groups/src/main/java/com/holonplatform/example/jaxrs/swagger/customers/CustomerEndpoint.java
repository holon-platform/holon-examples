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
package com.holonplatform.example.jaxrs.swagger.customers;

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
import com.holonplatform.example.model.MCustomer;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api("Customer management")
@Component
@Path("/customers")
public class CustomerEndpoint {

	/*
	 * Get a list of customers PropertyBox in JSON.
	 */
	@ApiOperation("Get all the customers")
	@CustomerModel
	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public List<PropertyBox> getCustomers() {
		return getCustomerStore().getAll();
	}

	/*
	 * Get a customer PropertyBox in JSON.
	 */
	@ApiOperation("Get a customer by id")
	@ApiResponses({ @ApiResponse(code = 200, message = "OK", response = PropertyBox.class) })
	@CustomerModel
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCustomer(@ApiParam("The customer id") @PathParam("id") Long id) {
		return getCustomerStore().get(id).map(p -> Response.ok(p).build())
				.orElse(Response.status(Status.NOT_FOUND).build());
	}

	/*
	 * Create a customer. The @PropertySetRef must be used to declare the request PropertyBox property set.
	 */
	@ApiOperation("Create a customer")
	@ApiResponses({ @ApiResponse(code = 201, message = "Customer created") })
	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addCustomer(@CustomerModel PropertyBox customer) {
		if (customer == null) {
			return Response.status(Status.BAD_REQUEST).entity("Missing customer").build();
		}
		// set id
		long nextId = getCustomerStore().nextId();
		customer.setValue(MCustomer.ID, nextId);
		getCustomerStore().put(customer);
		return Response.created(URI.create("/api/customers/" + nextId)).build();
	}

	/*
	 * Update a customer. The @PropertySetRef must be used to declare the request PropertyBox property set.
	 */
	@ApiOperation("Update a customer")
	@ApiResponses({ @ApiResponse(code = 204, message = "Customer updated"),
			@ApiResponse(code = 404, message = "Customer not found") })
	@PUT
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateCustomer(@CustomerModel PropertyBox customer) {
		if (customer == null) {
			return Response.status(Status.BAD_REQUEST).entity("Missing customer").build();
		}
		if (!customer.getValueIfPresent(MCustomer.ID).isPresent()) {
			return Response.status(Status.BAD_REQUEST).entity("Missing customer id").build();
		}
		return getCustomerStore().get(customer.getValue(MCustomer.ID)).map(p -> {
			getCustomerStore().put(customer);
			return Response.noContent().build();
		}).orElse(Response.status(Status.NOT_FOUND).build());
	}

	/*
	 * Delete a customer by id.
	 */
	@ApiOperation("Delete a customer")
	@ApiResponses({ @ApiResponse(code = 204, message = "Customer deleted") })
	@DELETE
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response deleteCustomer(@ApiParam("The customer id to delete") @PathParam("id") Long id) {
		getCustomerStore().remove(id);
		return Response.noContent().build();
	}

	private static CustomerStore getCustomerStore() {
		return CustomerStore.INSTANCE;
	}

}
