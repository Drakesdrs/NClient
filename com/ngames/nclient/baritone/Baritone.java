package com.ngames.nclient.baritone;

import com.ngames.nclient.NClient;
import com.ngames.nclient.hack.Hacks;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketPlayer.Rotation;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.FoodStats;
import net.minecraft.util.Timer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.util.text.TextComponentString;

public class Baritone {
   public static final int CRAFTING_1 = 0;
   public static final int CRAFTING_2 = 1;
   public static final int CRAFTING_3 = 2;
   public static final int CRAFTING_4 = 3;
   public static final int ARMOR_HELMET = 4;
   public static final int ARMOR_CHESTPLACE = 5;
   public static final int ARMOR_LEGGINS = 6;
   public static final int ARMOR_BOOTS = 7;
   public static final int INVENTORY_1 = 8;
   public static final int INVENTORY_2 = 9;
   public static final int INVENTORY_3 = 10;
   public static final int INVENTORY_4 = 11;
   public static final int INVENTORY_5 = 12;
   public static final int INVENTORY_6 = 13;
   public static final int INVENTORY_7 = 14;
   public static final int INVENTORY_8 = 15;
   public static final int INVENTORY_9 = 16;
   public static final int INVENTORY_10 = 17;
   public static final int INVENTORY_11 = 18;
   public static final int INVENTORY_12 = 19;
   public static final int INVENTORY_13 = 20;
   public static final int INVENTORY_14 = 21;
   public static final int INVENTORY_15 = 22;
   public static final int INVENTORY_16 = 23;
   public static final int INVENTORY_17 = 24;
   public static final int INVENTORY_18 = 25;
   public static final int INVENTORY_19 = 26;
   public static final int INVENTORY_20 = 27;
   public static final int INVENTORY_21 = 28;
   public static final int INVENTORY_22 = 29;
   public static final int INVENTORY_23 = 30;
   public static final int INVENTORY_24 = 31;
   public static final int INVENTORY_25 = 32;
   public static final int INVENTORY_26 = 33;
   public static final int INVENTORY_27 = 34;
   public static final int HOTBAR_1 = 35;
   public static final int HOTBAR_2 = 36;
   public static final int HOTBAR_3 = 37;
   public static final int HOTBAR_4 = 38;
   public static final int HOTBAR_5 = 39;
   public static final int HOTBAR_6 = 40;
   public static final int HOTBAR_7 = 41;
   public static final int HOTBAR_8 = 42;
   public static final int HOTBAR_9 = 43;
   public static final int OFFHAND = 45;
   public static final int INVENTORY = 0;
   public static final int CHEST = 1;
   public static boolean clickSync = false;
   public static float yaw;
   public static float pitch;
   public static float prevYaw;
   public static float prevPitch;
   public static boolean overrideRotation;
   public static boolean serverSprinting;
   public static boolean serverSprintingState;
   public static boolean overrideSprinting;
   public static boolean needRotate;
   private static short rotationPriority;

   public static void rightClickMouse() {
      try {
         Method m = Minecraft.class.getDeclaredMethod("func_147121_ag");
         m.setAccessible(true);
         m.invoke(NClient.MC);
      } catch (NoSuchMethodException var1) {
         var1.printStackTrace();
      } catch (SecurityException var2) {
         var2.printStackTrace();
      } catch (IllegalAccessException var3) {
         var3.printStackTrace();
      } catch (IllegalArgumentException var4) {
         var4.printStackTrace();
      } catch (InvocationTargetException var5) {
         var5.getCause().printStackTrace();
      }

   }

   public static void leftClickMouse() {
      try {
         Method m = Minecraft.class.getDeclaredMethod("func_147116_af");
         m.setAccessible(true);
         m.invoke(NClient.MC);
      } catch (NoSuchMethodException var1) {
         var1.printStackTrace();
      } catch (SecurityException var2) {
         var2.printStackTrace();
      } catch (IllegalAccessException var3) {
         var3.printStackTrace();
      } catch (IllegalArgumentException var4) {
         var4.printStackTrace();
      } catch (InvocationTargetException var5) {
         var5.printStackTrace();
      }

      ++Hacks.cpsCount.clicks;
   }

