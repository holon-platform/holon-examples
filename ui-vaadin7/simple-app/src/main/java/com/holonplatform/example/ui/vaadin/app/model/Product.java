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
package com.holonplatform.example.ui.vaadin.app.model;

import com.holonplatform.core.Validator;
import com.holonplatform.core.datastore.DataTarget;
import com.holonplatform.core.property.BooleanProperty;
import com.holonplatform.core.property.NumericProperty;
import com.holonplatform.core.property.Property;
import com.holonplatform.core.property.PropertySet;
import com.holonplatform.core.property.PropertyValueConverter;
import com.holonplatform.core.property.StringProperty;

/**
 * Product model
 */
public interface Product {

	public static final NumericProperty<Long> ID = NumericProperty.longType("id").message("Product ID");

	public static final StringProperty SKU = StringProperty.create("sku").message("SKU");

	public static final StringProperty DESCRIPTION = StringProperty.create("description").message("Description");

	public static final StringProperty CATEGORY = StringProperty.create("category").message("Category");

	public static final NumericProperty<Double> UNIT_PRICE = NumericProperty.doubleType("price").message("Price")
			// not negative value validator
			.validator(Validator.notNegative());

	public static final BooleanProperty WITHDRAWN = BooleanProperty.create("withdrawn")
			.message("Withdrawn")
			// set a property value converter from Integer model type to Boolean
			.converter(PropertyValueConverter.numericBoolean(Integer.class));

	// Product property set
	public static final PropertySet<Property<?>> PRODUCT = PropertySet
			.builderOf(ID, SKU, DESCRIPTION, CATEGORY, UNIT_PRICE, WITHDRAWN) //
			.identifier(ID) // Set the ID property as identifier
			.build();

	// "products" DataTarget
	public static final DataTarget<?> TARGET = DataTarget.named("products");

}
