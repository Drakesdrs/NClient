package com.ngames.nclient.hack.hacks;

import com.ngames.nclient.hack.Category;
import com.ngames.nclient.hack.Hack;
import com.ngames.nclient.hack.NClientHack;
import com.ngames.nclient.hack.settings.SettingString;

@NClientHack(
   category = Category.CHAT,
   description = "Add prefix in your messages",
   name = "ChatPrefix",
   words = "ChatPrefix prefix ChatModdifications Chat"
)
public class ChatPrefix extends Hack {
   public SettingString prefix = new SettingString("Prefix", " > ", 255);

   public ChatPrefix() {
      this.settings = Hack.addSettings(this);
   }
}
