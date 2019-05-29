package com.ericlindau.psx.config;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author Eric Lindau
 *
 * Annotation for indicating fields automatically populated
 * by Configurable utilities.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Configured {}
