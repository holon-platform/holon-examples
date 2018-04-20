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
package com.holonplatform.example.model;

import java.time.LocalDate;

import com.holonplatform.core.Validator;
import com.holonplatform.core.property.BooleanProperty;
import com.holonplatform.core.property.PropertySet;
import com.holonplatform.core.property.PropertyValueConverter;
import com.holonplatform.core.property.StringProperty;
import com.holonplatform.core.property.TemporalProperty;

/**
 * Account property model.
 */
public interface Account {

	public static final StringProperty ID = StringProperty.create("id") // Account id
			.validator(Validator.max(100)); // limit to 100 chars

	public static final StringProperty SECRET = StringProperty.create("secret"); // Account secret

	public static final StringProperty DESCRIPTION = StringProperty.create("description");

	public static final TemporalProperty<LocalDate> EXPIRY_DATE = TemporalProperty.localDate("expiry");

	public static final BooleanProperty ENABLED = BooleanProperty.create("enabled")
			.converter(PropertyValueConverter.numericBoolean(Integer.class)); // Converted to boolean from an integer
																				// model type

	// Account property set
	public static final PropertySet<?> ACCOUNT = PropertySet.builderOf(ID, SECRET, DESCRIPTION, EXPIRY_DATE, ENABLED)
			.identifier(ID) // Account identifier
			.build();

}
