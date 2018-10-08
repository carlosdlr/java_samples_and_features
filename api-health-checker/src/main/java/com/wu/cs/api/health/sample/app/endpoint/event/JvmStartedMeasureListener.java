package com.wu.cs.api.health.sample.app.endpoint.event;

import java.util.Date;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import com.wu.cs.api.health.sample.app.endpoint.exception.SaveEntityException;
import com.wu.cs.api.health.sample.app.endpoint.logic.ApiHealthcheckMeasureLogic;
import com.wu.cs.api.health.sample.app.endpoint.model.ApiHealthcheckMeasure;
import com.wu.cs.api.health.sample.app.endpoint.model.MeasureNames;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class JvmStartedMeasureListener {

  @Autowired
  private ApiHealthcheckMeasureLogic apiHealthcheckMeasureLogic;
 
  @EventListener
  public void jvmStartedMeasureEvent(ContextStartedEvent event) {
    ApiHealthcheckMeasure apiHealthcheckMeasure = new ApiHealthcheckMeasure();
    apiHealthcheckMeasure.setMeasureName(MeasureNames.JVM_STARTED_TIME.getName());
    apiHealthcheckMeasure.setMeasureStartedDate(new Date());
    apiHealthcheckMeasure.setMeasureNumberValue(0.0);
    apiHealthcheckMeasure.setMeasureStringValue("--");
    apiHealthcheckMeasure.setApiName(event.getApplicationContext().getId().toUpperCase());
    
    try {
      apiHealthcheckMeasureLogic.save(Optional.of(apiHealthcheckMeasure));
      log.info(MeasureNames.JVM_STARTED_TIME.getName() + " measure were stored successfuly");
    } catch (SaveEntityException e) {
      log.error(MeasureNames.JVM_STARTED_TIME.getName() + "measure error: "+ e.getMessage());
    }

  }

}
