package com.example.mydependencyinjection.util.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface ConstructorParameters {
    Class<?>[] value();
}
