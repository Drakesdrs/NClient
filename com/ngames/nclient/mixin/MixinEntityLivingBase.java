package com.ngames.nclient.mixin;

import com.ngames.nclient.event.NClientEvent;
import com.ngames.nclient.hack.Hacks;
import net.minecraft.entity.EntityLivingBase;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({EntityLivingBase.class})
public class MixinEntityLivingBase {
   @Inject(
      method = {"getJumpUpwardsMotion"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void getJumpUpwardsMotion(CallbackInfoReturnable<Float> info) {
      if (Hacks.highJump.isEnabled()) {
         info.setReturnValue(0.42F * Hacks.highJump.height.getValue());
      }

   }

   @Inject(
      method = {"jump"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void jump(CallbackInfo info) {
      if (NClientEvent.callEvent(new NClientEvent.PlayerJumpEvent())) {
         info.cancel();
      }

   }
}
