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
package com.holonplatform.example.core.property.test;

import static com.holonplatform.example.core.property.model.MProduct.CATEGORY;
import static com.holonplatform.example.core.property.model.MProduct.CATEGORY_DESCRIPTION;
import static com.holonplatform.example.core.property.model.MProduct.DESCRIPTION;
import static com.holonplatform.example.core.property.model.MProduct.ID;
import static com.holonplatform.example.core.property.model.MProduct.PRODUCT;
import static com.holonplatform.example.core.property.model.MProduct.UNIT_PRICE;
import static com.holonplatform.example.core.property.model.MProduct.WITHDRAWN;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Locale;
import java.util.Optional;

import org.junit.BeforeClass;
import org.junit.Test;

import com.holonplatform.core.Context;
import com.holonplatform.core.Validator.ValidationException;
import com.holonplatform.core.i18n.LocalizationContext;
import com.holonplatform.core.internal.utils.TestUtils;
import com.holonplatform.core.property.PropertyBox;
import com.holonplatform.core.property.PropertySet;
import com.holonplatform.core.property.PropertyValuePresenterRegistry;
import com.holonplatform.example.core.property.support.DatasetService;
import com.holonplatform.example.core.property.support.DatasetServiceImpl;
import com.holonplatform.example.core.property.support.WithdrawnPropertyPresenter;

public class TestPropertyModel {

	@BeforeClass
	public static void init() {

		// create a LocalizationContext with a MessageProvider which never returns a translation
		LocalizationContext ctx = LocalizationContext.builder().messageProvider((locale, code) -> Optional.empty())
				.withInitialLocale(Locale.US).build();
		// register it as a ClassLoader-scoped resource
		Context.get().classLoaderScope().ifPresent(scope -> scope.put(LocalizationContext.CONTEXT_KEY, ctx));

		// register the DatasetService as a ClassLoader-scoped resource
		Context.get().classLoaderScope()
				.ifPresent(scope -> scope.put(DatasetService.class.getName(), new DatasetServiceImpl()));
	}

	@Test
	public void propertySet() {

		// check all expected properties are in the set
		assertTrue(PRODUCT.contains(ID));
		assertTrue(PRODUCT.contains(DESCRIPTION));
		assertTrue(PRODUCT.contains(CATEGORY));
		assertTrue(PRODUCT.contains(UNIT_PRICE));
		assertTrue(PRODUCT.contains(WITHDRAWN));

		assertTrue(PRODUCT.contains(CATEGORY_DESCRIPTION));

		// get the properties as a Stream
		long propertyCount = PRODUCT.stream().count();

		assertEquals(6, propertyCount);

		// get the properties as a List
		propertyCount = PRODUCT.asList().size();

		assertEquals(6, propertyCount);

	}

	@Test
	public void propertyCaptions() {

		String caption = ID.getMessage();

		assertEquals("Product ID", caption);

		String translationMessageCode = ID.getMessageCode();

		assertEquals("product.id", translationMessageCode);

		// If a localized LocalizationContext is available, get the localized property caption
		String localizedCaption = LocalizationContext.getCurrent()
				.map(localizationContext -> localizationContext.getMessage(ID))
				.orElse("No LocalizationContext available");

		assertEquals("Product ID", localizedCaption);

		// It is equivalent to:
		localizedCaption = LocalizationContext.translate(ID);

	}

	@Test
	public void propertyBox() {

		// Create a PropertyBox and set the property values
		PropertyBox box = PropertyBox.create(PRODUCT);
		box.setValue(ID, 1L);
		box.setValue(DESCRIPTION, "The product 1");
		box.setValue(CATEGORY, "C1");
		box.setValue(UNIT_PRICE, 12.77);
		box.setValue(WITHDRAWN, false);

		assertEquals(Long.valueOf(1), box.getValue(ID));
		assertEquals("The product 1", box.getValue(DESCRIPTION));
		assertEquals("C1", box.getValue(CATEGORY));
		assertEquals(Double.valueOf(12.77), box.getValue(UNIT_PRICE));

		// Get the WITHDRAWN property value using an Optional
		assertFalse(box.getValueIfPresent(WITHDRAWN).orElse(Boolean.FALSE));

		// Check the PropertyBox contains the ID property with a not null value
		assertTrue(box.containsValue(ID));

		// Clone the PropertyBox
		PropertyBox cloned = box.cloneBox();
		assertEquals(Long.valueOf(1), cloned.getValue(ID));

		// Clone the PropertyBox using a different PropertySet
		cloned = box.cloneBox(PropertySet.of(ID, DESCRIPTION));
		assertFalse(cloned.containsValue(CATEGORY));

	}

	@Test
	public void propertyValidation() {

		// Expect the validation fails for a NULL value
		TestUtils.expectedException(ValidationException.class, () -> ID.validate(null));

		// Expect the validation succeeds
		ID.validate(1L);

		PropertyBox box = PropertyBox.create(PRODUCT);
		box.setValue(ID, 1L);

		// Validate the PropertyBox (including the ID property)
		box.validate();

	}

	@Test
	public void virtualProperty() {

		// Create a PropertyBox
		PropertyBox box = PropertyBox.builder(PRODUCT).set(ID, 1L).set(CATEGORY, "C1").build();

		// Get the CATEGORY_DESCRIPTION virtual property value using the current PropertyBox values
		String categoryDescription = box.getValue(CATEGORY_DESCRIPTION);

		assertEquals("Category C1", categoryDescription);
	}

	@Test
	public void propertyPresenter() {

		// Default property presentation
		String presentation = WITHDRAWN.present(Boolean.TRUE);
		assertEquals("true", presentation);

		// Register the WithdrawnPropertyPresenter bound to the WITHDRAWN property
		PropertyValuePresenterRegistry.get().register(p -> WITHDRAWN.equals(p), new WithdrawnPropertyPresenter());

		presentation = WITHDRAWN.present(Boolean.TRUE);
		assertEquals("The product was withdrawn", presentation);

		// presentation using a PropertyBox
		PropertyBox box = PropertyBox.builder(PRODUCT).set(ID, 1L).set(WITHDRAWN, true).build();

		presentation = box.present(WITHDRAWN);
		assertEquals("The product was withdrawn", presentation);

	}

	@Test
	public void propertyPresenter2() {

		// presentation using a PropertyBox
		PropertyBox box = PropertyBox.builder(PRODUCT).set(ID, 1L).set(CATEGORY, "C1").build();

		// present the CATEGORY property value
		String presentation = box.present(CATEGORY);
		assertEquals("C1", presentation);

		// Register a new presenter
		PropertyValuePresenterRegistry.get().register(
				// Condition: the property has a "DATASET" named configuration parameter with a non null value
				p -> p.getConfiguration().hasNotNullParameter("DATASET"),
				// Presenter: use the DatasetService (retrieved from Context) to obtain the value description
				(p, v) -> {
					return Context.get().resource(DatasetService.class)
							.orElseThrow(() -> new IllegalStateException("The DatasetService resource is missing"))
							// get the value description using DatasetService "getDescription" method
							.getDescription(p.getConfiguration().getParameter("DATASET", String.class).orElse(null),
									(String) v);
				});

		// present the CATEGORY property value: the new presenter will be used
		presentation = box.present(CATEGORY);
		assertEquals("Category C1", presentation);

	}
	
	@Test
	public void beanProperties() {
		
	}

}
