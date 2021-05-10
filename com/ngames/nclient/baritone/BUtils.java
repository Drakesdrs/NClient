package com.ngames.nclient.baritone;

import com.ngames.nclient.NClient;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.RayTraceResult.Type;

public class BUtils {
   public static double toDegrees(double Angle) {
      return Angle * 180.0D / 3.141592653589793D;
   }

   public static float toMinecraftDegrees(float angle) {
      return angle > 180.0F ? -(360.0F - angle) : angle;
   }

   public static double getLookingCoordinate() {
      double ret = 0.0D;
      BUtils.Direction der = getDirection(false);
      if (der == BUtils.Direction.Xneg || der == BUtils.Direction.Xpos) {
         ret = NClient.MC.field_71439_g.field_70165_t;
      }

      if (der == BUtils.Direction.Zneg || der == BUtils.Direction.Zpos) {
         ret = NClient.MC.field_71439_g.field_70161_v;
      }

      return ret;
   }

   public static double getLookingCoordinate(Entity entity) {
      double ret = 0.0D;
      BUtils.Direction der = getDirection(false);
      if (der == BUtils.Direction.Xneg || der == BUtils.Direction.Xpos) {
         ret = entity.field_70165_t;
      }

      if (der == BUtils.Direction.Zneg || der == BUtils.Direction.Zpos) {
         ret = entity.field_70161_v;
      }

      return ret;
   }

   public static List<Integer> genSimpleDelays(float cps) {
      List<Integer> ret = new ArrayList();
      int size = Math.round(cps);

      for(int i = 0; i < size; ++i) {
         ret.add(Math.round(1000.0F / cps));
      }

      return ret;
   }

   public static List<Integer> genDelayNoise(float cps) {
      List<Integer> ret = new ArrayList();
      int size = Math.round(cps);

      for(int i = 0; i < size; ++i) {
         int k = Math.round(1000.0F / cps);
         ret.add(randomInRange(Math.round((float)k - (float)k * 0.3F), Math.round((float)k + (float)k * 0.3F)));
      }

      return ret;
   }

   public static List<Integer> genAdvancedDelayNoise(float cps, int phase, boolean powered, float speed, float valueRange, float minPhaseSize, float maxPhaseSize, float delayMlMin, float delayMlMax, short minLDelayChance, short maxLDelayChance) {
      List<Integer> ret = new ArrayList();
      int size = randomInRange(Math.round(minPhaseSize * cps), Math.round(maxPhaseSize * cps));
      boolean second = false;
      int prev = Math.round(1000.0F / cps);

      int j;
      for(j = 0; j < size; ++j) {
         boolean isDouble = random((short)20);
         int delay = Math.round((float)randomInRange(Math.round(speed / cps * (1.0F - valueRange)), Math.round(speed / cps * (1.0F + valueRange))) * (random(phase > 2 ? maxLDelayChance : minLDelayChance) ? (phase > 2 ? delayMlMax : (float)phase) : delayMlMin));
         if (second && isDouble) {
            delay = randomInRange(prev - 3, prev + 3);
         }

         second = !second;
         ret.add(delay);
         prev = delay;
      }

      if (powered) {
         for(int i = 0; i < ret.size(); ++i) {
            j = Math.round((float)randomInRange(3, 7) * 10.0F / (float)(phase > 5 ? 5 : phase));
            float power = (float)(randomInRange(j - j / 20, j + j / 20) / 100);
            ret.set(i, Math.round((float)(Integer)ret.get(i) * (1.0F + power)));
         }
      }

      return ret;
   }

   public static int randomInRange(int min, int max) {
      return min + (int)(Math.random() * (double)(max - min + 1));
   }

   public static float randomInRange(float min, float max) {
      return min + (float)((int)(Math.random() * (double)(max - min + 1.0F)));
   }

   public static boolean random() {
      return randomInRange(0, 100) < 50;
   }

   public static boolean random(short chanse) {
      return randomInRange(0, 100) < chanse;
   }

   public static boolean isInRange(float x, float y, float range) {
      return Math.max(x, y) - Math.min(x, y) < range;
   }

   public static boolean isInRange(double x, double y, float range) {
      return Math.max(x, y) - Math.min(x, y) < (double)range;
   }

   public static double getDistance(Vec3d pos1, Vec3d pos2) {
      double dX = Math.max(pos1.field_72450_a, pos2.field_72450_a) - Math.min(pos1.field_72450_a, pos2.field_72450_a);
      double dY = Math.max(pos1.field_72448_b, pos2.field_72448_b) - Math.min(pos1.field_72448_b, pos2.field_72448_b);
      double dZ = Math.max(pos1.field_72449_c, pos2.field_72449_c) - Math.min(pos1.field_72449_c, pos2.field_72449_c);
      return (double)MathHelper.func_76133_a(dX * dX + dY * dY + dZ * dZ);
   }

