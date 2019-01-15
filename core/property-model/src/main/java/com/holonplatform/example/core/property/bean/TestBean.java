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
package com.holonplatform.example.core.property.bean;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import com.holonplatform.core.beans.Config;
import com.holonplatform.core.i18n.Caption;

public class TestBean {

	@NotNull
	@Caption(value = "Product ID", messageCode = "product.id")
	private Long id;

	@Max(500)
	@Caption(value = "Description", messageCode = "product.description")
	private String description;

	@Config(key = "DATASET", value = "CATEGORY")
	@Caption(value = "Category", messageCode = "product.category")
	private String category;

	@PositiveOrZero
	@Caption(value = "Price", messageCode = "product.price")
	private Double unitPrice;

	@Caption(value = "Withdrawn", messageCode = "product.withdrawn")
	private boolean withdrawn;

	public TestBean() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Double getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}

	public boolean isWithdrawn() {
		return withdrawn;
	}

	public void setWithdrawn(boolean withdrawn) {
		this.withdrawn = withdrawn;
	}

}
