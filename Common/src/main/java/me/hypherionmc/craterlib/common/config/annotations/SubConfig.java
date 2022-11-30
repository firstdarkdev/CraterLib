package me.hypherionmc.craterlib.common.config.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author HypherionSA
 * @date 03/07/2022
 * Used to determine if a Config section should be rendered as a separate screen
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface SubConfig {
}

