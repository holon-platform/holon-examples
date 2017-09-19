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
package com.holonplatform.example.core.context;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.holonplatform.core.Context;

/**
 * Spring Boot Application
 */
@SpringBootApplication
public class Application {

	// Configure the ExampleResource class as a Bean
	@Bean
	public ExampleResource exampleResource() {
		return new ExampleResourceImpl("Example resource");
	}

	public static void main(String[] args) {
		// Run
		SpringApplication.run(Application.class, args);

		// Get the ExampleResource by type
		Context.get().resource(ExampleResource.class).ifPresent(resource -> {
			System.out.println("Message: " + resource.getMessage());
		});

		// Get the ExampleResource by resource key (Spring Bean name)
		Context.get().resource("exampleResource", ExampleResource.class).ifPresent(resource -> {
			System.out.println("Message: " + resource.getMessage());
		});

		// Use the current Thread context to override the ExampleResource instance
		Context.get().executeThreadBound("exampleResource", new ExampleResourceImpl("Thread resource"), () -> {
			Context.get().resource("exampleResource", ExampleResource.class).ifPresent(resource -> {
				System.out.println("Message: " + resource.getMessage());
			});
		});

	}

}
