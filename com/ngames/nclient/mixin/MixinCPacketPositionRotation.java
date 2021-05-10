package com.ngames.nclient.mixin;

import com.ngames.nclient.NClient;
import com.ngames.nclient.baritone.Baritone;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.client.CPacketPlayer.PositionRotation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({PositionRotation.class})
public class MixinCPacketPositionRotation {
   @Inject(
      method = {"writePacketData"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void writePacketData(PacketBuffer buf, CallbackInfo info) {
      if (Baritone.overrideRotation) {
         buf.writeDouble(NClient.MC.field_71439_g.field_70165_t);
         buf.writeDouble(NClient.MC.field_71439_g.func_174813_aQ().field_72338_b);
         buf.writeDouble(NClient.MC.field_71439_g.field_70161_v);
         buf.writeFloat(Baritone.yaw);
         buf.writeFloat(Baritone.pitch);
         buf.writeByte(NClient.MC.field_71439_g.field_70122_E ? 1 : 0);
         info.cancel();
      }

   }
}
