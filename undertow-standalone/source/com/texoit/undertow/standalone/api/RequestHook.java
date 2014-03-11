package com.texoit.undertow.standalone.api;

import io.undertow.server.HttpServerExchange;

public interface RequestHook {

	void execute(RequestHookChain chain, HttpServerExchange exchange) throws DrowningException;

}
