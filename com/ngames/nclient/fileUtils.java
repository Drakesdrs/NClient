package com.ngames.nclient;

import com.ngames.nclient.hack.Hack;
import com.ngames.nclient.hack.HackUtils;
import com.ngames.nclient.hack.settings.Setting;
import com.ngames.nclient.hack.settings.SettingBoolean;
import com.ngames.nclient.hack.settings.SettingChoose;
import com.ngames.nclient.hack.settings.SettingString;
import com.ngames.nclient.hack.settings.SettingValue;
import com.ngames.nclient.hack.settings.Settings;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;
import org.apache.commons.io.FileUtils;

public class fileUtils {
   public static void saveAll() {
      String output = "";

      for(Iterator var1 = NClient.HackList.iterator(); var1.hasNext(); output = output + "\n") {
         Hack h = (Hack)var1.next();
         output = output + HackUtils.getName(h) + "%D%";
         output = output + String.valueOf(h.isEnabled()) + "%D%";

         for(Iterator var3 = h.settings.iterator(); var3.hasNext(); output = output + "%D%") {
            Setting s = (Setting)var3.next();
            output = output + s.name + "%SD%";
            if (s.type == Settings.BOOLEAN) {
               output = output + String.valueOf(((SettingBoolean)s).getValue());
            } else if (s.type == Settings.CHOOSE) {
               output = output + String.valueOf(((SettingChoose)s).getValue());
            } else if (s.type == Settings.STRING_TYPE) {
               output = output + ((SettingString)s).getValue();
            } else if (s.type == Settings.VALUE_TYPE) {
               output = output + String.valueOf(((SettingValue)s).getValue());
            }
         }

         if (output.charAt(output.length() - 1) == '%') {
            output = output.substring(0, output.length() - 3);
         }
      }

      try {
         FileUtils.write(NClient.settings, output, "UTF-8");
      } catch (IOException var5) {
         var5.printStackTrace();
      }

   }

   public static void loadAll() {
      List input = null;

      try {
         input = FileUtils.readLines(NClient.settings, "UTF-8");
      } catch (IOException var13) {
         var13.printStackTrace();
      }

      if (input != null && !input.isEmpty()) {
         Iterator var1 = input.iterator();

         while(var1.hasNext()) {
            String s = (String)var1.next();
            String[] params = s.split(Pattern.quote("%D%"));
            String name = params[0];
            boolean isEnabled = Boolean.valueOf(params[1]);
            Hack hack = NClient.getHack(name);

            for(int i = 2; i < params.length; ++i) {
               String[] setting = params[i].split(Pattern.quote("%SD%"));
               String settingName = setting[0];
               String settingValue = setting[1];
               Iterator var11 = hack.settings.iterator();

               while(var11.hasNext()) {
                  Setting stng = (Setting)var11.next();
                  if (stng.name.equals(settingName)) {
                     if (stng.type == Settings.BOOLEAN) {
                        ((SettingBoolean)stng).setValue(Boolean.valueOf(settingValue));
                     } else if (stng.type == Settings.CHOOSE) {
                        ((SettingChoose)stng).setValue(Byte.valueOf(settingValue));
                     } else if (stng.type == Settings.STRING_TYPE) {
                        ((SettingString)stng).setValue(String.valueOf(settingValue));
                     } else if (stng.type == Settings.VALUE_TYPE) {
                        ((SettingValue)stng).setValue(Float.valueOf(settingValue));
                     }
                  }
               }
            }

            if (!hack.isEnabled() && isEnabled) {
               hack.onEnable();
            }
         }

      }
   }
}
