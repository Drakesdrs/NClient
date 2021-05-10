package com.ngames.nclient.mixin;

import com.ngames.nclient.NClient;
import com.ngames.nclient.hack.Hacks;
import java.util.UUID;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({ModifiableAttributeInstance.class})
public class MixinModifiableAttributeInstance {
   @Shadow
   private double field_111132_f;

   @Inject(
      method = {"applyModifier"},
      at = {@At("TAIL")},
      cancellable = true
   )
   private void applyModifier(AttributeModifier modifier, CallbackInfo info) {
      if (Hacks.strafe.isEnabled()) {
         IAttributeInstance iatt = NClient.MC.field_71439_g.func_110148_a(SharedMonsterAttributes.field_111263_d);
         if (!iatt.func_180374_a(Hacks.strafe.strafe)) {
            iatt.func_111121_a(Hacks.strafe.strafe);
         }

         iatt.func_188479_b(UUID.fromString("662A6B8D-DA3E-4C1C-8813-96EA6097278D"));
      }

   }

   @Inject(
      method = {"removeModifier"},
      at = {@At("TAIL")},
      cancellable = true
   )
   private void removeModifier(UUID p_188479_1_, CallbackInfo info) {
      if (Hacks.strafe.isEnabled()) {
         IAttributeInstance iatt = NClient.MC.field_71439_g.func_110148_a(SharedMonsterAttributes.field_111263_d);
         if (!iatt.func_180374_a(Hacks.strafe.strafe)) {
            iatt.func_111121_a(Hacks.strafe.strafe);
         }

         iatt.func_188479_b(UUID.fromString("662A6B8D-DA3E-4C1C-8813-96EA6097278D"));
      }

   }

   @Inject(
      method = {"removeModifier"},
      at = {@At("TAIL")},
      cancellable = true
   )
   private void removeModifier(AttributeModifier modifier, CallbackInfo info) {
      if (Hacks.strafe.isEnabled()) {
         IAttributeInstance iatt = NClient.MC.field_71439_g.func_110148_a(SharedMonsterAttributes.field_111263_d);
         if (!iatt.func_180374_a(Hacks.strafe.strafe)) {
            iatt.func_111121_a(Hacks.strafe.strafe);
         }

         iatt.func_188479_b(UUID.fromString("662A6B8D-DA3E-4C1C-8813-96EA6097278D"));
      }

   }
}
