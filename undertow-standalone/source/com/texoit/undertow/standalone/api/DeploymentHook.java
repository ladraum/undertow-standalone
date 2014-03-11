package com.texoit.undertow.standalone.api;

public interface DeploymentHook {

	void onDeploy( DeploymentContext context );

	void onUndeploy( DeploymentContext context );

}
