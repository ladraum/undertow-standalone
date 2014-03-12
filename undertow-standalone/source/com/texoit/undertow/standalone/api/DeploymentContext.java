package com.texoit.undertow.standalone.api;

import io.undertow.server.HttpHandler;

import java.util.Collection;

public interface DeploymentContext {

	DeploymentContext register( RequestHook hook );

	Collection<Class<?>> availableClasses();

	Collection<DeploymentHook> deploymentHooks();

	Collection<RequestHook> requestHooks();

	DeploymentContext register( String uri, HttpHandler handler );

}
