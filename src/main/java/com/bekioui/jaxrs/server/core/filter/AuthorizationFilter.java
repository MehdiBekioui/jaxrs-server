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
package com.bekioui.jaxrs.server.core.filter;

import static javax.ws.rs.core.HttpHeaders.AUTHORIZATION;

import java.io.IOException;
import java.security.Principal;
import java.util.Objects;

import javax.annotation.PostConstruct;
import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.ext.Provider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.bekioui.jaxrs.server.api.authorization.AuthorizationContext;
import com.bekioui.jaxrs.server.api.authorization.AuthorizationDeserializer;

@Component
@Provider
@PreMatching
@Priority(Priorities.AUTHORIZATION)
public final class AuthorizationFilter implements ContainerRequestFilter {

	@Value("${jaxrs.server.authorization.enabled:false}")
	private boolean authorizationEnabled;

	@Value("${jaxrs.server.security.enabled:false}")
	private boolean securityEnabled;

	@Autowired(required = false)
	private AuthorizationDeserializer authorizationMapper;

	@PostConstruct
	private void postConstruct() {
		if (authorizationEnabled) {
			if (!securityEnabled) {
				throw new RuntimeException("Security is not enabled.");
			}
			Objects.requireNonNull(authorizationMapper);
		}
	}

	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		String authorization = requestContext.getHeaderString(AUTHORIZATION);

		if (authorization != null) {
			AuthorizationContext authorizationContext = authorizationMapper.deserialize(authorization);
			if (authorizationContext != null) {
				requestContext.setSecurityContext(new AuthorizedSecurityContext(authorizationContext));
			}
		}
	}

	private class AuthorizedSecurityContext implements SecurityContext {

		private final AuthorizationContext authorizationContext;

		public AuthorizedSecurityContext(AuthorizationContext authorizationContext) {
			this.authorizationContext = authorizationContext;
		}

		@Override
		public Principal getUserPrincipal() {
			return authorizationContext.getPrincipal();
		}

		@Override
		public boolean isUserInRole(String role) {
			return authorizationContext.getRoles().contains(role);
		}

		@Override
		public boolean isSecure() {
			return false;
		}

		@Override
		public String getAuthenticationScheme() {
			return SecurityContext.FORM_AUTH;
		}

	}

}
