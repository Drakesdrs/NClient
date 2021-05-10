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

@NClientHack(
   category = Category.PLAYER,
   description = "Automatically eat soup",
   name = "AutoSoup",
   words = "AutoSoup Regen"
)
public class AutoSoup extends Hack {
   private final SettingValue health = new SettingValue("Health", 12.0F, 1.0F, 19.0F);
   private final SettingValue stopHealth = new SettingValue("stopHealth", 18.0F, 1.0F, 20.0F);
   private final SettingValue attemps = new SettingValue("Attemps", 5.0F, 1.0F, 1000.0F);
   private final SettingValue useMinDelay = new SettingValue("UseMinDelay", 30.0F, 0.0F, 1000.0F);
   private final SettingValue useMaxDelay = new SettingValue("UseMaxDelay", 50.0F, 0.0F, 1000.0F);
   private final SettingBoolean fastUse = new SettingBoolean("FastUse", true);
   private final SettingBoolean onlyHotbar = new SettingBoolean("OnlyHotbar", false);
   private final SettingBoolean setBack = new SettingBoolean("SetBack", true);
   private final SettingValue setBackSlot = new SettingValue("SetBackSlot", 0.0F, 0.0F, 8.0F);
   private boolean isRun;

   public AutoSoup() {
      this.settings = Hack.addSettings(this);
   }

   public void onEnable() {
      super.onEnable();
      new Listener(NClientEvent.PlayerHealthChangeEvent.class, this);
   }

   public void onInvoke(NClientEvent event) {
      if (!this.isRun) {
         (new Thread(() -> {
            this.isRun = true;
            if (NClient.MC.field_71439_g.func_110143_aJ() <= this.health.getValue()) {
               int prevSlot = (int)this.setBackSlot.getValue();
               int slot = Baritone.getSlotFor(Items.field_151009_A, this.onlyHotbar.getValue());
               if (slot != -1) {
                  int slot2 = Baritone.getSlotFor(Items.field_151054_z, true);
                  if (slot2 != -1) {
                     Baritone.setMainHand(slot2);
                     Baritone.dropCurrentItem();
                  }

                  if (!this.onlyHotbar.getValue() && slot > 8) {
                     Baritone.putInAir(slot);
                  }

                  slot = Baritone.getSlotFor(Items.field_151009_A, true);
                  if (slot != -1 && slot < 9) {
                     Baritone.setMainHand(slot);
                     this.eat();
                     if (Baritone.isInMainHand(Items.field_151054_z) || Baritone.isInMainHand(Items.field_151009_A)) {
                        Baritone.dropCurrentItem();
                     }

                     if (this.setBack.getValue()) {
                        Baritone.setMainHand(prevSlot);
                     }
                  }
               }
            }

            this.isRun = false;
         })).start();
      }

   }

   private void eat() {
      Baritone.clickSync = true;

      for(int i = 0; NClient.MC.field_71439_g.func_110143_aJ() < this.stopHealth.getValue() && (float)i < this.attemps.getValue(); ++i) {
         if (this.fastUse.getValue() && Baritone.isInMainHand(Items.field_151009_A)) {
            Baritone.rightClickMouse();
            BUtils.sleep((long)BUtils.randomInRange((int)this.useMinDelay.getValue(), (int)this.useMaxDelay.getValue()));
         }

         if (!this.fastUse.getValue() && Baritone.isInMainHand(Items.field_151009_A)) {
            Baritone.useItem();
            BUtils.sleep((long)BUtils.randomInRange((int)this.useMinDelay.getValue(), (int)this.useMaxDelay.getValue()));
         }

         if (!this.fastUse.getValue()) {
            Baritone.usedItem();
         }
      }

      Baritone.clickSync = false;
   }

   public void onUpdate() {
      super.onUpdate();
      this.onInvoke(new NClientEvent.PlayerHealthChangeEvent());
   }
}
