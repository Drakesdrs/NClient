package com.ngames.nclient.hack.hacks;

import com.ngames.nclient.NClient;
import com.ngames.nclient.baritone.BUtils;
import com.ngames.nclient.baritone.Baritone;
import com.ngames.nclient.baritone.SafeThread;
import com.ngames.nclient.event.Listener;
import com.ngames.nclient.event.NClientEvent;
import com.ngames.nclient.hack.Category;
import com.ngames.nclient.hack.Hack;
import com.ngames.nclient.hack.Hacks;
import com.ngames.nclient.hack.NClientHack;
import com.ngames.nclient.hack.settings.SettingBoolean;
import com.ngames.nclient.hack.settings.SettingChoose;
import com.ngames.nclient.hack.settings.SettingValue;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

@NClientHack(
   category = Category.COMBAT,
   description = "Automatically attack players",
   name = "KillAura18",
   words = "KillAura18 KillAura TriggerBot AutoClicker"
)
public class KillAura18 extends Hack {
   private final SettingChoose DelayType = new SettingChoose("DelayType", (byte)2, new String[]{"simple", "random", "advanced"});
   private final SettingChoose ClickType = new SettingChoose("ClickType", (byte)3, new String[]{"ClickMouse", "AAC", "target", "AACTarget"});
   private final SettingBoolean AACTCheckEntityType = new SettingBoolean("AACTCheckEntityType", true);
   private final SettingValue CPSMin = new SettingValue("CPSMin", 17.0F, 1.0F, 100.0F);
   private final SettingValue CPSMax = new SettingValue("CPSMax", 21.0F, 1.0F, 100.0F);
   private final SettingValue Range = new SettingValue("Range", 6.0F, 1.0F, 10.0F);
   private final SettingBoolean TickSync = new SettingBoolean("TickSync", true);
   private final SettingBoolean RotationSync = new SettingBoolean("RotationSync", true);
   private final SettingValue CheckDelay = new SettingValue("CheckDelay", 10.0F, 0.0F, 1000.0F);
   private final SettingBoolean multipleTarget = new SettingBoolean("MultipleTarget", true);
   private final SettingChoose priority = new SettingChoose("Priority", (byte)0, new String[]{"health", "distance", "multi"});
   private final SettingValue STargetAttacks = new SettingValue("STargetAttacks", 2.0F, 1.0F, 100.0F);
   private final SettingBoolean rayTracing = new SettingBoolean("RayTracing", true);
   private final SettingBoolean rtOnlyNotMO = new SettingBoolean("RTOnlyNotMO", true);
   private final SettingBoolean clientRotations = new SettingBoolean("ClientRotations", true);
   private final SettingChoose autoBlock = new SettingChoose("AutoBlock", (byte)0, new String[]{"off", "legit", "packet"});
   private final SettingBoolean attackMobs = new SettingBoolean("AttackMobs", false);
   private final SettingBoolean attackPlayers = new SettingBoolean("AttackPlayers", true);
   public Entity target;
   private Entity botTarget;
   public boolean targeted = false;
   private boolean waitClick;
   private List<Entity> multiTargets = new ArrayList();
   int k;
   int m = 1;
   boolean rtr;
   short rotationPriority = 10;

   public KillAura18() {
      this.settings = Hack.addSettings(this);
   }

   public void onEnable() {
      super.onEnable();
      new Listener(NClientEvent.RunTickKeyboardEvent.class, this);
      new Listener(NClientEvent.OnPlayerUpdateEvent.class, this);
      new Listener(NClientEvent.LivingUpdatedEvent.class, this);
      (new SafeThread(() -> {
         List<Integer> delays = new ArrayList();
         if (this.DelayType.getValue() == 0) {
            delays = BUtils.genSimpleDelays(this.CPSMax.getValue());
         } else if (this.DelayType.getValue() == 1) {
            delays = BUtils.genDelayNoise((float)BUtils.randomInRange(Math.round(this.CPSMin.getValue()), Math.round(this.CPSMax.getValue())));
         } else if (this.DelayType.getValue() == 2) {
            delays = Hacks.advClickerDelays.genAdvancedDelayNoise(this.CPSMin.getValue(), this.CPSMax.getValue());
         }

         int delay;
         for(Iterator var2 = ((List)delays).iterator(); var2.hasNext(); BUtils.sleep((long)delay, BUtils.randomInRange(0, 999999))) {
            delay = (Integer)var2.next();
            if (!this.enabled) {
               break;
            }

            if (!this.RotationSync.getValue()) {
               this.getTarget();
            }

            if ((this.RotationSync.getValue() && this.rtr || this.mouseOver() && this.rtOnlyNotMO.getValue() || this.rayTrace()) && this.canBeAttacked(this.target) && !Baritone.clickSync) {
               if (this.TickSync.getValue()) {
                  if (this.autoBlock.getValue() == 1) {
                     Baritone.usedItem();
                  }

                  if (!NClient.MC.field_71439_g.func_184587_cr() && Baritone.getLeftClickCounter() <= 0) {
                     this.waitClick = true;
                  }
               } else {
                  this.attack();
               }

               this.targeted = true;
            } else {
               this.targeted = false;
            }
         }

         if (!this.targeted) {
            BUtils.sleep((long)((int)this.CheckDelay.getValue()));
         }

      }, this)).start();
   }

