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
package com.holonplatform.example.jaxrs.propertybox.server;

import java.io.IOException;
import java.net.URI;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

public class Server {

	public static void main(String[] args) throws IOException {

		// register the endpoint resource
		final ResourceConfig config = new ResourceConfig().register(PropertyBoxJsonEndpoint.class);

		// create and start Grizzly server
		final HttpServer grizzlyServer = GrizzlyHttpServerFactory
				.createHttpServer(URI.create("http://localhost:8080/api/"), config);
		grizzlyServer.start();

		// wait for a key press to shutdown
		System.in.read();
		grizzlyServer.shutdown();
	}

}
