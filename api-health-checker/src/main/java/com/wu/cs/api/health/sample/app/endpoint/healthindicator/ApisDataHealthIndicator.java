package com.wu.cs.api.health.sample.app.endpoint.healthindicator;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.ReactiveHealthIndicator;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class ApisDataHealthIndicator implements ReactiveHealthIndicator{

  @Override
  public Mono<Health> health() {
      return checkDownstreamServiceHealth().onErrorResume(
        ex -> Mono.just(new Health.Builder().down(ex).build())
      );
  }

  private Mono<Health> checkDownstreamServiceHealth() {
      // we could use WebClient to check health reactively
      return Mono.just(new Health.Builder().withDetail("200", "custom health checker").up().build());
  }
  
  

}
