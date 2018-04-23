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
package com.holonplatform.example.core.property.model;

import com.holonplatform.core.Context;
import com.holonplatform.core.Validator;
import com.holonplatform.core.property.BooleanProperty;
import com.holonplatform.core.property.NumericProperty;
import com.holonplatform.core.property.PropertySet;
import com.holonplatform.core.property.StringProperty;
import com.holonplatform.core.property.VirtualProperty;
import com.holonplatform.example.core.property.support.DatasetService;

/**
 * Product property model
 */
public interface Product {

	public static final NumericProperty<Long> ID = NumericProperty.longType("id").message("Product ID")
			.messageCode("product.id") // Product ID
			// not null value validator
			.validator(Validator.notNull());

	public static final StringProperty DESCRIPTION = StringProperty.create("description") // Description
			.message("Description").messageCode("product.description")
			// max 500 characters value validator
			.validator(Validator.max(500));

	public static final StringProperty CATEGORY = StringProperty.create("category") // Category
			.message("Category").messageCode("product.category")
			// add a configuration property named "DATASET" with value "CATEGORY"
			.configuration("DATASET", "CATEGORY");

	public static final NumericProperty<Double> UNIT_PRICE = NumericProperty.doubleType("price") // Price
			.message("Price").messageCode("product.price")
			// not negative value validator
			.validator(Validator.notNegative());

	public static final BooleanProperty WITHDRAWN = BooleanProperty.create("withdrawn") // Withdrawn
			.message("Withdrawn").messageCode("product.withdrawn");

	// Category description
	public static final VirtualProperty<String> CATEGORY_DESCRIPTION = VirtualProperty.create(String.class,
			propertyBox -> propertyBox.getValueIfPresent(CATEGORY)
					// get the DatasetService as Context resource
					.map(category -> Context.get().resource(DatasetService.class)
							.orElseThrow(() -> new IllegalStateException("The DatasetService resource is missing"))
							// get the value description using DatasetService "getDescription" method
							.getDescription("CATEGORY", category))
					// if the value is not present return a default description
					.orElse("[No category]"));

	// Product property set with the ID property as identifier property
	public static final PropertySet<?> PRODUCT = PropertySet
			.builderOf(ID, DESCRIPTION, CATEGORY, CATEGORY_DESCRIPTION, UNIT_PRICE, WITHDRAWN).identifier(ID).build();

}
