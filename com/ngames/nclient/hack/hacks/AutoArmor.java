package com.ngames.nclient.hack.hacks;

import com.ngames.nclient.NClient;
import com.ngames.nclient.baritone.BUtils;
import com.ngames.nclient.event.Listener;
import com.ngames.nclient.event.NClientEvent;
import com.ngames.nclient.hack.Category;
import com.ngames.nclient.hack.Hack;
import com.ngames.nclient.hack.NClientHack;
import com.ngames.nclient.hack.settings.SettingBoolean;
import com.ngames.nclient.hack.settings.SettingValue;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

@NClientHack(
   category = Category.PLAYER,
   description = "Automatically equip best armor in your inventory",
   name = "AutoArmor",
   words = "AutoArmor Armor"
)
public class AutoArmor extends Hack {
   private SettingValue equipDelay = new SettingValue("EquipDelay", 100.0F, 0.0F, 3000.0F);
   private SettingBoolean equipWhenInvOpen = new SettingBoolean("EquipWhenInvOpen", false);
   private int[] bestArmorSlots = new int[4];
   private boolean isRun;

   public AutoArmor() {
      this.settings = Hack.addSettings(this);
   }

   public void onEnable() {
      super.onEnable();
      new Listener(NClientEvent.LivingUpdatedEvent.class, this);
   }

   public void onInvoke(NClientEvent event) {
      if (!this.isRun) {
         (new Thread(() -> {
            this.isRun = true;
            if (NClient.MC.field_71462_r instanceof GuiInventory && !this.equipWhenInvOpen.getValue()) {
               this.isRun = false;
            } else {
               this.getBestArmor();

               for(int i = 0; i < 4; ++i) {
                  int armorValue = this.getArmorValue(((ItemStack)NClient.MC.field_71439_g.field_71071_by.field_70460_b.get(i)).func_77973_b());
                  if (armorValue < this.bestArmorSlots[i]) {
                     if (armorValue == -1) {
                        NClient.MC.field_71442_b.func_187098_a(0, this.bestArmorSlots[i], 0, ClickType.QUICK_MOVE, NClient.MC.field_71439_g);
                     } else {
                        NClient.MC.field_71442_b.func_187098_a(0, this.bestArmorSlots[i], 0, ClickType.PICKUP, NClient.MC.field_71439_g);
                        NClient.MC.field_71442_b.func_187098_a(0, 5 + i, 0, ClickType.PICKUP, NClient.MC.field_71439_g);
                     }

                     BUtils.sleep((long)((int)this.equipDelay.getValue()));
                  }
               }

               this.isRun = false;
            }
         })).start();
      }
   }

   private void getBestArmor() {
      int[] bestArmorValues = new int[4];

      for(int slot = 0; slot < 36; ++slot) {
         ItemStack stack = NClient.MC.field_71439_g.field_71071_by.func_70301_a(slot);
         if (stack.func_190916_E() <= 1 && stack != null && stack.func_77973_b() instanceof ItemArmor) {
            ItemArmor armor = (ItemArmor)stack.func_77973_b();
            int armorType = armor.field_77881_a.ordinal() - 2;
            if (armorType != 2 || !NClient.MC.field_71439_g.field_71071_by.func_70440_f(armorType).func_77973_b().equals(Items.field_185160_cR)) {
               int armorValue = armor.field_77879_b;
               if (armorValue > bestArmorValues[armorType]) {
                  this.bestArmorSlots[armorType] = slot;
                  bestArmorValues[armorType] = armorValue;
               }
            }
         }
      }

   }

   private int getArmorValue(Item itemArmor) {
      return itemArmor == null ? -1 : ((ItemArmor)itemArmor).field_77879_b;
   }
}
