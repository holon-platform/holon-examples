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
package com.holonplatform.example.jaxrs.springboot.auth.basic.test;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.holonplatform.http.HttpStatus;
import com.holonplatform.http.rest.ResponseEntity;
import com.holonplatform.http.rest.RestClient;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class Client {

	@LocalServerPort
	private int serverPort;

	@Test
	public void testClient() {

		RestClient client = RestClient.forTarget("http://localhost:" + serverPort + "/api/");

		// this should not be authorized
		ResponseEntity<String> response = client.request().path("anyrole").get(String.class);
		Assert.assertEquals(HttpStatus.UNAUTHORIZED, response.getStatus());
		response.close();

		// authenticate as act1
		response = client.request().path("anyrole").authorizationBasic("act1", "act1pwd").get(String.class);
		// now should be OK
		Assert.assertEquals(HttpStatus.OK, response.getStatus());
		response.close();

		// act1 has the ROLE1
		response = client.request().path("role1").authorizationBasic("act1", "act1pwd").get(String.class);
		Assert.assertEquals(HttpStatus.OK, response.getStatus());
		response.close();

		// act1 has NOT the ROLE2
		response = client.request().path("role2").authorizationBasic("act1", "act1pwd").get(String.class);
		Assert.assertEquals(HttpStatus.FORBIDDEN, response.getStatus());
		response.close();

		// authenticate as act2, which has the ROLE2
		response = client.request().path("role2").authorizationBasic("act2", "act2pwd").get(String.class);
		Assert.assertEquals(HttpStatus.OK, response.getStatus());
		response.close();

	}

}
