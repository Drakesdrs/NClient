package com.ngames.nclient.hack.hacks;

import com.ngames.nclient.baritone.Baritone;
import com.ngames.nclient.event.Listener;
import com.ngames.nclient.event.NClientEvent;
import com.ngames.nclient.hack.Category;
import com.ngames.nclient.hack.Hack;
import com.ngames.nclient.hack.NClientHack;
import com.ngames.nclient.hack.settings.SettingValue;

@NClientHack(
   category = Category.MOVEMENT,
   description = "looking in direction that you set",
   name = "LookingForward",
   words = "LookingForward Rotate"
)
public class LookingForward extends Hack {
   private final SettingValue yaw = new SettingValue("Yaw", -90.0F, -180.0F, 180.0F);
   private final SettingValue pitch = new SettingValue("Pitch", 0.0F, -90.0F, 90.0F);

   public LookingForward() {
      this.settings = Hack.addSettings(this);
   }

   public void onEnable() {
      super.onEnable();
      new Listener(NClientEvent.RunTickKeyboardEvent.class, this);
   }

   public void onInvoke(NClientEvent event) {
      Baritone.rotatePlayer(this.yaw.getValue(), this.pitch.getValue());
   }
}
