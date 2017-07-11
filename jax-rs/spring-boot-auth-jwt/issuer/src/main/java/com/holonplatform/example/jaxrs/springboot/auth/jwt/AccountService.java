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
package com.holonplatform.example.jaxrs.springboot.auth.jwt;

import static com.holonplatform.core.property.PathProperty.create;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.holonplatform.auth.Account;
import com.holonplatform.auth.Account.AccountProvider;
import com.holonplatform.auth.Credentials;
import com.holonplatform.core.datastore.DataTarget;
import com.holonplatform.core.datastore.Datastore;
import com.holonplatform.core.property.PathProperty;
import com.holonplatform.core.property.PropertyBox;
import com.holonplatform.core.property.PropertySet;
import com.holonplatform.core.property.PropertyValueConverter;

@Service
public class AccountService implements AccountProvider {

	public static final PathProperty<String> ACCOUNT_ID = create("id", String.class);
	public static final PathProperty<String> ACCOUNT_SECRET = create("secret", String.class);
	public static final PathProperty<String> ACCOUNT_NAME = create("name", String.class);
	public static final PathProperty<Boolean> ACCOUNT_LOCKED = create("locked", boolean.class)
			.converter(PropertyValueConverter.numericBoolean(int.class));

	public static final PathProperty<String> ROLE_ACCOUNT_ID = create("account_id", String.class);
	public static final PathProperty<String> ROLE_ID = create("role", String.class);

	@Autowired
	private Datastore datastore;

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.auth.Account.AccountProvider#loadAccountById(java.lang.String)
	 */
	@Override
	public Optional<Account> loadAccountById(String id) {
		return datastore.query().target(DataTarget.named("accounts")).filter(ACCOUNT_ID.eq(id))
				.findOne(PropertySet.of(ACCOUNT_ID, ACCOUNT_SECRET, ACCOUNT_NAME, ACCOUNT_LOCKED))
				.map((r) -> convert(r));
	}

	/*
	 * Convert account data into an Account instance, retrieving roles from the 'permissions' table
	 */
	private Account convert(PropertyBox act) {
		return Account.builder(act.getValue(ACCOUNT_ID))
				// set credentials (hashed with SHA-256 and Base64 encoded)
				.credentials(Credentials.builder().secret(act.getValue(ACCOUNT_SECRET))
						.hashAlgorithm(Credentials.Encoder.HASH_SHA_256).base64Encoded().build())
				// configuration
				.enabled(true).locked(act.getValue(ACCOUNT_LOCKED))
				// add details
				.detail("name", act.getValue(ACCOUNT_NAME))
				// load permissions
				.permissionStrings(datastore.query().target(DataTarget.named("account_roles"))
						.filter(ROLE_ACCOUNT_ID.eq(act.getValue(ACCOUNT_ID))).list(ROLE_ID))
				.build();
	}

}
