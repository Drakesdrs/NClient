package com.ngames.nclient.mixin;

import com.ngames.nclient.NClient;
import com.ngames.nclient.baritone.Baritone;
import com.ngames.nclient.event.NClientEvent;
import com.ngames.nclient.hack.Hacks;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketEntityAction.Action;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumHand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({EntityPlayerSP.class})
public class MixinEntityPlayerSP {
   @Inject(
      method = {"swingArm"},
      at = {@At("TAIL")},
      cancellable = true
   )
   private void swingArm(EnumHand hand, CallbackInfo info) {
      if (hand != EnumHand.OFF_HAND && Hacks.criticals.isEnabled() && Hacks.criticals.type.getValue() == 2) {
         NClient.MC.field_71439_g.func_184609_a(EnumHand.OFF_HAND);
         info.cancel();
      }

      if (NClientEvent.callEvent(new NClientEvent.PlayerSwingArmEvent())) {
         info.cancel();
      }

   }

   @Inject(
      method = {"onUpdate"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void onUpdate(CallbackInfo info) {
      if (NClientEvent.callEvent(new NClientEvent.OnPlayerUpdateEvent())) {
         info.cancel();
      }

   }

   @Inject(
      method = {"onUpdate"},
      at = {@At("TAIL")},
      cancellable = true
   )
   private void onUpdated(CallbackInfo info) {
      if (NClientEvent.callEvent(new NClientEvent.OnPlayerUpdatedEvent())) {
         info.cancel();
      }

   }

   @Inject(
      method = {"isSneaking"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void isSneaking(CallbackInfoReturnable<Boolean> info) {
      if (Hacks.sneak.isEnabled() && Hacks.sneak.onlyServer.getValue()) {
         info.setReturnValue(true);
         info.cancel();
      }

   }

   @Inject(
      method = {"onLivingUpdate"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void onLivingUpdate(CallbackInfo info) {
      if (NClientEvent.callEvent(new NClientEvent.LivingUpdatedEvent())) {
         info.cancel();
      }

   }

   @Inject(
      method = {"onUpdateWalkingPlayer"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void onUpdateWalkingPlayer(CallbackInfo info) {
      if (Baritone.serverSprintingState != Baritone.serverSprinting && Baritone.overrideSprinting) {
         if (Baritone.serverSprinting) {
            NClient.MC.func_147114_u().func_147297_a(new CPacketEntityAction(NClient.MC.field_71439_g, Action.START_SPRINTING));
         } else {
            NClient.MC.func_147114_u().func_147297_a(new CPacketEntityAction(NClient.MC.field_71439_g, Action.STOP_SPRINTING));
         }

         Baritone.serverSprintingState = Baritone.serverSprinting;
      }

   }

   @Inject(
      method = {"removeActivePotionEffect"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void removeActivePotionEffect(CallbackInfoReturnable<PotionEffect> info) {
      if (NClientEvent.callEvent(new NClientEvent.PotionEffectRemovedEvent())) {
         info.cancel();
      }

   }
}
