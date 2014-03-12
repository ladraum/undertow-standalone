undertow-standalone
===================

Undertow Standalone is a micro container running under Undertow core. It
just provide an out-of-box structure to initialize your application without
writing some bootstraping lines of code.

## Don't repeat your self
Undertow is an amazing web development plataform. It is the current Wildfly
core engine, is simple and [scalable](http://www.techempower.com/benchmarks/#section=data-r8&hw=i7&test=plaintext).
Nevertheless, it has no standard way to struture and embed your project.

Here is the default hello world exemple provided by Undertow documentation.

```java
public class HelloWorldServer {

    public static void main(final String[] args) {
        Undertow server = Undertow.builder()
                .addHttpListener(8080, "localhost")
                .setHandler(new HttpHandler() {
                    @Override
                    public void handleRequest(final HttpServerExchange exchange) throws Exception {
                        exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/plain");
                        exchange.getResponseSender().send("Hello World");
                    }
                }).build();
        server.start();
    }
}
```

Undertow Standalone aim to save you from the pain of creating its countless lines of code
to achieve the your daily web development goals.

```java
@Path("/")
public class HelloWorldHandler implements HttpHandler {

	@Override
	public void handleRequest( HttpServerExchange exchange ) throws Exception {
    exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/plain");
    exchange.getResponseSender().send( "Hello World" );
  }

}
```

## Intercepting requests
Undertow internaly has a simple chain of responsibility where it store its routing
design and provide an [easy to use DSL](http://undertow.io/documentation/core/built-in-handlers.html).
Even so, intercept requests isn't an easy task to do.

Undertow Standalone provide an easy RequestHook where you can intercept requests, change
the HttpServerExchange state and define if the request should go on or not.

```java

@lombok.extern.log4j.Log4j
public class TraceRequestHook implements RequestHook {

	@Override
	public void execute( RequestHookChain chain, HttpServerExchange exchange ) throws DrowningException {
	  // Tracing the request
	  String message = exchange.getRequestMethod().toString() + ":" + exchange.getRequestPath();
		log.info( message );
		
		// Allowing the next chain of hooks to do its job.
		// If no other RequestHook are available, it will execute the Default Handler
		chain.executeNext();
	}
}

```

Every request hook is disposed in a chain of resposibility. If a RequestHook does not call ```chain.executeNext()```
it will interrupt the request lifecycle. It means that the hook is able to do what it want with the request.
RequestHook's are useful to plug external frameworks like [RestEasy](http://www.jboss.org/resteasy) or a custom template
engine like [Mustache](http://mustache.github.io/).

## Listening to deployments
Its possible to listen deployments events like ```onDeploy``` and ```onUndeploy```.

```java
@lombok.extern.log4j.Log4j
public class TraceDeploymentHook implements DeploymentHook {

	@Override
	public void onDeploy( DeploymentContext context ) {
		log.info( "On deploy" );
	}

	@Override
	public void onUndeploy( DeploymentContext context ) {
		log.info( "On undeploy" );
	}

}

```

With DeploymentContext is possible to analyze available classes in Classpath ( throught
```context.availableClasses()``` ). Its also possible to register new request hooks ( throught
```context.register( new RequestHook {  /*...*/ } )``` ) and register Undertow HttpHandlers
( throught ```context.register( "/hello/", new HttpHandler {  /*...*/ } )``` ), providing
a full lifecycle to your application.

## Instalation instructions

Goto [Releases](https://github.com/TeXOLabs/undertow-standalone/releases) and download the
[last bundled release](https://github.com/TeXOLabs/undertow-standalone/releases/download/1.0-Alpha1/undertow-standalone-1.0.Alpha1.zip) and unzip it in a nice location.

As a micro container it was designed to contains a single Java application. Your just unzipped undertow folder
should contains three directories:
* bin: which contains scripts to manage undertow
* lib: where you should put all your dependencies
* webapp: where you could put all your web assets and resources

Once you deployed your deps and resources, you could start the application as following:
```bash
# on linux console environment
./bin/undertow.sh
# on windows, in powershell console environment
# yes, you should use linux slashes here
sh bin/undertow.sh
```

## Creating a bundle from my maven project
```TODO```

## Contributing
Undertow Standalone need your help to provide the best to the community. Even simple tasks like
testing the micro container, finding typos in docs or reporting improvements feedbacks will be welcome.

## Community / Support

* Google Group: yet not created
* [GitHub Issues](https://github.com/TeXOLabs/undertow-standalone/issues)

### License

Undertow Standalone is [Apache 2.0 licensed](http://www.apache.org/licenses/LICENSE-2.0.html).
