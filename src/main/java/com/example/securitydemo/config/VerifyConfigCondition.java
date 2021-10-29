package com.example.securitydemo.config;

import lombok.extern.java.Log;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.type.AnnotatedTypeMetadata;

@Log
@Configuration
public class VerifyConfigCondition implements Condition {
    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        boolean result = context.getEnvironment().getProperty("application.auth.useLdap",Boolean.class,false);
        log.info("**********VerifyConfigCondition with application.auth.useLdap:{"+result+"}****************");
        return result;
    }
}
