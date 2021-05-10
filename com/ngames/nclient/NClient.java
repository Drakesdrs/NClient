package com.ngames.nclient;

import com.ngames.nclient.baritone.BUtils;
import com.ngames.nclient.event.Listener;
import com.ngames.nclient.gui.Gui;
import com.ngames.nclient.gui.Theme;
import com.ngames.nclient.gui.font.NFontRenderer;
import com.ngames.nclient.hack.Hack;
import com.ngames.nclient.hack.Hacks;
import com.ngames.nclient.proxy.CommonProxy;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.Display;

@Mod(
   modid = "nclient",
   name = "N-Client",
   version = "B1",
   acceptedMinecraftVersions = "[1.12]"
)
public class NClient {
   public static final String MODID = "nclient";
   public static final String NAME = "N-Client";
   public static final String VERSION = "B1";
   public static final Minecraft MC = Minecraft.func_71410_x();
   public static final ResourceLocation background = new ResourceLocation("textures/gui/background.png");
   public static final ResourceLocation blur = new ResourceLocation("textures/gui/blur.png");
   public static NFontRenderer chatFont;
   public static Logger logger;
   public static Gui gui;
   public static Theme theme = new Theme();
   public static File path;
   public static File NClientPath;
   public static File settings;
   @SidedProxy(
      clientSide = "com.ngames.nclient.proxy.ClientProxy",
      serverSide = "com.ngames.nclient.proxy.CommonProxy"
   )
   public static CommonProxy proxy;
   public static char commandPrefix;
   public static HashMap<String, Integer> hacks;
   public static List<Hack> HackList;
   public static boolean inPvP;
   public static boolean isLeftPressed;
   public static List<Listener> listeners;
   public static List<Long> c;
   public static long t;
   public static byte tps;
   public static byte ticks;

   @EventHandler
   public void preInit(FMLPreInitializationEvent event) {
      logger = event.getModLog();
      HackList = Hacks.initL();
      hacks = Hacks.init();
      proxy.preInit(event);
      if (!NClientPath.exists()) {
         NClientPath.mkdir();
      }

      try {
         if (!settings.exists()) {
            settings.createNewFile();
         }
      } catch (IOException var3) {
         var3.printStackTrace();
      }

   }

   @EventHandler
   public void init(FMLInitializationEvent event) {
      proxy.init(event);
      c.add(0L);
      (new Thread(() -> {
         while(true) {
            ++t;
            BUtils.sleep(1L);
         }
      })).start();
      (new Thread(() -> {
         while(true) {
            tps = ticks;
            ticks = 0;
            BUtils.sleep(1000L);
         }
      })).start();
   }

   @EventHandler
   public void postInit(FMLPostInitializationEvent event) {
      Display.setTitle("N-Client B1");
      proxy.postInit(event);
   }

   public static Hack getHack(String name) {
      return (Hack)HackList.get((Integer)hacks.get(name));
   }

   public static boolean isHackExist(String name) {
      boolean ret = false;
      if (hacks.containsKey(name)) {
         ret = true;
      }

      return ret;
   }

   static {
      path = MC.field_71412_D;
      NClientPath = new File(path, "N-Client");
      settings = new File(NClientPath, "Settings.json");
      commandPrefix = '.';
      hacks = new HashMap();
      HackList = new ArrayList();
      inPvP = false;
      isLeftPressed = false;
      listeners = new ArrayList();
      c = new ArrayList();
      t = 0L;
   }
}
