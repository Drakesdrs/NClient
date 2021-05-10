package com.ngames.nclient.hack;

public class InHUDValue {
   private InHUDValue.Type type;
   private Object value;

   public InHUDValue(Object value) {
      this.value = value;
      if (value instanceof Float[]) {
         this.type = InHUDValue.Type.VECTOR_FLOAT;
      } else if (value instanceof Integer[]) {
         this.type = InHUDValue.Type.VECTOR_INTEGER;
      } else if (value instanceof Float) {
         this.type = InHUDValue.Type.FLOAT;
      } else if (value instanceof Integer) {
         this.type = InHUDValue.Type.INTEGER;
      } else if (value instanceof String) {
         this.type = InHUDValue.Type.STRING;
      } else {
         this.type = InHUDValue.Type.NULL;
      }

   }

   public String toString() {
      if (this.type == InHUDValue.Type.VECTOR_FLOAT) {
         return "[" + ((Float[])((Float[])this.value))[0].toString() + ", " + ((Float[])((Float[])this.value))[1].toString() + "]";
      } else if (this.type == InHUDValue.Type.VECTOR_INTEGER) {
         return "[" + ((Integer[])((Integer[])this.value))[0].toString() + ", " + ((Integer[])((Integer[])this.value))[1].toString() + "]";
      } else if (this.type == InHUDValue.Type.FLOAT) {
         return ((Float)this.value).toString();
      } else if (this.type == InHUDValue.Type.INTEGER) {
         return ((Integer)this.value).toString();
      } else {
         return this.type == InHUDValue.Type.STRING ? (String)this.value : "";
      }
   }

   public void set(Object value) {
      this.value = value;
   }

   public Object get() {
      return this.value;
   }

   public static enum Type {
      VECTOR_FLOAT,
      VECTOR_INTEGER,
      FLOAT,
      INTEGER,
      STRING,
      NULL;
   }
}
