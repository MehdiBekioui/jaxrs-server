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
package com.bekioui.jaxrs.server.impl.resource;

import io.swagger.jaxrs.Reader;
import io.swagger.jaxrs.config.DefaultReaderConfig;
import io.swagger.models.Swagger;

import java.util.Arrays;
import java.util.Set;

import javax.ws.rs.core.Response;

import com.bekioui.jaxrs.server.api.resource.SwaggerResource;

public final class SwaggerResourceImpl implements SwaggerResource {

	private Swagger swagger;

	public SwaggerResourceImpl(Swagger swagger, Set<Class<?>> classes) {
		DefaultReaderConfig defaultReaderConfig = new DefaultReaderConfig();
		defaultReaderConfig.setScanAllResources(true);
		defaultReaderConfig.setIgnoredRoutes(Arrays.asList(new String[] { PATH }));
		this.swagger = new Reader(swagger, defaultReaderConfig).read(classes);
	}

	@Override
	public Response get() {
		return Response.ok().entity(swagger).build();
	}

}
