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

import static com.holonplatform.example.model.MProduct.ID;
import static com.holonplatform.example.model.MProduct.PRODUCT;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.holonplatform.core.datastore.DataTarget;
import com.holonplatform.core.datastore.Datastore;
import com.holonplatform.core.property.PropertyBox;

/**
 * A simple in-memory products data store.
 */
@Service
public class ProductStore {

	// "products" table DataTarget
	private static final DataTarget<String> TARGET = DataTarget.named("products");

	@Autowired
	private Datastore datastore;

	public List<PropertyBox> getAll() {
		return datastore.query().target(TARGET).list(PRODUCT);
	}

	public Optional<PropertyBox> get(long id) {
		return datastore.query().target(TARGET).filter(ID.eq(id)).findOne(PRODUCT);
	}

	public void put(PropertyBox value) {
		datastore.save(TARGET, value);
	}

	public boolean remove(long id) {
		return datastore.bulkDelete(TARGET).filter(ID.eq(id)).execute().getAffectedCount() > 0;
	}

	public long nextId() {
		return datastore.query().target(TARGET).findOne(ID.max()).orElse(0L) + 1;
	}

}
