package com.ngames.nclient.hack.settings;

import com.ngames.nclient.gui.ButtonChoose;
import java.util.ArrayList;
import java.util.List;

public class SettingChoose extends Setting {
   public ButtonChoose element;
   private byte value;
   public final int max;
   public List<String> mapping = new ArrayList();

   public SettingChoose(String name, byte value, String... names) {
      super(name, Settings.CHOOSE);
      this.value = value;
      this.max = names.length - 1;
      String[] var4 = names;
      int var5 = names.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         String vname = var4[var6];
         this.mapping.add(vname);
      }

   }

   public void onUpdate() {
      this.element.ds = this.name + ": " + (String)this.mapping.get(this.getValue());
   }

   public void setValue() {
      byte value = ++this.value;
      if (value <= this.max && value >= 0) {
         this.value = value;
      }

      if (value > this.max && value >= 0) {
         this.value = (byte)(value % (this.max + 1));
      }

   }

   public void setValue(byte value) {
      if (value <= this.max && value >= 0) {
         this.value = value;
      }

      if (value > this.max && value >= 0) {
         this.value = (byte)(value % (this.max + 1));
      }

   }

   public byte getValue() {
      return this.value;
   }
}
