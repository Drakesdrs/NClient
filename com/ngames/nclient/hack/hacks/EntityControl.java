package com.ngames.nclient.hack.hacks;

import com.ngames.nclient.hack.Category;
import com.ngames.nclient.hack.Hack;
import com.ngames.nclient.hack.NClientHack;

@NClientHack(
   category = Category.MOVEMENT,
   description = "Allows you to control entities without saddle",
   name = "EntityControl",
   words = "EntityControl"
)
public class EntityControl extends Hack {
   public EntityControl() {
      this.settings = Hack.addSettings(this);
   }
}
