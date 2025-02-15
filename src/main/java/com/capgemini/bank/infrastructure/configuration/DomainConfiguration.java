package com.capgemini.bank.infrastructure.configuration;


import com.capgemini.bank.domain.Account;
import com.capgemini.bank.domain.annotations.DomainService;
import com.capgemini.bank.domain.annotations.Stub;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;


@Configuration
@ComponentScan(
        basePackageClasses = {Account.class},
        includeFilters = {@ComponentScan.Filter(type = FilterType.ANNOTATION, classes = {DomainService.class, Stub.class})})
public class DomainConfiguration {
}