   public static void processKeyF3(int auxKey) {
      try {
         Method m = Minecraft.class.getDeclaredMethod("func_184122_c", Integer.TYPE);
         m.setAccessible(true);
         m.invoke(NClient.MC, auxKey);
      } catch (NoSuchMethodException var2) {
         var2.printStackTrace();
      } catch (SecurityException var3) {
         var3.printStackTrace();
      } catch (IllegalAccessException var4) {
         var4.printStackTrace();
      } catch (IllegalArgumentException var5) {
         var5.printStackTrace();
      } catch (InvocationTargetException var6) {
         var6.getCause().printStackTrace();
      }

   }

   public static void attackEntity(Entity target) {
      if (target != null && NClient.MC.field_71439_g != null) {
         NClient.MC.field_71442_b.func_78764_a(NClient.MC.field_71439_g, target);
         NClient.MC.field_71439_g.func_184609_a(EnumHand.MAIN_HAND);
      }

      ++Hacks.cpsCount.clicks;
   }

   public static void attackEntity() {
      if (NClient.MC.field_71439_g != null && NClient.MC.field_71476_x.field_72313_a == Type.ENTITY) {
         NClient.MC.field_71442_b.func_78764_a(NClient.MC.field_71439_g, NClient.MC.field_71476_x.field_72308_g);
         NClient.MC.field_71439_g.func_184609_a(EnumHand.MAIN_HAND);
      }

      ++Hacks.cpsCount.clicks;
   }

   public static void interactWithEntity(Entity target) {
      NClient.MC.field_71442_b.func_187097_a(NClient.MC.field_71439_g, target, EnumHand.MAIN_HAND);
   }

   public static void rightClickBlock(BlockPos block, EnumHand hand) {
      NClient.MC.field_71442_b.func_187099_a(NClient.MC.field_71439_g, NClient.MC.field_71441_e, block, EnumFacing.func_190914_a(block, NClient.MC.field_71439_g), new Vec3d((double)block.func_177958_n(), (double)block.func_177956_o(), (double)block.func_177952_p()), hand);
   }

   public static int getLeftClickCounter() {
      Field f = null;

      try {
         f = Minecraft.class.getDeclaredField("field_71429_W");
         f.setAccessible(true);
      } catch (SecurityException | NoSuchFieldException var4) {
         var4.printStackTrace();
      }

      int ret = 10;

      try {
         ret = f.getInt(NClient.MC);
      } catch (IllegalAccessException | IllegalArgumentException var3) {
         var3.printStackTrace();
      }

      return ret;
   }

   public static void setLeftClickCounter() {
      Field f = null;

      try {
         f = Minecraft.class.getDeclaredField("field_71429_W");
         f.setAccessible(true);
      } catch (SecurityException | NoSuchFieldException var3) {
         var3.printStackTrace();
      }

      try {
         f.setInt(NClient.MC, 10);
      } catch (IllegalAccessException | IllegalArgumentException var2) {
         var2.printStackTrace();
      }

   }

   public static void setTimer(Timer timer) {
      Field f = null;

      try {
         f = Minecraft.class.getDeclaredField("field_71428_T");
         f.setAccessible(true);
         f.set(NClient.MC, timer);
      } catch (SecurityException | IllegalArgumentException | IllegalAccessException | NoSuchFieldException var3) {
         var3.printStackTrace();
      }

   }

   public static void setSPRINTING_SPEED_BOOST(AttributeModifier SPRINTING_SPEED_BOOST) {
      Field f = null;

      try {
         f = EntityLivingBase.class.getDeclaredField("field_110157_c");
         f.setAccessible(true);
         f.set(NClient.MC.field_71439_g, SPRINTING_SPEED_BOOST);
      } catch (SecurityException | IllegalArgumentException | IllegalAccessException | NoSuchFieldException var3) {
         var3.printStackTrace();
      }

   }

   public static int getJumpTicks() {
      Field f = null;

      try {
         f = EntityLivingBase.class.getDeclaredField("field_70773_bE");
         f.setAccessible(true);
      } catch (SecurityException | NoSuchFieldException var4) {
         var4.printStackTrace();
      }

      int ret = -1;

      try {
         ret = f.getInt(NClient.MC.field_71439_g);
      } catch (IllegalAccessException | IllegalArgumentException var3) {
         var3.printStackTrace();
      }

      return ret;
   }

   public static boolean getServerSprintState() {
      Field f = null;

      try {
         f = EntityPlayerSP.class.getDeclaredField("field_175171_bO");
         f.setAccessible(true);
      } catch (SecurityException | NoSuchFieldException var4) {
         var4.printStackTrace();
      }

      boolean ret = false;

      try {
         ret = f.getBoolean(NClient.MC.field_71439_g);
      } catch (IllegalAccessException | IllegalArgumentException var3) {
         var3.printStackTrace();
      }

      return ret;
   }

