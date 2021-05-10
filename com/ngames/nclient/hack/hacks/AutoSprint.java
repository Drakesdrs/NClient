package com.ngames.nclient.hack.hacks;

import com.ngames.nclient.NClient;
import com.ngames.nclient.event.Listener;
import com.ngames.nclient.event.NClientEvent;
import com.ngames.nclient.hack.Category;
import com.ngames.nclient.hack.Hack;
import com.ngames.nclient.hack.NClientHack;
import com.ngames.nclient.hack.settings.SettingBoolean;

@NClientHack(
   category = Category.MOVEMENT,
   description = "Automatically toggle sprint",
   name = "AutoSprint",
   words = "AutoSprint Sprint Speed Strafe"
)
public class AutoSprint extends Hack {
   private SettingBoolean onlyForward = new SettingBoolean("OnlyForward", true);

   public AutoSprint() {
      this.settings = Hack.addSettings(this);
   }

   public void onEnable() {
      super.onEnable();
      new Listener(NClientEvent.RunTickKeyboardEvent.class, this);
   }

   public void onInvoke(NClientEvent event) {
      boolean fow = NClient.MC.field_71474_y.field_74351_w.func_151470_d();
      boolean back = NClient.MC.field_71474_y.field_74368_y.func_151470_d();
      boolean left = NClient.MC.field_71474_y.field_74370_x.func_151470_d();
      boolean right = NClient.MC.field_71474_y.field_74366_z.func_151470_d();
      if ((fow || !this.onlyForward.getValue() && (fow || back || left || right)) && !NClient.MC.field_71439_g.func_70051_ag()) {
         NClient.MC.field_71439_g.func_70031_b(true);
      }

   }
}
