package com.ngames.nclient.hack.hacks;

import com.ngames.nclient.hack.Category;
import com.ngames.nclient.hack.Hack;
import com.ngames.nclient.hack.NClientHack;
import com.ngames.nclient.hack.settings.SettingString;
import java.util.ArrayList;
import java.util.List;

@NClientHack(
   category = Category.CHAT,
   description = "Automatically say EZZ when you kill player",
   name = "AutoEz",
   words = "AutoEz KillAura Ez"
)
public class AutoEz extends Hack {
   public SettingString message = new SettingString("Message", "{PLAYER}, you just got EZZZZZ niggered by NClient!", 255);
   public List<Integer> targeted = new ArrayList();

   public AutoEz() {
      this.settings = Hack.addSettings(this);
   }
}
