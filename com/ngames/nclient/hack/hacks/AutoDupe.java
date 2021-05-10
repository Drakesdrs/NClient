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
import com.ngames.nclient.hack.InHUDValue;
import com.ngames.nclient.hack.NClientHack;
import com.ngames.nclient.hack.settings.SettingValue;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.block.BlockLever;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityMinecartEmpty;
import net.minecraft.entity.passive.EntityDonkey;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;

@NClientHack(
   category = Category.EXPLOIT,
   description = "Automatically dupe items",
   name = "AutoDupe",
   words = "AutoDupe Dupe"
)
public class AutoDupe extends Hack {
   private final SettingValue stealDelay = new SettingValue("StealDelay", 100.0F, 0.0F, 1000.0F);
   private final SettingValue loadDonkeyAttemps = new SettingValue("loadDonkeyAttemps", 10.0F, 1.0F, 1000.0F);
   private final SettingValue loadDonkeyClickDelay = new SettingValue("loadDonkeyClickDelay", 5000.0F, 1000.0F, 20000.0F);
   private Vec3i startPos;
   private boolean waitF3;
   private Minecraft MC;
   private BlockPos lever1;
   private BlockPos lever2;
   private BlockPos chest;
   private Entity lastMinecart;
   private Entity preLastMinecart;
   private boolean isStarted;

   public AutoDupe() {
      this.MC = NClient.MC;
      this.settings = Hack.addSettings(this);
      this.inHud = new InHUDValue(0);
   }

   public void onEnable() {
      super.onEnable();
      new Listener(NClientEvent.RunTickKeyboardEvent.class, this);
      Vec3d v = this.MC.field_71439_g.func_174791_d();
      this.startPos = new Vec3i(v.field_72450_a, v.field_72448_b, v.field_72449_c);
      (new Thread(() -> {
         this.start();
         this.isStarted = true;
      })).start();
      (new SafeThread(() -> {
         if (!this.isStarted) {
            BUtils.sleep(100L);
         } else {
            this.cycle();
         }

      }, () -> {
         while(this.MC.field_71441_e == null) {
            BUtils.sleep(50L);
         }

         BUtils.sleep(3000L);
         this.stop();
         this.start();
      }, this)).start();
   }

   public void onInvoke(NClientEvent event) {
      if (this.waitF3) {
         Baritone.processKeyF3(30);
         this.waitF3 = false;
      }

   }

   private void start() {
      List<Entity> except = new ArrayList();
      int count = 0;
      Hacks.packetCanceller.CPacketEntityAction.setValue(false);
      Hacks.packetCanceller.CPacketInput.setValue(true);
      Hacks.packetCanceller.CPacketPlayerAbilities.setValue(false);
      Hacks.packetCanceller.CPacketPlayerDigging.setValue(true);
      Hacks.packetCanceller.CPacketPlayerTryUseItem.setValue(false);
      Hacks.packetCanceller.CPacketPlayerTryUseItemOnBlock.setValue(false);
      Hacks.packetCanceller.CPacketPosition.setValue(true);
      Hacks.packetCanceller.CPacketPositionRotation.setValue(true);
      Hacks.packetCanceller.CPacketRotation.setValue(true);
      Hacks.packetCanceller.CPacketUseEntity.setValue(false);
      Hacks.packetCanceller.CPacketVehicleMove.setValue(true);
      if (!Hacks.entityControl.isEnabled()) {
         Hacks.entityControl.onToggle();
      }

      if (!Hacks.packetCanceller.isEnabled()) {
         Hacks.packetCanceller.onToggle();
      }

      Entity prevMinecart = null;

      Entity minecart;
      while(count < 25 && this.enabled && this.MC.field_71441_e != null) {
         minecart = Baritone.findEntity(6.0F, EntityMinecartEmpty.class, except);
         Entity prev = this.MC.field_71439_g.func_184187_bx();
         if (minecart == null && this.enabled) {
            Baritone.displayMessage("There aren't minecarts near you.");

            while(minecart == null && this.MC.field_71441_e != null) {
               minecart = Baritone.findEntity(6.0F, EntityMinecartEmpty.class, except);
               BUtils.sleep(500L);
            }
         }

         Baritone.setRotationToEntity(minecart, 0.3F);
         if (prev != null) {
            int to1 = 160;

            label114:
            while(true) {
               do {
                  if (this.MC.field_71439_g.func_184187_bx() != prev || this.MC.field_71441_e == null || !this.enabled) {
                     break label114;
                  }

                  BUtils.sleep(50L);
                  Baritone.interactWithEntity(minecart);
                  --to1;
               } while(to1 != 0);

               while(this.MC.field_71439_g.func_184187_bx() != prevMinecart && this.MC.field_71441_e != null && this.enabled) {
                  Baritone.interactWithEntity(prevMinecart);
                  BUtils.sleep(100L);
               }

               while(this.MC.field_71439_g.func_184187_bx() != prev && this.MC.field_71441_e != null && this.enabled) {
                  Baritone.interactWithEntity(prev);
                  BUtils.sleep(100L);
               }

               to1 = 160;
            }
         } else {
            while(this.MC.field_71439_g.func_184187_bx() == null && this.MC.field_71441_e != null) {
               BUtils.sleep(50L);
               Baritone.interactWithEntity(minecart);
            }
         }

         if (count == 14) {
            this.waitF3 = true;

            while(this.waitF3 && this.MC.field_71441_e != null) {
               BUtils.sleep(50L);
            }
         }

         if (count == 23) {
            this.preLastMinecart = minecart;
         }

         if (count == 24) {
            this.lastMinecart = minecart;
         }

         except.add(minecart);
         prevMinecart = prev;
         ++count;
         BUtils.sleep(50L);
      }

      minecart = Baritone.findEntity(6.0F, EntityDonkey.class);
      int to = 80;

      while(this.MC.field_71439_g.func_184187_bx() != minecart && this.MC.field_71441_e != null) {
         Baritone.setRotationToEntity(minecart, 0.1F);
         BUtils.sleep(50L);
         Baritone.interactWithEntity(minecart);
         --to;
         if (to == 0) {
            this.stop();
            this.start();
            return;
         }
      }

      this.getPos();
   }

