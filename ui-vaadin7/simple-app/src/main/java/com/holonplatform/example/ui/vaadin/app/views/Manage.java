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
package com.holonplatform.example.ui.vaadin.app.views;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.holonplatform.core.Validator;
import com.holonplatform.core.datastore.DataTarget;
import com.holonplatform.core.datastore.Datastore;
import com.holonplatform.core.exceptions.DataAccessException;
import com.holonplatform.example.model.MProduct;
import com.holonplatform.vaadin.components.Components;
import com.holonplatform.vaadin.components.PropertyInputForm;
import com.holonplatform.vaadin.navigator.ViewNavigator;
import com.holonplatform.vaadin.navigator.annotations.ViewParameter;
import com.holonplatform.vaadin.navigator.annotations.VolatileView;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

@VolatileView
@SpringView(name = "manage")
public class Manage extends VerticalLayout implements com.vaadin.navigator.View {

	private static final long serialVersionUID = 1L;

	@ViewParameter
	private Long id;

	@Autowired
	private Datastore datastore;

	private PropertyInputForm form;

	@PostConstruct
	public void init() {
		Components.configure(this)
				// set margins and size full to view content
				.margin().fullSize()
				.addAndExpandFull(
						// add a form using Product property set
						form = Components.input.form().fullSize().properties(MProduct.PRODUCT)
								// set id as read-only
								.readOnly(MProduct.ID)
								// set SKU as required
								.required(MProduct.SKU)
								// set "DFT" as CATEGORY default value
								.defaultValue(MProduct.CATEGORY, p -> "DFT")
								// add a validator to check DESCRIPTION with minimum 3 characters
								.withValidator(MProduct.DESCRIPTION, Validator.min(3))
								// .initializer(c -> Components.configure(c).margin().spacing())
								.build())
				.add(Components.hl().margin().spacing()
						// SAVE action
						.add(Components.button().caption("Save").styleName(ValoTheme.BUTTON_PRIMARY)
								.onClick(e -> save()).build())
						// CLEAR action
						.add(Components.button().caption("Clear")
								// clear the form
								.onClick(e -> form.clear()).build())
						.build());
	}

	@Override
	public void enter(ViewChangeEvent event) {
		// if id parameter is not null, we are in edit mode
		if (id != null) {
			// load the product data
			form.setValue(datastore.query().target(DataTarget.named("products")).filter(MProduct.ID.eq(id))
					.findOne(MProduct.PRODUCT)
					// throw an exception if a product with given id was not found
					.orElseThrow(() -> new DataAccessException("Data not found: " + id)));
		}
	}

	@Transactional
	private void save() {
		// check valid and get PropertyBox value
		form.getValueIfValid().ifPresent(value -> {

			if (id == null) {
				// if "insert" mode, generate next key: the current max + 1
				value.setValue(MProduct.ID, datastore.query().target(DataTarget.named("products"))
						.findOne(MProduct.ID.max()).map((v) -> v + 1).orElse(1L));
			}

			// save and notify
			datastore.save(DataTarget.named("products"), value);
			Notification.show("Saved [" + value.getValue(MProduct.ID) + "]", Type.TRAY_NOTIFICATION);

			// go back home
			ViewNavigator.require().navigateToDefault();
		});

	}

}
