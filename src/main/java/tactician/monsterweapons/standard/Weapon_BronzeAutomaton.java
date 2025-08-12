package tactician.monsterweapons.standard;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.city.BronzeAutomaton;
import tactician.character.TacticianRobin;
import tactician.powers.weapons.Weapon2LancePower;
import tactician.powers.weapons.Weapon7ThunderPower;

public class Weapon_BronzeAutomaton {
	@SpirePatch(clz = BronzeAutomaton.class, method = "getMove")
	public static class Flail {
		@SpireInsertPatch(rloc = 24)
		public static void Insert(BronzeAutomaton _inst) {
			if (AbstractDungeon.player instanceof TacticianRobin) { AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(_inst, _inst, new Weapon2LancePower(_inst))); }
		}
	}
	@SpirePatch(clz = BronzeAutomaton.class, method = "getMove")
	public static class TM15 {
		@SpireInsertPatch(rloc = 7)
		public static void Insert(BronzeAutomaton _inst) {
			if (AbstractDungeon.player instanceof TacticianRobin) { AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(_inst, _inst, new Weapon7ThunderPower(_inst))); }
		}
	}
}