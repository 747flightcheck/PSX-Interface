package com.ericlindau.psx.core.processing;

public final class Math {
  /**
   * Rescales value from an original range of [min, max] to a range of [newMin, newMax].
   *
   * @param value
   * @param min
   * @param max
   * @param newMin
   * @param newMax
   * @return the rescaled value
   */
  public static long rescale(float value, long min, long max, long newMin, long newMax) {
    long dist = java.lang.Math.abs(min) + java.lang.Math.abs(max);
    float x = (value - min) / dist;
    return java.lang.Math.round(newMax * x + newMin * (1 - x));
  }
}
