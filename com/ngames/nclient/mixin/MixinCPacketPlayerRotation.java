package com.ngames.nclient.mixin;

import com.ngames.nclient.NClient;
import com.ngames.nclient.baritone.Baritone;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.client.CPacketPlayer.Rotation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({Rotation.class})
public class MixinCPacketPlayerRotation {
   @Inject(
      method = {"writePacketData"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void writePacketData(PacketBuffer buf, CallbackInfo info) {
      if (Baritone.overrideRotation) {
         buf.writeFloat(Baritone.yaw);
         buf.writeFloat(Baritone.pitch);
         buf.writeByte(NClient.MC.field_71439_g.field_70122_E ? 1 : 0);
         info.cancel();
      }

   }
}
