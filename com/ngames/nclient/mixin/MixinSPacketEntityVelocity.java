package com.ngames.nclient.mixin;

import com.ngames.nclient.NClient;
import com.ngames.nclient.baritone.BUtils;
import com.ngames.nclient.hack.Hacks;
import com.ngames.nclient.hack.hacks.Velocity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.server.SPacketEntityVelocity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({SPacketEntityVelocity.class})
public class MixinSPacketEntityVelocity {
   @Shadow
   private int field_149417_a;
   @Shadow
   private int field_149415_b;
   @Shadow
   private int field_149416_c;
   @Shadow
   private int field_149414_d;

   @Inject(
      method = {"readPacketData"},
      at = {@At("TAIL")},
      cancellable = true
   )
   private void readPacketData(PacketBuffer buf, CallbackInfo info) {
      Velocity hack = Hacks.velocity;
      if (hack.isEnabled() && this.field_149417_a == NClient.MC.field_71439_g.func_145782_y()) {
         this.field_149415_b = (int)((float)this.field_149415_b * BUtils.randomInRange(hack.horizontalMin.getValue(), hack.horizontalMax.getValue()));
         this.field_149416_c = (int)((float)this.field_149416_c * BUtils.randomInRange(hack.verticalMin.getValue(), hack.verticalMax.getValue()));
         this.field_149414_d = (int)((float)this.field_149414_d * BUtils.randomInRange(hack.horizontalMin.getValue(), hack.horizontalMax.getValue()));
      }

   }
}
