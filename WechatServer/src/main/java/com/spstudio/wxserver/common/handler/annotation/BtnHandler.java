package com.spstudio.wxserver.common.handler.annotation;
import org.springframework.stereotype.Component;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by Soul on 2016/12/8.
 */
@Component
@Retention(RetentionPolicy.RUNTIME)
public @interface BtnHandler {
    String value() default "";
    String key() default "";
}
