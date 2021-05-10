package com.ngames.nclient.gui.font;

import com.ngames.nclient.NClient;
import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;

public class Fonts {
   public static Font BLADRMF_;
   public static Font BRITANIC;
   public static Font BRLNSB;
   public static Font BRLNSDB;
   public static Font BRLNSR;
   public static Font BROADW;
   public static Font Elegance;
   public static Font Ellis___;
   public static Font Excess__;
   public static Font Hotmb___;
   public static Font Mandela_;
   public static Font Matte___;
   public static Font MATURASC;
   public static Font Sneabo__;
   public static Font STENCIL;
   public static Font Trendy__;
   public static Font tt0628m_;
   public static Font tt0756m_;
   public static Font TT1024M_;
   public static Font tt1247m_;
   public static Font tt1248m_;
   private static final File fontsDir;

   public static void init() {
      try {
         BLADRMF_ = Font.createFont(0, new File(fontsDir, "BLADRMF_.TTF"));
         BRITANIC = Font.createFont(0, new File(fontsDir, "BRITANIC.TTF"));
         BRLNSB = Font.createFont(0, new File(fontsDir, "BRLNSB.TTF"));
         BRLNSDB = Font.createFont(0, new File(fontsDir, "BRLNSDB.TTF"));
         BRLNSR = Font.createFont(0, new File(fontsDir, "BRLNSR.TTF"));
         BROADW = Font.createFont(0, new File(fontsDir, "BROADW.TTF"));
         Elegance = Font.createFont(0, new File(fontsDir, "Elegance.ttf"));
         Ellis___ = Font.createFont(0, new File(fontsDir, "Ellis___.ttf"));
         Excess__ = Font.createFont(0, new File(fontsDir, "Excess__.ttf"));
         Hotmb___ = Font.createFont(0, new File(fontsDir, "Hotmb___.ttf"));
         Mandela_ = Font.createFont(0, new File(fontsDir, "Mandela_.ttf"));
         Matte___ = Font.createFont(0, new File(fontsDir, "Matte___.ttf"));
         MATURASC = Font.createFont(0, new File(fontsDir, "MATURASC.TTF"));
         Sneabo__ = Font.createFont(0, new File(fontsDir, "Sneabo__.ttf"));
         STENCIL = Font.createFont(0, new File(fontsDir, "STENCIL.TTF"));
         Trendy__ = Font.createFont(0, new File(fontsDir, "Trendy__.ttf"));
         tt0628m_ = Font.createFont(0, new File(fontsDir, "tt0628m_.ttf"));
         tt0756m_ = Font.createFont(0, new File(fontsDir, "tt0756m_.ttf"));
         TT1024M_ = Font.createFont(0, new File(fontsDir, "TT1024M_.TTF"));
         tt1247m_ = Font.createFont(0, new File(fontsDir, "tt1247m_.ttf"));
         tt1248m_ = Font.createFont(0, new File(fontsDir, "tt1248m_.ttf"));
      } catch (IOException | FontFormatException var1) {
         var1.printStackTrace();
      }

   }

   static {
      fontsDir = new File(NClient.NClientPath, "assets\\fonts");
   }
}
