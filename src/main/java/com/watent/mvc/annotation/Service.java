package com.watent.mvc.annotation;

import java.lang.annotation.*;

/**
 * Service
 *
 * @author Dylan
 * @date 2018/3/30 09:50
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Service {

    String value() default "";
}
