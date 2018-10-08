package com.wu.cs.api.health.sample.app.endpoint.model;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "API_HEALTH_CHECK_MEASURE")
@Getter @Setter @NoArgsConstructor
public class ApiHealthcheckMeasure {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name="ID")
  private Long id;
  @Column(name="MEASURE_NAME")
  private String measureName;
  @Column(name="MEASURE_NUMBER_VALUE")
  private Double measureNumberValue;
  @Column(name="API_NAME")
  private String apiName;
  @Column(name="MEASURE_STRING_VALUE")
  private String measureStringValue;
  @Column(name="MEASURE_STARTED_DATE")
  private Date measureStartedDate;
  
  
  public ApiHealthcheckMeasure(String measureName, Double measureNumberValue, String apiName, String measureStringValue, Date measureStartedDate) {
    this.measureName = measureName;
    this.measureNumberValue = measureNumberValue;
    this.apiName = apiName;
    this.measureStringValue = measureStringValue;
    this.measureStartedDate = measureStartedDate;
  }
   
  @Override
  public String toString() {
    return "ApiHealthcheckMeasure{" +
        "id=" + id +
        ", measureName='" + measureName + '\'' +
        ", measureNumberValue='" + measureNumberValue +
        ", apiName='" + apiName + '\'' +
        ", measureStringValue='" + measureStringValue + '\'' +
        ", measureStartedDate='" + measureStartedDate + '\'' +
        '}';
  }
}