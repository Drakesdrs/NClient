package com.ngames.nclient.mixin;

import com.ngames.nclient.NClient;
import com.ngames.nclient.hack.Hacks;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.entity.Entity;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.network.play.server.SPacketDestroyEntities;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({SPacketDestroyEntities.class})
public class MixinSPacketDestroyEntities {
   @Shadow
   private int[] field_149100_a;

   @Inject(
      method = {"processPacket"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void processPacket(INetHandlerPlayClient handler, CallbackInfo info) {
      int[] var3 = this.field_149100_a;
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         int id = var3[var5];
         Entity e = NClient.MC.field_71441_e.func_73045_a(id);
         if (e instanceof EntityOtherPlayerMP && Hacks.autoEz.isEnabled() && Hacks.autoEz.targeted.contains(id)) {
            NClient.MC.field_71439_g.func_71165_d(Hacks.autoEz.message.getValue().replace("{PLAYER}", e.func_70005_c_()));
            Hacks.autoEz.targeted.remove(id);
         }
      }

   }
}
