package com.spring.samples.webfulx.reactive.logging;

import reactor.core.publisher.Flux;

/**
 * This class shows how to use logging in a reactive stream
 * using Spring webflux 
 * @author carlosdlr
 *
 */
public class WebFluxReactiveLoggingExample {


	public static void main(String [] args) {

		//creates a reactive stream using spring flux and enable logging using the log method
		Flux<Integer> reactiveStream = Flux.range(1, 5).log();

		//now we subscribe to the to this stream to consume the generated values 
		reactiveStream.subscribe();

		//using the take approach we can say to flux that just take a specific number of events in the stream.
		//the take method also cancel the stream after 3 events
		reactiveStream = Flux.range(1, 5).log().take(3);

		//now we subscribe to the to this stream to consume the generated values 
		reactiveStream.subscribe();
		
        //with this approach the stream will complete this is because we observe the output of using take() instead of what was requested by this method.
		reactiveStream = Flux.range(1, 5).take(3).log();
		
		//now we subscribe to the to this stream to consume the generated values 
		reactiveStream.subscribe();

	}
}
