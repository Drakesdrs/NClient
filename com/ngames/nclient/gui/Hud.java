package com.ngames.nclient.gui;

import com.ngames.nclient.NClient;
import com.ngames.nclient.baritone.BUtils;
import com.ngames.nclient.baritone.SafeThread;
import com.ngames.nclient.hack.Hack;
import com.ngames.nclient.hack.HackUtils;
import com.ngames.nclient.hack.Hacks;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Hud {
   public static int x;
   public static int y;
   public static Hud.ColorBuffer mainColor = new Hud.ColorBuffer((byte)0);

   public static void drawHUD() {
      if (mainColor.theme != Hacks.hud.theme.getValue()) {
         updateTheme();
      }

      List<String> StringList = new ArrayList();
      Map<String, String> inHuds = new HashMap();
      Iterator var2 = NClient.HackList.iterator();

      while(var2.hasNext()) {
         Hack h = (Hack)var2.next();
         if (h.isEnabled() && !h.hidden.getValue()) {
            StringList.add(HackUtils.getName(h));
            if (h.inHud != null) {
               inHuds.put(HackUtils.getName(h), h.inHud.toString());
            }
         }
      }

      Collections.sort(StringList, new Comparator<String>() {
         public int compare(String s1, String s2) {
            return Integer.compare(NClient.MC.field_71466_p.func_78256_a(s1), NClient.MC.field_71466_p.func_78256_a(s2));
         }
      });
      StringList.add(0, "N-Client B1");
      int yStep = Math.round((float)mainColor.currentTheme.size() / (float)StringList.size()) / (mainColor.currentTheme.size() / 255);
      int xStep = Math.round((float)mainColor.currentTheme.size() / (float)((String)StringList.get(StringList.size() - 1)).length()) / (mainColor.currentTheme.size() / 255);
      int dy = y;
      Hud.ColorBuffer vbuff = new Hud.ColorBuffer(mainColor);
      Iterator var6 = StringList.iterator();

      while(var6.hasNext()) {
         String s = (String)var6.next();
         Hud.ColorBuffer hbuff = new Hud.ColorBuffer(vbuff);
         int dx = x;
         char[] var10 = s.toCharArray();
         int var11 = var10.length;

         for(int var12 = 0; var12 < var11; ++var12) {
            Character c = var10[var12];
            NClient.MC.field_71466_p.func_175063_a(c.toString(), (float)dx, (float)dy, hbuff.next(xStep));
            dx += NClient.MC.field_71466_p.func_78256_a(c.toString());
         }

         if (inHuds.containsKey(s)) {
            NClient.MC.field_71466_p.func_78276_b((String)inHuds.get(s), x + 3 + NClient.MC.field_71466_p.func_78256_a(s), dy, -1);
         }

         dy += 15;
         vbuff.next(yStep);
      }

   }

   public static void updateTheme() {
      mainColor.setTheme(Hacks.hud.theme.getValue());
   }

   public static void enable() {
      (new SafeThread(() -> {
         if (Hacks.hud.isEnabled()) {
            mainColor.next();
         } else {
            mainColor.j = 0;
         }

         BUtils.sleep(5L);
      }, Hacks.hud)).start();
   }

   public static class ColorBuffer {
      private int j = 0;
      private boolean direction = true;
      private List<Integer> currentTheme;
      public byte theme;

      public ColorBuffer(byte theme) {
         this.currentTheme = NClient.theme.getTheme(theme);
         this.theme = theme;
      }

      public ColorBuffer(Hud.ColorBuffer other) {
         this.j = other.j;
         this.direction = other.direction;
         this.currentTheme = other.currentTheme;
         this.theme = other.theme;
      }

      public void setTheme(byte theme) {
         this.currentTheme = NClient.theme.getTheme(theme);
         this.theme = theme;
         this.j = 0;
         this.direction = true;
      }

      public int next() {
         int ret = (Integer)this.currentTheme.get(this.j);
         if (this.direction) {
            ++this.j;
         } else {
            --this.j;
         }

         if (this.j == this.currentTheme.size() - 1) {
            this.direction = false;
         }

         if (this.j == 0) {
            this.direction = true;
         }

         return ret;
      }

      public int next(int step) {
         int ret = (Integer)this.currentTheme.get(this.j);
         if (this.direction) {
            this.j += step;
         } else {
            this.j -= step;
         }

         if (this.j >= this.currentTheme.size() - 1) {
            this.direction = false;
            this.j = this.currentTheme.size() - 1;
         }

         if (this.j <= 0) {
            this.direction = true;
            this.j = 0;
         }

         return ret;
      }
   }
}
