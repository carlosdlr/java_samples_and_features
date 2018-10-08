package com.wu.cs.api.health.sample.app.endpoint.event;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.context.support.RequestHandledEvent;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wu.cs.api.health.sample.app.endpoint.exception.EntityNotFoundException;
import com.wu.cs.api.health.sample.app.endpoint.exception.SaveEntityException;
import com.wu.cs.api.health.sample.app.endpoint.logic.ApiHealthcheckMeasureLogic;
import com.wu.cs.api.health.sample.app.endpoint.model.ApiData;
import com.wu.cs.api.health.sample.app.endpoint.model.ApiHealthcheckMeasure;
import com.wu.cs.api.health.sample.app.endpoint.model.MeasureNames;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ApisCalledMeasureListener {

  @Autowired
  private ApiHealthcheckMeasureLogic apiHealthcheckMeasureLogic;
  @Autowired
  private SimpMessageSendingOperations messagingTemplate;

  @EventListener(classes = RequestHandledEvent.class)
  public void apiHealthcheckMeasure (RequestHandledEvent event) {
    Optional<ApiHealthcheckMeasure> apiHealthcheckMeasure = Optional.ofNullable(new ApiHealthcheckMeasure());
    apiHealthcheckMeasure.get().setMeasureName(MeasureNames.CALLED_APIS_QUANTITY.getName());
    String apiName = event.getShortDescription().split(";")[0].replaceAll("url=\\[/", "").replaceAll("\\]", "").replace("/", "");
    apiHealthcheckMeasure.get().setApiName(apiName.toUpperCase());

    try {
      apiHealthcheckMeasure = apiHealthcheckMeasureLogic.getByMeasureAndApiName(apiHealthcheckMeasure);
      Map<String,ApiData> apiInfoMap = convertJsonStringToMap(apiHealthcheckMeasure.get().getMeasureStringValue());
      ApiData apiData = apiInfoMap.get(apiName);
      setQuantityCallsAndFailures(apiData, event);
      apiData.setApiCallTimeAverageInMilliSeconds(event.getProcessingTimeMillis()/apiData.getApiCallQuantity());
      apiInfoMap.replace(apiName, apiData);
      apiHealthcheckMeasure.get().setMeasureStringValue(convertMapToJsonString(apiInfoMap));
      
      messagingTemplate.convertAndSend("/topic/apidatastats", apiData);


    } catch (EntityNotFoundException enf) {
      log.info(enf.getMessage());
      setDataForNewMeasure(apiHealthcheckMeasure, event);
    } catch (Exception e) {
      log.error(e.getMessage());
      return;
    }

    try {
      apiHealthcheckMeasureLogic.save(apiHealthcheckMeasure);
    } catch (SaveEntityException e) {
      log.error(e.getMessage());
    }

  }

  private void setQuantityCallsAndFailures(ApiData apiData, RequestHandledEvent event) {
    if(event.wasFailure()) {
      apiData.setApiCallErrorsQuantity(apiData.getApiCallErrorsQuantity()+1);
    }else {
      apiData.setApiCallQuantity(apiData.getApiCallQuantity()+1);
    }
  }

  private String convertMapToJsonString(Map<?,?> mapToconvert) throws JsonProcessingException {
    ObjectMapper mapper = new ObjectMapper();
    return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(mapToconvert);
  }

  private Map<String,ApiData> convertJsonStringToMap(String stringToconvert) throws JsonParseException, JsonMappingException, IOException {
    ObjectMapper mapper = new ObjectMapper();
    TypeReference<HashMap<String, ApiData>> typeRef = new TypeReference<HashMap<String,ApiData>>() {};

    return mapper.readValue(stringToconvert, typeRef);
  }

  private void setDataForNewMeasure(Optional<ApiHealthcheckMeasure> apiHealthcheckMeasure, RequestHandledEvent event) {
    apiHealthcheckMeasure.get().setMeasureNumberValue(0.0);
    apiHealthcheckMeasure.get().setMeasureStartedDate(new Date());
    Map<String, ApiData> apiInfoMap =  new HashMap<String, ApiData>();
    ApiData apiData = new ApiData();
    setQuantityCallsAndFailures(apiData, event);
    apiData.setApiCallTimeAverageInMilliSeconds(event.getProcessingTimeMillis()/apiData.getApiCallQuantity());
    apiData.setName(apiHealthcheckMeasure.get().getApiName().toLowerCase());

    apiInfoMap.put(apiData.getName(), apiData);

    try {
      apiHealthcheckMeasure.get().setMeasureStringValue(convertMapToJsonString(apiInfoMap));
    } catch (JsonProcessingException e) {
      log.error(e.getMessage());
      return;
    }
  }
}