   public static Set<ChunkPos> getVisibleChunks() {
      Field f = null;

      try {
         f = WorldClient.class.getDeclaredField("field_184157_a");
         f.setAccessible(true);
      } catch (SecurityException | NoSuchFieldException var4) {
         var4.printStackTrace();
      }

      Set ret = null;

      try {
         ret = (Set)f.get(NClient.MC.field_71441_e);
      } catch (IllegalAccessException | IllegalArgumentException var3) {
         var3.printStackTrace();
      }

      return ret;
   }

   public static FoodStats getFoodStats() {
      return NClient.MC.field_71439_g.func_71024_bL();
   }

   public static void dropItem(int slotId) {
      NClient.MC.field_71442_b.func_187098_a(NClient.MC.field_71439_g.field_71070_bA.field_75152_c, slotId, 1, ClickType.THROW, NClient.MC.field_71439_g);
   }

   public static void dropCurrentItem() {
      NClient.MC.field_71439_g.func_71040_bB(true);
   }

   public static void addRotationPlayer(float yaw, float pitch) {
      rotatePlayer(NClient.MC.field_71439_g.field_70177_z + yaw, NClient.MC.field_71439_g.field_70125_A + pitch);
   }

   public static void dropAllItems(float minDelay, float maxDelay) {
      dropAllItems((int)minDelay, (int)maxDelay);
   }

   public static void dropAllItems() {
      List<Integer> id = new ArrayList();
      Iterator var1 = NClient.MC.field_71439_g.field_71070_bA.field_75151_b.iterator();

      while(var1.hasNext()) {
         Slot slot = (Slot)var1.next();
         id.add(slot.getSlotIndex());
      }

      var1 = id.iterator();

      while(var1.hasNext()) {
         int cid = (Integer)var1.next();
         dropItem(cid);
      }

   }

   public static void dropAllItems(int minDelay, int maxDelay) {
      List<Integer> id = new ArrayList();
      Iterator var3 = NClient.MC.field_71439_g.field_71070_bA.field_75151_b.iterator();

      while(var3.hasNext()) {
         Slot slot = (Slot)var3.next();
         id.add(slot.getSlotIndex());
      }

      var3 = id.iterator();

      while(var3.hasNext()) {
         int cid = (Integer)var3.next();
         BUtils.sleep((long)BUtils.randomInRange(minDelay, maxDelay), BUtils.randomInRange(0, 999999));
         dropItem(cid);
      }

   }

   public static void closeInventory() {
      NClient.MC.field_71439_g.field_71070_bA.func_75134_a(NClient.MC.field_71439_g);
      NClient.MC.func_147108_a((GuiScreen)null);
      if (NClient.MC.field_71462_r == null) {
         NClient.MC.func_71381_h();
      }

   }

   public static void rotatePlayer(float yaw, float pitch) {
      NClient.MC.field_71439_g.field_70177_z = yaw;
      NClient.MC.field_71439_g.field_70125_A = pitch;
   }

   public static void rotatePlayer(float yaw, float pitch, boolean onlyServer) {
      if (onlyServer) {
         CPacketPlayer pc = new CPacketPlayer();
         new Rotation(yaw, pitch, true);
         NClient.MC.func_147114_u().func_147297_a(pc);
      } else {
         rotatePlayer(yaw, pitch);
      }

   }

   public static void rotateHead(float yaw) {
      NClient.MC.field_71439_g.field_70759_as = yaw;
   }

   public static void addRotationPlayer(float yaw, float pitch, boolean onlyServer) {
      Baritone.yaw += yaw;
      Baritone.pitch += pitch;
      if (onlyServer) {
         updateRotation();
      } else {
         addRotationPlayer(yaw, pitch);
      }

   }

   public static void addRotationHead(float yaw) {
      rotateHead(NClient.MC.field_71439_g.field_70759_as + yaw);
   }

   public static void setRotationToEntity(Entity entity) {
      setRotationToEntity(entity, 0.5F);
   }

   public static void setRotationToEntity(Entity entity, boolean onlyServer) {
      setRotationToEntity(entity, 0.5F, onlyServer);
   }

