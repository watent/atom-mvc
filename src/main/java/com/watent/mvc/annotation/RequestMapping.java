package com.watent.mvc.annotation;

import java.lang.annotation.*;

/**
 * Service
 *
 * @author Dylan
 * @date 2018/3/30 09:50
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestMapping {

    String value() default "";
}