   private void getPos() {
      List<BlockPos> blocks = Baritone.findBlocks(Blocks.field_150442_at);

      for(int i = 0; i < blocks.size(); ++i) {
         BlockPos block = (BlockPos)blocks.get(i);
         if (block.func_177958_n() != (int)this.preLastMinecart.field_70165_t && block.func_177952_p() != (int)this.preLastMinecart.field_70161_v) {
            if (block.func_177958_n() == (int)this.lastMinecart.field_70165_t - 1 || block.func_177958_n() == (int)this.lastMinecart.field_70165_t + 1) {
               this.lever1 = block;
            }
         } else {
            this.lever2 = block;
         }
      }

      this.chest = new BlockPos(this.lever1.func_177958_n(), this.lever1.func_177956_o() - 2, this.lever1.func_177952_p());
   }

   private void stop() {
      this.stop1();
      this.stop2();
   }

   private void stop1() {
      if (Hacks.entityControl.isEnabled()) {
         Hacks.entityControl.onToggle();
      }

      if (Hacks.packetCanceller.isEnabled()) {
         Hacks.packetCanceller.onToggle();
      }

      int to2 = 100;

      while(this.MC.field_71439_g.func_184187_bx() != null && this.MC.field_71441_e != null && this.enabled) {
         KeyBinding.func_74510_a(this.MC.field_71474_y.field_74311_E.func_151463_i(), true);
         BUtils.sleep(50L);
         --to2;
         if (to2 == 0) {
            KeyBinding.func_74510_a(this.MC.field_71474_y.field_74311_E.func_151463_i(), false);
            BUtils.sleep(50L);
            Baritone.setRotationToBlockPos(this.lever1);
            BUtils.sleep(100L);
            Baritone.rightClickBlock(this.lever1, EnumHand.MAIN_HAND);
            Iterator var2 = this.MC.field_71441_e.func_72910_y().iterator();

            while(var2.hasNext()) {
               Entity e = (Entity)var2.next();
               if (e instanceof EntityDonkey && e != this.MC.field_71439_g.func_184187_bx()) {
                  Baritone.setRotationToEntity(e);
                  Baritone.interactWithEntity(e);
               }
            }

            BUtils.sleep(2000L);
            Baritone.setRotationToBlockPos(this.lever1);
            BUtils.sleep(100L);
            Baritone.rightClickBlock(this.lever1, EnumHand.MAIN_HAND);
            to2 = 100;
         }
      }

      KeyBinding.func_74510_a(this.MC.field_71474_y.field_74311_E.func_151463_i(), false);
   }

