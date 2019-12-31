/*
 * Copyright 2000-2019 Holon TDCN.
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
package com.holonplatform.example.datastore.mongo;

import com.holonplatform.core.Validator;
import com.holonplatform.core.datastore.DataTarget;
import com.holonplatform.core.property.BooleanProperty;
import com.holonplatform.core.property.NumericProperty;
import com.holonplatform.core.property.PropertySet;
import com.holonplatform.core.property.StringProperty;

/**
 * Product property model
 */
public interface Product {

	public static final StringProperty ID = StringProperty.create("_id");

	public static final StringProperty SKU = StringProperty.create("sku");

	public static final StringProperty DESCRIPTION = StringProperty.create("description");

	public static final StringProperty CATEGORY = StringProperty.create("category");

	public static final NumericProperty<Double> UNIT_PRICE = NumericProperty.doubleType("price")
			// not negative value validator
			.withValidator(Validator.notNegative());

	public static final BooleanProperty WITHDRAWN = BooleanProperty.create("withdrawn");

	// Product property set
	public static final PropertySet<?> PRODUCT = PropertySet
			.builderOf(ID, SKU, DESCRIPTION, CATEGORY, UNIT_PRICE, WITHDRAWN).withIdentifier(ID).build();

	// "products" DataTarget
	public static final DataTarget<?> TARGET = DataTarget.named("products");

}
