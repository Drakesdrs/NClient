package com.ngames.nclient.hack.hacks;

import com.ngames.nclient.NClient;
import com.ngames.nclient.baritone.BUtils;
import com.ngames.nclient.baritone.Baritone;
import com.ngames.nclient.event.Listener;
import com.ngames.nclient.event.NClientEvent;
import com.ngames.nclient.hack.Category;
import com.ngames.nclient.hack.Hack;
import com.ngames.nclient.hack.NClientHack;
import com.ngames.nclient.hack.settings.SettingBoolean;
import com.ngames.nclient.hack.settings.SettingValue;
import net.minecraft.init.Items;
import net.minecraft.potion.Potion;

@NClientHack(
   category = Category.PLAYER,
   description = "Automatically eat gapples",
   name = "AutoGapple",
   words = "AutoGapple AutoEat"
)
public class AutoGapple extends Hack {
   private final SettingBoolean allowInPvP = new SettingBoolean("AllowInPvP", false);
   private final SettingValue health = new SettingValue("Health", 10.0F, 0.0F, 20.0F);
   private final SettingBoolean regenEffect = new SettingBoolean("RegenEffect", true);
   private final SettingBoolean fireProtectEffect = new SettingBoolean("FireProtectEffect", false);
   private final SettingBoolean offHand = new SettingBoolean("OffHand", false);
   private final SettingBoolean onlyHotbar = new SettingBoolean("OnlyHotbar", false);
   private final SettingBoolean setBack = new SettingBoolean("SetBack", true);
   private boolean isRun = false;

   public AutoGapple() {
      this.settings = Hack.addSettings(this);
   }

   public void onEnable() {
      this.onUpdate();
      new Listener(NClientEvent.PlayerFoodStatsChangeEvent.class, this);
      new Listener(NClientEvent.PotionEffectRemovedEvent.class, this);
      new Listener(NClientEvent.PlayerHealthChangeEvent.class, this);
      this.enabled = true;
   }

   private boolean calcUsing() {
      return (this.allowInPvP.getValue() || !NClient.inPvP) && (this.health.getValue() >= NClient.MC.field_71439_g.func_110143_aJ() || !NClient.MC.field_71439_g.func_70644_a(Potion.func_188412_a(10)) && this.regenEffect.getValue() || !NClient.MC.field_71439_g.func_70644_a(Potion.func_188412_a(12)) && this.fireProtectEffect.getValue());
   }

   public void onInvoke(NClientEvent event) {
      if (!this.isRun) {
         (new Thread(() -> {
            this.isRun = true;
            if (this.calcUsing()) {
               int slotIn = Baritone.getSlotFor(Items.field_151153_ao, this.onlyHotbar.getValue());
               int currSlot = NClient.MC.field_71439_g.field_71071_by.field_70461_c;
               if (slotIn != -1) {
                  if (slotIn > 8 && !this.onlyHotbar.getValue()) {
                     Baritone.putInMainHand(slotIn);
                  } else if (slotIn < 9) {
                     Baritone.setMainHand(slotIn);
                  } else if (this.offHand.getValue()) {
                     Baritone.putInOffHand(slotIn);
                  } else {
                     slotIn = -1;
                  }
               }

               if (slotIn != -1) {
                  Baritone.useItem();

                  while(this.calcUsing() && this.enabled) {
                     BUtils.sleep(100L);
                  }

                  Baritone.usedItem();
               }

               if (this.setBack.getValue()) {
                  if (this.offHand.getValue()) {
                     Baritone.putInOffHand(slotIn);
                  } else if (slotIn != -1) {
                     if (slotIn < 9) {
                        Baritone.setMainHand(currSlot);
                     } else {
                        Baritone.putInMainHand(slotIn);
                     }
                  }
               }
            }

            this.isRun = false;
         })).start();
      }

   }

   public void onUpdate() {
      super.onUpdate();
      this.onInvoke(new NClientEvent.PlayerFoodStatsChangeEvent());
   }
}
