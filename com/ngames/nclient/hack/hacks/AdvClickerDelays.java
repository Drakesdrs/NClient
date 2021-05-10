package com.ngames.nclient.hack.hacks;

import com.ngames.nclient.NClient;
import com.ngames.nclient.baritone.BUtils;
import com.ngames.nclient.hack.Category;
import com.ngames.nclient.hack.Hack;
import com.ngames.nclient.hack.NClientHack;
import com.ngames.nclient.hack.settings.SettingValue;
import java.util.List;

@NClientHack(
   category = Category.COMBAT,
   description = "Generate click delays for hacks that use advanced click delays",
   name = "AdvClickerDelays",
   words = "AdvClickerDelays AutoClicker KillAura18 KillAura19 KillAura"
)
public class AdvClickerDelays extends Hack {
   public final SettingValue ADSpeed = new SettingValue("ADSpeed", 700.0F, 1.0F, 10000.0F);
   public final SettingValue ADValueRange = new SettingValue("ADValueRange", 0.2F, 0.1F, 10.0F);
   public final SettingValue ADMinPhaseSize = new SettingValue("ADMinPhaseSize", 3.0F, 1.0F, 1000000.0F);
   public final SettingValue ADMaxPhaseSize = new SettingValue("ADMaxPhaseSize", 15.0F, 1.0F, 1000000.0F);
   public final SettingValue ADStressHP = new SettingValue("ADStressHP", 4.0F, 1.0F, 20.0F);
   public final SettingValue ADDelayMlMin = new SettingValue("ADDelayMlMin", 1.5F, 0.1F, 100.0F);
   public final SettingValue ADDelayMlMax = new SettingValue("ADDelayMlMax", 2.5F, 0.1F, 100.0F);
   public final SettingValue ADLDelayChanceMin = new SettingValue("ADLDelayChanceMin", 40.0F, 1.0F, 100.0F);
   public final SettingValue ADLDelayChanceMax = new SettingValue("ADLDelayChanceMax", 60.0F, 1.0F, 100.0F);
   private int phase;
   private boolean powered;

   public AdvClickerDelays() {
      this.settings = Hack.addSettings(this);
   }

   public List<Integer> genAdvancedDelayNoise(float CPSMin, float CPSMax) {
      ++this.phase;
      this.powered = NClient.MC.field_71439_g.func_110143_aJ() <= this.ADStressHP.getValue();
      return BUtils.genAdvancedDelayNoise((float)BUtils.randomInRange(Math.round(CPSMin), Math.round(CPSMax)), this.phase, this.powered, this.ADSpeed.getValue(), this.ADValueRange.getValue(), this.ADMinPhaseSize.getValue(), this.ADMaxPhaseSize.getValue(), this.ADDelayMlMin.getValue(), this.ADDelayMlMax.getValue(), (short)((int)this.ADLDelayChanceMin.getValue()), (short)((int)this.ADLDelayChanceMax.getValue()));
   }

   public void onEnable() {
   }
}
