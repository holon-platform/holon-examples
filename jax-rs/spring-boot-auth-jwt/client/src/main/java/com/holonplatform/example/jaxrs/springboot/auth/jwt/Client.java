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

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.ws.rs.client.ClientBuilder;

import com.holonplatform.http.exceptions.UnsuccessfulResponseException;
import com.holonplatform.http.rest.ResponseEntity;
import com.holonplatform.http.rest.RestClient;
import com.holonplatform.jaxrs.client.JaxrsRestClient;

/**
 * Client main class
 */
public class Client {

	public static void main(String[] args) {

		RestClient client = JaxrsRestClient.create(getTrustAllClient());

		try {
			// obtain a JWT token
			String jwt = client.request().target("https://localhost:8443/jwt/issue")
					.authorizationBasic("act1", "act1secret").getForEntity(String.class).orElse(null);

			System.out.println("JWT token: " + jwt);

			String response = client.request().target("http://localhost:8080/api").path("roles")
					.authorizationBearer(jwt).getForEntity(String.class).orElse(null);

			System.out.println("Roles: " + response);

			response = client.request().target("http://localhost:8080/api").path("role1").authorizationBearer(jwt)
					.getForEntity(String.class).orElse(null);

			System.out.println("Role1: " + response);

			ResponseEntity<String> re = client.request().target("http://localhost:8080/api").path("role2")
					.authorizationBearer(jwt).get(String.class);
			
			System.out.println("Role2: " + re.getStatus());

		} catch (UnsuccessfulResponseException e) {
			// print exception message
			System.err.println(e.getMessage());
			// print the response body as String, if present
			e.getResponse().as(String.class).ifPresent(r -> System.err.println(r));
		}

	}

	/*
	 * Use a "trust all" SSLContext to avoid certificate errors
	 */
	private static javax.ws.rs.client.Client getTrustAllClient() {
		try {
			final SSLContext sslcontext = SSLContext.getInstance("TLS");
			sslcontext.init(null, new TrustManager[] { new X509TrustManager() {
				@Override
				public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
				}

				@Override
				public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
				}

				@Override
				public X509Certificate[] getAcceptedIssuers() {
					return new X509Certificate[0];
				}

			} }, new java.security.SecureRandom());

			final ClientBuilder builder = ClientBuilder.newBuilder().sslContext(sslcontext)
					.hostnameVerifier((s1, s2) -> true);
			return builder.build();
		} catch (KeyManagementException | NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}

}
