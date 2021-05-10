package com.ngames.nclient.hack.settings;

import com.ngames.nclient.gui.ButtonString;

public class SettingString extends Setting {
   public ButtonString element;
   private String value;
   public final int length;

   public SettingString(String name, String value, int length) {
      super(name, Settings.STRING_TYPE);
      this.value = value;
      this.length = length;
   }

   public void onUpdate() {
      this.element.ds = this.name + ": " + this.getValue();
   }

   public void setValue(String value) {
      if (value.length() <= this.length) {
         this.value = value;
      }

   }

   public String getValue() {
      return this.value;
   }
}
