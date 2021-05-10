package com.ngames.nclient.hack.hacks;

import com.ngames.nclient.hack.Category;
import com.ngames.nclient.hack.Hack;
import com.ngames.nclient.hack.InHUDValue;
import com.ngames.nclient.hack.NClientHack;
import com.ngames.nclient.hack.settings.SettingBoolean;

@NClientHack(
   category = Category.EXPLOIT,
   description = "Cancel client packets",
   name = "PacketCanceller",
   words = "PacketCanceller"
)
public class PacketCanceller extends Hack {
   public final SettingBoolean CPacketInput = new SettingBoolean("CPacketInput", true);
   public final SettingBoolean CPacketPosition = new SettingBoolean("CPacketPosition", true);
   public final SettingBoolean CPacketPositionRotation = new SettingBoolean("CPacketPositionRotation", true);
   public final SettingBoolean CPacketRotation = new SettingBoolean("CPacketRotation", true);
   public final SettingBoolean CPacketPlayerAbilities = new SettingBoolean("CPacketPlayerAbilities", true);
   public final SettingBoolean CPacketPlayerDigging = new SettingBoolean("CPacketPlayerDigging", true);
   public final SettingBoolean CPacketPlayerTryUseItem = new SettingBoolean("CPacketPlayerTryUseItem", true);
   public final SettingBoolean CPacketPlayerTryUseItemOnBlock = new SettingBoolean("CPacketPlayerTryUseItemOnBlock", true);
   public final SettingBoolean CPacketEntityAction = new SettingBoolean("CPacketEntityAction", true);
   public final SettingBoolean CPacketUseEntity = new SettingBoolean("CPacketUseEntity", true);
   public final SettingBoolean CPacketVehicleMove = new SettingBoolean("CPacketVehicleMove", true);

   public PacketCanceller() {
      this.settings = Hack.addSettings(this);
      this.inHud = new InHUDValue(0);
   }

   public void onDisable() {
      this.inHud.set(0);
      super.onDisable();
   }
}
