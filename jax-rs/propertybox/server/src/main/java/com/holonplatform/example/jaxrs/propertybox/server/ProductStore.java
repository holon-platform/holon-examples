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
package com.holonplatform.example.jaxrs.propertybox.server;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.holonplatform.core.property.PropertyBox;
import com.holonplatform.example.model.MProduct;

/**
 * A simple in-memory products data store.
 */
public enum ProductStore {

	INSTANCE;

	private final Map<Long, PropertyBox> DATA = new LinkedHashMap<>();

	public List<PropertyBox> getAll() {
		return new ArrayList<>(DATA.values());
	}

	public Optional<PropertyBox> get(long id) {
		return Optional.ofNullable(DATA.get(id));
	}

	public void put(PropertyBox value) {
		if (value != null) {
			value.getValueIfPresent(MProduct.ID).ifPresent(id -> DATA.put(id, value));
		}
	}

	public boolean remove(long id) {
		return DATA.remove(id) != null;
	}
	
	public long nextId() {
		return DATA.keySet().stream().mapToLong(Long::longValue).max().orElse(0) + 1;
	}

}
