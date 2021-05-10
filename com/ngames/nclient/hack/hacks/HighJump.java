package com.ngames.nclient.hack.hacks;

import com.ngames.nclient.hack.Category;
import com.ngames.nclient.hack.Hack;
import com.ngames.nclient.hack.NClientHack;
import com.ngames.nclient.hack.settings.SettingValue;

@NClientHack(
   category = Category.MOVEMENT,
   description = "Change your jumps height",
   name = "HighJump",
   words = "HighJump SlimeJump JumpHeight"
)
public class HighJump extends Hack {
   public SettingValue height = new SettingValue("Height", 2.0F, 0.0F, 100.0F);

   public HighJump() {
      this.settings = Hack.addSettings(this);
   }
}
