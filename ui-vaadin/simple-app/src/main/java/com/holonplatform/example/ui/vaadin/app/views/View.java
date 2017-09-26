/*
 * Copyright 2016-2017 Axioma srl.
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

import static com.holonplatform.example.ui.vaadin.app.model.Product.ID;
import static com.holonplatform.example.ui.vaadin.app.model.Product.PRODUCT;
import static com.holonplatform.example.ui.vaadin.app.model.Product.TARGET;

import org.springframework.beans.factory.annotation.Autowired;

import com.holonplatform.core.datastore.Datastore;
import com.holonplatform.core.exceptions.DataAccessException;
import com.holonplatform.core.i18n.Caption;
import com.holonplatform.vaadin.components.Components;
import com.holonplatform.vaadin.components.PropertyViewForm;
import com.holonplatform.vaadin.navigator.ViewNavigator;
import com.holonplatform.vaadin.navigator.annotations.OnShow;
import com.holonplatform.vaadin.navigator.annotations.ViewParameter;
import com.holonplatform.vaadin.navigator.annotations.WindowView;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

@WindowView(windowWidth = "500px", windowHeigth = "400px")
@Caption("View product")
@SpringView(name = "view")
public class View extends VerticalLayout implements com.vaadin.navigator.View {

	private static final long serialVersionUID = 1L;

	@ViewParameter
	private long id;

	@Autowired
	private Datastore datastore;

	private final PropertyViewForm viewForm;

	public View() {
		super();

		Components.configure(this)
				// set margins and size full to view content
				.margin().fullSize()
				// add view form using Product property set
				.addAndExpandFull(viewForm = Components.view.form().fullSize().properties(PRODUCT).build()).add(
						// horizontal layout as bottom toolbar
						Components.hl().fullWidth().spacing().styleName(ValoTheme.WINDOW_BOTTOM_TOOLBAR)
								// EDIT action
								.add(Components.button().caption("Edit").fullWidth().styleName(ValoTheme.BUTTON_PRIMARY)
										// navigate to "manage" view providing product id parameter
										.onClick(e -> ViewNavigator.require().toView("manage").withParameter("id", id)
												.navigate())
										.build())
								// DELETE action
								.add(Components.button().caption("Delete").fullWidth()
										.styleName(ValoTheme.BUTTON_DANGER)
										// ask confirmation before delete
										.onClick(e -> Components.questionDialog().message("Are you sure?")
												.callback(answeredYes -> {
													// if confirmed, delete the current form product PropertyBox
													datastore.delete(TARGET, viewForm.getValue());
													// navigate back
													ViewNavigator.require().navigateBack();
												}).open())
										.build())
								.build());
	}

	@OnShow
	public void onShow() {
		// set the view form product value
		viewForm.setValue(
				// load product using id parameter
				datastore.query().target(TARGET).filter(ID.eq(id)).findOne(PRODUCT)
						// throw an exception if not found
						.orElseThrow(() -> new DataAccessException("Product not found: " + id)));
	}

}
