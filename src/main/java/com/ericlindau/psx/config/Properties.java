package com.ericlindau.psx.config;

/**
 * @author Eric Lindau
 *
 * Configurable default properties that will be applied
 * to descendant Values unless explicitly specified.
 */
class Properties extends Configurable {

  @Configured
  private long min = 0; // TODO: Defaults inline
  @Configured
  private long max = 1;
  @Configured
  private boolean digital = false;

}
