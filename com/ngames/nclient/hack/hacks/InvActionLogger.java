package com.ngames.nclient.hack.hacks;

import com.ngames.nclient.hack.Category;
import com.ngames.nclient.hack.Hack;
import com.ngames.nclient.hack.NClientHack;

@NClientHack(
   category = Category.PLAYER,
   description = "Send info about actions in your inventory to chat.",
   name = "InvActionLogger",
   words = "InvActionLogger"
)
public class InvActionLogger extends Hack {
   public InvActionLogger() {
      this.settings = Hack.addSettings(this);
   }
}
