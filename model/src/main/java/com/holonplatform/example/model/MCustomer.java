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

import com.holonplatform.core.Validator;
import com.holonplatform.core.i18n.Caption;
import com.holonplatform.core.property.PathProperty;
import com.holonplatform.core.property.PropertySet;
import com.holonplatform.core.property.VirtualProperty;

/**
 * Customer model.
 */
public final class MCustomer {

	public static enum Status {

		@Caption(value = "Active", messageCode = "customer.status.active")
		ACTIVE,

		@Caption(value = "Blocked", messageCode = "customer.status.blocked")
		BLOCKED,

		@Caption(value = "Canceled", messageCode = "customer.status.canceled")
		CANCELED;

	}

	public static final PathProperty<Long> ID = PathProperty.create("id", Long.class); // Customer ID

	public static final PathProperty<String> NAME = PathProperty.create("name", String.class) // Name
			.message("Name").messageCode("customer.name");

	public static final PathProperty<String> SURNAME = PathProperty.create("surname", String.class) // Surname
			.message("Surname").messageCode("customer.surname");

	public static final PathProperty<String> EMAIL = PathProperty.create("email", String.class) // E-mail
			.validator(Validator.email()).messageCode("E-mail").message("customer.email");

	public static final PathProperty<Status> STATUS = PathProperty.create("status", Status.class) // Status
			.message("Status").messageCode("customer.status");

	public static final VirtualProperty<String> FULL_NAME = VirtualProperty
			.create(String.class, // Name + surname
					propertyBox -> (propertyBox.getValueIfPresent(NAME).orElse("")
							+ propertyBox.getValueIfPresent(SURNAME).map(v -> " " + v).orElse("")).trim())
			.message("Name").messageCode("customer.fullname");

	public static final VirtualProperty<Boolean> IS_ACTIVE = VirtualProperty.create(Boolean.class, // Status == ACTIVE
			propertyBox -> propertyBox.getValueIfPresent(STATUS).map(status -> Status.ACTIVE.equals(status))
					.orElse(Boolean.FALSE));

	// Customer property set
	public static final PropertySet<?> CUSTOMER = PropertySet.of(ID, NAME, SURNAME, EMAIL, STATUS, FULL_NAME,
			IS_ACTIVE);

	/*
	 * Model class intended to be used only as static fields container.
	 */
	private MCustomer() {
	}

}
