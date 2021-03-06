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

import com.holonplatform.core.datastore.DataTarget;
import com.holonplatform.core.datastore.Datastore;
import com.holonplatform.example.ui.vaadin.app.model.Product;
import com.holonplatform.vaadin7.components.Components;
import com.holonplatform.vaadin7.components.PropertyListing;
import com.holonplatform.vaadin7.navigator.ViewNavigator;
import com.holonplatform.vaadin7.spring.DefaultView;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

@DefaultView
@UIScope
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
				.fullSize().spacing()
				.add(Components.button().caption("Add new").styleName(ValoTheme.BUTTON_PRIMARY)
						// navigate to "manage" view
						.onClick(e -> ViewNavigator.require().toView("manage").navigate()).build())
				// build and add listing
				.addAndExpandFull(listing = Components.listing.properties(Product.PRODUCT)
						// setup data source using Datastore with 'products' table name target and product ID as pk
						.dataSource(datastore, DataTarget.named("products"), Product.ID)
						// disable auto refresh: will be triggered on view enter
						.autoRefresh(false)
						// when user clicks on a row, open the 'view' named View providing product id parameter
						.withItemClickListener((i, p, e) -> ViewNavigator.require().toView("view")
								.withParameter("id", i.getValue(Product.ID)).navigate())
						// set full size and build
						.fullSize().build());
	}

	@Override
	public void enter(ViewChangeEvent event) {
		// refresh the listing contents
		listing.refresh();
	}

}