   public static void setRotationToEntity(Entity entity, float dPitch) {
      setRotationToEntity(entity, dPitch, false);
   }

   public static void setRotationToEntity(Entity entity, float dPitch, boolean onlyServer) {
      BUtils.EntityLookHelper elh = new BUtils.EntityLookHelper(NClient.MC.field_71439_g);
      elh.setLookPositionWithEntity(entity, 360.0F, 360.0F);
      elh.onUpdateLook(dPitch, onlyServer);
   }

   public static void setRotationToBlockPos(BlockPos pos) {
      BUtils.EntityLookHelper elh = new BUtils.EntityLookHelper(NClient.MC.field_71439_g);
      elh.setLookPosition((double)((float)pos.func_177958_n() + 0.5F), (double)((float)pos.func_177956_o() + 0.5F), (double)((float)pos.func_177952_p() + 0.5F), 360.0F, 360.0F);
      elh.onUpdateLook(0.0F);
   }

   public static void refreshRotation() {
      yaw = NClient.MC.field_71439_g.field_70177_z;
      pitch = NClient.MC.field_71439_g.field_70125_A;
   }

   public static void smoothRotatePlayer(float yaw, float pitch, long millis, boolean dirYaw, boolean dirPitch) {
      float aYaw = 0.0F;
      float aPitch = 0.0F;

      while(NClient.MC.field_71439_g.field_70177_z != yaw || NClient.MC.field_71439_g.field_70125_A != pitch) {
         if (NClient.MC.field_71439_g.field_70177_z != yaw && dirYaw) {
            aYaw = 0.01F;
         }

         if (NClient.MC.field_71439_g.field_70125_A != pitch && dirPitch) {
            aPitch = 0.01F;
         }

         if (NClient.MC.field_71439_g.field_70177_z != yaw && !dirYaw) {
            aYaw = -0.01F;
         }

         if (NClient.MC.field_71439_g.field_70125_A != pitch && !dirPitch) {
            aPitch = -0.01F;
         }

         if (NClient.MC.field_71439_g.field_70177_z > yaw && dirYaw || NClient.MC.field_71439_g.field_70125_A > pitch && dirPitch || NClient.MC.field_71439_g.field_70177_z < yaw && !dirYaw || NClient.MC.field_71439_g.field_70125_A < pitch && !dirPitch) {
            break;
         }

         addRotationPlayer(aYaw, aPitch);
         BUtils.sleep((long)((float)millis / 0.01F));
      }

   }

   public static void smoothRotateHead(float yaw, long millis) {
      double yawMs = (double)(yaw / (float)millis);

      for(int i = 0; (long)i < millis; ++i) {
         addRotationHead((float)yawMs);
      }

      rotateHead(yaw);
   }

   public static List<BlockPos> findBlocks(Block block) {
      List<BlockPos> ret = new ArrayList();
      if (NClient.MC.field_71441_e != null) {
         Iterator var2 = getVisibleChunks().iterator();

         while(var2.hasNext()) {
            ChunkPos cp = (ChunkPos)var2.next();

            for(int x = 0; x < 16; ++x) {
               for(int y = 0; y < 256; ++y) {
                  for(int z = 0; z < 16; ++z) {
                     BlockPos pos = new BlockPos(cp.func_180334_c() + x, y, cp.func_180333_d() + z);
                     IBlockState blockState = NClient.MC.field_71441_e.func_180495_p(pos);
                     if (blockState.func_177230_c() == block) {
                        ret.add(pos);
                     }
                  }
               }
            }
         }
      }

      return ret;
   }

   public static Entity findEntity(float range, Class<? extends Entity> type) {
      Entity ret = null;
      double distance = Double.MAX_VALUE;
      Iterator var5 = NClient.MC.field_71441_e.func_72910_y().iterator();

      while(true) {
         Entity e;
         double dis;
         do {
            do {
               if (!var5.hasNext()) {
                  return ret;
               }

               e = (Entity)var5.next();
               dis = (double)NClient.MC.field_71439_g.func_70032_d(e);
            } while(e.getClass() != type);
         } while(ret != null && !(dis < distance));

         if (dis <= (double)range) {
            ret = e;
            distance = dis;
         }
      }
   }

