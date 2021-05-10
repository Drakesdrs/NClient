package com.ngames.nclient.hack.hacks;

import com.ngames.nclient.NClient;
import com.ngames.nclient.baritone.BUtils;
import com.ngames.nclient.baritone.Baritone;
import com.ngames.nclient.baritone.SafeThread;
import com.ngames.nclient.hack.Category;
import com.ngames.nclient.hack.Hack;
import com.ngames.nclient.hack.Hacks;
import com.ngames.nclient.hack.NClientHack;
import com.ngames.nclient.hack.settings.SettingBoolean;
import com.ngames.nclient.hack.settings.SettingValue;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;

@NClientHack(
   category = Category.COMBAT,
   description = "Automatically aiming at the entity",
   name = "AimAssist",
   words = "Aim AimAssist AimAssistent Aimbot KillAura"
)
public class AimAssist extends Hack {
   private SettingValue updateDelay = new SettingValue("UpdateDelay", 10.0F, 0.0F, 1000.0F);
   private SettingBoolean auraTarget = new SettingBoolean("AuraTarget", true);
   private SettingValue range = new SettingValue("Range", 4.0F, 0.0F, 20.0F);
   private final SettingBoolean rtOnlyNotMO = new SettingBoolean("RTOnlyNotMO", true);
   private Entity target;

   public AimAssist() {
      this.settings = addSettings(this);
   }

   public void onEnable() {
      super.onEnable();
      (new SafeThread(() -> {
         if (this.auraTarget.getValue()) {
            this.target = Hacks.killAura18.target;
         } else {
            this.target = Baritone.getPriorityTarget(this.range.getValue(), true, false, false, (Entity)null, (byte)1);
         }

         if (this.target != null) {
            boolean rtr = BUtils.rayTraceBlocks(NClient.MC.field_71439_g.func_174824_e(NClient.MC.func_184121_ak()), new Vec3d(this.target.field_70165_t + (double)this.target.field_70130_N * 0.5D, this.target.field_70163_u + (double)this.target.field_70131_O * 0.5D, this.target.field_70161_v + (double)this.target.field_70130_N * 0.5D));
            if (rtr && Baritone.isAlive(this.target) && NClient.MC.field_71439_g.func_70032_d(this.target) <= this.range.getValue() && (Hacks.killAura18.isEnabled() || !this.auraTarget.getValue()) && (this.rtOnlyNotMO.getValue() && NClient.MC.field_71476_x.field_72308_g != null || !this.rtOnlyNotMO.getValue())) {
               Baritone.setRotationToEntity(this.target);
            }
         }

         BUtils.sleep((long)((int)this.updateDelay.getValue()));
      }, this)).start();
   }
}
