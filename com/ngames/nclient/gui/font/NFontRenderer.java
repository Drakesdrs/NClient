package com.ngames.nclient.gui.font;

import com.ngames.nclient.NClient;
import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.util.StringUtils;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;

public class NFontRenderer {
   private static final Pattern COLOR_CODE_PATTERN = Pattern.compile("§[0123456789abcdefklmnor]");
   public final int FONT_HEIGHT = 9;
   private final int[] colorCodes = new int[]{0, 170, 43520, 43690, 11141120, 11141290, 16755200, 11184810, 5592405, 5592575, 5635925, 5636095, 16733525, 16733695, 16777045, 16777215};
   private final Map<String, Float> cachedStringWidth = new HashMap();
   private float antiAliasingFactor;
   private UnicodeFont unicodeFont;
   private int prevScaleFactor = (new ScaledResolution(Minecraft.func_71410_x())).func_78325_e();
   private Font font;

   public NFontRenderer(Font font, int fontSize) {
      ScaledResolution resolution = new ScaledResolution(Minecraft.func_71410_x());
      this.prevScaleFactor = resolution.func_78325_e();
      this.unicodeFont = new UnicodeFont(font.deriveFont((float)font.getSize() * (float)this.prevScaleFactor / 2.0F), fontSize, false, false);
      this.unicodeFont.addAsciiGlyphs();
      this.unicodeFont.getEffects().add(new ColorEffect(Color.WHITE));

      try {
         this.unicodeFont.loadGlyphs();
      } catch (SlickException var5) {
         var5.printStackTrace();
      }

      this.antiAliasingFactor = (float)resolution.func_78325_e();
   }

   public void drawStringScaled(String text, int givenX, int givenY, int color, double givenScale) {
      GL11.glPushMatrix();
      GL11.glTranslated((double)givenX, (double)givenY, 0.0D);
      GL11.glScaled(givenScale, givenScale, givenScale);
      this.drawString(text, 0.0F, 0.0F, color);
      GL11.glPopMatrix();
   }

   public int drawString(String text, float x, float y, int color) {
      if (text == null) {
         return 0;
      } else {
         ScaledResolution resolution = new ScaledResolution(Minecraft.func_71410_x());

         try {
            if (resolution.func_78325_e() != this.prevScaleFactor) {
               this.prevScaleFactor = resolution.func_78325_e();
               this.unicodeFont = new UnicodeFont(this.font.deriveFont((float)this.font.getSize() * (float)this.prevScaleFactor / 2.0F));
               this.unicodeFont.addAsciiGlyphs();
               this.unicodeFont.getEffects().add(new ColorEffect(Color.WHITE));
               this.unicodeFont.loadGlyphs();
            }
         } catch (SlickException var27) {
            var27.printStackTrace();
         }

         this.antiAliasingFactor = (float)resolution.func_78325_e();
         GL11.glPushMatrix();
         GlStateManager.func_179152_a(1.0F / this.antiAliasingFactor, 1.0F / this.antiAliasingFactor, 1.0F / this.antiAliasingFactor);
         x *= this.antiAliasingFactor;
         y *= this.antiAliasingFactor;
         float originalX = x;
         float red = (float)(color >> 16 & 255) / 255.0F;
         float green = (float)(color >> 8 & 255) / 255.0F;
         float blue = (float)(color & 255) / 255.0F;
         float alpha = (float)(color >> 24 & 255) / 255.0F;
         GlStateManager.func_179131_c(red, green, blue, alpha);
         int currentColor = color;
         char[] characters = text.toCharArray();
         GlStateManager.func_179140_f();
         GlStateManager.func_179147_l();
         GlStateManager.func_179120_a(770, 771, 1, 0);
         GlStateManager.func_179112_b(770, 771);
         String[] parts = COLOR_CODE_PATTERN.split(text);
         int index = 0;
         String[] var15 = parts;
         int var16 = parts.length;

         for(int var17 = 0; var17 < var16; ++var17) {
            String s = var15[var17];
            String[] var19 = s.split("\n");
            int var20 = var19.length;

            int codeIndex;
            for(codeIndex = 0; codeIndex < var20; ++codeIndex) {
               String s2 = var19[codeIndex];
               String[] var23 = s2.split("\r");
               int var24 = var23.length;

               for(int var25 = 0; var25 < var24; ++var25) {
                  String s3 = var23[var25];
                  this.unicodeFont.drawString(x, y, s3, new org.newdawn.slick.Color(currentColor));
                  x += (float)this.unicodeFont.getWidth(s3);
                  index += s3.length();
                  if (index < characters.length && characters[index] == '\r') {
                     x = originalX;
                     ++index;
                  }
               }

               if (index < characters.length && characters[index] == '\n') {
                  x = originalX;
                  y += this.getHeight(s2) * 2.0F;
                  ++index;
               }
            }

            if (index < characters.length) {
               char colorCode = characters[index];
               if (colorCode == 167) {
                  char colorChar = characters[index + 1];
                  codeIndex = "0123456789abcdef".indexOf(colorChar);
                  if (codeIndex < 0) {
                     if (colorChar == 'r') {
                        currentColor = color;
                     }
                  } else {
                     currentColor = this.colorCodes[codeIndex];
                  }

                  index += 2;
               }
            }
         }

         GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
         GlStateManager.func_179144_i(0);
         GlStateManager.func_179121_F();
         return (int)this.getWidth(text);
      }
   }