   public static Entity findEntity(float range, Class<? extends Entity> type, List<Entity> except) {
      Entity ret = null;
      double distance = Double.MAX_VALUE;
      Iterator var6 = NClient.MC.field_71441_e.func_72910_y().iterator();

      while(var6.hasNext()) {
         Entity e = (Entity)var6.next();
         boolean contains = false;
         Iterator var9 = except.iterator();

         while(var9.hasNext()) {
            Entity e1 = (Entity)var9.next();
            if (e1.func_145782_y() == e.func_145782_y()) {
               contains = true;
               break;
            }
         }

         double dis = (double)NClient.MC.field_71439_g.func_70032_d(e);
         if (e.getClass() == type && dis < distance && !contains && dis <= (double)range) {
            distance = dis;
            ret = e;
         }
      }

      return ret;
   }

   public static void useItem(EnumHand hand) {
      NClient.MC.field_71442_b.func_187101_a(NClient.MC.field_71439_g, NClient.MC.field_71441_e, hand);
   }

   public static void useItem() {
      KeyBinding.func_74510_a(NClient.MC.field_71474_y.field_74313_G.func_151463_i(), true);
   }

   public static void usedItem() {
      KeyBinding.func_74510_a(NClient.MC.field_71474_y.field_74313_G.func_151463_i(), false);
   }

   public static void putInMainHand(int slotId) {
      NClient.MC.field_71442_b.func_187098_a(NClient.MC.field_71439_g.field_71069_bz.field_75152_c, slotId, 0, ClickType.PICKUP_ALL, NClient.MC.field_71439_g);
      NClient.MC.field_71442_b.func_187098_a(NClient.MC.field_71439_g.field_71069_bz.field_75152_c, NClient.MC.field_71439_g.field_71071_by.field_70461_c + 36, 0, ClickType.SWAP, NClient.MC.field_71439_g);
      NClient.MC.field_71442_b.func_187098_a(NClient.MC.field_71439_g.field_71069_bz.field_75152_c, slotId, 0, ClickType.PICKUP_ALL, NClient.MC.field_71439_g);
   }

   public static void putInOffHand(int slotId) {
      if (slotId < 9) {
         slotId += 36;
      }

      NClient.MC.field_71442_b.func_187098_a(NClient.MC.field_71439_g.field_71069_bz.field_75152_c, slotId, 0, ClickType.PICKUP, NClient.MC.field_71439_g);
      NClient.MC.field_71442_b.func_187098_a(NClient.MC.field_71439_g.field_71069_bz.field_75152_c, 45, 0, ClickType.PICKUP, NClient.MC.field_71439_g);
      NClient.MC.field_71442_b.func_187098_a(NClient.MC.field_71439_g.field_71069_bz.field_75152_c, slotId, 0, ClickType.PICKUP, NClient.MC.field_71439_g);
   }

   public static void putInAir(int slotId) {
      NClient.MC.field_71442_b.func_187098_a(0, slotId, 1, ClickType.QUICK_MOVE, NClient.MC.field_71439_g);
   }

   public static void setMainHand(int slotId) {
      NClient.MC.field_71439_g.field_71071_by.field_70461_c = slotId;
   }

   public static int getItem(ItemStack itemStack, boolean onlyHotbar) {
      int ret = -1;
      int i = 0;
      Item item = itemStack.func_77973_b();

      for(Iterator var5 = NClient.MC.field_71439_g.field_71071_by.field_70462_a.iterator(); var5.hasNext(); ++i) {
         ItemStack itemStack2 = (ItemStack)var5.next();
         Item item2 = itemStack2.func_77973_b();
         if (item.equals(item2)) {
            if (onlyHotbar && i > 34 && i < 44) {
               ret = i;
            } else if (!onlyHotbar) {
               ret = i;
            }
         }
      }

      return ret;
   }

   public static int getBestFood(boolean onlyHotbar) {
      int ret = 8;
      boolean isFood = false;
      int i = 0;

      for(Iterator var4 = NClient.MC.field_71439_g.field_71071_by.field_70462_a.iterator(); var4.hasNext(); ++i) {
         ItemStack itemStack = (ItemStack)var4.next();
         Item item = itemStack.func_77973_b();
         ItemStack itemStack2 = (ItemStack)NClient.MC.field_71439_g.field_71071_by.field_70462_a.get(ret);
         Item item2 = itemStack2.func_77973_b();
         if (item instanceof ItemFood) {
            isFood = true;
            if (item2 instanceof ItemFood) {
               if (((ItemFood)item).func_150905_g(itemStack) > ((ItemFood)item2).func_150905_g(itemStack2)) {
                  if (onlyHotbar && i > 34 && i < 44) {
                     ret = i;
                  } else if (!onlyHotbar) {
                     ret = i;
                  }
               }
            } else {
               ret = i;
            }
         }
      }

      if (isFood) {
         return ret;
      } else {
         return -1;
      }
   }

