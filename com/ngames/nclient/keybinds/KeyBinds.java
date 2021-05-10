package com.ngames.nclient.keybinds;

import com.ngames.nclient.NClient;
import com.ngames.nclient.hack.Hack;
import com.ngames.nclient.hack.NClientHack;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;

public class KeyBinds {
   private static final String catergory = "N-Client";
   public static List<KeyBinding> keyBinding = new ArrayList();

   public static void register() {
      Iterator var0 = NClient.HackList.iterator();

      while(var0.hasNext()) {
         Hack h = (Hack)var0.next();
         keyBinding.add(new KeyBinding(((NClientHack)h.getClass().getAnnotation(NClientHack.class)).name(), ((NClientHack)h.getClass().getAnnotation(NClientHack.class)).keyBind(), "N-Client"));
      }

      var0 = keyBinding.iterator();

      while(var0.hasNext()) {
         KeyBinding kb = (KeyBinding)var0.next();
         setRegister(kb);
      }

   }

   private static void setRegister(KeyBinding binding) {
      ClientRegistry.registerKeyBinding(binding);
   }

   public static void getBind(int keyCode) {
      Iterator var1 = keyBinding.iterator();

      while(var1.hasNext()) {
         KeyBinding kb = (KeyBinding)var1.next();
         if (kb.func_151463_i() == keyCode) {
            NClient.getHack(kb.func_151464_g()).onToggle();
         }
      }

   }

   public static int getHackKeyBind(String name) {
      int ret = 0;
      Iterator var2 = keyBinding.iterator();

      while(var2.hasNext()) {
         KeyBinding kb = (KeyBinding)var2.next();
         if (kb.func_151464_g().equals(name)) {
            ret = kb.func_151463_i();
         }
      }

      return ret;
   }
}
