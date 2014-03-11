package com.texoit.undertow.standalone;

import io.undertow.server.HttpServerExchange;

import java.util.Iterator;

import lombok.RequiredArgsConstructor;

import com.texoit.undertow.standalone.api.DrowningException;
import com.texoit.undertow.standalone.api.RequestHook;
import com.texoit.undertow.standalone.api.RequestHookChain;

@RequiredArgsConstructor
public class DefaultRequestHookChain implements RequestHookChain {

	final Iterator<RequestHook> hooks;
	final HttpServerExchange exchange;

	@Override
	public void executeNext() throws DrowningException {
		RequestHook hook = getNextHookClass();
		hook.execute( this, this.exchange );
	}

	public RequestHook getNextHookClass() throws DrowningException {
		if ( !this.hooks.hasNext() )
			throw new DrowningException( "No hook available found." );
		RequestHook next = this.hooks.next();
		return next;
	}
}
