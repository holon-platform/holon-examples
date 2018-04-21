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
package com.holonplatform.example.jaxrs.swagger.customers;

import com.holonplatform.core.Validator;
import com.holonplatform.core.i18n.Caption;
import com.holonplatform.core.property.NumericProperty;
import com.holonplatform.core.property.PathProperty;
import com.holonplatform.core.property.PropertySet;
import com.holonplatform.core.property.StringProperty;
import com.holonplatform.core.property.VirtualProperty;

/**
 * Customer property model.
 */
public interface Customer {

	public enum Status {

		@Caption(value = "Active", messageCode = "customer.status.active")
		ACTIVE,

		@Caption(value = "Blocked", messageCode = "customer.status.blocked")
		BLOCKED,

		@Caption(value = "Canceled", messageCode = "customer.status.canceled")
		CANCELED;

	}

	public static final NumericProperty<Long> ID = NumericProperty.longType("id").message("Customer ID")
			.messageCode("customer.id"); // Customer ID

	public static final StringProperty NAME = StringProperty.create("name") // Name
			.message("Name").messageCode("customer.name");

	public static final StringProperty SURNAME = StringProperty.create("surname") // Surname
			.message("Surname").messageCode("customer.surname");

	public static final StringProperty EMAIL = StringProperty.create("email") // E-mail
			.validator(Validator.email()).messageCode("E-mail").message("customer.email");

	public static final PathProperty<Status> STATUS = PathProperty.create("status", Status.class) // Status
			.message("Status").messageCode("customer.status");

	public static final VirtualProperty<String> FULL_NAME = VirtualProperty.create(String.class, // Name + surname
			propertyBox -> (propertyBox.getValueIfPresent(NAME).orElse("")
					+ propertyBox.getValueIfPresent(SURNAME).map(v -> " " + v).orElse("")).trim())
			.message("Name").messageCode("customer.fullname");

	public static final VirtualProperty<Boolean> IS_ACTIVE = VirtualProperty.create(Boolean.class, // Status == ACTIVE
			propertyBox -> propertyBox.getValueIfPresent(STATUS).map(status -> Status.ACTIVE.equals(status))
					.orElse(Boolean.FALSE));

	// Customer property set
	public static final PropertySet<?> CUSTOMER = PropertySet
			.builderOf(ID, NAME, SURNAME, EMAIL, STATUS, FULL_NAME, IS_ACTIVE).identifier(ID) // Customer identifier
			.build();

}
