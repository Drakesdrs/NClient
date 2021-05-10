package com.ngames.nclient.mixin;

import com.ngames.nclient.hack.Hacks;
import net.minecraft.entity.passive.EntityPig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({EntityPig.class})
public class MixinEntityPig {
   @Inject(
      method = {"canBeSteered"},
      at = {@At("HEAD")},
      cancellable = true
   )
   public void canBeSteered(CallbackInfoReturnable<Boolean> info) {
      if (Hacks.entityControl.isEnabled()) {
         info.setReturnValue(true);
         info.cancel();
      }

   }

   @Inject(
      method = {"getSaddled"},
      at = {@At("HEAD")},
      cancellable = true
   )
   public void getSaddled(CallbackInfoReturnable<Boolean> info) {
      if (Hacks.entityControl.isEnabled()) {
         info.setReturnValue(true);
         info.cancel();
      }

   }
}
