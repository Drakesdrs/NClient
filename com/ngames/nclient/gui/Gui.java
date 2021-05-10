package com.ngames.nclient.gui;

import com.ngames.nclient.NClient;
import com.ngames.nclient.hack.Category;
import com.ngames.nclient.hack.Hack;
import com.ngames.nclient.hack.HackUtils;
import com.ngames.nclient.hack.Hacks;
import com.ngames.nclient.hack.settings.Setting;
import com.ngames.nclient.hack.settings.SettingBoolean;
import com.ngames.nclient.hack.settings.SettingChoose;
import com.ngames.nclient.hack.settings.SettingString;
import com.ngames.nclient.hack.settings.SettingValue;
import com.ngames.nclient.hack.settings.Settings;
import com.ngames.nclient.keybinds.KeyBinds;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.BiomeHell;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public class Gui extends GuiScreen {
   public static int mouseX;
   public static int mouseY;
   public static boolean justPressed;
   public Button selButton;
   public Button mouseOverButton;
   public Setting listining;
   public boolean listen;
   public String cache = "";
   public final GuiScreen lastScreen;
   public static Category currentCategory;
   Framebuffer framebuffer;
   List<Button> field_146292_n = new ArrayList();
   int ButtonID = 8;
   List<List<Object>> buttonCoords = new ArrayList();
   boolean sync = false;
   boolean wait = true;

   public Gui(GuiScreen lastScreen) {
      this.lastScreen = lastScreen;
      this.framebuffer = new Framebuffer(NClient.MC.field_71443_c, NClient.MC.field_71440_d, false);
      this.addButtons();
      GL11.glEnable(3553);
   }

   public void func_73863_a(int mouseX, int mouseY, float partialTicks) {
      if (Hacks.clickGUI.isEnabled()) {
         this.calculateMouse();
         GlStateManager.func_179124_c(1.0F, 1.0F, 1.0F);
         this.func_193975_a(true);
         this.func_73733_a(0, 0, this.field_146294_l, this.field_146295_m, -1072689136, -804253680);
         this.drawInfo();
         Iterator var4 = this.field_146292_n.iterator();

         while(var4.hasNext()) {
            Button gb = (Button)var4.next();
            gb.func_191745_a(NClient.MC, mouseX, mouseY, NClient.MC.func_184121_ak());
         }

         this.drawStrings();
         if (this.mouseOverButton != null && NClient.isHackExist(this.mouseOverButton.field_146126_j)) {
            this.func_146279_a(HackUtils.getDescrption(NClient.getHack(this.mouseOverButton.field_146126_j)), mouseX, mouseY);
         }

         this.mouseOverButton = null;
      }

   }

   protected void func_73869_a(char typedChar, int keyCode) {
      if (keyCode == 1 || keyCode == KeyBinds.getHackKeyBind("ClickGUI")) {
         this.field_146297_k.func_147108_a((GuiScreen)null);
         if (this.field_146297_k.field_71462_r == null) {
            this.field_146297_k.func_71381_h();
         }

         Hacks.clickGUI.onToggle();
         justPressed = true;
      }

      if (this.listen) {
         if (this.listining.type == Settings.VALUE_TYPE && (Character.isDigit(typedChar) || typedChar == '-' || typedChar == '.')) {
            if (this.cache.equals("")) {
               this.cache = "";
            }

            this.cache = this.cache + typedChar;
         }

         if (this.listining.type == Settings.VALUE_TYPE && keyCode == 14) {
            this.cache = StringUtils.chop(this.cache);
         }

         if (this.listining.type == Settings.STRING_TYPE && !Character.isISOControl(typedChar)) {
            if (this.cache.equals("")) {
               this.cache = "";
            }

            this.cache = this.cache + typedChar;
         }

         if (this.listining.type == Settings.STRING_TYPE && keyCode == 14) {
            this.cache = StringUtils.chop(this.cache);
         }
      }

      if (keyCode == 28) {
         this.listen = false;
         if (NClient.isHackExist(this.selButton.field_146126_j)) {
            if (this.listining.type == Settings.VALUE_TYPE && !this.cache.equals("")) {
               SettingValue sv = (SettingValue)this.listining;
               if (NumberUtils.isCreatable(this.cache)) {
                  sv.setValue(Float.valueOf(this.cache));
               }

               sv.onUpdate();
            }

            if (this.listining.type == Settings.STRING_TYPE && !this.cache.equals("")) {
               SettingString ss = (SettingString)this.listining;
               ss.setValue(this.cache);
               ss.onUpdate();
            }

            NClient.getHack(this.selButton.field_146126_j).onUpdate();
            this.cache = "";
         }
      }

   }

   public boolean func_73868_f() {
      return false;
   }

   public static int getScale() {
      int scale = NClient.MC.field_71474_y.field_74335_Z;
      if (scale == 0) {
         scale = 1000;
      }

      int scaleFactor;
      for(scaleFactor = 0; scaleFactor < scale && NClient.MC.field_71443_c / (scaleFactor + 1) >= 320 && NClient.MC.field_71440_d / (scaleFactor + 1) >= 240; ++scaleFactor) {
      }

      if (scaleFactor == 0) {
         scaleFactor = 1;
      }

      return scaleFactor;
   }

   protected void func_73864_a(int x, int y, int mouseButton) {
      this.calculateMouse();
      x = mouseX;
      y = mouseY;
      Iterator var4 = this.buttonCoords.iterator();

      while(true) {
         while(true) {
            Button b;
            Integer y1;
            Integer y2;
            do {
               Integer x2;
               do {
                  Integer x1;
                  do {
                     do {
                        if (!var4.hasNext()) {
                           this.wait = false;
                           return;
                        }

                        List<Object> obj = (List)var4.next();
                        b = (Button)obj.get(0);
                        x1 = (Integer)obj.get(1);
                        y1 = (Integer)obj.get(2);
                        x2 = (Integer)obj.get(3);
                        y2 = (Integer)obj.get(4);
                     } while(x <= x1);
                  } while(x >= x2);
               } while(y <= y1);
            } while(y >= y2);

            if (b.field_146127_k < 9 && b.field_146127_k > 0) {
               if (currentCategory == Category.getCategory(b.field_146127_k)) {
                  currentCategory = Category.ALL;
                  this.reloadHacks();
               } else {
                  currentCategory = Category.getCategory(b.field_146127_k);
                  this.reloadHacks();
               }
            } else if (b.field_146127_k < 8096) {
               Hack h = NClient.getHack(b.field_146126_j);
               if (mouseButton == 0) {
                  h.onToggle();
               }

               if (mouseButton == 1) {
                  (new Thread(() -> {
                     while(this.wait) {
                     }

                     if (this.selButton != null && this.selButton.equals(b)) {
                        this.clearSettings(h);
                        this.selButton = null;
                     } else {
                        if (this.selButton != null) {
                           this.clearSettings(NClient.getHack(this.selButton.field_146126_j));
                        }

                        this.selButton = b;
                        this.addSettings(h);
                     }

                  })).start();
               }
            } else if (b.field_146127_k >= 8096) {
               if (b instanceof ButtonFloat) {
                  ButtonFloat bf = (ButtonFloat)b;
                  this.listining = bf.parent;
                  this.listen = true;
                  this.listining.onUpdate();
               }

               if (b instanceof ButtonString) {
                  ButtonString bs = (ButtonString)b;
                  this.listining = bs.parent;
                  this.listen = true;
                  this.listining.onUpdate();
               }

               if (b instanceof ButtonChoose) {
                  ButtonChoose bc = (ButtonChoose)b;
                  bc.func_146118_a(mouseX, mouseY);
                  NClient.getHack(this.selButton.field_146126_j).onUpdate();
               }

               if (b instanceof ButtonBoolean) {
                  ButtonBoolean bb = (ButtonBoolean)b;
                  bb.func_146118_a(mouseX, mouseY);
                  NClient.getHack(this.selButton.field_146126_j).onUpdate();
               }
            }
         }
      }
   }

   private void calculateMouse() {
      int scaleFactor = getScale();
      mouseX = Mouse.getX() / scaleFactor;
      mouseY = NClient.MC.field_71440_d / scaleFactor - Mouse.getY() / scaleFactor - 1;
   }

   private void calculateButtons() {
      List<List<Object>> buttonCoords = new ArrayList();
      Iterator var2 = this.field_146292_n.iterator();

      while(var2.hasNext()) {
         Button b = (Button)var2.next();
         Integer _x1 = b.field_146128_h;
         Integer _y1 = b.field_146129_i;
         Integer _x2 = b.field_146128_h + b.field_146120_f;
         Integer _y2 = b.field_146129_i + b.field_146121_g;
         List<Object> data = new ArrayList();
         data.add(b);
         data.add(_x1);
         data.add(_y1);
         data.add(_x2);
         data.add(_y2);
         buttonCoords.add(data);
      }

      this.buttonCoords = buttonCoords;
   }

   private void addButtons() {
      int y = 30;
      this.field_146292_n.add(this.func_189646_b(new Button(1, 10, y, 80, 20, "Misc")));
      int y = y + 25;
      this.field_146292_n.add(this.func_189646_b(new Button(2, 10, y, 80, 20, "Chat")));
      y += 25;
      this.field_146292_n.add(this.func_189646_b(new Button(3, 10, y, 80, 20, "World")));
      y += 25;
      this.field_146292_n.add(this.func_189646_b(new Button(4, 10, y, 80, 20, "Player")));
      y += 25;
      this.field_146292_n.add(this.func_189646_b(new Button(5, 10, y, 80, 20, "Render")));
      y += 25;
      this.field_146292_n.add(this.func_189646_b(new Button(6, 10, y, 80, 20, "Combat")));
      y += 25;
      this.field_146292_n.add(this.func_189646_b(new Button(7, 10, y, 80, 20, "Exploit")));
      y += 25;
      this.field_146292_n.add(this.func_189646_b(new Button(8, 10, y, 80, 20, "Movement")));
      this.addHacks();
      this.calculateButtons();
   }

   private void addHacks() {
      int j = 0;
      int x2 = 95;
      int y2 = 30;
      List<Hack> hackList = NClient.HackList;

      for(int i = 0; i < hackList.size(); ++i) {
         if (HackUtils.getCategory((Hack)hackList.get(i)) == currentCategory || currentCategory == Category.ALL) {
            String name = HackUtils.getName((Hack)hackList.get(i));
            if (j == 0) {
               this.field_146292_n.add(this.func_189646_b(new Button(++this.ButtonID, x2, y2, 80, 20, name)));
            }

            if (j == 1) {
               this.field_146292_n.add(this.func_189646_b(new Button(++this.ButtonID, x2 + 85, y2, 80, 20, name)));
            }

            if (j == 2) {
               this.field_146292_n.add(this.func_189646_b(new Button(++this.ButtonID, x2 + 170, y2, 80, 20, name)));
               y2 += 25;
               j = -1;
            }

            ++j;
         }
      }

   }

   private void clearHacks() {
      for(int i = 0; i < this.field_146292_n.size(); ++i) {
         if (NClient.isHackExist(((Button)this.field_146292_n.get(i)).field_146126_j)) {
            this.field_146292_n.remove(i);
         }
      }

   }

   private void reloadHacks() {
      this.clearHacks();
      this.addHacks();
      this.calculateButtons();
   }

   private void drawStrings() {
      NClient.MC.field_71466_p.func_78276_b("N-Client B1", 10, 10, -1);
   }

   private void addSettings(Hack hack) {
      int settingID = 8096;
      int x = 355;
      int y = 30;
      int i = 0;
      Iterator var6 = hack.settings.iterator();

      while(var6.hasNext()) {
         Setting s = (Setting)var6.next();
         if (s.getType() == Settings.VALUE_TYPE) {
            SettingValue sv = (SettingValue)s;
            Button b = new ButtonFloat(settingID, x, y, s.name, sv);
            sv.element = (ButtonFloat)b;
            this.field_146292_n.add(this.func_189646_b(b));
         }

         if (s.getType() == Settings.STRING_TYPE) {
            SettingString ss = (SettingString)s;
            Button b = new ButtonString(settingID, x, y, s.name, ss);
            ss.element = (ButtonString)b;
            this.field_146292_n.add(this.func_189646_b(b));
         }

         if (s.getType() == Settings.CHOOSE) {
            SettingChoose sc = (SettingChoose)s;
            Button b = new ButtonChoose(settingID, x, y, s.name, sc);
            sc.element = (ButtonChoose)b;
            this.field_146292_n.add(this.func_189646_b(b));
         }

         if (s.getType() == Settings.BOOLEAN) {
            SettingBoolean sb = (SettingBoolean)s;
            Button b = new ButtonBoolean(settingID, x, y, s.name, sb);
            sb.element = (ButtonBoolean)b;
            this.field_146292_n.add(this.func_189646_b(b));
         }

         s.id = settingID++;
         y += 25;
         ++i;
         if (i > 18) {
            x += 125;
            y = 30;
         }
      }

      this.calculateButtons();
   }

   private void clearSettings(Hack hack) {
      int i;
      Iterator var3;
      Setting s;
      for(i = 0; i < this.field_146292_n.size(); ++i) {
         var3 = hack.settings.iterator();

         while(var3.hasNext()) {
            s = (Setting)var3.next();
            if (((Button)this.field_146292_n.get(i)).getSetting() != null && s.id == ((Button)this.field_146292_n.get(i)).getSetting().id && ((Button)this.field_146292_n.get(i)).field_146127_k >= 8096) {
               this.field_146292_n.remove(i);
            }
         }
      }

      for(i = 0; i < this.field_146292_n.size(); ++i) {
         var3 = hack.settings.iterator();

         while(var3.hasNext()) {
            s = (Setting)var3.next();
            if (Button.getSetting((GuiButton)this.field_146292_n.get(i)) != null && s.id == Button.getSetting((GuiButton)this.field_146292_n.get(i)).id && ((Button)this.field_146292_n.get(i)).field_146127_k >= 8096) {
               this.field_146292_n.remove(i);
            }
         }
      }

      this.calculateButtons();
   }

   public static void drawBackground(ResourceLocation texture) {
      GlStateManager.func_179140_f();
      GlStateManager.func_179106_n();
      Tessellator tessellator = Tessellator.func_178181_a();
      BufferBuilder bufferbuilder = tessellator.func_178180_c();
      NClient.MC.func_110434_K().func_110577_a(texture);
      GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
      bufferbuilder.func_181668_a(7, DefaultVertexFormats.field_181709_i);
      bufferbuilder.func_181662_b(0.0D, (double)NClient.MC.field_71462_r.field_146295_m, 0.0D).func_187315_a(0.0D, 1.0D).func_181669_b(64, 64, 64, 255).func_181675_d();
      bufferbuilder.func_181662_b((double)NClient.MC.field_71462_r.field_146294_l, (double)NClient.MC.field_71462_r.field_146295_m, 0.0D).func_187315_a(1.0D, 1.0D).func_181669_b(64, 64, 64, 255).func_181675_d();
      bufferbuilder.func_181662_b((double)NClient.MC.field_71462_r.field_146294_l, 0.0D, 0.0D).func_187315_a(1.0D, 0.0D).func_181669_b(64, 64, 64, 255).func_181675_d();
      bufferbuilder.func_181662_b(0.0D, 0.0D, 0.0D).func_187315_a(0.0D, 0.0D).func_181669_b(64, 64, 64, 255).func_181675_d();
      tessellator.func_78381_a();
   }

   public static void drawBackground() {
      drawBackground(NClient.background);
   }

   private void drawInfo() {
      String coords = "XYZ: [" + NClient.MC.field_71439_g.field_70165_t + ", " + NClient.MC.field_71439_g.field_70163_u + ", " + NClient.MC.field_71439_g.field_70161_v + "]";
      String nether = !(NClient.MC.field_71441_e.func_180494_b(NClient.MC.field_71439_g.func_180425_c()) instanceof BiomeHell) ? "Nether: [" + NClient.MC.field_71439_g.field_70165_t / 8.0D + ", " + NClient.MC.field_71439_g.field_70163_u + ", " + NClient.MC.field_71439_g.field_70161_v / 8.0D + "]" : "Overworld: [" + NClient.MC.field_71439_g.field_70165_t * 8.0D + ", " + NClient.MC.field_71439_g.field_70163_u + ", " + NClient.MC.field_71439_g.field_70161_v * 8.0D + "]";
      String fps = "FPS: " + Minecraft.func_175610_ah();
      String tps = "TPS: " + NClient.tps;
      String ping = "PING: " + String.valueOf(NClient.MC.func_147104_D() == null ? 0L : NClient.MC.func_147104_D().field_78844_e);
      String biggest = coords.length() > nether.length() ? coords : nether;
      int y = 30;
      this.func_73733_a(485, y, 490 + NClient.MC.field_71466_p.func_78256_a(biggest) + 5, y + 5 + 75, -1072689136, -804253680);
      int y = y + 5;
      NClient.MC.field_71466_p.func_78276_b(coords, 490, y, -1);
      y += 15;
      NClient.MC.field_71466_p.func_78276_b(nether, 490, y, -1);
      y += 15;
      NClient.MC.field_71466_p.func_78276_b(fps, 490, y, -1);
      y += 15;
      NClient.MC.field_71466_p.func_78276_b(tps, 490, y, -1);
      y += 15;
      NClient.MC.field_71466_p.func_78276_b(ping, 490, y, -1);
   }

   public void _drawGradientRect(int left, int top, int right, int bottom, int startColor, int endColor) {
      this.func_73733_a(left, top, right, bottom, startColor, endColor);
   }

   static {
      currentCategory = Category.ALL;
   }
}
