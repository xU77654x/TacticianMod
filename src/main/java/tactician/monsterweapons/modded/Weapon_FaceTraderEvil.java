package tactician.monsterweapons.modded;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tactician.character.TacticianRobin;
import tactician.powers.weapons.*;

public class Weapon_FaceTraderEvil {

	@SpirePatch(cls = "downfall.monsters.FaceTrader", method = "getMove", requiredModId = "downfall")
	public static class SsserpentHead {
		@SpireInsertPatch(rloc = 6)
		public static void Insert(AbstractMonster _inst) {
			if (AbstractDungeon.player instanceof TacticianRobin) { AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(_inst, _inst, new Weapon5WindPower(_inst))); }
		}
	}

	@SpirePatch(cls = "downfall.monsters.FaceTrader", method = "getMove", requiredModId = "downfall")
	public static class GremlinVisage {
		@SpireInsertPatch(rloc = 10)
		public static void Insert(AbstractMonster _inst) {
			if (AbstractDungeon.player instanceof TacticianRobin) { AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(_inst, _inst, new Weapon3AxePower(_inst))); }
		}
	}

	@SpirePatch(cls = "downfall.monsters.FaceTrader", method = "getMove", requiredModId = "downfall")
	public static class NlothsHungryFace {
		@SpireInsertPatch(rloc = 14)
		public static void Insert(AbstractMonster _inst) {
			if (AbstractDungeon.player instanceof TacticianRobin) { AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(_inst, _inst, new Weapon4BowPower(_inst))); }
		}
	}

	@SpirePatch(cls = "downfall.monsters.FaceTrader", method = "getMove", requiredModId = "downfall")
	public static class CultistMask {
		@SpireInsertPatch(rloc = 24)
		public static void Insert(AbstractMonster _inst) {
			if (AbstractDungeon.player instanceof TacticianRobin) { AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(_inst, _inst, new Weapon8DarkPower(_inst))); }
		}
	}

	@SpirePatch(cls = "downfall.monsters.FaceTrader", method = "getMove", requiredModId = "downfall")
	public static class FaceOfCleric {
		@SpireInsertPatch(rloc = 28)
		public static void Insert(AbstractMonster _inst) {
			if (AbstractDungeon.player instanceof TacticianRobin) { AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(_inst, _inst, new Weapon4BowPower(_inst))); }
		}
	}
}