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
import com.ngames.nclient.hack.settings.SettingChoose;
import com.ngames.nclient.hack.settings.SettingValue;
import net.minecraft.init.Items;

@NClientHack(
   category = Category.PLAYER,
   description = "Automatically put totems in offhand",
   name = "AutoTotem",
   words = "AutoTotem"
)
public class AutoTotem extends Hack {
   private final SettingChoose replace = new SettingChoose("Replace", (byte)2, new String[]{"all", "empty", "exceptCrystals"});
   private final SettingValue health = new SettingValue("Health", 8.0F, 1.0F, 20.0F);
   private final SettingBoolean setBack = new SettingBoolean("SetBack", true);
   private final SettingValue setBackHealth = new SettingValue("SetBackHealth", 15.0F, 2.0F, 20.0F);
   private boolean isRun;
   private int setBackSlot = -1;

   public AutoTotem() {
      this.settings = Hack.addSettings(this);
   }

   public void onEnable() {
      super.onEnable();
      new Listener(NClientEvent.LivingUpdatedEvent.class, this);
      new Listener(NClientEvent.PlayerHealthChangeEvent.class, this);
   }

   public void onInvoke(NClientEvent event) {
      if (!this.isRun || !(event instanceof NClientEvent.PlayerHealthChangeEvent)) {
         if (event instanceof NClientEvent.LivingUpdatedEvent) {
            if (!Baritone.isInOffHand(Items.field_190929_cY)) {
               int slotFor = Baritone.getSlotFor(Items.field_190929_cY, false);
               if (slotFor != -1) {
                  if (NClient.MC.field_71439_g.func_110143_aJ() <= this.health.getValue() && (this.replace.getValue() == 1 && Baritone.isEmptyInOffHand() || this.replace.getValue() == 0 || this.replace.getValue() == 2 && !Baritone.isInOffHand(Items.field_185158_cP))) {
                     Baritone.putInOffHand(slotFor);
                  }

                  this.setBackSlot = slotFor;
               }
            }
         } else {
            (new Thread(() -> {
               this.isRun = true;

               while(this.enabled && NClient.MC.field_71441_e != null && this.setBack.getValue() && this.setBackSlot != -1) {
                  if (NClient.MC.field_71439_g.func_110143_aJ() >= this.setBackHealth.getValue()) {
                     Baritone.putInOffHand(this.setBackSlot);
                     break;
                  }

                  BUtils.sleep(50L);
               }

               this.isRun = false;
            })).start();
         }

      }
   }
}
