package com.ngames.nclient.hack.hacks;

import com.ngames.nclient.NClient;
import com.ngames.nclient.gui.Hud;
import com.ngames.nclient.hack.Category;
import com.ngames.nclient.hack.Hack;
import com.ngames.nclient.hack.NClientHack;
import com.ngames.nclient.hack.settings.SettingChoose;
import com.ngames.nclient.hack.settings.SettingValue;

@NClientHack(
   category = Category.RENDER,
   description = "Ingame HUD",
   name = "HUD",
   words = "HUD InGameGUI watermark"
)
public class HUD extends Hack {
   public final SettingChoose theme = new SettingChoose("Theme", (byte)0, new String[]{"RG", "RB", "BlackWhite", "RGB"});
   private final SettingValue x = new SettingValue("X", 10.0F, 0.0F, 8096.0F);
   private final SettingValue y = new SettingValue("Y", 10.0F, 0.0F, 8096.0F);

   public HUD() {
      this.settings = Hack.addSettings(this);
   }

   public void onEnable() {
      super.onEnable();
      Hud.mainColor.setTheme(this.theme.getValue());
      Hud.x = (int)this.x.getValue();
      Hud.y = (int)this.y.getValue();
      Hud.updateTheme();
      Hud.enable();
   }

   public void onUpdate() {
      super.onUpdate();
      if (NClient.MC.field_71462_r != null) {
         this.x.max = (float)NClient.MC.field_71462_r.field_146294_l;
         this.y.max = (float)NClient.MC.field_71462_r.field_146295_m;
      }

      Hud.updateTheme();
      Hud.x = (int)this.x.getValue();
      Hud.y = (int)this.y.getValue();
   }
}
