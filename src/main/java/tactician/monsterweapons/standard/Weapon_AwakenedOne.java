package tactician.monsterweapons.standard;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.beyond.AwakenedOne;
import tactician.character.TacticianRobin;
import tactician.powers.weapons.*;

public class Weapon_AwakenedOne {
	@SpirePatch(clz = AwakenedOne.class, method = "getMove")
	public static class Slash {
		@SpireInsertPatch(rlocs = {2, 12, 18})
		public static void Insert(AwakenedOne _inst) {
			if (AbstractDungeon.player instanceof TacticianRobin) { AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(_inst, _inst, new Weapon1SwordPower(_inst))); }
		}
	}
	@SpirePatch(clz = AwakenedOne.class, method = "getMove")
	public static class SoulStrike {
		@SpireInsertPatch(rlocs = {10, 20})
		public static void Insert(AwakenedOne _inst) {
			if (AbstractDungeon.player instanceof TacticianRobin) { AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(_inst, _inst, new Weapon3AxePower(_inst))); }
		}
	}

	@SpirePatch(clz = AwakenedOne.class, method = "getMove")
	public static class DarkEcho {
		@SpireInsertPatch(rloc = 27)
		public static void Insert(AwakenedOne _inst) {
			if (AbstractDungeon.player instanceof TacticianRobin) { AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(_inst, _inst, new Weapon8DarkPower(_inst))); }
		}
	}

	@SpirePatch(clz = AwakenedOne.class, method = "getMove")
	public static class Tackle {
		@SpireInsertPatch(rlocs = {35, 41})
		public static void Insert(AwakenedOne _inst) {
			if (AbstractDungeon.player instanceof TacticianRobin) { AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(_inst, _inst, new Weapon2LancePower(_inst))); }
		}
	}
}