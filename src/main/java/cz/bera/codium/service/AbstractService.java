package cz.bera.codium.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

public class AbstractService {

  @Autowired
  protected MessageSource messageSource;

}
