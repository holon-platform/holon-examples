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
package com.holonplatform.example.core.property;

public interface DatasetService {

	/**
	 * Get the dataset value description for given dataset <code>name</code>.
	 * @param datasetName Dataset name to which the value belongs
	 * @param value The value for which to obtain the description
	 * @return The description of the value
	 */
	String getDescription(String datasetName, String value);

}