   private boolean rayTrace() {
      boolean rayTraceResult = false;
      rayTraceResult = BUtils.rayTraceBlocks(NClient.MC.field_71439_g.func_174824_e(NClient.MC.func_184121_ak()), new Vec3d(this.target.field_70165_t + (double)this.target.field_70130_N * 0.5D, this.target.field_70163_u + (double)this.target.field_70131_O * 0.5D, this.target.field_70161_v + (double)this.target.field_70130_N * 0.5D));
      if (!this.rayTracing.getValue()) {
         rayTraceResult = true;
      }

      if (rayTraceResult) {
         Baritone.setRotationToEntity(this.target, !this.clientRotations.getValue());
         if (!this.clientRotations.getValue()) {
            Baritone.updateRotation();
            Baritone.overrideRotation = true;
         }

         return true;
      } else {
         for(int i = 0; i < 10; ++i) {
            rayTraceResult = BUtils.rayTraceBlocks(NClient.MC.field_71439_g.func_174824_e(NClient.MC.func_184121_ak()), new Vec3d(this.target.field_70165_t + (double)this.target.field_70130_N * 0.5D, this.target.field_70163_u + (double)this.target.field_70131_O * 0.1D * (double)i, this.target.field_70161_v + (double)this.target.field_70130_N * 0.5D));
            if (rayTraceResult) {
               Baritone.setRotationToEntity(this.target, (float)(i / 10), !this.clientRotations.getValue());
               if (!this.clientRotations.getValue()) {
                  Baritone.updateRotation();
                  Baritone.overrideRotation = true;
               }
               break;
            }
         }

         if (!rayTraceResult) {
            Baritone.setRotationPriority((short)0);
            Baritone.refreshRotation();
         }

         return rayTraceResult;
      }
   }

   public void onInvoke(NClientEvent event) {
      if (this.enabled) {
         if (event instanceof NClientEvent.RunTickKeyboardEvent && this.waitClick && this.canBeAttacked(this.target)) {
            this.attack();
            this.waitClick = false;
         }

         if (event instanceof NClientEvent.OnPlayerUpdateEvent && this.RotationSync.getValue()) {
            Baritone.calcRotationPriority(this.rotationPriority);
            this.getTarget();
            if (this.target != null && this.canBeAttacked(this.target)) {
               this.rtr = this.mouseOver() && this.rtOnlyNotMO.getValue() || this.rayTrace();
            }
         }

         if (event instanceof NClientEvent.LivingUpdatedEvent && !this.clientRotations.getValue()) {
            this.fixMovement();
         }
      }

   }

   private void attack() {
      if (this.autoBlock.getValue() == 2) {
         NClient.MC.func_147114_u().func_147297_a(new CPacketPlayerTryUseItem(EnumHand.OFF_HAND));
      }

      if (this.ClickType.getValue() == 0) {
         Baritone.leftClickMouse();
      } else if (this.ClickType.getValue() == 1) {
         Baritone.attackEntity();
      } else if (this.ClickType.getValue() == 2) {
         Baritone.attackEntity(this.target);
      } else if (this.ClickType.getValue() == 3) {
         Baritone.attackEntity(this.botTarget != null ? this.botTarget : this.target);
      }

      if (this.autoBlock.getValue() == 1) {
         Baritone.useItem();
      }

      Baritone.setRotationPriority((short)0);
   }

   private void getTarget() {
      if ((float)this.m == this.STargetAttacks.getValue() || this.priority.getValue() != 2 || !this.canBeAttacked(this.target)) {
         if (this.priority.getValue() < 2) {
            this.target = Baritone.getPriorityTarget(this.Range.getValue(), this.attackPlayers.getValue(), this.attackMobs.getValue(), !this.multipleTarget.getValue(), this.target, this.priority.getValue());
         } else {
            if (this.k >= this.multiTargets.size() || this.multiTargets.isEmpty()) {
               this.multiTargets = Baritone.getMultiTargets(this.Range.getValue(), this.attackPlayers.getValue(), this.attackMobs.getValue());
               this.k = 0;
            }

            for(int i = 0; i < this.multiTargets.size() && this.enabled && !this.multiTargets.isEmpty(); ++i) {
               if (this.canBeAttacked((Entity)this.multiTargets.get(i))) {
                  this.target = (Entity)this.multiTargets.get(i);
                  break;
               }
            }

            if (!this.multiTargets.isEmpty() && this.multiTargets.get(this.k) != null && this.canBeAttacked((Entity)this.multiTargets.get(this.k))) {
               this.target = (Entity)this.multiTargets.get(this.k);
            }

            ++this.k;
         }

         if (NClient.MC.field_71441_e != null && this.target != null && this.ClickType.getValue() == 3) {
            this.botTarget = Baritone.getBotTarget(this.target, this.AACTCheckEntityType.getValue());
         }
      }

      if (this.priority.getValue() == 2) {
         ++this.m;
         if ((float)this.m > this.STargetAttacks.getValue()) {
            this.m = 1;
         }
      }

   }

