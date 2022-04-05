package com.bso.companycob.infrastructure.aws;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@ConditionalOnProperty(name = "messaging.use-raw-aws-sdk", havingValue = "false", matchIfMissing = true)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface OnUseRawAwsSdkDisabled {}
