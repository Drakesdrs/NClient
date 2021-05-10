package com.ngames.nclient.hack.hacks;

import com.ngames.nclient.hack.Category;
import com.ngames.nclient.hack.Hack;
import com.ngames.nclient.hack.NClientHack;

@NClientHack(
   category = Category.COMBAT,
   description = "Cancel rotations from server",
   name = "NoRotate",
   words = "NoRotate"
)
public class NoRotate extends Hack {
   public NoRotate() {
      this.settings = Hack.addSettings(this);
   }
}
