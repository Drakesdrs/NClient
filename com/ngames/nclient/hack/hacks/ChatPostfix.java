package com.ngames.nclient.hack.hacks;

import com.ngames.nclient.hack.Category;
import com.ngames.nclient.hack.Hack;
import com.ngames.nclient.hack.NClientHack;
import com.ngames.nclient.hack.settings.SettingString;

@NClientHack(
   category = Category.CHAT,
   description = "Add postfix in your messages",
   name = "ChatPostfix",
   words = "ChatPostfix Postfix ChatModdifications Chat"
)
public class ChatPostfix extends Hack {
   public SettingString postfix = new SettingString("Postfix", " » ɴ-ᴄʟɪᴇɴᴛ", 255);

   public ChatPostfix() {
      this.settings = Hack.addSettings(this);
   }
}