   private void stop2() {
      if (!Hacks.timer.isEnabled()) {
         Hacks.timer.onToggle();
      }

      this.MC.field_71439_g.func_71165_d("#goto " + this.startPos.func_177958_n() + " " + this.startPos.func_177956_o() + " " + this.startPos.func_177952_p());
      BUtils.sleep(3000L);

      while((int)this.MC.field_71439_g.field_70165_t != this.startPos.func_177958_n() && (int)this.MC.field_71439_g.field_70163_u != this.startPos.func_177956_o() && (int)this.MC.field_71439_g.field_70161_v != (int)this.MC.field_71439_g.field_70161_v && this.enabled && this.MC.field_71441_e != null) {
         BUtils.sleep(2000L);
         this.MC.field_71439_g.func_71165_d("#goto " + this.startPos.func_177958_n() + " " + this.startPos.func_177956_o() + " " + this.startPos.func_177952_p());
      }

      BUtils.sleep(2000L);
      if (Hacks.timer.isEnabled()) {
         Hacks.timer.onToggle();
      }

   }

   private void lootDonkey() {
      for(int i = 1; i <= 16; ++i) {
         BUtils.sleep((long)((int)this.stealDelay.getValue()));
         this.MC.field_71442_b.func_187098_a(this.MC.field_71439_g.field_71070_bA.field_75152_c, i, 0, ClickType.QUICK_MOVE, this.MC.field_71439_g);
         if (!this.enabled) {
            break;
         }
      }

   }

   private void fillChest() {
      while(this.MC.field_71439_g.field_71070_bA.field_75151_b.size() < 54 && this.enabled && this.MC.field_71441_e != null) {
         BUtils.sleep(50L);
      }

      for(int i = 54; i <= 89; ++i) {
         if (((Slot)this.MC.field_71439_g.field_71070_bA.field_75151_b.get(i)).func_75216_d()) {
            BUtils.sleep((long)((int)this.stealDelay.getValue()));
            this.MC.field_71442_b.func_187098_a(this.MC.field_71439_g.field_71070_bA.field_75152_c, i, 0, ClickType.QUICK_MOVE, this.MC.field_71439_g);
            this.inHud.set((Integer)this.inHud.get() + 1);
         }

         if (!this.enabled) {
            break;
         }
      }

   }

   private void fillDonkey() {
      for(int i = 17; i <= 52; ++i) {
         boolean isFull = true;

         for(int k = 2; k <= 16; ++k) {
            if (!((Slot)this.MC.field_71439_g.field_71070_bA.field_75151_b.get(k)).func_75216_d()) {
               isFull = false;
            }
         }

         if (isFull) {
            return;
         }

         if (((Slot)this.MC.field_71439_g.field_71070_bA.field_75151_b.get(i)).func_75216_d()) {
            BUtils.sleep((long)((int)this.stealDelay.getValue()));
            this.MC.field_71442_b.func_187098_a(this.MC.field_71439_g.field_71070_bA.field_75152_c, i, 0, ClickType.QUICK_MOVE, this.MC.field_71439_g);
         }

         if (!this.enabled) {
            return;
         }
      }

   }

   private void openInv() {
      this.MC.field_71439_g.func_175163_u();
      int to = 200;

      while(true) {
         do {
            if (!this.MC.field_71415_G) {
               return;
            }

            BUtils.sleep(50L);
            --to;
         } while(to != 0);

         int to2 = 50;

         while(this.MC.field_71439_g.func_184187_bx() != this.lastMinecart) {
            Baritone.setRotationToEntity(this.lastMinecart);
            Baritone.interactWithEntity(this.lastMinecart);
            BUtils.sleep(100L);
            --to2;
            if (to2 == 0) {
               this.toggleLever1(false);
               this.toggleLever2(false);
               this.stop();
               this.start();
               return;
            }
         }

         this.MC.field_71439_g.func_175163_u();
         int to3 = 100;

         while(this.MC.field_71415_G) {
            BUtils.sleep(50L);
            --to3;
            if (to3 == 0) {
               this.toggleLever1(false);
               this.toggleLever2(false);
               this.stop();
               this.start();
               return;
            }
         }
      }
   }

