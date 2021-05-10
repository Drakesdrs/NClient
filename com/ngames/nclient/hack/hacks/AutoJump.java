package com.ngames.nclient.hack.hacks;

import com.ngames.nclient.baritone.Baritone;
import com.ngames.nclient.event.Listener;
import com.ngames.nclient.event.NClientEvent;
import com.ngames.nclient.hack.Category;
import com.ngames.nclient.hack.Hack;
import com.ngames.nclient.hack.NClientHack;

@NClientHack(
   category = Category.MOVEMENT,
   description = "Automatically jumping",
   name = "AutoJump",
   words = "AutoJump AntiAFK"
)
public class AutoJump extends Hack {
   public AutoJump() {
      this.settings = Hack.addSettings(this);
   }

   public void onEnable() {
      super.onEnable();
      new Listener(NClientEvent.RunTickKeyboardEvent.class, this);
   }

   public void onInvoke(NClientEvent event) {
      Baritone.setJumping(this.enabled);
   }
}