   public int drawStringWithShadow(String text, float x, float y, int color) {
      if (text != null && text != "") {
         this.drawString(StringUtils.func_76338_a(text), x + 0.5F, y + 0.5F, 0);
         return this.drawString(text, x, y, color);
      } else {
         return 0;
      }
   }

   public void drawCenteredString(String text, float x, float y, int color) {
      this.drawString(text, x - (float)((int)this.getWidth(text) >> 1), y - 2.0F, color);
   }

   public void drawCenteredTextScaled(String text, int givenX, int givenY, int color, double givenScale) {
      GL11.glPushMatrix();
      GL11.glTranslated((double)givenX, (double)givenY, 0.0D);
      GL11.glScaled(givenScale, givenScale, givenScale);
      this.drawCenteredString(text, 0.0F, 0.0F, color);
      GL11.glPopMatrix();
   }

   public void drawCenteredStringWithShadow(String text, float x, float y, int color) {
      this.drawCenteredString(StringUtils.func_76338_a(text), x + 0.5F, y + 0.5F, color);
      this.drawCenteredString(text, x, y, color);
   }

   public void drawHoveringText(String text, int x, int y) {
      if (!text.isEmpty()) {
         GlStateManager.func_179101_C();
         RenderHelper.func_74518_a();
         GlStateManager.func_179140_f();
         GlStateManager.func_179097_i();
         int i = (int)this.getWidth(text);
         int l1 = x + 12;
         int i2 = y - 12;
         int k = 8;
         if (l1 + i > NClient.MC.field_71443_c) {
            l1 -= 28 + i;
         }

         if (i2 + k + 6 > NClient.MC.field_71440_d) {
            i2 = NClient.MC.field_71440_d - k - 6;
         }

         NClient.gui._drawGradientRect(l1 - 3, i2 - 4, l1 + i + 3, i2 - 3, -267386864, -267386864);
         NClient.gui._drawGradientRect(l1 - 3, i2 + k + 3, l1 + i + 3, i2 + k + 4, -267386864, -267386864);
         NClient.gui._drawGradientRect(l1 - 3, i2 - 3, l1 + i + 3, i2 + k + 3, -267386864, -267386864);
         NClient.gui._drawGradientRect(l1 - 4, i2 - 3, l1 - 3, i2 + k + 3, -267386864, -267386864);
         NClient.gui._drawGradientRect(l1 + i + 3, i2 - 3, l1 + i + 4, i2 + k + 3, -267386864, -267386864);
         NClient.gui._drawGradientRect(l1 - 3, i2 - 3 + 1, l1 - 3 + 1, i2 + k + 3 - 1, 1347420415, 1344798847);
         NClient.gui._drawGradientRect(l1 + i + 2, i2 - 3 + 1, l1 + i + 3, i2 + k + 3 - 1, 1347420415, 1344798847);
         NClient.gui._drawGradientRect(l1 - 3, i2 - 3, l1 + i + 3, i2 - 3 + 1, 1347420415, 1347420415);
         NClient.gui._drawGradientRect(l1 - 3, i2 + k + 2, l1 + i + 3, i2 + k + 3, 1344798847, 1344798847);
         this.drawStringWithShadow(text, (float)l1, (float)i2, -1);
         GlStateManager.func_179145_e();
         GlStateManager.func_179126_j();
         RenderHelper.func_74519_b();
         GlStateManager.func_179091_B();
      }

   }

