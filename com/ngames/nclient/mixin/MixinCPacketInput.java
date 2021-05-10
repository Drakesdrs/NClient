package com.ngames.nclient.mixin;

import com.ngames.nclient.hack.Hacks;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.client.CPacketInput;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({CPacketInput.class})
public class MixinCPacketInput {
   @Shadow
   private boolean field_149621_d;

   @Inject(
      method = {"writePacketData"},
      at = {@At("HEAD")}
   )
   private void writePacketData(PacketBuffer buf, CallbackInfo info) {
      if (Hacks.sneak.isEnabled() && Hacks.sneak.onlyServer.getValue()) {
         this.field_149621_d = true;
      }

   }
}
