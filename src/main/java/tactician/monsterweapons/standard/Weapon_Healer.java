package tactician.monsterweapons.standard;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.city.Healer;
import tactician.character.TacticianRobin;
import tactician.powers.weapons.Weapon5WindPower;
import tactician.powers.weapons.Weapon6FirePower;

public class Weapon_Healer {
	@SpirePatch(clz = Healer.class, method = "getMove")
	public static class HealerHealsHealth {
		@SpireInsertPatch(rlocs = {10, 15})
		public static void Insert(Healer _inst) {
			if (AbstractDungeon.player instanceof TacticianRobin) { AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(_inst, _inst, new Weapon5WindPower(_inst))); }
		}
	}

	@SpirePatch(clz = Healer.class, method = "getMove")
	public static class StrengthenParty {
		@SpireInsertPatch(rloc = 35)
		public static void Insert(Healer _inst) {
			if (AbstractDungeon.player instanceof TacticianRobin) { AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(_inst, _inst, new Weapon6FirePower(_inst))); }
		}
	}
}