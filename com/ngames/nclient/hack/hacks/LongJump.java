package com.ngames.nclient.hack.hacks;

import com.ngames.nclient.NClient;
import com.ngames.nclient.event.Listener;
import com.ngames.nclient.event.NClientEvent;
import com.ngames.nclient.hack.Category;
import com.ngames.nclient.hack.Hack;
import com.ngames.nclient.hack.Hacks;
import com.ngames.nclient.hack.NClientHack;
import com.ngames.nclient.hack.settings.SettingValue;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.init.MobEffects;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.common.ForgeHooks;

@NClientHack(
   category = Category.MOVEMENT,
   description = "Change your jumps length",
   name = "LongJump",
   words = "LongJump JumpLength Speed"
)
public class LongJump extends Hack {
   private SettingValue length = new SettingValue("Length", 1.1F, 0.0F, 100.0F);

   public LongJump() {
      this.settings = Hack.addSettings(this);
   }

   public void onEnable() {
      super.onEnable();
      new Listener(NClientEvent.PlayerJumpEvent.class, this);
   }

   public void onInvoke(NClientEvent event) {
      NClient.MC.field_71439_g.field_70181_x = Hacks.highJump.isEnabled() ? (double)(0.42F * Hacks.highJump.height.getValue()) : 0.41999998688697815D;
      EntityPlayerSP var10000;
      if (NClient.MC.field_71439_g.func_70644_a(MobEffects.field_76430_j)) {
         var10000 = NClient.MC.field_71439_g;
         var10000.field_70181_x += (double)((float)(NClient.MC.field_71439_g.func_70660_b(MobEffects.field_76430_j).func_76458_c() + 1) * 0.1F);
      }

      float f = NClient.MC.field_71439_g.field_70177_z * 0.017453292F;
      if (NClient.MC.field_71439_g.func_70051_ag()) {
         var10000 = NClient.MC.field_71439_g;
         var10000.field_70159_w -= (double)(MathHelper.func_76126_a(f) * 0.2F);
         var10000 = NClient.MC.field_71439_g;
         var10000.field_70179_y += (double)(MathHelper.func_76134_b(f) * 0.2F);
      }

      var10000 = NClient.MC.field_71439_g;
      var10000.field_70159_w *= (double)this.length.getValue();
      var10000 = NClient.MC.field_71439_g;
      var10000.field_70179_y *= (double)this.length.getValue();
      NClient.MC.field_71439_g.field_70160_al = true;
      ForgeHooks.onLivingJump(NClient.MC.field_71439_g);
      event.setCalceled();
   }
}
