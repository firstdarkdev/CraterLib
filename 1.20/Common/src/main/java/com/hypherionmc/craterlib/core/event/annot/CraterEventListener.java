package com.hypherionmc.craterlib.core.event.annot;

import com.hypherionmc.craterlib.core.event.CraterEventPriority;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface CraterEventListener {
    int priority() default CraterEventPriority.NORMAL;
}