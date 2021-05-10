package com.ngames.nclient.hack.hacks;

import com.ngames.nclient.NClient;
import com.ngames.nclient.gui.Gui;
import com.ngames.nclient.hack.Category;
import com.ngames.nclient.hack.Hack;
import com.ngames.nclient.hack.NClientHack;
import net.minecraft.client.gui.GuiScreen;

@NClientHack(
   category = Category.RENDER,
   description = "Open the clickGUI",
   name = "ClickGUI",
   words = "ClickGUI Menu Hud HudEditor",
   keyBind = 54
)
public class ClickGUI extends Hack {
   public ClickGUI() {
      this.settings = Hack.addSettings(this);
   }

   public void onEnable() {
      super.onEnable();
      NClient.gui = new Gui(NClient.MC.field_71462_r);
      NClient.MC.func_147108_a(NClient.gui);
   }

   public void onDisable() {
      NClient.MC.func_147108_a((GuiScreen)null);
      if (NClient.MC.field_71462_r == null) {
         NClient.MC.func_71381_h();
      }

      Gui.justPressed = true;
      super.onDisable();
   }
}