   public static int getFoodWithHeal(byte healLvl, boolean onlyHotbar) {
      int ret = 8;
      boolean isFood = false;
      int i = 0;
      byte oldDelta = 21;

      for(Iterator var6 = NClient.MC.field_71439_g.field_71071_by.field_70462_a.iterator(); var6.hasNext(); ++i) {
         ItemStack itemStack = (ItemStack)var6.next();
         Item item = itemStack.func_77973_b();
         ItemStack itemStack2 = (ItemStack)NClient.MC.field_71439_g.field_71071_by.field_70462_a.get(ret);
         Item item2 = itemStack2.func_77973_b();
         if (item instanceof ItemFood) {
            isFood = true;
            if (item2 instanceof ItemFood) {
               byte healAmount = (byte)((ItemFood)item).func_150905_g(itemStack);
               byte healAmount2 = (byte)((ItemFood)item2).func_150905_g(itemStack2);
               byte delta = (byte)(healAmount > healAmount2 ? healAmount - healAmount2 : healAmount2 - healAmount);
               if (delta < oldDelta) {
                  if (onlyHotbar && i > 34 && i < 44) {
                     ret = i;
                  } else if (!onlyHotbar) {
                     ret = i;
                  }

                  oldDelta = delta;
               }
            } else {
               ret = i;
            }
         }
      }

      if (isFood) {
         return ret;
      } else {
         return -1;
      }
   }

   public static Entity getPriorityTarget(float range, boolean onlyPlayers, boolean onlyMobs, boolean onlyPrevTarget, @Nullable Entity prevTarget, byte priority) {
      Entity target = null;
      float distance = Float.MAX_VALUE;
      float hp = Float.MAX_VALUE;
      if (NClient.MC.field_71441_e != null) {
         for(int i = 0; i < NClient.MC.field_71441_e.func_72910_y().size(); ++i) {
            Entity e = (Entity)NClient.MC.field_71441_e.func_72910_y().get(i);
            if (NClient.MC.field_71441_e != null && NClient.MC.field_71439_g.func_70032_d(e) <= range && isAlive(e) && (e instanceof EntityOtherPlayerMP && onlyPlayers || e instanceof EntityLiving && onlyMobs)) {
               float health = e instanceof EntityOtherPlayerMP ? ((EntityOtherPlayerMP)e).func_110143_aJ() : ((EntityLiving)e).func_110143_aJ();
               float dist = NClient.MC.field_71439_g.func_70032_d(e);
               if (priority == 0 && health < hp) {
                  target = e;
                  hp = health;
               }

               if (priority == 1 && dist < distance) {
                  target = e;
                  distance = dist;
               }

               if (health == hp && dist < distance) {
                  target = e;
               }

               if (dist < distance && health < hp) {
                  target = e;
               }
            }
         }
      }

      if (onlyPrevTarget && prevTarget != null && isAlive(prevTarget) && NClient.MC.field_71439_g.func_70032_d(prevTarget) < range) {
         target = prevTarget;
      }

      return target;
   }

   public static Entity getBotTarget(Entity target, boolean filterEntityType) {
      Entity entityBot = target;

      for(int i = 0; i < NClient.MC.field_71441_e.func_72910_y().size(); ++i) {
         Entity e = (Entity)NClient.MC.field_71441_e.func_72910_y().get(i);
         if (e != null && e != NClient.MC.field_71439_g) {
            boolean equalsPos = BUtils.isInRange(e.field_70165_t, entityBot.field_70165_t, 0.5F) && BUtils.isInRange(e.field_70163_u, entityBot.field_70163_u, 1.2F) && BUtils.isInRange(e.field_70161_v, entityBot.field_70161_v, 0.5F);
            boolean notPlayer = e instanceof EntityLiving && e.getClass() != EntityOtherPlayerMP.class;
            boolean biggestId = e.func_145782_y() > entityBot.func_145782_y();
            if (equalsPos && (notPlayer || !filterEntityType) && (biggestId || !notPlayer)) {
               entityBot = e;
            }
         }
      }

      return entityBot;
   }

