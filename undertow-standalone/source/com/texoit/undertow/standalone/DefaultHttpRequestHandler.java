package com.texoit.undertow.standalone;

import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import lombok.RequiredArgsConstructor;

import com.texoit.undertow.standalone.api.DeploymentContext;
import com.texoit.undertow.standalone.api.RequestHookChain;

@RequiredArgsConstructor
public class DefaultHttpRequestHandler implements HttpHandler {
	
	final DeploymentContext context;

	@Override
	public void handleRequest(HttpServerExchange exchange) throws Exception {
		RequestHookChain chain = new DefaultRequestHookChain( context.requestHooks().iterator(), exchange );
		chain.executeNext();
	}
}
