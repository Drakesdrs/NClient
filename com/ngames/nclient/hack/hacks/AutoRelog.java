package com.ngames.nclient.hack.hacks;

import com.ngames.nclient.NClient;
import com.ngames.nclient.baritone.BUtils;
import com.ngames.nclient.event.Listener;
import com.ngames.nclient.event.NClientEvent;
import com.ngames.nclient.hack.Category;
import com.ngames.nclient.hack.Hack;
import com.ngames.nclient.hack.NClientHack;
import com.ngames.nclient.hack.settings.SettingString;
import com.ngames.nclient.hack.settings.SettingValue;

@NClientHack(
   category = Category.PLAYER,
   description = "Automatically relogin on server",
   name = "AutoRelog",
   words = "AutoRelog relogin"
)
public class AutoRelog extends Hack {
   private final SettingString command = new SettingString("Command", "/skypvp", 255);
   private final SettingValue attemps = new SettingValue("Attemps", 100.0F, 1.0F, 100.0F);
   private final SettingValue delay = new SettingValue("Delay", 200.0F, 0.0F, 10000.0F);
   private final SettingValue radius = new SettingValue("SpawnRadius", 1.0F, 0.0F, 30000.0F);
   private final SettingValue x = new SettingValue("X", -5.0F, -3.0E7F, 3.0E7F);
   private final SettingValue y = new SettingValue("Y", 82.0F, -3.0E7F, 3.0E7F);
   private final SettingValue z = new SettingValue("Z", 43.0F, -3.0E7F, 3.0E7F);
   private boolean isRun;

   public AutoRelog() {
      this.settings = Hack.addSettings(this);
   }

   public void onEnable() {
      super.onEnable();
      new Listener(NClientEvent.PlayerJoinWorldEvent.class, this);
   }

   public void onInvoke(NClientEvent event) {
      if (!this.isRun) {
         (new Thread(() -> {
            this.isRun = true;

            for(int i = 0; (float)i < this.attemps.getValue() && this.isEnabled(); ++i) {
               BUtils.sleep((long)((int)this.delay.getValue()));
               double x = NClient.MC.field_71439_g.field_70142_S;
               double y = NClient.MC.field_71439_g.field_70137_T;
               double z = NClient.MC.field_71439_g.field_70136_U;
               if (x > (double)(this.x.getValue() - this.radius.getValue()) && x < (double)(this.x.getValue() + this.radius.getValue()) && y > (double)(this.y.getValue() - this.radius.getValue()) && y < (double)(this.y.getValue() + this.radius.getValue()) && z > (double)(this.z.getValue() - this.radius.getValue()) && z < (double)(this.z.getValue() + this.radius.getValue())) {
                  NClient.MC.field_71439_g.func_71165_d(this.command.getValue());
               }
            }

            this.isRun = false;
         })).start();
      }

   }

   public void onUpdate() {
      super.onUpdate();
      this.onInvoke(new NClientEvent.PlayerJoinWorldEvent());
   }
}
