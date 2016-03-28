/**
 * Copyright (C) 2016 Mehdi Bekioui (consulting@bekioui.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.bekioui.jaxrs.server.core.supplier;

import io.swagger.models.Contact;
import io.swagger.models.Info;
import io.swagger.models.License;
import io.swagger.models.Scheme;
import io.swagger.models.Swagger;

import java.util.Arrays;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public final class SwaggerSupplier implements Supplier<Swagger> {

	@Value("${netty.jaxrs.swagger.host:localhost:8080}")
	private String host;

	@Value("${netty.jaxrs.swagger.info.contact.email:}")
	private String contactEmail;

	@Value("${netty.jaxrs.swagger.info.contact.name:}")
	private String contactName;

	@Value("${netty.jaxrs.swagger.info.contact.url:}")
	private String contactUrl;

	@Value("${netty.jaxrs.swagger.info.description:}")
	private String description;

	@Value("${netty.jaxrs.swagger.info.license.name:}")
	private String licenseName;

	@Value("${netty.jaxrs.swagger.info.license.url:}")
	private String licenseUrl;

	@Value("${netty.jaxrs.swagger.info.license.vendorExtensions.name:}")
	private String licenseVendorExtensionsName;

	@Value("${netty.jaxrs.swagger.info.license.vendorExtensions.object:}")
	private String licenseVendorExtensionsObject;

	@Value("${netty.jaxrs.swagger.info.termsOfService:}")
	private String termsOfService;

	@Value("${netty.jaxrs.swagger.info.title:}")
	private String title;

	@Value("${netty.jaxrs.swagger.info.vendorExtensions.name:}")
	private String infoVendorExtensionsName;

	@Value("${netty.jaxrs.swagger.info.vendorExtensions.object:}")
	private String infoVendorExtensionsObject;

	@Value("${netty.jaxrs.swagger.info.version:}")
	private String version;

	private final Supplier<Swagger> supplier = () -> {
		Swagger swagger = new Swagger();
		swagger.setSchemes(Arrays.asList(Scheme.HTTP));
		swagger.setHost(host);

		Contact contact = new Contact();
		contact.setEmail(contactEmail);
		contact.setName(contactName);
		contact.setUrl(contactUrl);

		License license = new License();
		license.setName(licenseName);
		license.setUrl(licenseUrl);
		license.setVendorExtension(licenseVendorExtensionsName, licenseVendorExtensionsObject);

		Info info = new Info();
		info.setContact(contact);
		info.setDescription(description);
		info.setLicense(license);
		info.setTermsOfService(termsOfService);
		info.setTitle(title);
		info.setVendorExtension(infoVendorExtensionsName, infoVendorExtensionsObject);
		info.setVersion(version);

		swagger.setInfo(info);

		return swagger;
	};

	@Override
	public Swagger get() {
		return supplier.get();
	}

}
