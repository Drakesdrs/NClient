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
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.util.math.MathHelper;

@NClientHack(
   category = Category.MOVEMENT,
   description = "Change your strafe speed",
   name = "Strafe",
   words = "Strafe Speed Sprint"
)
public class Strafe extends Hack {
   public SettingChoose mode = new SettingChoose("Mode", (byte)1, new String[]{"simple", "slient"});
   private int leftTicks = 0;
   private int rightTicks = 0;
   private int fowTicks = 0;
   private int backTicks = 0;
   public AttributeModifier strafe = (new AttributeModifier(UUID.randomUUID(), "Strafe  NClient", 0.30000001192092896D, 2)).func_111168_a(false);
   private short rotationPriority = 2;

   public Strafe() {
      this.settings = addSettings(this);
   }

   public void onEnable() {
      super.onEnable();
      new Listener(NClientEvent.LivingUpdatedEvent.class, this);
   }

   public void onDisable() {
      Baritone.overrideRotation = false;
      NClient.MC.field_71439_g.func_110148_a(SharedMonsterAttributes.field_111263_d).func_111124_b(this.strafe);
      Baritone.setRotationPriority((short)0);
      super.onDisable();
   }

   public void onInvoke(NClientEvent event) {
      boolean flag = this.mode.getValue() == 1;
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

      if (flag2 && !fow && NClient.MC.field_71439_g.field_70122_E && Baritone.getJumpTicks() == 0 && NClient.MC.field_71474_y.field_74314_A.func_151470_d()) {
         float f = yaw * 0.017453292F;
         EntityPlayerSP var10000 = NClient.MC.field_71439_g;
         var10000.field_70159_w -= (double)(MathHelper.func_76126_a(f) * 0.2F);
         var10000 = NClient.MC.field_71439_g;
         var10000.field_70179_y += (double)(MathHelper.func_76134_b(f) * 0.2F);
         this.rotationPriority = 11;
      } else {
         this.rotationPriority = 2;
      }

      boolean rotate = Baritone.calcRotationPriority(this.rotationPriority);
      if (flag && yaw != Baritone.yaw && rotate) {
         Baritone.yaw = yaw;
         Baritone.overrideRotation = this.enabled;
         Baritone.updateRotation();
      } else {
         Baritone.setRotationPriority((short)0);
      }

      if (rotate) {
         Baritone.pitch = NClient.MC.field_71439_g.field_70125_A;
      }

      float input = Math.max(Math.abs(NClient.MC.field_71439_g.field_70702_br), Math.abs(NClient.MC.field_71439_g.field_191988_bg));
      if (!NClient.MC.field_71439_g.func_70051_ag()) {
         NClient.MC.field_71439_g.func_70031_b(true);
      }

      if (flag) {
         NClient.MC.field_71439_g.field_191988_bg = input;
         NClient.MC.field_71439_g.field_70702_br = 0.0F;
      }

      if (this.isEnabled() && (rotate || NClient.MC.field_71439_g.field_70177_z == yaw)) {
         IAttributeInstance iatt = NClient.MC.field_71439_g.func_110148_a(SharedMonsterAttributes.field_111263_d);
         if (!iatt.func_180374_a(this.strafe)) {
            iatt.func_111121_a(this.strafe);
         }

         iatt.func_188479_b(UUID.fromString("662A6B8D-DA3E-4C1C-8813-96EA6097278D"));
      }

   }
}
