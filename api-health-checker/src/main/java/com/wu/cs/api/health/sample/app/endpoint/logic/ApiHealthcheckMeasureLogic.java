package com.wu.cs.api.health.sample.app.endpoint.logic;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wu.cs.api.health.sample.app.endpoint.exception.EntityNotFoundException;
import com.wu.cs.api.health.sample.app.endpoint.exception.SaveEntityException;
import com.wu.cs.api.health.sample.app.endpoint.model.ApiHealthcheckMeasure;
import com.wu.cs.api.health.sample.app.endpoint.repository.ApiHealthcheckMeasureRepository;
import lombok.extern.slf4j.Slf4j;

/**
 * Class that implements the logic to retrieve and store the book entity info 
 * @author carlosdlr
 *
 */
@Service
@Slf4j
public class ApiHealthcheckMeasureLogic {

  /**
   * Data component to interact with the DB independent of the implementation
   */
  @Autowired
  private ApiHealthcheckMeasureRepository repository;
  /**
   * Constant messages for logic and info validation
   */
  private static final String NULL_MSG = "The measure or ID cannot be null";
  private static final String MEASURE_SAVED_MSG = "The measure has been saved ";
  private static final String MEASURE_UPDATED_MSG = "The measure has been updated ";
  private static final String MEASURE_IS_NOT_PRESENT_IN_DB_MSG = "The measure is not present in the db ";

  /**
   * Method that validates if a measure instance exist in the DB
   * @param measure instance to validate
   * @return boolean value true if is present in the DB or false if not
   */
  public boolean exists (final Optional<ApiHealthcheckMeasure> apiHealthcheckMeasure) {

    if(apiHealthcheckMeasure.isPresent() && isValidParamToSeacrh(apiHealthcheckMeasure.get().getId())) {
      
      return repository.existsById(apiHealthcheckMeasure.get().getId());

    }else if (apiHealthcheckMeasure.isPresent() && isValidParamToSeacrh(apiHealthcheckMeasure.get().getMeasureName()) 
        && isValidParamToSeacrh(apiHealthcheckMeasure.get().getApiName())){
      
      return repository.findByMeasureAndApiName(apiHealthcheckMeasure.get().getMeasureName(), apiHealthcheckMeasure.get().getApiName()).isPresent();
      
    }else {
        
      log.error(NULL_MSG);
      return false;
      
    }

  }

  private boolean isValidParamToSeacrh(final Object param) {
    boolean isValid = false;

    if(param instanceof Long) {

      isValid = param != null ? true : false;

    }

    if(param instanceof String) {

      final String paramValue = (String) param;
      isValid = paramValue != null || !paramValue.isEmpty() ? true : false;

    }

    return isValid;
  }

  /**
   * Method that allows save a book entity
   * @param book
   */
  public ApiHealthcheckMeasure save (final Optional<ApiHealthcheckMeasure> apiHealthcheckMeasure) throws SaveEntityException{
    ApiHealthcheckMeasure savedMeasure = null;
    try {

      if(!exists(apiHealthcheckMeasure)) {

        savedMeasure = apiHealthcheckMeasure.get();
        savedMeasure = repository.save(savedMeasure);
        log.info(MEASURE_SAVED_MSG + savedMeasure.toString());  
        return savedMeasure;

      }else {

        savedMeasure = repository.findById(apiHealthcheckMeasure.get().getId()).get();
        savedMeasure.setMeasureNumberValue(apiHealthcheckMeasure.get().getMeasureNumberValue());
        savedMeasure.setMeasureStringValue(apiHealthcheckMeasure.get().getMeasureStringValue());
        log.info(MEASURE_UPDATED_MSG + savedMeasure.toString()); 
        return repository.save(savedMeasure);

      }
    } catch (IllegalArgumentException e) {
      throw new SaveEntityException(e.getMessage());
    }

  }

  /**
   * Method that allows find a measure by ID
   * @param measure to find
   * @return Optional<ApiHealthcheckMeasure> the measure instance if is found it
   */
  public Optional<ApiHealthcheckMeasure> getByID (final Optional<ApiHealthcheckMeasure> apiHealthcheckMeasure) throws EntityNotFoundException{
    Optional<ApiHealthcheckMeasure> measureResutl = null;
    final String message = MEASURE_IS_NOT_PRESENT_IN_DB_MSG+apiHealthcheckMeasure.toString();

    try {

      if(exists(apiHealthcheckMeasure)) {

        measureResutl = repository.findById(apiHealthcheckMeasure.get().getId());

      }else {

        log.info(message);
        throw new EntityNotFoundException(ApiHealthcheckMeasure.class, "id", apiHealthcheckMeasure.get().getId().toString());

      }

      return measureResutl;

    } catch (IllegalArgumentException e) {
      log.info(message);
      throw new EntityNotFoundException(ApiHealthcheckMeasure.class, "id", apiHealthcheckMeasure.get().getId().toString());
    }

  }

  public Optional<ApiHealthcheckMeasure> getByName (final Optional<ApiHealthcheckMeasure> apiHealthcheckMeasure) throws EntityNotFoundException{
    Optional<ApiHealthcheckMeasure> measureResutl = null;
    final String message = MEASURE_IS_NOT_PRESENT_IN_DB_MSG+apiHealthcheckMeasure.toString();

    try {

      if(exists(apiHealthcheckMeasure)) {

        measureResutl = repository.findByName(apiHealthcheckMeasure.get().getMeasureName());

      }else {

        log.info(message);
        throw new EntityNotFoundException(ApiHealthcheckMeasure.class, "measureName", apiHealthcheckMeasure.get().getMeasureName().toString());

      }

      return measureResutl;

    } catch (IllegalArgumentException e) {
      log.info(message);
      throw new EntityNotFoundException(ApiHealthcheckMeasure.class, "measureName", apiHealthcheckMeasure.get().getMeasureName().toString());
    }

  }
  
  public Optional<ApiHealthcheckMeasure> getByMeasureAndApiName (final Optional<ApiHealthcheckMeasure> apiHealthcheckMeasure) throws EntityNotFoundException{
    Optional<ApiHealthcheckMeasure> measureResutl = null;
    final String message = MEASURE_IS_NOT_PRESENT_IN_DB_MSG+apiHealthcheckMeasure.toString();

    try {

      if(exists(apiHealthcheckMeasure)) {

        measureResutl = repository.findByMeasureAndApiName(apiHealthcheckMeasure.get().getMeasureName(), apiHealthcheckMeasure.get().getApiName());

      }else {

        log.info(message);
        throw new EntityNotFoundException(ApiHealthcheckMeasure.class, "measureName", apiHealthcheckMeasure.get().getMeasureName().toString(), 
            "apiName", apiHealthcheckMeasure.get().getApiName());

      }

      return measureResutl;

    } catch (IllegalArgumentException e) {
      log.info(message);
      throw new EntityNotFoundException(ApiHealthcheckMeasure.class, "measureName", apiHealthcheckMeasure.get().getMeasureName().toString(), 
          "apiName", apiHealthcheckMeasure.get().getApiName());
    }

  }

}
