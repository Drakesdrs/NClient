package com.ngames.nclient.hack.hacks;

import com.ngames.nclient.baritone.BUtils;
import com.ngames.nclient.baritone.Baritone;
import com.ngames.nclient.baritone.SafeThread;
import com.ngames.nclient.hack.Category;
import com.ngames.nclient.hack.Hack;
import com.ngames.nclient.hack.NClientHack;
import com.ngames.nclient.hack.settings.SettingBoolean;
import com.ngames.nclient.hack.settings.SettingValue;

@NClientHack(
   category = Category.MOVEMENT,
   description = "Automatically spin around",
   name = "AutoSpin",
   words = "AutoSpin Spin SpinAround"
)
public class AutoSpin extends Hack {
   private final SettingValue speed = new SettingValue("Speed", 3.0F, 1.0F, 10.0F);
   private final SettingBoolean onlyServer = new SettingBoolean("OnlyServer", true);

   public AutoSpin() {
      this.settings = Hack.addSettings(this);
   }

   public void onEnable() {
      super.onEnable();
      Baritone.refreshRotation();
      Baritone.overrideRotation = true;
      (new SafeThread(() -> {
         Baritone.addRotationPlayer(1.1F - this.speed.getValue(), 0.0F, this.onlyServer.getValue());
         BUtils.sleep((long)(11.0F - this.speed.getValue()));
         Baritone.overrideRotation = true;
      }, this)).start();
   }

   public void onDisable() {
      Baritone.overrideRotation = false;
      Baritone.refreshRotation();
      super.onDisable();
   }
}
