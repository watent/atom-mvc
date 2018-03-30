package com.watent.mvc.annotation;

import java.lang.annotation.*;

/**
 * Controller
 *
 * @author Dylan
 * @date 2018/3/30 09:50
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Controller {


    String value() default "";
}
