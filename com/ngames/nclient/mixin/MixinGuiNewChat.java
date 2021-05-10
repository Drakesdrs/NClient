package com.ngames.nclient.mixin;

import com.ngames.nclient.NClient;
import com.ngames.nclient.hack.Hacks;
import java.util.List;
import net.minecraft.client.gui.GuiNewChat;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({GuiNewChat.class})
public class MixinGuiNewChat {
   @Inject(
      method = {"addToSentMessages"},
      at = {@At("TAIL")}
   )
   private void addToSentMessages(String message, CallbackInfo info) {
      if (Hacks.chatPrefix.isEnabled() && message.charAt(0) != '/' && message.charAt(0) != '#' && message.charAt(0) != NClient.commandPrefix) {
         message = message.substring(Hacks.chatPrefix.prefix.getValue().length());
      }

      if (Hacks.chatPostfix.isEnabled() && message.charAt(0) != '/' && message.charAt(0) != '#' && message.charAt(0) != NClient.commandPrefix) {
         message = message.substring(0, message.length() - Hacks.chatPostfix.postfix.getValue().length());
      }

      List<String> sent = NClient.MC.field_71456_v.func_146158_b().func_146238_c();
      sent.set(sent.size() - 1, message);
   }
}
