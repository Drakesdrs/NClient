package com.ngames.nclient.hack.hacks;

import com.ngames.nclient.NClient;
import com.ngames.nclient.baritone.BUtils;
import com.ngames.nclient.baritone.Baritone;
import com.ngames.nclient.event.Listener;
import com.ngames.nclient.event.NClientEvent;
import com.ngames.nclient.hack.Category;
import com.ngames.nclient.hack.Hack;
import com.ngames.nclient.hack.NClientHack;
import com.ngames.nclient.hack.settings.SettingBoolean;
import com.ngames.nclient.hack.settings.SettingChoose;
import com.ngames.nclient.hack.settings.SettingValue;

@NClientHack(
   category = Category.PLAYER,
   description = "Automatically eat food when hungry",
   name = "AutoEat",
   words = "AutoEat Eat"
)
public class AutoEat extends Hack {
   private final SettingValue foodLvl = new SettingValue("FoodLvl", 10.0F, 0.0F, 20.0F);
   private final SettingChoose preferFood = new SettingChoose("PreferFood", (byte)0, new String[]{"best", "wise"});
   private final SettingBoolean setBack = new SettingBoolean("SetBack", true);
   private final SettingBoolean eatInPvP = new SettingBoolean("EatInPvP", false);
   private boolean isRun = false;

   public AutoEat() {
      this.settings = Hack.addSettings(this);
   }

   public void onEnable() {
      super.onEnable();
      new Listener(NClientEvent.PlayerFoodStatsChangeEvent.class, this);
   }

   public void onInvoke(NClientEvent event) {
      if (!this.isRun) {
         (new Thread(() -> {
            this.isRun = true;
            if ((float)NClient.MC.field_71439_g.func_71024_bL().func_75116_a() <= this.foodLvl.getValue()) {
               while(Baritone.getFoodStats().func_75121_c() && this.enabled) {
                  byte isEaten = (byte)Baritone.getFoodStats().func_75116_a();
                  if (!this.eatInPvP.getValue() && !NClient.inPvP || this.eatInPvP.getValue()) {
                     int slotId = -1;
                     int hand = NClient.MC.field_71439_g.field_71071_by.field_70461_c;
                     if (this.preferFood.getValue() == 0) {
                        slotId = Baritone.getBestFood(true);
                     }

                     if (this.preferFood.getValue() == 1) {
                        slotId = Baritone.getFoodWithHeal((byte)Baritone.getFoodStats().func_75116_a(), true);
                     }

                     if (NClient.MC.field_71439_g.func_71043_e(false) && slotId != -1) {
                        Baritone.setMainHand(slotId);
                        Baritone.useItem();

                        for(; isEaten >= Baritone.getFoodStats().func_75116_a() && this.enabled; BUtils.sleep(100L)) {
                           if (NClient.MC.field_71439_g.field_71071_by.field_70461_c != slotId) {
                              Baritone.setMainHand(slotId);
                           }
                        }

                        Baritone.usedItem();
                        if (this.setBack.getValue()) {
                           Baritone.setMainHand(hand);
                        }
                     }
                  }
               }
            }

            this.isRun = false;
         })).start();
      }

   }

   public void onUpdate() {
      super.onUpdate();
      this.onInvoke(new NClientEvent.PlayerFoodStatsChangeEvent());
   }
}
