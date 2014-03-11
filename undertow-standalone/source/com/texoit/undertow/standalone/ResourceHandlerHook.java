package com.texoit.undertow.standalone;

import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import lombok.RequiredArgsConstructor;

import com.texoit.undertow.standalone.api.DrowningException;
import com.texoit.undertow.standalone.api.RequestHook;
import com.texoit.undertow.standalone.api.RequestHookChain;

@RequiredArgsConstructor( staticName = "wrap" )
public class ResourceHandlerHook implements RequestHook {

	final HttpHandler resourceHandler;

	@Override
	public void execute( RequestHookChain chain, HttpServerExchange exchange )
			throws DrowningException {
		try {
			this.resourceHandler.handleRequest( exchange );
		} catch ( Exception cause ) {
			throw new DrowningException( cause );
		}
	}

}
