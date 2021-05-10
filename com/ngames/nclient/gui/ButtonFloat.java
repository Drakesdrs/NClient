package com.ngames.nclient.gui;

import com.ngames.nclient.NClient;
import com.ngames.nclient.hack.settings.SettingValue;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;

public class ButtonFloat extends Button {
   SettingValue parent;
   public String ds;

   public ButtonFloat(int buttonId, int x, int y, String buttonText, SettingValue parent) {
      super(buttonId, x, y, 120, 20, buttonText);
      this.parent = parent;
      this.ds = this.parent.name + ": " + this.parent.getValue();
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
         }

         boolean isListining = NClient.gui.listen && NClient.gui.listining != null && NClient.gui.listining.id == this.field_146127_k;
         if (isListining) {
            j = listenColor;
         }

         if (!NClient.gui.cache.equals("null") && isListining) {
            this.ds = this.parent.name + ": " + NClient.gui.cache;
         }

         this.func_73732_a(NClient.MC.field_71466_p, this.ds, this.field_146128_h + this.field_146120_f / 2, this.field_146129_i + (this.field_146121_g - 8) / 2, j);
      }

   }

   public void func_146118_a(int mouseX, int mouseY) {
      if (NClient.gui.listining.id == this.field_146127_k) {
         NClient.gui.listen = false;
         NClient.gui.listining = null;
      } else {
         NClient.gui.listining = this.parent;
      }

   }
}