   private void cycle() {
      if (this.lever1 == null || this.lever2 == null || this.chest == null) {
         this.getPos();
         if (this.lever1 == null) {
            Baritone.displayMessage("failed find: lever1");
         }

         if (this.chest == null) {
            Baritone.displayMessage("failed find: chest");
         }

         if (this.lever2 == null) {
            Baritone.displayMessage("failed find: lever2");
         }
      }

      this.toggleLever1(true);
      BUtils.sleep((long)((int)this.loadDonkeyClickDelay.getValue()));
      this.toggleLever2(true);
      BUtils.sleep(1000L);
      Entity donkey = null;
      int to1 = (int)this.loadDonkeyAttemps.getValue();

      while(!(donkey instanceof EntityDonkey) || donkey == null && this.enabled) {
         boolean currentState = this.MC.field_71441_e.func_180495_p(this.lever1).equals(this.MC.field_71441_e.func_180495_p(this.lever1).func_177226_a(BlockLever.field_176359_b, true));
         this.toggleLever1(!currentState);
         if (currentState) {
            BUtils.sleep(1000L);
         } else {
            BUtils.sleep((long)((int)this.loadDonkeyClickDelay.getValue()));
         }

         Baritone.setRotationToBlockPos(this.chest);
         BUtils.sleep(100L);
         donkey = NClient.MC.field_71476_x.field_72308_g;
         --to1;
         if (to1 == 0) {
            this.isStarted = false;
            this.toggleLever2(false);
            this.toggleLever1(false);
            this.stop();
            this.start();
            this.isStarted = true;
            return;
         }
      }

      this.openInv();
      this.lootDonkey();
      this.MC.field_71439_g.func_71053_j();
      this.toggleLever2(false);
      Baritone.interactWithEntity(donkey);
      BUtils.sleep(2000L);
      this.openInv();
      this.fillDonkey();
      this.MC.field_71439_g.func_71053_j();
      int empties = 0;
      Iterator var4 = this.MC.field_71439_g.field_71071_by.field_70462_a.iterator();

      while(var4.hasNext()) {
         ItemStack item = (ItemStack)var4.next();
         if (Baritone.isEmpty(item)) {
            ++empties;
         }
      }

      if (empties < 15) {
         boolean stop2 = false;
         byte to = 20;

         while(this.MC.field_71415_G) {
            Baritone.setRotationToBlockPos(this.chest);
            Baritone.rightClickBlock(this.chest, EnumHand.MAIN_HAND);
            BUtils.sleep(500L);
            if (to == 0) {
               stop2 = true;
               this.stop1();
               BUtils.sleep(500L);
               Baritone.setRotationToBlockPos(this.chest);
               Baritone.rightClickBlock(this.chest, EnumHand.MAIN_HAND);
               break;
            }
         }

         this.fillChest();
         this.MC.field_71439_g.func_71053_j();
         if (stop2) {
            this.stop2();
         }
      }

      if ((Integer)Hacks.packetCanceller.inHud.get() >= 27000) {
         this.stop();
         this.start();
      }

   }

   private void toggleLever1(boolean state) {
      int to2 = 40;

      while(!this.MC.field_71441_e.func_180495_p(this.lever1).equals(this.MC.field_71441_e.func_180495_p(this.lever1).func_177226_a(BlockLever.field_176359_b, state)) && this.MC.field_71441_e != null && this.enabled) {
         BUtils.sleep(50L);
         --to2;
         if (to2 == 0) {
            Baritone.setRotationToBlockPos(this.lever1);
            BUtils.sleep(100L);
            Baritone.rightClickBlock(this.lever1, EnumHand.MAIN_HAND);
            to2 = 40;
         }
      }

   }

   private void toggleLever2(boolean state) {
      int to2 = 40;

      while(!this.MC.field_71441_e.func_180495_p(this.lever2).equals(this.MC.field_71441_e.func_180495_p(this.lever2).func_177226_a(BlockLever.field_176359_b, state)) && this.MC.field_71441_e != null && this.enabled) {
         BUtils.sleep(50L);
         --to2;
         if (to2 == 0) {
            Baritone.setRotationToBlockPos(this.lever2);
            BUtils.sleep(100L);
            Baritone.rightClickBlock(this.lever2, EnumHand.MAIN_HAND);
            to2 = 40;
         }
      }

   }
}
