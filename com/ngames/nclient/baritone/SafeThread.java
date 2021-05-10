package com.ngames.nclient.baritone;

import com.ngames.nclient.NClient;
import com.ngames.nclient.hack.Hack;

public class SafeThread {
   public Runnable runnable;
   public Runnable ifWorldNull;
   public Hack hack;

   public SafeThread(Runnable runnable, Hack hack) {
      this.runnable = runnable;
      this.hack = hack;
   }

   public SafeThread(Runnable runnable, Runnable ifWorldNull, Hack hack) {
      this.runnable = runnable;
      this.ifWorldNull = ifWorldNull;
      this.hack = hack;
   }

   public void start() {
      (new Thread(() -> {
         while(true) {
            try {
               if (this.hack.isEnabled()) {
                  if (NClient.MC.field_71441_e == null) {
                     if (this.ifWorldNull != null) {
                        this.ifWorldNull.run();
                        continue;
                     }

                     BUtils.sleep(50L);
                     continue;
                  }

                  this.runnable.run();
                  continue;
               }
            } catch (NullPointerException var2) {
               if (this.hack.isEnabled()) {
                  var2.printStackTrace();
                  BUtils.sleep(50L);
                  this.start();
               }
            }

            return;
         }
      })).start();
   }
}
