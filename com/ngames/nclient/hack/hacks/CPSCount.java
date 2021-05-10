package com.ngames.nclient.hack.hacks;

import com.ngames.nclient.baritone.BUtils;
import com.ngames.nclient.baritone.SafeThread;
import com.ngames.nclient.hack.Category;
import com.ngames.nclient.hack.Hack;
import com.ngames.nclient.hack.InHUDValue;
import com.ngames.nclient.hack.NClientHack;

@NClientHack(
   category = Category.COMBAT,
   description = "Display your current CPS",
   name = "CPSCount",
   words = "CPS CPSCount KillAura AutoClicker ClickerDelays"
)
public class CPSCount extends Hack {
   public int clicks;
   public float cps;

   public CPSCount() {
      this.settings = Hack.addSettings(this);
      this.inHud = new InHUDValue(0.0F);
   }

   public void onEnable() {
      super.onEnable();
      (new SafeThread(() -> {
         BUtils.sleep(500L);
         this.cps = (float)(this.clicks * 2);
         this.inHud.set(this.cps);
         this.clicks = 0;
      }, this)).start();
   }
}
