package com.ngames.nclient.hack.hacks;

import com.ngames.nclient.NClient;
import com.ngames.nclient.baritone.BUtils;
import com.ngames.nclient.baritone.Baritone;
import com.ngames.nclient.event.Listener;
import com.ngames.nclient.event.NClientEvent;
import com.ngames.nclient.hack.Category;
import com.ngames.nclient.hack.Hack;
import com.ngames.nclient.hack.NClientHack;
import com.ngames.nclient.hack.settings.SettingChoose;
import java.util.UUID;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.util.math.MathHelper;

@NClientHack(
   category = Category.MOVEMENT,
   description = "several ways to moving quickly",
   name = "Speed",
   words = "Speed SpeedHack AutoJump"
)
public class Speed extends Hack {
   public final SettingChoose type = new SettingChoose("Type", (byte)0, new String[]{"Legit", "test0.1", "advanced"});
   private final AttributeModifier entitySpeed = (new AttributeModifier(UUID.randomUUID(), "Speed NClient", 0.33000000011920927D, 2)).func_111168_a(false);
   private int leftTicks = 0;
   private int rightTicks = 0;
   private int fowTicks = 0;
   private int backTicks = 0;

   public Speed() {
      this.settings = Hack.addSettings(this);
   }

   public void onEnable() {
      super.onEnable();
      new Listener(NClientEvent.OnPlayerUpdateEvent.class, this);
      new Listener(NClientEvent.PlayerJumpEvent.class, this);
   }

   public void onDisable() {
      Baritone.overrideRotation = false;
      NClient.MC.field_71439_g.func_110148_a(SharedMonsterAttributes.field_111263_d).func_111124_b(this.entitySpeed);
      super.onDisable();
   }

   public void onUpdate() {
      super.onUpdate();
   }

   public void onInvoke(NClientEvent event) {
      if (event instanceof NClientEvent.PlayerJumpEvent && this.type.getValue() == 2) {
         event.setCalceled();
      } else {
         switch(this.type.getValue()) {
         case 0:
            this.handleType0();
         case 1:
            this.handleType1();
         case 2:
            this.handleType2();
         default:
         }
      }
   }

   private void handleType0() {
      if (Baritone.isMoving()) {
         KeyBinding.func_74510_a(NClient.MC.field_71474_y.field_74314_A.func_151463_i(), true);
         if (NClient.MC.field_71439_g.field_191988_bg != 0.0F) {
            NClient.MC.field_71439_g.func_70031_b(true);
         }
      } else {
         KeyBinding.func_74510_a(NClient.MC.field_71474_y.field_74314_A.func_151463_i(), false);
      }

   }

   private void handleType1() {
      boolean fow = NClient.MC.field_71474_y.field_74351_w.func_151470_d();
      boolean back = NClient.MC.field_71474_y.field_74368_y.func_151470_d();
      boolean left = NClient.MC.field_71474_y.field_74370_x.func_151470_d();
      boolean right = NClient.MC.field_71474_y.field_74366_z.func_151470_d();
      if ((fow || left || back || right) && NClient.MC.field_71439_g.field_70122_E) {
         float yaw = NClient.MC.field_71439_g.field_70177_z;
         if (back) {
            yaw += 180.0F;
         }

         if (right) {
            yaw += 90.0F;
         }

         if (left) {
            yaw -= 90.0F;
         }

         if (fow && (right || left)) {
            yaw = right ? yaw - 45.0F : yaw + 45.0F;
         }

         yaw = BUtils.toMinecraftDegrees(yaw);
         float f = yaw * 0.017453292F;
         NClient.MC.field_71439_g.field_71158_b.field_78901_c = true;
         NClient.MC.field_71439_g.field_70181_x = 0.41999998688697815D;
         EntityPlayerSP var10000 = NClient.MC.field_71439_g;
         var10000.field_70159_w -= (double)(MathHelper.func_76126_a(f) * 0.2F);
         var10000 = NClient.MC.field_71439_g;
         var10000.field_70179_y += (double)(MathHelper.func_76134_b(f) * 0.2F);
      }

   }

