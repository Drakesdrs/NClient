package com.ngames.nclient.mixin;

import com.ngames.nclient.event.NClientEvent;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({Minecraft.class})
public class MixinMinecraft {
   @Inject(
      method = {"runTickKeyboard"},
      at = {@At("TAIL")},
      cancellable = true
   )
   private void runTickKeyboard(CallbackInfo info) {
      if (NClientEvent.callEvent(new NClientEvent.RunTickKeyboardEvent())) {
         info.cancel();
      }

   }
}
