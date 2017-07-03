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

import java.time.LocalDateTime;

import com.holonplatform.core.i18n.Caption;
import com.holonplatform.core.property.PathProperty;
import com.holonplatform.core.property.PropertySet;

/**
 * Order model.
 */
public final class MOrder {

	public static enum Status {

		@Caption(value = "Draft", messageCode = "order.status.draft")
		DRAFT,

		@Caption(value = "Confirmed", messageCode = "order.status.confirmed")
		CONFIRMED,

		@Caption(value = "In progress", messageCode = "order.status.wip")
		IN_PROGRESS,

		@Caption(value = "Shipping", messageCode = "order.status.shipping")
		SHIPPING,

		@Caption(value = "Completed", messageCode = "order.status.completed")
		COMPLETED,

		@Caption(value = "Canceled", messageCode = "order.status.canceled")
		CANCELED;

	}

	public static final PathProperty<Long> ID = PathProperty.create("id", Long.class); // Order ID

	public static final PathProperty<Long> CUSTOMER_ID = PathProperty.create("customer_id", Long.class); // Customer ID

	public static final PathProperty<LocalDateTime> DATE_TIME = PathProperty.create("date", LocalDateTime.class);

	public static final PathProperty<Status> STATUS = PathProperty.create("status", Status.class);

	// Order property set
	public static final PropertySet<?> ORDER = PropertySet.of(ID, CUSTOMER_ID, DATE_TIME, STATUS);

	/*
	 * Model class intended to be used only as static fields container.
	 */
	private MOrder() {
	}

}
