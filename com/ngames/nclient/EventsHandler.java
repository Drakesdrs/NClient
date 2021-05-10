package com.ngames.nclient;

import com.ngames.nclient.baritone.BUtils;
import com.ngames.nclient.baritone.Baritone;
import com.ngames.nclient.event.NClientEvent;
import com.ngames.nclient.gui.Gui;
import com.ngames.nclient.gui.Hud;
import com.ngames.nclient.hack.Hacks;
import com.ngames.nclient.hack.hacks.Criticals;
import com.ngames.nclient.hack.hacks.NoRender;
import com.ngames.nclient.keybinds.KeyBinds;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiIngameMenu;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Items;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.GuiScreenEvent.BackgroundDrawnEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.client.event.RenderLivingEvent.Pre;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.CriticalHitEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.eventhandler.Event.Result;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.MouseInputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.WorldTickEvent;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public class EventsHandler {
   @SubscribeEvent
   public void onJoin(EntityJoinWorldEvent event) {
      Entity e = event.getEntity();
      if (e instanceof EntityPlayerSP) {
         fileUtils.loadAll();
         if (NClientEvent.callEvent(new NClientEvent.PlayerJoinWorldEvent())) {
            event.setCanceled(true);
         }
      }

      if (Hacks.noRender.isEnabled()) {
         NoRender nr = Hacks.noRender;
         if (nr.item.getValue() && e instanceof EntityItem) {
            e.func_174812_G();
         }

         if (nr.entity.getValue() && e instanceof EntityLiving && !(e instanceof EntityOtherPlayerMP)) {
            e.func_174812_G();
         }

         if (nr.other.getValue() && !(e instanceof EntityOtherPlayerMP) && !(e instanceof EntityLiving) && !(e instanceof EntityItem)) {
            e.func_174812_G();
         }
      }

   }

   @SubscribeEvent
   public void onCommand(ClientChatEvent event) {
      if (event.getMessage().charAt(0) == NClient.commandPrefix) {
      }

      CommandExecutor.onCommand(event.getMessage());
      char first = event.getMessage().charAt(0);
      if (Hacks.chatPrefix.isEnabled() && first != '/' && first != NClient.commandPrefix && first != '#') {
         event.setMessage(Hacks.chatPrefix.prefix.getValue() + event.getMessage());
      }

      if (Hacks.chatPostfix.isEnabled() && first != '/' && first != NClient.commandPrefix && first != '#') {
         event.setMessage(event.getMessage() + Hacks.chatPostfix.postfix.getValue());
      }

   }

   @SubscribeEvent
   public void onTick(WorldTickEvent event) {
      ++NClient.ticks;
   }

   @SubscribeEvent
   public void onKeyPress(KeyInputEvent event) {
      if (NClient.MC.field_71441_e != null && !(NClient.MC.field_71462_r instanceof GuiChat) && !Keyboard.getEventKeyState()) {
         if (Gui.justPressed) {
            if (Keyboard.getEventKey() != KeyBinds.getHackKeyBind("ClickGUI")) {
               KeyBinds.getBind(Keyboard.getEventKey());
            }

            Gui.justPressed = false;
         } else {
            KeyBinds.getBind(Keyboard.getEventKey());
         }
      }

   }

   @SubscribeEvent
   public void onCritical(CriticalHitEvent event) {
      if (Hacks.criticals.isEnabled() && Hacks.criticals.type.getValue() == 0) {
         event.setResult(Result.ALLOW);
      }

   }

   @SubscribeEvent
   public void onRenderGameOverlay(RenderGameOverlayEvent event) {
      if (!event.isCancelable() && event.getType() == ElementType.HELMET) {
         if (Hacks.hud.isEnabled()) {
            Hud.drawHUD();
         }

         if (NClient.gui != null && Hacks.noRender.isEnabled()) {
            NClient.gui.func_73863_a(Gui.mouseX, Gui.mouseY, NClient.MC.func_184121_ak());
         }
      }

   }

   @SubscribeEvent
   public void onRenderEntity(Pre<?> event) {
   }

   @SubscribeEvent
   public void onMouseClick(MouseInputEvent event) {
      if (!Mouse.getEventButtonState() && Mouse.getEventButton() == 0) {
         NClient.c.add(NClient.t);
         ++Hacks.cpsCount.clicks;
         NClient.isLeftPressed = false;
      } else if (Mouse.getEventButton() == 0) {
         NClient.isLeftPressed = true;
      }

      if (Mouse.getEventButtonState() && (NClient.MC.field_71474_y.field_74313_G.func_151470_d() || NClient.MC.field_71474_y.field_74312_F.func_151470_d() && NClient.MC.field_71476_x.field_72313_a != Type.MISS) && (Baritone.isInMainHand(Items.field_151079_bi) || Baritone.isInMainHand(Items.field_151031_f) || Baritone.isInMainHand(Items.field_151126_ay)) && Baritone.overrideRotation) {
         Baritone.prevYaw = Baritone.yaw;
         Baritone.prevPitch = Baritone.pitch;
         Baritone.yaw = NClient.MC.field_71439_g.field_70177_z;
         Baritone.pitch = NClient.MC.field_71439_g.field_70125_A;
         Baritone.needRotate = true;
      }

      if (!Mouse.getEventButtonState() && (!NClient.MC.field_71474_y.field_74313_G.func_151470_d() || !Baritone.isInMainHand(Items.field_151079_bi) && !Baritone.isInMainHand(Items.field_151031_f) && !Baritone.isInMainHand(Items.field_151126_ay)) && (!NClient.MC.field_71474_y.field_74312_F.func_151470_d() || NClient.MC.field_71476_x.field_72313_a == Type.MISS) && Baritone.needRotate && Baritone.overrideRotation) {
         Baritone.yaw = Baritone.prevYaw;
         Baritone.pitch = Baritone.prevPitch;
         Baritone.needRotate = false;
      }

   }

   @SubscribeEvent
   public void onPvP(AttackEntityEvent event) {
      if (event.getEntity() instanceof EntityPlayerSP) {
         NClient.inPvP = true;
         (new Thread(() -> {
            BUtils.sleep(5000L);
            NClient.inPvP = false;
         })).start();
         if (!Hacks.autoEz.targeted.contains(event.getTarget().func_145782_y())) {
            Hacks.autoEz.targeted.add(event.getTarget().func_145782_y());
         }

         event.setCanceled(Criticals.miniJump(event.getTarget()));
      }

   }

   @SubscribeEvent
   public void onHurt(LivingAttackEvent event) {
      if (event.getEntity() instanceof EntityPlayerSP) {
         NClient.inPvP = true;
         (new Thread(() -> {
            BUtils.sleep(5000L);
            NClient.inPvP = false;
         })).start();
      }

   }

   @SubscribeEvent
   public void onBackgroundDrawn(BackgroundDrawnEvent event) {
      if (!(event.getGui() instanceof GuiIngameMenu) && NClient.MC.field_71441_e == null) {
         Gui.drawBackground();
      }
   }
}
