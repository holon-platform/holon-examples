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
package com.holonplatform.example.jaxrs.springboot.datastore;

import com.holonplatform.core.Validator;
import com.holonplatform.core.datastore.DataTarget;
import com.holonplatform.core.property.PathProperty;
import com.holonplatform.core.property.PropertySet;

/**
 * Product model
 */
public final class Product {

	public static final PathProperty<Long> ID = PathProperty.create("id", Long.class);

	public static final PathProperty<String> SKU = PathProperty.create("sku", String.class);

	public static final PathProperty<String> DESCRIPTION = PathProperty.create("description", String.class);

	public static final PathProperty<String> CATEGORY = PathProperty.create("category", String.class);

	public static final PathProperty<Double> UNIT_PRICE = PathProperty.create("price", Double.class)
			// not negative value validator
			.validator(Validator.notNegative());

	// Product property set
	public static final PropertySet<?> PRODUCT = PropertySet.of(ID, SKU, DESCRIPTION, CATEGORY, UNIT_PRICE);

	// "products" table DataTarget
	public static final DataTarget<String> TARGET = DataTarget.named("products");

	/*
	 * Model class intended to be used only as static fields container.
	 */
	private Product() {
	}

}
