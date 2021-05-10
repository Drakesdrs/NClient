package com.ngames.nclient.mixin;

import com.ngames.nclient.NClient;
import com.ngames.nclient.hack.Hacks;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.server.SPacketPlayerPosLook;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({SPacketPlayerPosLook.class})
public class MixinSPacketPlayerPosLook {
   @Shadow
   private float field_148936_d;
   @Shadow
   private float field_148937_e;

   @Inject(
      method = {"readPacketData"},
      at = {@At("TAIL")},
      cancellable = true
   )
   private void readPacketData(PacketBuffer buf, CallbackInfo info) {
      if (Hacks.noRotate.isEnabled()) {
         this.field_148936_d = NClient.MC.field_71439_g.field_70177_z;
         this.field_148937_e = NClient.MC.field_71439_g.field_70125_A;
      }

   }
}
