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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Customer management")
@Component
@Path("/customers")
public class CustomerEndpoint {

	/*
	 * Get a list of customers PropertyBox in JSON.
	 */
	@Operation(summary = "Get all the customers")
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
	@Operation(summary = "Get a customer by id")
	@CustomerModel
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCustomer(@Parameter(name = "The customer id") @PathParam("id") Long id) {
		return getCustomerStore().get(id).map(p -> Response.ok(p).build())
				.orElse(Response.status(Status.NOT_FOUND).build());
	}

	/*
	 * Create a customer. The @PropertySetRef must be used to declare the request PropertyBox property set.
	 */
	@Operation(summary = "Create a customer")
	@ApiResponses(@ApiResponse(responseCode = "201", description = "Customer created"))
	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addCustomer(@CustomerModel PropertyBox customer) {
		if (customer == null) {
			return Response.status(Status.BAD_REQUEST).entity("Missing customer").build();
		}
		// set id
		long nextId = getCustomerStore().nextId();
		customer.setValue(Customer.ID, nextId);
		getCustomerStore().put(customer);
		return Response.created(URI.create("/api/customers/" + nextId)).build();
	}

	/*
	 * Update a customer. The @PropertySetRef must be used to declare the request PropertyBox property set.
	 */
	@Operation(summary = "Update a customer")
	@ApiResponses({ @ApiResponse(responseCode = "204", description = "Customer updated"),
			@ApiResponse(responseCode = "404", description = "Customer not found") })
	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateCustomer(@CustomerModel PropertyBox customer) {
		if (customer == null) {
			return Response.status(Status.BAD_REQUEST).entity("Missing customer").build();
		}
		if (!customer.getValueIfPresent(Customer.ID).isPresent()) {
			return Response.status(Status.BAD_REQUEST).entity("Missing customer id").build();
		}
		return getCustomerStore().get(customer.getValue(Customer.ID)).map(p -> {
			getCustomerStore().put(customer);
			return Response.noContent().build();
		}).orElse(Response.status(Status.NOT_FOUND).build());
	}

	/*
	 * Delete a customer by id.
	 */
	@Operation(summary = "Delete a customer")
	@ApiResponses(@ApiResponse(responseCode = "204", description = "Customer deleted"))
	@DELETE
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response deleteCustomer(@Parameter(name = "The customer id to delete") @PathParam("id") Long id) {
		getCustomerStore().remove(id);
		return Response.noContent().build();
	}

	private static CustomerStore getCustomerStore() {
		return CustomerStore.INSTANCE;
	}

}
