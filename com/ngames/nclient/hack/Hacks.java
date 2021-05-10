package com.ngames.nclient.hack;

import com.ngames.nclient.NClient;
import com.ngames.nclient.hack.hacks.AdvClickerDelays;
import com.ngames.nclient.hack.hacks.AimAssist;
import com.ngames.nclient.hack.hacks.AutoArmor;
import com.ngames.nclient.hack.hacks.AutoClicker;
import com.ngames.nclient.hack.hacks.AutoDupe;
import com.ngames.nclient.hack.hacks.AutoEat;
import com.ngames.nclient.hack.hacks.AutoEz;
import com.ngames.nclient.hack.hacks.AutoGapple;
import com.ngames.nclient.hack.hacks.AutoJump;
import com.ngames.nclient.hack.hacks.AutoRelog;
import com.ngames.nclient.hack.hacks.AutoSoup;
import com.ngames.nclient.hack.hacks.AutoSpin;
import com.ngames.nclient.hack.hacks.AutoSprint;
import com.ngames.nclient.hack.hacks.AutoTotem;
import com.ngames.nclient.hack.hacks.CPSCount;
import com.ngames.nclient.hack.hacks.ChatPostfix;
import com.ngames.nclient.hack.hacks.ChatPrefix;
import com.ngames.nclient.hack.hacks.ClickGUI;
import com.ngames.nclient.hack.hacks.Criticals;
import com.ngames.nclient.hack.hacks.EntityControl;
import com.ngames.nclient.hack.hacks.HUD;
import com.ngames.nclient.hack.hacks.HighJump;
import com.ngames.nclient.hack.hacks.InvActionLogger;
import com.ngames.nclient.hack.hacks.ItemSpammer;
import com.ngames.nclient.hack.hacks.KillAura18;
import com.ngames.nclient.hack.hacks.LongJump;
import com.ngames.nclient.hack.hacks.LookingForward;
import com.ngames.nclient.hack.hacks.NoRender;
import com.ngames.nclient.hack.hacks.NoRotate;
import com.ngames.nclient.hack.hacks.PacketCanceller;
import com.ngames.nclient.hack.hacks.Reach;
import com.ngames.nclient.hack.hacks.Sneak;
import com.ngames.nclient.hack.hacks.Spammer;
import com.ngames.nclient.hack.hacks.Speed;
import com.ngames.nclient.hack.hacks.Strafe;
import com.ngames.nclient.hack.hacks.Timer;
import com.ngames.nclient.hack.hacks.Velocity;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class Hacks {
   public static final AdvClickerDelays advClickerDelays = new AdvClickerDelays();
   public static final AimAssist aimAssist = new AimAssist();
   public static final AutoArmor autoArmor = new AutoArmor();
   public static final AutoClicker autoClicker = new AutoClicker();
   public static final AutoDupe autoDupe = new AutoDupe();
   public static final AutoEat autoEat = new AutoEat();
   public static final AutoEz autoEz = new AutoEz();
   public static final AutoGapple autoGapple = new AutoGapple();
   public static final AutoJump autoJump = new AutoJump();
   public static final AutoRelog autoRelog = new AutoRelog();
   public static final AutoSoup autoSoup = new AutoSoup();
   public static final AutoSpin autoSpin = new AutoSpin();
   public static final AutoSprint autoSprint = new AutoSprint();
   public static final AutoTotem autoTotem = new AutoTotem();
   public static final ChatPostfix chatPostfix = new ChatPostfix();
   public static final ChatPrefix chatPrefix = new ChatPrefix();
   public static final ClickGUI clickGUI = new ClickGUI();
   public static final CPSCount cpsCount = new CPSCount();
   public static final Criticals criticals = new Criticals();
   public static final EntityControl entityControl = new EntityControl();
   public static final HighJump highJump = new HighJump();
   public static final HUD hud = new HUD();
   public static final InvActionLogger invActionLogger = new InvActionLogger();
   public static final ItemSpammer itemSpammer = new ItemSpammer();
   public static final KillAura18 killAura18 = new KillAura18();
   public static final LongJump longJump = new LongJump();
   public static final LookingForward lookingForward = new LookingForward();
   public static final NoRender noRender = new NoRender();
   public static final PacketCanceller packetCanceller = new PacketCanceller();
   public static final NoRotate noRotate = new NoRotate();
   public static final Reach reach = new Reach();
   public static final Sneak sneak = new Sneak();
   public static final Spammer spammer = new Spammer();
   public static final Speed speed = new Speed();
   public static final Strafe strafe = new Strafe();
   public static final Timer timer = new Timer();
   public static final Velocity velocity = new Velocity();

   public static HashMap<String, Integer> init() {
      HashMap<String, Integer> hacks = new HashMap();
      int i = 0;

      for(Iterator var2 = NClient.HackList.iterator(); var2.hasNext(); ++i) {
         Hack h = (Hack)var2.next();
         hacks.put(((NClientHack)h.getClass().getAnnotation(NClientHack.class)).name(), i);
      }

      return hacks;
   }

   public static List<Hack> initL() {
      List<Hack> ret = new ArrayList();
      Field[] var1 = Hacks.class.getDeclaredFields();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         Field hackField = var1[var3];
         if (hackField.getType().getSuperclass() == Hack.class) {
            try {
               ret.add((Hack)hackField.get((Object)null));
            } catch (IllegalAccessException | IllegalArgumentException var6) {
               var6.printStackTrace();
            }
         }
      }

      return ret;
   }
}
