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

import static com.holonplatform.example.ui.vaadin.app.model.Product.ID;
import static com.holonplatform.example.ui.vaadin.app.model.Product.PRODUCT;
import static com.holonplatform.example.ui.vaadin.app.model.Product.TARGET;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.holonplatform.core.datastore.Datastore;
import com.holonplatform.vaadin.components.Components;
import com.holonplatform.vaadin.components.PropertyListing;
import com.holonplatform.vaadin.navigator.ViewNavigator;
import com.holonplatform.vaadin.navigator.annotations.OnShow;
import com.holonplatform.vaadin.spring.DefaultView;
import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

@DefaultView // Declare this view as the navigator default view
@UIScope // "stateful" view
@SpringView(name = "home")
public class Home extends VerticalLayout implements View {

	private static final long serialVersionUID = 1L;

	@Autowired
	private Datastore datastore;

	private PropertyListing listing;

	@PostConstruct
	public void init() {
		Components.configure(this)
				// set full to view content
				.fullSize().spacing().add(Components.button().caption("Add new").styleName(ValoTheme.BUTTON_PRIMARY)
						// on click, navigate to "manage" view
						.onClick(e -> ViewNavigator.require().toView("manage").navigate()).build())
				// build and add listing
				.addAndExpandFull(listing = Components.listing.properties(PRODUCT)
						// setup listing data source using the Datastore with "products" as data target
						.dataSource(datastore, TARGET)
						// froze the ID column
						.frozenColumns(1)
						// set the ID column width and style
						.width(ID, 120).style(ID, "id-column")
						// when user clicks on a row, open the "view" View, providing the product "id" parameter
						.withItemClickListener((i, p, r, e) -> ViewNavigator.require().toView("view")
								.withParameter("id", i.getValue(ID)).navigate())
						// set full size and build
						.fullSize().build());
	}

	@OnShow
	public void onShow() {
		// refresh the listing at view display
		listing.refresh();
	}

}
