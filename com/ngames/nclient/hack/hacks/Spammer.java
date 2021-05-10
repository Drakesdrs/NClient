package com.ngames.nclient.hack.hacks;

import com.ngames.nclient.NClient;
import com.ngames.nclient.baritone.BUtils;
import com.ngames.nclient.baritone.SafeThread;
import com.ngames.nclient.hack.Category;
import com.ngames.nclient.hack.Hack;
import com.ngames.nclient.hack.NClientHack;
import com.ngames.nclient.hack.settings.SettingBoolean;
import com.ngames.nclient.hack.settings.SettingString;
import com.ngames.nclient.hack.settings.SettingValue;
import org.apache.commons.lang3.RandomStringUtils;

@NClientHack(
   category = Category.CHAT,
   description = "Sending messages in chat",
   name = "Spammer",
   words = "Spammer spam chat"
)
public class Spammer extends Hack {
   private final SettingString message = new SettingString("Message", "NClient on top!", 255);
   private final SettingValue delay = new SettingValue("Delay", 3100.0F, 0.0F, 8.64E7F);
   private final SettingBoolean random = new SettingBoolean("Random", false);

   public Spammer() {
      this.settings = Hack.addSettings(this);
   }

   public void onEnable() {
      this.onUpdate();
      this.enabled = true;
      (new SafeThread(() -> {
         NClient.MC.field_71439_g.func_71165_d(this.message.getValue() + (this.random.getValue() ? " [" + RandomStringUtils.random(10, true, true) + "]" : ""));
         BUtils.sleep((long)((int)this.delay.getValue()));
      }, this)).start();
   }
}
