package com.ngames.nclient.event;

import com.ngames.nclient.NClient;
import com.ngames.nclient.hack.Hack;

public class Listener {
   public Hack hack;
   private Class<? extends NClientEvent> eventType;

   public Listener(Class<? extends NClientEvent> eventType, Hack hack) {
      this.hack = hack;
      this.eventType = eventType;
      NClient.listeners.add(this);
   }

   public void invoke(NClientEvent event) {
      this.hack.onInvoke(event);
   }

   public Class<? extends NClientEvent> getEventType() {
      return this.eventType;
   }
}
