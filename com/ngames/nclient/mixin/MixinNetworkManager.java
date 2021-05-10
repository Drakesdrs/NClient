package com.ngames.nclient.mixin;

import com.ngames.nclient.hack.Hacks;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketInput;
import net.minecraft.network.play.client.CPacketPlayerAbilities;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraft.network.play.client.CPacketVehicleMove;
import net.minecraft.network.play.client.CPacketPlayer.Position;
import net.minecraft.network.play.client.CPacketPlayer.PositionRotation;
import net.minecraft.network.play.client.CPacketPlayer.Rotation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({NetworkManager.class})
public class MixinNetworkManager {
   @Inject(
      method = {"sendPacket"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void sendPacket(Packet<?> packetIn, CallbackInfo info) {
      if (Hacks.packetCanceller.isEnabled()) {
         if (packetIn instanceof CPacketInput && Hacks.packetCanceller.CPacketInput.getValue()) {
            info.cancel();
         }

         if (packetIn instanceof Position && Hacks.packetCanceller.CPacketPosition.getValue()) {
            info.cancel();
         }

         if (packetIn instanceof PositionRotation && Hacks.packetCanceller.CPacketPositionRotation.getValue()) {
            info.cancel();
         }

         if (packetIn instanceof Rotation && Hacks.packetCanceller.CPacketRotation.getValue()) {
            info.cancel();
         }

         if (packetIn instanceof CPacketPlayerAbilities && Hacks.packetCanceller.CPacketPlayerAbilities.getValue()) {
            info.cancel();
         }

         if (packetIn instanceof CPacketPlayerDigging && Hacks.packetCanceller.CPacketPlayerDigging.getValue()) {
            info.cancel();
         }

         if (packetIn instanceof CPacketPlayerTryUseItem && Hacks.packetCanceller.CPacketPlayerTryUseItem.getValue()) {
            info.cancel();
         }

         if (packetIn instanceof CPacketPlayerTryUseItemOnBlock && Hacks.packetCanceller.CPacketPlayerTryUseItemOnBlock.getValue()) {
            info.cancel();
         }

         if (packetIn instanceof CPacketEntityAction && Hacks.packetCanceller.CPacketEntityAction.getValue()) {
            info.cancel();
         }

         if (packetIn instanceof CPacketUseEntity && Hacks.packetCanceller.CPacketUseEntity.getValue()) {
            info.cancel();
         }

         if (packetIn instanceof CPacketVehicleMove && Hacks.packetCanceller.CPacketVehicleMove.getValue()) {
            info.cancel();
         }

         if (info.isCancelled()) {
            Hacks.packetCanceller.inHud.set((Integer)Hacks.packetCanceller.inHud.get() + 1);
         }
      }

   }
}
