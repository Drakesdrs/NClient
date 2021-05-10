package com.ngames.nclient.hack;

public class HackUtils {
   public static String getName(Hack hack) {
      return ((NClientHack)hack.getClass().getAnnotation(NClientHack.class)).name();
   }

   public static String getDescrption(Hack hack) {
      return ((NClientHack)hack.getClass().getAnnotation(NClientHack.class)).description();
   }

   public static Category getCategory(Hack hack) {
      return ((NClientHack)hack.getClass().getAnnotation(NClientHack.class)).category();
   }

   public static String getWords(Hack hack) {
      return ((NClientHack)hack.getClass().getAnnotation(NClientHack.class)).words();
   }

   public static int getDefaultKeyBind(Hack hack) {
      return ((NClientHack)hack.getClass().getAnnotation(NClientHack.class)).keyBind();
   }
}
