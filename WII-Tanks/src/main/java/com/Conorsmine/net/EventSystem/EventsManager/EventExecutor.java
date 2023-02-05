package com.Conorsmine.net.EventSystem.EventsManager;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface EventExecutor {

//    boolean isCancelable() default false;
    EventPriority priority() default EventPriority.NORMAL;

}
