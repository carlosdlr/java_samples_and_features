package com.wu.cs.api.health.sample.app.endpoint.model;

public enum MeasureNames {
  JVM_STARTED_TIME ("JVM_STARTED_TIME"),
  CALLED_APIS_QUANTITY("CALLED_APIS_QUANTITY"),
  AVERAGE_FINISH_TIME_API("AVERAGE_FINISH_TIME_API"),
  APIS_ERRORS_QUANTITY("APIS_ERRORS_QUANTITY"),
  JDBC_POOL_SIZE("JDBC_POOL_SIZE"),
  API_CPU_USAGE(""),
  API_MEMORY_USAGE("");
  
  private final String name;
  
  MeasureNames(String name){
     this.name = name;
  }

  public String getName() {
    return this.name;
  }

}