   public float getWidth(String text) {
      if (this.cachedStringWidth.size() > 1000) {
         this.cachedStringWidth.clear();
      }

      return (Float)this.cachedStringWidth.computeIfAbsent(text, (e) -> {
         return (float)this.unicodeFont.getWidth(text.replaceAll(COLOR_CODE_PATTERN.toString(), "")) / this.antiAliasingFactor;
      });
   }

   public float getCharWidth(char c) {
      return (float)this.unicodeFont.getWidth(String.valueOf(c));
   }

   public float getHeight(String s) {
      return (float)this.unicodeFont.getHeight(s) / 2.0F;
   }

   public UnicodeFont getFont() {
      return this.unicodeFont;
   }

   public void drawSplitString(ArrayList<String> lines, int x, int y, int color) {
      this.drawString(String.join("\n\r", lines), (float)x, (float)y, color);
   }

   public List<String> splitString(String text, int wrapWidth) {
      List<String> lines = new ArrayList();
      String[] splitText = text.split(" ");
      StringBuilder currentString = new StringBuilder();
      String[] var6 = splitText;
      int var7 = splitText.length;

      for(int var8 = 0; var8 < var7; ++var8) {
         String word = var6[var8];
         String potential = currentString + " " + word;
         if (this.getWidth(potential) >= (float)wrapWidth) {
            lines.add(currentString.toString());
            currentString = new StringBuilder();
         }

         currentString.append(word).append(" ");
      }

      lines.add(currentString.toString());
      return lines;
   }

   public int getStringWidth(String p_Name) {
      return this.unicodeFont.getWidth(p_Name.replaceAll(COLOR_CODE_PATTERN.toString(), "")) / 2;
   }

   public float getStringHeight(String p_Name) {
      return this.getHeight(p_Name);
   }

   public String trimStringToWidth(String text, int width) {
      return this.trimStringToWidth(text, width, false);
   }

   public String trimStringToWidth(String text, int width, boolean reverse) {
      StringBuilder stringbuilder = new StringBuilder();
      int i = 0;
      int j = reverse ? text.length() - 1 : 0;
      int k = reverse ? -1 : 1;
      boolean flag = false;
      boolean flag1 = false;

      for(int l = j; l >= 0 && l < text.length() && i < width; l += k) {
         char c0 = text.charAt(l);
         float i1 = this.getWidth(text);
         if (flag) {
            flag = false;
            if (c0 != 'l' && c0 != 'L') {
               if (c0 == 'r' || c0 == 'R') {
                  flag1 = false;
               }
            } else {
               flag1 = true;
            }
         } else if (i1 < 0.0F) {
            flag = true;
         } else {
            i = (int)((float)i + i1);
            if (flag1) {
               ++i;
            }
         }

         if (i > width) {
            break;
         }

         if (reverse) {
            stringbuilder.insert(0, c0);
         } else {
            stringbuilder.append(c0);
         }
      }

      return stringbuilder.toString();
   }
}
