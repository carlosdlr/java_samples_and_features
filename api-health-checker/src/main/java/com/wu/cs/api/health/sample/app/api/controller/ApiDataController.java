package com.wu.cs.api.health.sample.app.api.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.wu.cs.api.health.sample.app.endpoint.model.ApiData;
import com.wu.cs.api.health.sample.app.endpoint.model.ApiHealthcheckMeasure;

@Controller
public class ApiDataController {
  
  //@Autowired
  //private ApiHealthcheckMeasureLogic apiHealthcheckMeasureLogic;
  
  @MessageMapping("/apidata")
  @SendTo("/topic/apidatastats")
  public Map<String, ApiData> getApiData(ApiData data) throws Exception {
    Map<String, ApiData> dataResult = new HashMap<>();
    ApiHealthcheckMeasure apiHealthcheckMeasure = new ApiHealthcheckMeasure();
    apiHealthcheckMeasure.setMeasureName(data.getName());
    
   //Optional<ApiHealthcheckMeasure> data = apiHealthcheckMeasureLogic.getByName(Optional.of(apiHealthcheckMeasure));
   dataResult.put("key1", data);
   
    return dataResult;
    
  }

}
