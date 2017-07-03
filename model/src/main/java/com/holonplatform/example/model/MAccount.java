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
import com.holonplatform.core.property.PathProperty;
import com.holonplatform.core.property.PropertySet;
import com.holonplatform.core.property.PropertyValueConverter;

/**
 * Account model.
 */
public final class MAccount {

	public static final PathProperty<String> ID = PathProperty.create("id", String.class) // Account id
			.validator(Validator.max(100)); // limit to 100 chars

	public static final PathProperty<String> SECRET = PathProperty.create("secret", String.class); // Account secret

	public static final PathProperty<String> DESCRIPTION = PathProperty.create("description", String.class);

	public static final PathProperty<LocalDate> EXPIRY_DATE = PathProperty.create("expiry", LocalDate.class);

	public static final PathProperty<Boolean> ENABLED = PathProperty.create("enabled", Boolean.class)
			.converter(PropertyValueConverter.numericBoolean(Integer.class)); // Converted to boolean from an integer
																				// model type

	// Account property set
	public static final PropertySet<?> ACCOUNT = PropertySet.of(ID, SECRET, DESCRIPTION, EXPIRY_DATE, ENABLED);

	/*
	 * Model class intended to be used only as static fields container.
	 */
	private MAccount() {
	}

}
