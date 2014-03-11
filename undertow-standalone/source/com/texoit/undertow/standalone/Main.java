package com.texoit.undertow.standalone;

import java.io.IOException;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import lombok.RequiredArgsConstructor;
import lombok.val;

import com.texoit.undertow.standalone.api.DrowningException;

@RequiredArgsConstructor
public class Main {

	final Lock lock = new ReentrantLock();

	final String host;
	final int port;

	DrowningUndertowServer undertowServer;

	public void start() throws DrowningException {
		lock.lock();
		undertowServer = new DrowningUndertowServer(host, port);
		undertowServer.start();
	}

	public void stop(){
		undertowServer.stop();
		lock.unlock();
	}

	public void mainloop() throws InterruptedException, DrowningException{
		Condition newCondition = lock.newCondition();
		start();

		try {
			while( true )
				newCondition.awaitNanos(1);
		} catch ( InterruptedException cause ) {
			stop();
		}
	}

	public static void main(String[] args) throws InterruptedException, DrowningException, IOException, ClassNotFoundException {
		val configuration = Configuration.instance();
		val main = new Main( configuration.host(), configuration.port() );
		main.mainloop();
	}
}
