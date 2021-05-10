package com.ngames.nclient.hack.settings;

import com.ngames.nclient.gui.ButtonFloat;

public class SettingValue extends Setting {
   public ButtonFloat element;
   private float value;
   public final float min;
   public float max;

   public SettingValue(String name, float value, float min, float max) {
      super(name, Settings.VALUE_TYPE);
      this.value = value;
      this.min = min;
      this.max = max;
   }

   public void onUpdate() {
      this.element.ds = this.name + ": " + this.getValue();
   }

   public void setValue(float value) {
      if (value < this.min) {
         this.value = this.min;
      } else if (value > this.max) {
         this.value = this.max;
      } else {
         this.value = value;
      }

   }

   public float getValue() {
      return this.value;
   }
}
