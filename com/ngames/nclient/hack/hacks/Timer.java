package com.ngames.nclient.hack.hacks;

import com.ngames.nclient.baritone.Baritone;
import com.ngames.nclient.hack.Category;
import com.ngames.nclient.hack.Hack;
import com.ngames.nclient.hack.NClientHack;
import com.ngames.nclient.hack.settings.SettingValue;

@NClientHack(
   category = Category.WORLD,
   description = "Change minecraft tick speed",
   name = "Timer",
   words = "Timer speed"
)
public class Timer extends Hack {
   private SettingValue speed = new SettingValue("Speed", 4.0F, 0.0F, 50.0F);

   public Timer() {
      this.settings = Hack.addSettings(this);
   }

   public void onEnable() {
      Baritone.setTimer(new net.minecraft.util.Timer(20.0F * this.speed.getValue()));
      super.onEnable();
   }

   public void onDisable() {
      Baritone.setTimer(new net.minecraft.util.Timer(20.0F));
      super.onDisable();
   }

   public void onUpdate() {
      Baritone.setTimer(new net.minecraft.util.Timer(20.0F * this.speed.getValue()));
      super.onUpdate();
   }
}
