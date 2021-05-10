package com.ngames.nclient.hack.hacks;

import com.ngames.nclient.hack.Category;
import com.ngames.nclient.hack.Hack;
import com.ngames.nclient.hack.NClientHack;
import com.ngames.nclient.hack.settings.SettingValue;

@NClientHack(
   category = Category.COMBAT,
   description = "Change your knockback power",
   name = "Velocity",
   words = "Velocity AntiKnockback"
)
public class Velocity extends Hack {
   public SettingValue verticalMin = new SettingValue("HorizontalMin", 0.0F, 0.0F, 10.0F);
   public SettingValue horizontalMin = new SettingValue("HorizontalMax", 0.7F, 0.0F, 10.0F);
   public SettingValue verticalMax = new SettingValue("VerticalMin", 0.0F, 0.0F, 10.0F);
   public SettingValue horizontalMax = new SettingValue("VerticalMax", 0.7F, 0.0F, 10.0F);

   public Velocity() {
      this.settings = Hack.addSettings(this);
   }
}
