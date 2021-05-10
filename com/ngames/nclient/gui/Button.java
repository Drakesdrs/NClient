package com.ngames.nclient.gui;

import com.ngames.nclient.NClient;
import com.ngames.nclient.hack.settings.Setting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;

public class Button extends GuiButton {
   public static final int enabledColor = Theme.getRGB(-1, 45, 32);
   public static final int listenColor = Theme.getRGB(86, 64, -63);

   public Button(int buttonId, int x, int y, String buttonText) {
      super(buttonId, x, y, buttonText);
   }

   public Button(int buttonId, int x, int y, int widthIn, int heightIn, String buttonText) {
      super(buttonId, x, y, widthIn, heightIn, buttonText);
   }

   public void func_191745_a(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
      if (this.field_146125_m) {
         mc.func_110434_K().func_110577_a(field_146122_a);
         GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
         this.field_146123_n = mouseX >= this.field_146128_h && mouseY >= this.field_146129_i && mouseX < this.field_146128_h + this.field_146120_f && mouseY < this.field_146129_i + this.field_146121_g;
         int i = this.func_146114_a(this.field_146123_n);
         GlStateManager.func_179147_l();
         GlStateManager.func_187428_a(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA, SourceFactor.ONE, DestFactor.ZERO);
         GlStateManager.func_187401_a(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
         this.func_73729_b(this.field_146128_h, this.field_146129_i, 0, 46 + i * 20, this.field_146120_f / 2, this.field_146121_g);
         this.func_73729_b(this.field_146128_h + this.field_146120_f / 2, this.field_146129_i, 200 - this.field_146120_f / 2, 46 + i * 20, this.field_146120_f / 2, this.field_146121_g);
         this.func_146119_b(mc, mouseX, mouseY);
         int j = 14737632;
         if (this.packedFGColour != 0) {
            j = this.packedFGColour;
         } else if (!this.field_146124_l) {
            j = 10526880;
         } else if (this.field_146123_n) {
            j = 16777120;
            NClient.gui.mouseOverButton = this;
         }

         if (NClient.isHackExist(this.field_146126_j) && NClient.getHack(this.field_146126_j).isEnabled()) {
            j = enabledColor;
         }

         this.func_73732_a(NClient.MC.field_71466_p, this.field_146126_j, this.field_146128_h + this.field_146120_f / 2, this.field_146129_i + (this.field_146121_g - 8) / 2, j);
      }

   }

   public Setting getSetting() {
      if (this instanceof ButtonFloat) {
         return ((ButtonFloat)this).parent;
      } else if (this instanceof ButtonString) {
         return ((ButtonString)this).parent;
      } else if (this instanceof ButtonChoose) {
         return ((ButtonChoose)this).parent;
      } else {
         return this instanceof ButtonBoolean ? ((ButtonBoolean)this).parent : null;
      }
   }

   public static Setting getSetting(GuiButton button) {
      if (button instanceof ButtonFloat) {
         return ((ButtonFloat)button).parent;
      } else if (button instanceof ButtonString) {
         return ((ButtonString)button).parent;
      } else if (button instanceof ButtonChoose) {
         return ((ButtonChoose)button).parent;
      } else {
         return button instanceof ButtonBoolean ? ((ButtonBoolean)button).parent : null;
      }
   }
}
