package com.texoit.undertow.standalone;

import java.io.File;

import lombok.Getter;
import lombok.experimental.Accessors;

@Accessors(fluent=true)
public class Configuration {
	
	private static Configuration instance = new Configuration();

	@Getter(lazy=true)
	private final String libraryPath = getLibraryPath();
	
	@Getter(lazy=true)
	private final String resourcesPath = getResourcesPath();

	@Getter(lazy=true)
	private final String currentWorkDir = System.getProperty( "user.dir" );
	
	@Getter(lazy=true)
	private final Integer port = getPort();
	
	@Getter(lazy=true)
	private final String host = System.getProperty( "undertow.standalone.host", "localhost" );

	public static Configuration instance(){
		return instance;
	}

	private String getLibraryPath() {
		String appdir = System.getProperty( "undertow.standalone.libdir", "lib" );
		return currentWorkDir() + File.separatorChar + appdir;
	}

	public String getResourcesPath() {
		String appdir = System.getProperty( "undertow.standalone.appdir", "webapp" );
		return currentWorkDir() + File.separatorChar + appdir;
	}
	
	public Integer getPort() {
		try {
			String port = System.getProperty( "undertow.standalone.port", "9000" );
			return Integer.valueOf(port);
		} catch ( NumberFormatException cause ) {
			return 9000;
		}
	}
}