   private boolean canBeAttacked(Entity e) {
      return e != null && Baritone.isAlive(e) && NClient.MC.field_71439_g.func_70032_d(e) <= this.Range.getValue() && (e instanceof EntityOtherPlayerMP && this.attackPlayers.getValue() || e instanceof EntityLiving && this.attackMobs.getValue());
   }

   private boolean mouseOver() {
      return NClient.MC.field_71476_x.field_72308_g == this.target || this.ClickType.getValue() == 3 && NClient.MC.field_71476_x.field_72308_g == this.botTarget || this.ClickType.getValue() == 1 && NClient.MC.field_71476_x.field_72308_g != null;
   }

   private void fixMovement() {
      boolean fow = NClient.MC.field_71474_y.field_74351_w.func_151470_d();
      boolean jump = NClient.MC.field_71474_y.field_74314_A.func_151470_d();
      if (!fow || !NClient.MC.field_71439_g.func_70051_ag() || NClient.MC.field_71439_g.field_70177_z > Baritone.yaw - 30.0F && NClient.MC.field_71439_g.field_70177_z < Baritone.yaw + 30.0F) {
         NClient.MC.field_71439_g.func_70031_b(true);
      } else {
         NClient.MC.field_71439_g.func_70031_b(false);
      }

      if (jump && NClient.MC.field_71439_g.func_70051_ag() && Baritone.getJumpTicks() == 0) {
         float f = NClient.MC.field_71439_g.field_70177_z * 0.017453292F;
         EntityPlayerSP var10000 = NClient.MC.field_71439_g;
         var10000.field_70159_w += (double)(MathHelper.func_76126_a(f) * 0.2F);
         var10000 = NClient.MC.field_71439_g;
         var10000.field_70179_y -= (double)(MathHelper.func_76134_b(f) * 0.2F);
      }

      BUtils.Direction cdir = BUtils.getDirection(false, Baritone.yaw);
      BUtils.Direction sdir = BUtils.getDirection(false);
      float cyaw = NClient.MC.field_71439_g.field_70177_z;
      float syaw = Baritone.yaw;
      if (cdir != sdir) {
         boolean moveFow = syaw > cyaw - 30.0F && syaw < cyaw + 30.0F;
         boolean moveBack = syaw < cyaw - 120.0F || syaw > cyaw + 120.0F;
         boolean moveLeft = syaw < cyaw - 30.0F;
         boolean moveRight = syaw > cyaw + 30.0F;
         if (moveFow) {
            NClient.MC.field_71439_g.field_191988_bg = 1.0F;
            NClient.MC.field_71439_g.field_71158_b.field_192832_b = 1.0F;
         } else {
            NClient.MC.field_71439_g.field_191988_bg = 0.0F;
            NClient.MC.field_71439_g.field_71158_b.field_192832_b = 0.0F;
         }

         if (moveBack) {
            NClient.MC.field_71439_g.field_191988_bg = -1.0F;
            NClient.MC.field_71439_g.field_71158_b.field_192832_b = -1.0F;
         }

         if (moveLeft) {
            NClient.MC.field_71439_g.field_70702_br = -1.0F;
            NClient.MC.field_71439_g.field_71158_b.field_78902_a = -1.0F;
         }

         if (moveRight) {
            NClient.MC.field_71439_g.field_70702_br = 1.0F;
            NClient.MC.field_71439_g.field_71158_b.field_78902_a = 1.0F;
         }

         if (!moveRight && !moveLeft) {
            NClient.MC.field_71439_g.field_70702_br = 0.0F;
            NClient.MC.field_71439_g.field_71158_b.field_78902_a = 0.0F;
         }
      }

   }

   public void onUpdate() {
      Baritone.overrideRotation = !this.clientRotations.getValue();
      super.onUpdate();
   }

   public void onDisable() {
      if (!this.clientRotations.getValue()) {
         Baritone.overrideRotation = false;
      }

      super.onDisable();
   }
}
