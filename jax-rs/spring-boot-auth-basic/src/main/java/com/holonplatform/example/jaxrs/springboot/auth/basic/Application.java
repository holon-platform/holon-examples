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
package com.holonplatform.example.jaxrs.springboot.auth.basic;

import java.util.Optional;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.holonplatform.auth.Account;
import com.holonplatform.auth.Account.AccountProvider;
import com.holonplatform.auth.AuthenticationToken;
import com.holonplatform.auth.Credentials;
import com.holonplatform.auth.Realm;

@SpringBootApplication
public class Application {

	@Bean
	public Realm realm() {
		return Realm.builder()
				// Basic HTTP authorization schema resolver
				.withResolver(AuthenticationToken.httpBasicResolver())
				// authenticator using the accountProvider() service
				.withAuthenticator(Account.authenticator(accountProvider()))
				// default authorizer
				.withDefaultAuthorizer().build();
	}

	@Bean
	public AccountProvider accountProvider() {
		return accountId -> {
			// act1
			if ("act1".equals(accountId)) {
				return Optional.of(Account.builder(accountId)
						// set credentials
						.credentials(Credentials.builder().secret("act1pwd").build())
						// configuration
						.enabled(true)
						// add details
						.withDetail("name", "Account 1")
						// permission roles
						.permissionStrings("ROLE1").build());
			}
			// act2
			if ("act2".equals(accountId)) {
				return Optional.of(Account.builder(accountId)
						// set credentials
						.credentials(Credentials.builder().secret("act2pwd").build())
						// configuration
						.enabled(true)
						// add details
						.withDetail("name", "Account 2")
						// permission roles
						.permissionStrings("ROLE2").build());
			}
			return Optional.empty();
		};
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
