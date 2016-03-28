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
package com.bekioui.jaxrs.server.core;

import io.swagger.jaxrs.listing.SwaggerSerializers;
import io.swagger.models.Swagger;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ws.rs.Path;
import javax.ws.rs.ext.Provider;

import org.jboss.resteasy.plugins.interceptors.CorsFilter;
import org.jboss.resteasy.plugins.server.netty.NettyJaxrsServer;
import org.jboss.resteasy.spi.ResteasyDeployment;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.bekioui.jaxrs.server.api.ResourceDescriptor;
import com.bekioui.jaxrs.server.api.resource.SwaggerResource;
import com.bekioui.jaxrs.server.impl.filter.CorsFilterImpl;
import com.bekioui.jaxrs.server.impl.resource.SwaggerResourceImpl;
import com.excilys.ebi.utils.spring.log.slf4j.InjectLogger;

@Component
public class NettyServer {

	@Value("${jaxrs.server.rootResourcePath:}")
	private String rootResourcePath;

	@Value("${jaxrs.server.port:8080}")
	private int port;

	// Default: 1024 * 1024 * 10
	@Value("${jaxrs.server.maxRequestSize:10485760}")
	private int maxRequestSize;

	@Value("${jaxrs.server.cors.enabled:false}")
	private boolean corsEnabled;

	@Value("${jaxrs.server.swagger.enabled:false}")
	private boolean swaggerEnabled;

	@InjectLogger
	private Logger logger;

	@Autowired
	private ApplicationContext applicationContext;

	@Autowired
	private Supplier<Swagger> swaggerSupplier;

	@Autowired
	private Supplier<CorsFilter> corsFilterSupplier;

	@Autowired
	private List<ResourceDescriptor> resourceDescriptors;

	private NettyJaxrsServer server;

	@PostConstruct
	private void postConstruct() {
		ResteasyDeployment deployment = new ResteasyDeployment();

		resources(deployment);
		providers(deployment);

		server = new NettyJaxrsServer();
		server.setDeployment(deployment);
		server.setRootResourcePath(rootResourcePath);
		server.setPort(port);
		server.setMaxRequestSize(maxRequestSize);
		server.setSecurityDomain(null);

		server.start();

		logger.info("Netty Jaxrs server started.");
	}

	@PreDestroy
	private void preDestroy() {
		server.stop();
	}

	private void resources(ResteasyDeployment deployment) {
		BiPredicate<Package, Package> biPredicate = (p1, p2) -> p1.equals(p2) || p1.getName().startsWith(p2.getName() + ".");
		Function<Object, Stream<Package>> function = o -> Arrays.asList(o.getClass().getInterfaces()).stream().map(Class::getPackage);

		List<Object> resources = applicationContext.getBeansWithAnnotation(Path.class).values().stream() //
				.filter(o -> resourceDescriptors.stream().map(ResourceDescriptor::getPackage).anyMatch( //
						p -> biPredicate.test(o.getClass().getPackage(), p) || function.apply(o).anyMatch(ip -> biPredicate.test(ip, p)) //
				)).collect(Collectors.toList());

		if (swaggerEnabled) {
			Set<Class<?>> classes = resources.stream().map(Object::getClass).collect(Collectors.toSet());
			SwaggerResource swaggerRessource = new SwaggerResourceImpl(swaggerSupplier.get(), classes);
			deployment.getResources().add(swaggerRessource);
			deployment.getProviders().add(new SwaggerSerializers());
		}

		deployment.getResources().addAll(resources);
	}

	private void providers(ResteasyDeployment deployment) {
		if (corsEnabled) {
			deployment.getProviders().add(new CorsFilterImpl(corsFilterSupplier.get()));
		}
		deployment.getProviders().addAll(applicationContext.getBeansWithAnnotation(Provider.class).values());
	}

}
