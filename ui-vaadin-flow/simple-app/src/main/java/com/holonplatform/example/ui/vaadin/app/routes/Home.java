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

import static com.holonplatform.example.ui.vaadin.app.model.Product.ID;
import static com.holonplatform.example.ui.vaadin.app.model.Product.PRODUCT;
import static com.holonplatform.example.ui.vaadin.app.model.Product.TARGET;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.holonplatform.core.datastore.Datastore;
import com.holonplatform.vaadin.flow.components.Components;
import com.holonplatform.vaadin.flow.components.PropertyListing;
import com.holonplatform.vaadin.flow.navigator.Navigator;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route("")
@PageTitle("Products")
public class Home extends VerticalLayout {

	private static final long serialVersionUID = 1L;

	@Autowired
	private Datastore datastore;

	private PropertyListing listing;

	@PostConstruct
	public void init() {
		// configure the layout
		Components.configure(this).fullSize()
				// add new button
				.add(Components.button().text("Add new")
						// on click, navigate to "manage" view
						.onClick(e -> Navigator.get().navigateTo("manage")).build())
				// build and add listing, setting the flex grow ratio to 1
				.addAndExpand(listing = Components.listing.properties(PRODUCT).fullWidth()
						// setup listing data source using the Datastore with "products" as data target
						.dataSource(datastore, TARGET)
						// froze the Actions and ID columns
						.frozenColumns(2)
						// when user clicks on a row, route to Manage, providing the product "id" parameter
						.withItemClickListener(event -> {
							Navigator.get().navigation(Manage.class)
									.withQueryParameter("id", event.getItem().getValue(ID)).navigate();
						})
						// add a Actions column as first with a "Delete" Button
						.withComponentColumn(item -> Components.button().text("Delete").icon(VaadinIcon.TRASH)
								.onClick(event -> delete(item.getValue(ID))).build())
						.header("Actions").displayAsFirst().add()
						// build
						.build(), 1);
	}

	private void delete(Long productId) {
		// ask confirmation
		Components.dialog.question(answeredYes -> {
			if (answeredYes) {
				// if confirmed, delete the product
				datastore.bulkDelete(TARGET).filter(ID.eq(productId)).execute();
				// refresh list
				listing.refresh();
			}
		}).text("Are you sure?").open();
	}

}
