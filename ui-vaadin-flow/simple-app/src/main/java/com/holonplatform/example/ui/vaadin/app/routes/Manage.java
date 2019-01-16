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
package com.holonplatform.example.ui.vaadin.app.routes;

import static com.holonplatform.example.ui.vaadin.app.model.Product.CATEGORY;
import static com.holonplatform.example.ui.vaadin.app.model.Product.DESCRIPTION;
import static com.holonplatform.example.ui.vaadin.app.model.Product.ID;
import static com.holonplatform.example.ui.vaadin.app.model.Product.PRODUCT;
import static com.holonplatform.example.ui.vaadin.app.model.Product.SKU;
import static com.holonplatform.example.ui.vaadin.app.model.Product.TARGET;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.holonplatform.core.Validator;
import com.holonplatform.core.datastore.Datastore;
import com.holonplatform.core.datastore.DefaultWriteOption;
import com.holonplatform.core.exceptions.DataAccessException;
import com.holonplatform.vaadin.flow.components.Components;
import com.holonplatform.vaadin.flow.components.PropertyInputForm;
import com.holonplatform.vaadin.flow.navigator.Navigator;
import com.holonplatform.vaadin.flow.navigator.annotations.OnShow;
import com.holonplatform.vaadin.flow.navigator.annotations.QueryParameter;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route("manage")
@PageTitle("Edit product")
public class Manage extends VerticalLayout {

	private static final long serialVersionUID = 1L;

	@QueryParameter
	private Long id;

	@Autowired
	private Datastore datastore;

	private PropertyInputForm form;

	private Button clearButton;

	@PostConstruct
	public void init() {
		Components.configure(this)
				// add a PropertyInputForm using the Product property set
				.add(form = Components.input.form(PRODUCT)
						// set ID as read-only
						.readOnly(ID)
						// set SKU as required
						.required(SKU)
						// set "DFT" as CATEGORY default value
						.defaultValue(CATEGORY, () -> "DFT")
						// add a validator to check that DESCRIPTION has minimum 3 characters
						.withValidator(DESCRIPTION, Validator.min(3))
						// build the form
						.build())
				.add(Components.hl().margin().spacing()
						// SAVE action
						.add(Components.button().text("Save").withThemeVariants(ButtonVariant.LUMO_PRIMARY)
								.onClick(e -> save()).build())
						// CLEAR action
						.add(clearButton = Components.button().text("Clear").onClick(e -> form.clear()).build())
						.build());
	}

	@OnShow
	public void load() {
		// if the "id" parameter is not null, we are in edit mode
		if (id != null) {
			// load the product data
			form.setValue(datastore.query().target(TARGET).filter(ID.eq(id)).findOne(PRODUCT)
					// throw an exception if a product with given id was not found
					.orElseThrow(() -> new DataAccessException("Product not found: " + id)));
		}
		// enable the Clear button if not in edit mode
		clearButton.setVisible(id == null);
	}

	private void save() {
		// check valid and get PropertyBox value
		form.getValueIfValid().ifPresent(value -> {
			// save the product data
			datastore.save(TARGET, value, DefaultWriteOption.BRING_BACK_GENERATED_IDS);
			// notify the saved product id
			Notification.show("Product saved [" + ((id != null) ? id : value.getValue(ID)) + "]");
			// go back home
			Navigator.get().navigateToDefault();
		});

	}

}
