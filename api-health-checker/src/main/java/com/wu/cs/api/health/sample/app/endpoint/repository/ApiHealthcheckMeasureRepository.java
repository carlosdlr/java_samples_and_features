package com.wu.cs.api.health.sample.app.endpoint.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.wu.cs.api.health.sample.app.endpoint.model.ApiHealthcheckMeasure;

public interface ApiHealthcheckMeasureRepository extends JpaRepository<ApiHealthcheckMeasure, Long> {
   
  @Query("SELECT a FROM ApiHealthcheckMeasure a where a.measureName = :measureName")
  Optional<ApiHealthcheckMeasure> findByName(@Param("measureName") String measureName);
  
  @Query("SELECT a FROM ApiHealthcheckMeasure a where a.measureName = :measureName AND a.apiName = :apiName")
  Optional<ApiHealthcheckMeasure> findByMeasureAndApiName(@Param("measureName") String measureName, @Param("apiName") String apiName);
}
