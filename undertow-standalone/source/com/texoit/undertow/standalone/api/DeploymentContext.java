package com.texoit.undertow.standalone.api;

import java.util.Collection;

public interface DeploymentContext {

	DeploymentContext register( RequestHook hook );

	Collection<Class<?>> availableClasses();

	Collection<DeploymentHook> deploymentHooks();

	Collection<RequestHook> requestHooks();

}
