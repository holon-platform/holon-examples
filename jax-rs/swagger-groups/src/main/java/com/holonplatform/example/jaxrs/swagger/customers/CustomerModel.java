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
package com.holonplatform.example.jaxrs.swagger.customers;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.holonplatform.core.property.PropertySetRef;
import com.holonplatform.example.model.MCustomer;
import com.holonplatform.jaxrs.swagger.annotations.ApiPropertySetModel;

/*
 * Convenience annotation to combine the @PropertySetRef annotation to declare the customer property set and 
 * the @ApiPropertySetModel model annotation to create a Swagger model named "Customer" which corresponds to the 
 * customer property set.
 */
@Target({ ElementType.PARAMETER, ElementType.TYPE_USE })
@Retention(RetentionPolicy.RUNTIME)
@PropertySetRef(MCustomer.class)
@ApiPropertySetModel("Customer")
public @interface CustomerModel {

}
