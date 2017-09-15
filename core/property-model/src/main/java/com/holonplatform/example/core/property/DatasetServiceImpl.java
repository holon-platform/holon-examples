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

public class DatasetServiceImpl implements DatasetService {

	@Override
	public String getDescription(String datasetName, String value) {
		// here it should be written the "real" logic to retrieve the value description, maybe from a data source
		if ("CATEGORY".equals(datasetName)) {
			return "Category " + value;
		}
		return null;
	}

}
