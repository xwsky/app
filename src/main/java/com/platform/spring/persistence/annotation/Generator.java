package com.platform.spring.persistence.annotation;


import com.platform.spring.persistence.constant.Strategy;

import java.lang.annotation.*;



@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Generator {
	
	/**
	 * 默认为自己填写id
	 * @return
	 */
	Strategy strategy() default Strategy.MANUAL;
	
}
