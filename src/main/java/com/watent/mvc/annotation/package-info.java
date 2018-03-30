/**
 * 自定义注解
 * 1、元注解
 *   元注解的作用就是负责注解其他注解
 *      1.@Target, :作用目标，作用在方法上、变量上、还是类上
 *      2.@Retention,用于描述注解的生命周期，SOURCE:在源文件中有效，CLASS:在class文件中有效，RUNTIME:在运行时有效
 *      3.@Documented，javadoc此类的工具文档化
 *   自定义4个注解：Controller，Service，RequestMapping，Qualifier
 *   告诉他们注解就是存储信息的，跟xml功能差不多，存储了信息后，程序读到注解上面的信息做相应的操作
 *
 * @author Dylan
 * @date 2018/3/26 16:53
 */
package com.watent.mvc.annotation;