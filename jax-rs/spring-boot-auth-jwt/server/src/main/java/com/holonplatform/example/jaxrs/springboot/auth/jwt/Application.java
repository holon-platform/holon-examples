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

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.holonplatform.auth.AuthenticationToken;
import com.holonplatform.auth.Realm;
import com.holonplatform.auth.jwt.JwtAuthenticator;
import com.holonplatform.auth.jwt.JwtConfiguration;

@SpringBootApplication
public class Application {

	/*
	 * The JwtConfiguration bean is automatically configured using the 'holon.jwt.*' configuration properties
	 */
	@Bean
	public Realm realm(JwtConfiguration jwtConfiguration) {
		return Realm.builder()
				// HTTP Bearer authorization schema resolver
				.resolver(AuthenticationToken.httpBearerResolver())
				// authenticator using JwtConfiguration and allowing only 'TestIssuer' issuer name
				.authenticator(JwtAuthenticator.builder().configuration(jwtConfiguration).issuer("TestIssuer").build())
				// default authorizer
				.withDefaultAuthorizer().build();
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
