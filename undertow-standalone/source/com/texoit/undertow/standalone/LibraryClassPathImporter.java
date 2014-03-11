package com.texoit.undertow.standalone;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class LibraryClassPathImporter {

	private static final String CLASS_EXTENSION = ".class";
	final List<File> urls = new ArrayList<>();
	final List<Class<?>> classes = new ArrayList<>();
	final ClassLoader classLoader = ClassLoader.getSystemClassLoader();

	final String libDirectory;

	public List<Class<?>> retrieve() throws IOException {
		searchForJars( libDirectory );
		return classes;
	}

	public void searchForJars( String directory ) throws IOException {
		searchForJars( new File( directory ) );
	}

	public void searchForJars( File directory ) throws IOException {
		if ( directory.isDirectory() )
		for ( String fileName : directory.list() )
			memorizeFileOrRecursivelySearchAgain(directory, fileName);
	}

	private void memorizeFileOrRecursivelySearchAgain(File directory, String fileName) throws IOException  {
		if ( fileName.endsWith(".jar") )
			memorizeJar(directory, fileName);
		else
			searchForJars(fileName);
	}

	private void memorizeJar(File directory, String fileName) throws IOException {
		String path = directory.getAbsolutePath() + File.separatorChar + fileName;
		JarFile filePath = new JarFile( path );
		Enumeration<JarEntry> entries = filePath.entries();
		while ( entries.hasMoreElements() ) {
			JarEntry jarEntry = entries.nextElement();
			memorizeJarEntry(jarEntry);
		}
		filePath.close();
	}

	private void memorizeJarEntry( JarEntry jarEntry ) {
		final String name = jarEntry.getName();
		if ( name.endsWith(CLASS_EXTENSION) )
			memorizeClass(parse(name));
	}

	private void memorizeClass(final String parsedName) {
		try {
			Class<?> loadedClass = classLoader.loadClass(parsedName);
			classes.add(loadedClass);
		} catch (ClassNotFoundException e) {
		}
	}

	private String parse(final String name) {
		return name.replace(CLASS_EXTENSION, "")
					.replace(File.pathSeparatorChar, '.')
					.replace('/', '.')
					.replace('$', '.');
	}
}
