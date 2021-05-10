package com.ngames.nclient.hack.hacks;

import com.ngames.nclient.hack.Category;
import com.ngames.nclient.hack.Hack;
import com.ngames.nclient.hack.NClientHack;
import com.ngames.nclient.hack.settings.SettingValue;

@NClientHack(
   category = Category.COMBAT,
   description = "Set your reach distance",
   name = "Reach",
   words = "Reach"
)
public class Reach extends Hack {
   public final SettingValue distance = new SettingValue("Distance", 6.0F, 1.0F, 6.0F);

   public Reach() {
      this.settings = Hack.addSettings(this);
   }
}
