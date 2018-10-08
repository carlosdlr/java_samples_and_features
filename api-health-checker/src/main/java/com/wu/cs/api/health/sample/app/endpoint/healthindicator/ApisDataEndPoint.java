package com.wu.cs.api.health.sample.app.endpoint.healthindicator;

import java.util.HashMap;
import java.util.Map;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.web.annotation.WebEndpoint;
import org.springframework.stereotype.Component;

@Component
@WebEndpoint(id = "apisdatacustom", enableByDefault = true)
public class ApisDataEndPoint {
  
  @ReadOperation
  public Map<String, Object> getApiCustomData() {
    Map<String, Object> data = new HashMap<>();
    data.put("value", 2L);
    return data;
  }

}
