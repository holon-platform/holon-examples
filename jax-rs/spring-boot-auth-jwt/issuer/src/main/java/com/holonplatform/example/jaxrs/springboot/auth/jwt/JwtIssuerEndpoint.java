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
package com.holonplatform.example.jaxrs.springboot.auth.jwt;

import java.util.UUID;

import javax.ws.rs.GET;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.ext.Providers;

import org.springframework.stereotype.Component;

import com.holonplatform.auth.Authentication;
import com.holonplatform.auth.annotations.Authenticate;
import com.holonplatform.auth.jwt.JwtConfiguration;
import com.holonplatform.auth.jwt.JwtTokenBuilder;
import com.holonplatform.http.HttpHeaders;
import com.holonplatform.jaxrs.server.ResourceUtils;

@Authenticate(schemes = HttpHeaders.SCHEME_BASIC)
@Component
@Path("/jwt")
public class JwtIssuerEndpoint {

	@Context
	private Providers providers;

	@GET
	@Path("/issue")
	@Produces(MediaType.TEXT_PLAIN)
	public Response issue(@Context SecurityContext securityContext) {

		// get Authentication
		Authentication authc = (Authentication) securityContext.getUserPrincipal();

		// configuration
		JwtConfiguration configuration = ResourceUtils.lookupResource(getClass(), JwtConfiguration.class, providers)
				.orElseThrow(() -> new InternalServerErrorException("JWT configuration not available"));

		// build JWT
		String jwt = JwtTokenBuilder.buildJwtToken(configuration, authc, UUID.randomUUID().toString());
		// ok
		return Response.ok(jwt, MediaType.TEXT_PLAIN).build();

	}

}
