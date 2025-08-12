package tactician.monsterweapons.standard;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.city.Byrd;
import tactician.character.TacticianRobin;
import tactician.powers.weapons.Weapon0NeutralPower;
import tactician.powers.weapons.Weapon5WindPower;

public class Weapon_Byrd {
	@SpirePatch(clz = Byrd.class, method = "getMove")
	public static class PeckIsNeutral {
		@SpireInsertPatch(rlocs = {5, 22, 32, 44})
		public static void Insert(Byrd _inst) {
			if (AbstractDungeon.player instanceof TacticianRobin) { AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(_inst, _inst, new Weapon0NeutralPower(_inst))); }
		}
	}
	@SpirePatch(clz = Byrd.class, method = "getMove")
	public static class Swoop {
		@SpireInsertPatch(rlocs = {17, 35, 42})
		public static void Insert(Byrd _inst) {
			if (AbstractDungeon.player instanceof TacticianRobin) { AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(_inst, _inst, new Weapon5WindPower(_inst))); }
		}
	}
	@SpirePatch(clz = Byrd.class, method = "getMove")
	public static class Headbutt {
		@SpireInsertPatch(rloc = 52)
		public static void Insert(Byrd _inst) {
			if (AbstractDungeon.player instanceof TacticianRobin) { AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(_inst, _inst, new Weapon5WindPower(_inst))); }
		}
	}
}