   public static Vec3d getCenter(Entity entity) {
      double x = entity.field_70165_t + (double)(entity.field_70130_N / 2.0F);
      double y = entity.field_70163_u + (double)(entity.field_70131_O / 2.0F);
      double z = entity.field_70161_v + (double)(entity.field_70130_N / 2.0F);
      return new Vec3d(x, y, z);
   }

   public static void sleep(long ms) {
      try {
         Thread.sleep(ms);
      } catch (InterruptedException var3) {
         var3.printStackTrace();
      }

   }

   public static void sleep(long ms, int ns) {
      try {
         Thread.sleep(ms, ns);
      } catch (InterruptedException var4) {
         var4.printStackTrace();
      }

   }

   public static BUtils.Direction getDirection(boolean two, float yaw) {
      BUtils.Direction ret = null;
      if (yaw >= 46.0F && yaw <= 135.0F) {
         ret = BUtils.Direction.Xneg;
      }

      if (yaw >= 136.0F && yaw <= 180.0F || yaw <= -136.0F) {
         ret = BUtils.Direction.Zneg;
      }

      if (yaw <= 45.0F && yaw <= 0.0F || yaw >= -45.0F && yaw <= 0.0F) {
         ret = BUtils.Direction.Zpos;
      }

      if (ret == null) {
         ret = BUtils.Direction.Xpos;
      }

      if (two) {
         if (yaw > 30.0F && yaw < 60.0F) {
            ret = BUtils.Direction.XnegZpos;
         }

         if (yaw > 120.0F && yaw < 150.0F) {
            ret = BUtils.Direction.XnegZneg;
         }

         if (yaw > -60.0F && yaw < -30.0F) {
            ret = BUtils.Direction.XposZneg;
         }

         if (yaw > -150.0F && yaw < -120.0F) {
            ret = BUtils.Direction.XposZpos;
         }
      }

      return ret;
   }

   public static BUtils.Direction getDirection(boolean two) {
      return getDirection(two, NClient.MC.field_71439_g.field_70177_z);
   }

   public static boolean canAttackEntity() {
      return NClient.MC.field_71476_x.field_72313_a == Type.ENTITY;
   }

   public static boolean canAttackEntity(Entity entity) {
      return NClient.MC.field_71476_x.field_72313_a == Type.ENTITY && NClient.MC.field_71476_x.field_72308_g.equals(entity);
   }

   /** @deprecated */
   @Deprecated
   public static boolean rayTrace(Vec3d start, Vec3d end, boolean calcEntities) {
      Vec3d min = new Vec3d(Math.min(start.field_72450_a, end.field_72450_a), Math.min(start.field_72448_b, end.field_72448_b), Math.min(start.field_72449_c, end.field_72449_c));
      Vec3d max = new Vec3d(Math.max(start.field_72450_a, end.field_72450_a), Math.max(start.field_72448_b, end.field_72448_b), Math.max(start.field_72449_c, end.field_72449_c));
      double x = min.field_72450_a;
      double y = min.field_72448_b;
      double z = min.field_72449_c;
      Vec3d delta = new Vec3d(max.field_72450_a - min.field_72450_a, max.field_72448_b - min.field_72448_b, max.field_72449_c - min.field_72449_c);
      double dist = getDistance(start, end);
      Vec3d step = new Vec3d(delta.field_72450_a / (dist * 8.0D), delta.field_72448_b / (dist * 8.0D), delta.field_72449_c / (dist * 8.0D));

      do {
         if (x < max.field_72450_a) {
            x += step.field_72450_a;
         }

         if (y < max.field_72448_b) {
            y += step.field_72448_b;
         }

         if (z < max.field_72449_c) {
            z += step.field_72449_c;
         }

         IBlockState block = NClient.MC.field_71441_e.func_180495_p(new BlockPos(x, y, z));
         if (calcEntities) {
            Iterator var16 = NClient.MC.field_71441_e.field_72996_f.iterator();

            while(var16.hasNext()) {
               Entity e = (Entity)var16.next();
               if (e.func_70046_E().func_72318_a(new Vec3d(x, y, z))) {
                  return false;
               }
            }
         }

         AxisAlignedBB aabb = block.func_185890_d(NClient.MC.field_71441_e, new BlockPos(x, y, z));
         if (NClient.MC.field_71441_e != null && block.func_177230_c().func_176209_a(block, false) && aabb.func_72318_a(new Vec3d(x, y, z))) {
            return false;
         }
      } while(!(x >= max.field_72450_a) || !(y >= max.field_72448_b) || !(z >= max.field_72449_c));

      return true;
   }

   public static boolean rayTraceBlocks(Vec3d start, Vec3d end) {
      RayTraceResult result = NClient.MC.field_71441_e.func_72933_a(start, end);
      return result == null || result.field_72313_a != Type.BLOCK;
   }

