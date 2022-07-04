package com.mahara.stocker.configuration;

import org.hibernate.validator.HibernateValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.validation.Validator;
import javax.validation.ValidatorFactory;

@Configuration
public class Validation {
  @Bean
  public Validator validator() {
    ValidatorFactory validatorFactory =
        javax.validation.Validation.byProvider(HibernateValidator.class)
            .configure()
            // 快速失败模式
            .failFast(false)
            .buildValidatorFactory();
    return validatorFactory.getValidator();
  }
}
