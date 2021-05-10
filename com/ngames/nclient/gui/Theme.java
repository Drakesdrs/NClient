package com.ngames.nclient.gui;

import java.util.ArrayList;
import java.util.List;

public class Theme {
   public final List<Integer> RG;
   public final List<Integer> RB;
   public final List<Integer> BlackWhite;
   public final List<Integer> RGB;

   public Theme() {
      List<Integer> rg = generate(255, 0, 1, true);
      rg.addAll(generate(255, 0, 0, false));
      this.RG = rg;
      List<Integer> rb = new ArrayList();
      rb.addAll(generate(255, 0, 2, true));
      rb.addAll(generate(0, 255, 0, false));
      this.RB = rb;
      List<Integer> bw = new ArrayList();

      for(int i = 0; i < 255; ++i) {
         bw.add(getRGB(i, i, i));
      }

      this.BlackWhite = bw;
      List<Integer> rgb = generate(255, 0, 2, true);
      rgb.addAll(generate(0, 255, 0, false));
      rgb.addAll(generate(0, 255, 1, true));
      rgb.addAll(generate(0, 255, 2, false));
      rgb.addAll(generate(255, 0, 0, true));
      rgb.addAll(generate(255, 0, 1, false));
      this.RGB = rgb;
   }

   public static List<Integer> generate(int a, int b, int inc, boolean direction) {
      List<Integer> ret = new ArrayList();
      int i;
      if (direction) {
         for(i = 0; i < 255; ++i) {
            ret.add(sortRGB(a, b, i, inc));
         }
      } else {
         for(i = 255; i > 0; --i) {
            ret.add(sortRGB(a, b, i, inc));
         }
      }

      return ret;
   }

   private static int sortRGB(int a, int b, int c, int cPlace) {
      if (cPlace == 0) {
         return getRGB(c, a, b);
      } else if (cPlace == 1) {
         return getRGB(a, c, b);
      } else {
         return cPlace == 2 ? getRGB(a, b, c) : 0;
      }
   }

   public List<Integer> getTheme(byte id) {
      switch(id) {
      case 0:
         return this.RG;
      case 1:
         return this.RB;
      case 2:
         return this.BlackWhite;
      case 3:
         return this.RGB;
      default:
         return this.RGB;
      }
   }

   public static int getRGB(int r, int g, int b) {
      return (r << 16) + (g << 8) + b;
   }
}
