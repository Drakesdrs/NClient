package com.ngames.nclient.hack.hacks;

import com.ngames.nclient.NClient;
import com.ngames.nclient.baritone.Baritone;
import com.ngames.nclient.event.Listener;
import com.ngames.nclient.event.NClientEvent;
import com.ngames.nclient.hack.Category;
import com.ngames.nclient.hack.Hack;
import com.ngames.nclient.hack.NClientHack;
import com.ngames.nclient.hack.settings.SettingBoolean;
import java.lang.reflect.Field;
import net.minecraft.client.entity.EntityPlayerSP;

@NClientHack(
   category = Category.MOVEMENT,
   description = "Automatically press shift (sneaking)",
   name = "Sneak",
   words = "Sneak AutoSneak Shift"
)
public class Sneak extends Hack {
   public SettingBoolean onlyServer = new SettingBoolean("OnlyServer", true);

   public Sneak() {
      this.settings = Hack.addSettings(this);
   }

   public void onEnable() {
      super.onEnable();
      new Listener(NClientEvent.OnPlayerUpdateEvent.class, this);
      new Listener(NClientEvent.PlayerJoinWorldEvent.class, this);
   }

   public void onInvoke(NClientEvent event) {
      if (event instanceof NClientEvent.OnPlayerUpdateEvent && !this.onlyServer.getValue()) {
         Baritone.setSneaking(this.enabled);
      }

      if (event instanceof NClientEvent.PlayerJoinWorldEvent && this.enabled && this.onlyServer.getValue()) {
         Field f = null;

         try {
            f = EntityPlayerSP.class.getDeclaredField("field_175170_bN");
         } catch (SecurityException | NoSuchFieldException var5) {
            var5.printStackTrace();
         }

         f.setAccessible(true);

         try {
            f.setBoolean(NClient.MC.field_71439_g, false);
         } catch (IllegalAccessException | IllegalArgumentException var4) {
            var4.printStackTrace();
         }
      }

   }
}
