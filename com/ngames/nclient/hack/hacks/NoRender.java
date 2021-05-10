package com.ngames.nclient.hack.hacks;

import com.ngames.nclient.NClient;
import com.ngames.nclient.hack.Category;
import com.ngames.nclient.hack.Hack;
import com.ngames.nclient.hack.NClientHack;
import com.ngames.nclient.hack.settings.SettingBoolean;
import java.util.Iterator;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityItem;

@NClientHack(
   category = Category.RENDER,
   description = "Disable rending something that you will choose",
   name = "NoRender",
   words = "NoRender overlay antioverlay hide"
)
public class NoRender extends Hack {
   public final SettingBoolean item = new SettingBoolean("Item", true);
   public final SettingBoolean entity = new SettingBoolean("Entity", true);
   public final SettingBoolean other = new SettingBoolean("Other", true);

   public NoRender() {
      this.settings = Hack.addSettings(this);
   }

   public void onEnable() {
      super.onEnable();
      (new Thread(() -> {
         Iterator var1 = NClient.MC.field_71441_e.func_72910_y().iterator();

         while(var1.hasNext()) {
            Entity e = (Entity)var1.next();
            if (this.item.getValue() && e instanceof EntityItem) {
               e.func_174812_G();
            }

            if (this.entity.getValue() && e instanceof EntityLiving && !(e instanceof EntityOtherPlayerMP)) {
               e.func_174812_G();
            }

            if (this.other.getValue() && !(e instanceof EntityOtherPlayerMP) && !(e instanceof EntityLiving) && !(e instanceof EntityItem)) {
               e.func_174812_G();
            }
         }

      })).start();
   }
}
