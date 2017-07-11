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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

import com.holonplatform.auth.Account;
import com.holonplatform.auth.AuthenticationToken;
import com.holonplatform.auth.Realm;
import com.holonplatform.auth.jwt.JwtAuthenticator;
import com.holonplatform.auth.jwt.JwtConfigProperties;
import com.holonplatform.auth.jwt.JwtConfiguration;
import com.holonplatform.spring.EnvironmentConfigPropertyProvider;

@SpringBootApplication
public class Application {

	@Autowired
	private Environment environment;
	
	@Bean
	public Realm realm(AccountService accountProvider) {
		return Realm.builder()
				// Basic HTTP authorization schema resolver
				.resolver(AuthenticationToken.httpBearerResolver())
				// authenticator using AccountService
				.authenticator(Account.authenticator(accountProvider))
				// authenticator using JwtConfiguration and allowing only 'TestIssuer' issuer name
				.authenticator(
						JwtAuthenticator.builder().configuration(jwtConfiguration()).issuer("TestIssuer").build())
				// default authorizer
				.withDefaultAuthorizer().build();
	}
	
	// JWT configuration from properties
	@Bean
	public JwtConfiguration jwtConfiguration() {
		return JwtConfiguration.build(JwtConfigProperties.builder()
				.withPropertySource(EnvironmentConfigPropertyProvider.create(environment)).build());
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
