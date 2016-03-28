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
package com.bekioui.jaxrs.server.impl.filter;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;

import org.jboss.resteasy.plugins.interceptors.CorsFilter;

public final class CorsFilterImpl implements com.bekioui.jaxrs.server.api.filter.CorsFilter {

	private CorsFilter corsFilter;

	public CorsFilterImpl(CorsFilter corsFilter) {
		this.corsFilter = corsFilter;
	}

	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		corsFilter.filter(requestContext);
	}

	@Override
	public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {
		corsFilter.filter(requestContext, responseContext);
	}

}
