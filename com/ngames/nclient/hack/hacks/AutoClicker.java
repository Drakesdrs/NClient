package com.ngames.nclient.hack.hacks;

import com.ngames.nclient.NClient;
import com.ngames.nclient.baritone.BUtils;
import com.ngames.nclient.baritone.Baritone;
import com.ngames.nclient.baritone.SafeThread;
import com.ngames.nclient.event.Listener;
import com.ngames.nclient.event.NClientEvent;
import com.ngames.nclient.hack.Category;
import com.ngames.nclient.hack.Hack;
import com.ngames.nclient.hack.Hacks;
import com.ngames.nclient.hack.NClientHack;
import com.ngames.nclient.hack.settings.SettingBoolean;
import com.ngames.nclient.hack.settings.SettingChoose;
import com.ngames.nclient.hack.settings.SettingValue;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@NClientHack(
   category = Category.COMBAT,
   description = "Automatically clicking when mouse button is down",
   name = "AutoClicker",
   words = "AutoClicker KillAura"
)
public class AutoClicker extends Hack {
   private final SettingChoose DelayType = new SettingChoose("DelayType", (byte)1, new String[]{"simple", "random", "advanced"});
   private final SettingValue CPSMin = new SettingValue("CPSMin", 17.0F, 1.0F, 100.0F);
   private final SettingValue CPSMax = new SettingValue("CPSMax", 21.0F, 1.0F, 100.0F);
   private final SettingChoose ClickType = new SettingChoose("ClickType", (byte)1, new String[]{"legit", "AAC"});
   private final SettingBoolean TickSync = new SettingBoolean("TickSync", true);
   boolean waitClick;

   public AutoClicker() {
      this.settings = Hack.addSettings(this);
   }

   public void onEnable() {
      super.onEnable();
      new Listener(NClientEvent.RunTickKeyboardEvent.class, this);
      (new SafeThread(() -> {
         List<Integer> delays = new ArrayList();
         if (this.DelayType.getValue() == 0) {
            delays = BUtils.genSimpleDelays(this.CPSMax.getValue());
         } else if (this.DelayType.getValue() == 1) {
            delays = BUtils.genDelayNoise((float)BUtils.randomInRange(Math.round(this.CPSMin.getValue()), Math.round(this.CPSMax.getValue())));
         } else if (this.DelayType.getValue() == 2) {
            delays = Hacks.advClickerDelays.genAdvancedDelayNoise(this.CPSMin.getValue(), this.CPSMax.getValue());
         }

         int delay;
         for(Iterator var2 = ((List)delays).iterator(); var2.hasNext(); BUtils.sleep((long)delay, BUtils.randomInRange(0, 999999))) {
            delay = (Integer)var2.next();
            if (!this.enabled) {
               break;
            }

            if (NClient.MC.field_71441_e != null && !Baritone.clickSync && NClient.MC.field_71476_x.field_72308_g != null && NClient.isLeftPressed) {
               if (this.TickSync.getValue()) {
                  this.waitClick = true;
               } else {
                  this.attack();
               }
            }
         }

      }, this)).start();
   }

   public void onInvoke(NClientEvent event) {
      if (this.enabled && event instanceof NClientEvent.RunTickKeyboardEvent && this.waitClick) {
         this.attack();
         this.waitClick = false;
      }

   }

   private void attack() {
      if (this.ClickType.getValue() == 0) {
         Baritone.leftClickMouse();
      } else if (this.ClickType.getValue() == 1) {
         Baritone.attackEntity();
      }

   }
}
