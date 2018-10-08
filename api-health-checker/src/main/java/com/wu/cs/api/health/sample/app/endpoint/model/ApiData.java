package com.wu.cs.api.health.sample.app.endpoint.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class ApiData {
   
   private String name;
   private long apiCallQuantity;
   private float apiCallTimeAverageInMilliSeconds;
   private long apiCallErrorsQuantity;
  
   
   public ApiData(String name, long apiCallQuantity, float apiCallTimeAverageInMilliSeconds,
      long apiCallErrorsQuantity) {

    this.name = name;
    this.apiCallQuantity = apiCallQuantity;
    this.apiCallTimeAverageInMilliSeconds = apiCallTimeAverageInMilliSeconds;
    this.apiCallErrorsQuantity = apiCallErrorsQuantity;
  }
   
   
   
   
}
