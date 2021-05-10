package com.ngames.nclient.hack;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface NClientHack {
   String name();

   String description();

   String words();

   int keyBind() default 0;

   Category category();
}
