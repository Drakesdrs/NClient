package com.ngames.nclient.mixin;

import com.ngames.nclient.hack.Hacks;
import net.minecraft.entity.passive.AbstractHorse;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({AbstractHorse.class})
public class MixinAbstractHorse {
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
      method = {"isHorseSaddled"},
      at = {@At("HEAD")},
      cancellable = true
   )
   public void isHorseSaddled(CallbackInfoReturnable<Boolean> info) {
      if (Hacks.entityControl.isEnabled()) {
         info.setReturnValue(true);
         info.cancel();
      }

   }
}