   /** @deprecated */
   @Deprecated
   public static boolean rayTrace(Vec3d start, Vec3d end) {
      return rayTrace(start, end, false);
   }

   public static boolean isNearest(Entity entity1, Entity entity2) {
      return NClient.MC.field_71439_g.func_70032_d(entity1) < NClient.MC.field_71439_g.func_70032_d(entity2);
   }

   public static boolean isSmallerBB(Entity entity1, Entity entity2, BUtils.Direction dir) {
      AxisAlignedBB aabb1 = entity1.func_174813_aQ();
      AxisAlignedBB aabb2 = entity2.func_174813_aQ();
      boolean x = aabb1.field_72336_d - aabb1.field_72340_a < aabb2.field_72336_d - aabb2.field_72340_a;
      boolean z = aabb1.field_72334_f - aabb1.field_72339_c < aabb2.field_72334_f - aabb2.field_72339_c;
      return (dir == BUtils.Direction.Xneg || dir == BUtils.Direction.Xpos) && x || (dir == BUtils.Direction.Zneg || dir == BUtils.Direction.Zpos) && z;
   }

   public static class EntityLookHelper {
      private final EntityPlayerSP entity;
      private float deltaLookYaw;
      private float deltaLookPitch;
      private boolean isLooking;
      private double posX;
      private double posY;
      private double posZ;

      public EntityLookHelper(EntityPlayerSP entitylivingIn) {
         this.entity = entitylivingIn;
      }

      public void setLookPositionWithEntity(Entity entityIn, float deltaYaw, float deltaPitch) {
         this.posX = entityIn.field_70165_t;
         this.posY = (entityIn.func_174813_aQ().field_72338_b + entityIn.func_174813_aQ().field_72337_e) / 2.0D;
         this.posZ = entityIn.field_70161_v;
         this.deltaLookYaw = deltaYaw;
         this.deltaLookPitch = deltaPitch;
         this.isLooking = true;
      }

      public void setLookPosition(double x, double y, double z, float deltaYaw, float deltaPitch) {
         this.posX = x;
         this.posY = y;
         this.posZ = z;
         this.deltaLookYaw = deltaYaw;
         this.deltaLookPitch = deltaPitch;
         this.isLooking = true;
      }

      public void onUpdateLook(float dPitch, boolean onlyServer) {
         float yaw = 0.0F;
         float pitch = 0.0F;
         float yawHead = 0.0F;
         if (this.isLooking) {
            this.isLooking = false;
            double d0 = this.posX - this.entity.field_70165_t;
            double d1 = this.posY - (this.entity.field_70163_u + (double)this.entity.func_70047_e());
            double d2 = this.posZ - this.entity.field_70161_v;
            double d3 = (double)MathHelper.func_76133_a(d0 * d0 + d2 * d2);
            float f = (float)(MathHelper.func_181159_b(d2, d0) * 57.29577951308232D) - 90.0F;
            float f1 = (float)(-(MathHelper.func_181159_b(d1, d3) * 57.29577951308232D));
            pitch = this.updateRotation(this.entity.field_70125_A, f1, this.deltaLookPitch);
            yaw = this.updateRotation(this.entity.field_70177_z, f, this.deltaLookYaw);
         } else {
            yawHead = this.updateRotation(this.entity.field_70759_as, this.entity.field_70761_aq, 10.0F);
         }

         float f2 = MathHelper.func_76142_g(this.entity.field_70759_as - this.entity.field_70761_aq);
         if (f2 < -75.0F) {
            yawHead = this.entity.field_70761_aq - 75.0F;
         }

         if (f2 > 75.0F) {
            yawHead = this.entity.field_70761_aq + 75.0F;
         }

         if (onlyServer) {
            Baritone.yaw = yaw;
            Baritone.pitch = pitch;
         }

         if (!onlyServer) {
            this.entity.field_70177_z = yaw;
            this.entity.field_70125_A = pitch;
         }

         this.entity.field_70759_as = yawHead;
      }

      public void onUpdateLook(float dPitch) {
         this.onUpdateLook(dPitch, false);
      }

      private float updateRotation(float p_75652_1_, float p_75652_2_, float p_75652_3_) {
         float f = MathHelper.func_76142_g(p_75652_2_ - p_75652_1_);
         if (f > p_75652_3_) {
            f = p_75652_3_;
         }

         if (f < -p_75652_3_) {
            f = -p_75652_3_;
         }

         return p_75652_1_ + f;
      }

      public boolean getIsLooking() {
         return this.isLooking;
      }

      public double getLookPosX() {
         return this.posX;
      }

      public double getLookPosY() {
         return this.posY;
      }

      public double getLookPosZ() {
         return this.posZ;
      }
   }

   public static enum Direction {
      Xpos,
      Xneg,
      Zpos,
      Zneg,
      XposZpos,
      XposZneg,
      XnegZpos,
      XnegZneg;
   }
}