   public static List<Entity> getMultiTargets(float range, boolean onlyPlayers, boolean onlyMobs) {
      List<Entity> targets = new ArrayList();

      for(int i = 0; i < NClient.MC.field_71441_e.func_72910_y().size(); ++i) {
         Entity e = (Entity)NClient.MC.field_71441_e.func_72910_y().get(i);
         if (NClient.MC.field_71441_e != null && NClient.MC.field_71439_g.func_70032_d(e) <= range && isAlive(e) && (e instanceof EntityOtherPlayerMP && onlyPlayers || e instanceof EntityLiving && onlyMobs)) {
            targets.add(e);
         }
      }

      return targets;
   }

   public static boolean isAlive(Entity e) {
      return e.func_70089_S() && e instanceof EntityLivingBase ? ((EntityLivingBase)e).field_70725_aQ == 0 : true;
   }

   public static void displayMessage(String message) {
      NClient.MC.field_71439_g.func_145747_a(new TextComponentString("\\u00A7d[NClient]\\u00A73 " + message));
   }

   public static int getSlotFor(Item item, boolean onlyHotbar) {
      InventoryPlayer inv = NClient.MC.field_71439_g.field_71071_by;
      int slot = -1;
      int i = 0;

      for(Iterator var5 = inv.field_70462_a.iterator(); var5.hasNext(); ++i) {
         ItemStack itemStack = (ItemStack)var5.next();
         if (itemStack.func_77973_b().getRegistryName().equals(item.getRegistryName())) {
            if (onlyHotbar && i < 9) {
               slot = i;
            } else if (!onlyHotbar) {
               slot = i;
            }

            return slot;
         }
      }

      return slot;
   }

   public static boolean isInMainHand(Item item) {
      return ((ItemStack)NClient.MC.field_71439_g.field_71071_by.field_70462_a.get(NClient.MC.field_71439_g.field_71071_by.field_70461_c)).func_77973_b().getRegistryName().equals(item.getRegistryName());
   }

   public static boolean isInOffHand(Item item) {
      return Item.func_150891_b(((ItemStack)NClient.MC.field_71439_g.field_71071_by.field_184439_c.get(0)).func_77973_b()) == Item.func_150891_b(item);
   }

   public static boolean isEmptyInOffHand() {
      return ((ItemStack)NClient.MC.field_71439_g.field_71071_by.field_184439_c.get(0)).func_77969_a(ItemStack.field_190927_a);
   }

   public static boolean isEmpty(ItemStack item) {
      return item == null || item.func_190926_b() || item.func_77973_b() == Items.field_190931_a;
   }

   public static boolean isMoving() {
      return NClient.MC.field_71439_g.field_191988_bg != 0.0F || NClient.MC.field_71439_g.field_70702_br != 0.0F;
   }

   public static void updateRotation() {
      Field yaw = null;
      Field pitch = null;

      try {
         yaw = EntityPlayerSP.class.getDeclaredField("field_175164_bL");
         pitch = EntityPlayerSP.class.getDeclaredField("field_175165_bM");
      } catch (SecurityException | NoSuchFieldException var4) {
         var4.printStackTrace();
      }

      yaw.setAccessible(true);
      pitch.setAccessible(true);

      try {
         yaw.set(NClient.MC.field_71439_g, NClient.MC.field_71439_g.field_70177_z - 1.0F);
         pitch.set(NClient.MC.field_71439_g, NClient.MC.field_71439_g.field_70125_A - 1.0F);
      } catch (IllegalAccessException | IllegalArgumentException var3) {
         var3.printStackTrace();
      }

   }

   public static void setSneaking(boolean sneak) {
      if (NClient.MC.field_71439_g.func_70093_af() != sneak) {
         KeyBinding.func_74510_a(NClient.MC.field_71474_y.field_74311_E.func_151463_i(), sneak);
      }

   }

   public static void setJumping(boolean jump) {
      KeyBinding.func_74510_a(NClient.MC.field_71474_y.field_74314_A.func_151463_i(), jump);
   }

   public static void setOverrideSprinting(boolean state) {
      serverSprintingState = getServerSprintState();
      overrideSprinting = state;
   }

   public static boolean calcRotationPriority(short rotationPriorityOther) {
      if (rotationPriorityOther >= rotationPriority) {
         rotationPriority = rotationPriorityOther;
         return true;
      } else {
         return false;
      }
   }

   public static void setRotationPriority(short rotationPriority) {
      Baritone.rotationPriority = rotationPriority;
   }
}
