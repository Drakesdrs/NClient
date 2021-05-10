package com.ngames.nclient.hack;

import com.ngames.nclient.fileUtils;
import com.ngames.nclient.event.NClientEvent;
import com.ngames.nclient.hack.settings.Setting;
import com.ngames.nclient.hack.settings.SettingBoolean;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public abstract class Hack {
   protected boolean enabled;
   public List<Setting> settings = new ArrayList();
   public InHUDValue inHud;
   public final SettingBoolean hidden = new SettingBoolean("Hidden", false);

   public Hack() {
      this.settings = addSettings(this);
   }

   public void onToggle() {
      if (this.enabled) {
         this.onDisable();
      } else {
         this.onEnable();
      }

      fileUtils.saveAll();
   }

   public void onDisable() {
      this.enabled = false;
   }

   public void onEnable() {
      this.onUpdate();
      this.enabled = true;
   }

   public boolean isEnabled() {
      return this.enabled;
   }

   public void onUpdate() {
      fileUtils.saveAll();
   }

   public void onInvoke(NClientEvent event) {
   }

   public static List<Setting> addSettings(Hack hack) {
      List<Setting> ret = new ArrayList();
      Field[] var2 = hack.getClass().getDeclaredFields();
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         Field f = var2[var4];
         if (f.getType().getSuperclass() == Setting.class) {
            f.setAccessible(true);

            try {
               ret.add((Setting)f.get(hack));
            } catch (IllegalAccessException | IllegalArgumentException var7) {
               var7.printStackTrace();
            }
         }
      }

      ret.add(hack.hidden);
      return ret;
   }
}