   private void handleType2() {
      boolean flag2 = false;
      float yaw = NClient.MC.field_71439_g.field_70177_z;
      boolean fow = NClient.MC.field_71474_y.field_74351_w.func_151470_d();
      boolean back = NClient.MC.field_71474_y.field_74368_y.func_151470_d();
      boolean left = NClient.MC.field_71474_y.field_74370_x.func_151470_d();
      boolean right = NClient.MC.field_71474_y.field_74366_z.func_151470_d();
      this.rightTicks = right ? ++this.rightTicks : 0;
      this.leftTicks = left ? ++this.leftTicks : 0;
      this.fowTicks = fow ? ++this.fowTicks : 0;
      this.backTicks = back ? ++this.backTicks : 0;
      int latest = Math.min(Math.min(this.fowTicks == 0 ? Integer.MAX_VALUE : this.fowTicks, this.backTicks == 0 ? Integer.MAX_VALUE : this.backTicks), Math.min(this.leftTicks == 0 ? Integer.MAX_VALUE : this.leftTicks, this.rightTicks == 0 ? Integer.MAX_VALUE : this.rightTicks));
      if (fow || left || back || right) {
         if (back && this.backTicks == latest) {
            yaw += 180.0F;
         }

         if (right && this.rightTicks == latest) {
            yaw += 90.0F;
         }

         if (left && this.leftTicks == latest) {
            yaw -= 90.0F;
         }

         yaw = BUtils.toMinecraftDegrees(yaw);
         flag2 = true;
      }

      Baritone.yaw = yaw;
      Baritone.overrideRotation = this.enabled;
      Baritone.updateRotation();
      float input = Math.max(Math.abs(NClient.MC.field_71439_g.field_70702_br), Math.abs(NClient.MC.field_71439_g.field_191988_bg));
      if (!NClient.MC.field_71439_g.func_70051_ag() && flag2) {
         NClient.MC.field_71439_g.func_70031_b(true);
      }

      NClient.MC.field_71439_g.field_191988_bg = input;
      NClient.MC.field_71439_g.field_70702_br = 0.0F;
      if (fow) {
         NClient.MC.field_71439_g.field_71158_b.field_192832_b = 1.0F;
      }

      if (back) {
         NClient.MC.field_71439_g.field_71158_b.field_192832_b = -1.0F;
      }

      if (left) {
         NClient.MC.field_71439_g.field_71158_b.field_78902_a = -1.0F;
      }

      if (right) {
         NClient.MC.field_71439_g.field_71158_b.field_192832_b = 1.0F;
      }

      NClient.MC.field_71439_g.field_71158_b.field_78901_c = true;
      float f = yaw * 0.017453292F;
      if (flag2 && NClient.MC.field_71439_g.field_70122_E) {
         EntityPlayerSP var10000 = NClient.MC.field_71439_g;
         var10000.field_70159_w -= (double)(MathHelper.func_76126_a(f) * 0.23F);
         NClient.MC.field_71439_g.field_70181_x = 0.42D;
         Baritone.setTimer(new net.minecraft.util.Timer(20.0F));
         var10000 = NClient.MC.field_71439_g;
         var10000.field_70179_y += (double)(MathHelper.func_76134_b(f) * 0.23F);
      } else {
         Baritone.setTimer(new net.minecraft.util.Timer(20.3F));
      }

      if (this.isEnabled()) {
         IAttributeInstance iatt = NClient.MC.field_71439_g.func_110148_a(SharedMonsterAttributes.field_111263_d);
         if (!iatt.func_180374_a(this.entitySpeed)) {
            iatt.func_111121_a(this.entitySpeed);
         }

         iatt.func_188479_b(UUID.fromString("662A6B8D-DA3E-4C1C-8813-96EA6097278D"));
      }

   }
}
