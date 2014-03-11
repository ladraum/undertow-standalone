package com.texoit.undertow.standalone;

import io.undertow.server.handlers.PathHandler;

import java.util.Collection;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

import com.texoit.undertow.standalone.api.DeploymentContext;
import com.texoit.undertow.standalone.api.DeploymentHook;
import com.texoit.undertow.standalone.api.RequestHook;

@Getter
@Accessors( fluent = true )
@RequiredArgsConstructor( staticName = "with" )
public class DefaultDeploymentContext implements DeploymentContext {

	final Collection<RequestHook> requestHooks;
	final Collection<DeploymentHook> deploymentHooks;
	final PathHandler uris;
	final Collection<Class<?>> availableClasses;

	@Override
	public DeploymentContext register( RequestHook hook ) {
		this.requestHooks.add( hook );
		return this;
	}

}
