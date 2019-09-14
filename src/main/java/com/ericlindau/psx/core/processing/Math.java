package com.ericlindau.psx.core.processing;

public final class Math {
  public static long rescale(float value, long min, long max, long newMin, long newMax) {
    long dist = java.lang.Math.abs(min) + java.lang.Math.abs(max);
    float x = (value - min) / dist;
    return java.lang.Math.round(newMax * x + newMin * (1 - x));
  }
}
