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

import java.util.Arrays;
import java.util.function.Supplier;

import org.jboss.resteasy.plugins.interceptors.CorsFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.google.common.base.Strings;

@Component
public final class CorsFilterSupplier implements Supplier<CorsFilter> {

	@Value("${jaxrs.server.cors.allowCredentials:}")
	private Boolean allowCredentials;

	@Value("${jaxrs.server.cors.allowedHeaders:}")
	private String allowedHeaders;

	@Value("${jaxrs.server.cors.allowedMethods:}")
	private String allowedMethods;

	@Value("${jaxrs.server.cors.maxAge:}")
	private Integer maxAge;

	@Value("${jaxrs.server.cors.exposedHeaders:}")
	private String exposedHeaders;

	@Value("${jaxrs.server.cors.allowedOrigins:}")
	private String[] allowedOrigins;

	private final Supplier<CorsFilter> supplier = () -> {
		CorsFilter corsFilter = new CorsFilter();

		if (allowCredentials != null) {
			corsFilter.setAllowCredentials(allowCredentials);
		}

		if (!Strings.isNullOrEmpty(allowedHeaders)) {
			corsFilter.setAllowedHeaders(allowedHeaders);
		}

		if (!Strings.isNullOrEmpty(allowedMethods)) {
			corsFilter.setAllowedMethods(allowedMethods);
		}

		if (maxAge != null) {
			corsFilter.setCorsMaxAge(maxAge);
		}

		if (!Strings.isNullOrEmpty(exposedHeaders)) {
			corsFilter.setExposedHeaders(exposedHeaders);
		}

		if (allowedOrigins.length > 0) {
			corsFilter.getAllowedOrigins().addAll(Arrays.asList(allowedOrigins));
		}

		return corsFilter;
	};

	@Override
	public CorsFilter get() {
		return supplier.get();
	}

}
