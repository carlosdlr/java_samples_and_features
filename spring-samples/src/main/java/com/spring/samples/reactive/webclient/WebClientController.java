package com.spring.samples.reactive.webclient;

import java.net.URI;
import java.nio.charset.Charset;
import java.time.ZonedDateTime;
import java.util.Collections;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ReactiveHttpOutputMessage;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

@Controller
public class WebClientController {

	/**
	 * End point to invoke the service 
	 */
	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/resource")
	public void getResource() {
	}


	public void callingTheWebClient() {
		//First, we need to specify an HTTP method of a request by invoking the method(HttpMethod method) 
		//or calling its shortcut methods such as get, post, delete
		WebClient.UriSpec<WebClient.RequestBodySpec> request1 = createWebClientWithServerURLAndDefaultValues().method(HttpMethod.POST);
		WebClient.UriSpec<WebClient.RequestBodySpec> request2 = createWebClientWithServerURLAndDefaultValues().post();
        
		//The next move is to provide a URL. We can pass it to the uri API – as a String or a java.net.URL instance
		WebClient.RequestBodySpec uri1 = createWebClientWithServerURLAndDefaultValues().method(HttpMethod.POST)
				.uri("/resource");
		WebClient.RequestBodySpec uri2 = createWebClientWithServerURLAndDefaultValues().post()
				.uri(URI.create("/resource"));
		
		/*Moving on, we can set a request body, content type, length, cookies or headers – if we need to.
        For example, if we want to set a request body – there are two available ways – filling it with a BodyInserter 
        or delegating this work to a Publisher.
        The BodyInserter is an interface responsible for populating a ReactiveHttpOutputMessage body 
        with a given output message and a context used during the insertion. 
        A Publisher is a reactive component that is in charge of providing a potentially unbounded number of sequenced elements.*/
		WebClient.RequestHeadersSpec<?> requestSpec1 = uri1.body(BodyInserters.fromPublisher(Mono.just("data"), String.class));
        //The second way is the body method, which is a shortcut for the original body(BodyInserter inserter) method
		WebClient.RequestHeadersSpec<?> requestSpec2 = uri2.body(BodyInserters.fromObject("data"));
        
        //inserters using a publisher
        BodyInserter<Publisher<String>, ReactiveHttpOutputMessage> inserter1 = BodyInserters
                .fromPublisher(Subscriber::onComplete, String.class);

        /*LinkedMultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("key1", "value1");
        map.add("key2", "value2");*/
        
        //using a map
        //BodyInserter<MultiValueMap<String, ?>, ClientHttpRequest> inserter2 = BodyInserters.fromMultipartData(map);
        // or using a simple object
        BodyInserter<String, ReactiveHttpOutputMessage> inserter3 = BodyInserters.fromObject("body");
        
        // responses
        WebClient.ResponseSpec response1 = uri1.body(inserter3)
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .accept(MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML)
            .acceptCharset(Charset.forName("UTF-8"))
            .ifNoneMatch("*")
            .ifModifiedSince(ZonedDateTime.now())
            .retrieve();
        WebClient.ResponseSpec response2 = requestSpec2.retrieve();

	}

	/**
	 * Creates a client by using the DefaultWebClientBuilder class, 
	 * which allows full customization	
	 * @return a {@link WebClient}
	 */
	private WebClient createWebClientWithServerURLAndDefaultValues() {
		return WebClient.builder()
				.baseUrl("http://localhost:8081")
				.defaultCookie("cookieKey", "cookieValue")
				.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
				.defaultUriVariables(Collections.singletonMap("url", "http://localhost:8080"))
				.build();
	}

}
