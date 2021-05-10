package com.ngames.nclient.hack.hacks;

import com.ngames.nclient.NClient;
import com.ngames.nclient.baritone.BUtils;
import com.ngames.nclient.hack.Category;
import com.ngames.nclient.hack.Hack;
import com.ngames.nclient.hack.Hacks;
import com.ngames.nclient.hack.NClientHack;
import com.ngames.nclient.hack.settings.SettingChoose;
import com.ngames.nclient.hack.settings.SettingValue;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;

@NClientHack(
   category = Category.COMBAT,
   description = "Make your punches critical",
   name = "Criticals",
   words = "Criticals damage"
)
public class Criticals extends Hack {
   public SettingChoose type = new SettingChoose("Type", (byte)1, new String[]{"Packet", "MiniJump", "OffHand"});
   private SettingValue miniJumpHeigth = new SettingValue("MiniJumpHeight", 0.15F, 0.0F, 2.0F);

   public Criticals() {
      this.settings = Hack.addSettings(this);
   }

   public static boolean miniJump(Entity target) {
      if (Hacks.criticals.isEnabled() && Hacks.criticals.type.getValue() == 1 && NClient.MC.field_71439_g.field_70122_E) {
         EntityPlayerSP var10000 = NClient.MC.field_71439_g;
         var10000.field_70181_x += (double)Hacks.criticals.miniJumpHeigth.getValue();
         (new Thread(() -> {
            while(NClient.MC.field_71439_g.field_70143_R <= 0.0F) {
               BUtils.sleep(1L);
            }

            NClient.MC.field_71439_g.func_71059_n(target);
         })).start();
         return true;
      } else {
         return false;
      }
   }
}
