package com.texoit.undertow.standalone.api;

public interface RequestHookChain {

	void executeNext() throws DrowningException;

}