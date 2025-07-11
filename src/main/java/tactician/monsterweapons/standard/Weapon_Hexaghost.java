package tactician.monsterweapons.standard;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.exordium.Hexaghost;
import tactician.character.TacticianRobin;
import tactician.powers.weapons.*;

public class Weapon_Hexaghost {
	private static final boolean enemyWeapon = (AbstractDungeon.player instanceof TacticianRobin);

	@SpirePatch(clz = Hexaghost.class, method = "getMove")
	public static class Case0 {
		@SpireInsertPatch(rloc = 7)
		public static void Insert(Hexaghost _inst) {
			if (enemyWeapon) { AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(_inst, _inst, new Weapon6FirePower(_inst))); }
		}
	}

	@SpirePatch(clz = Hexaghost.class, method = "getMove")
	public static class Case1 {
		@SpireInsertPatch(rloc = 10)
		public static void Insert(Hexaghost _inst) {
			if (enemyWeapon) { AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(_inst, _inst, new Weapon5WindPower(_inst))); }
		}
	}

	@SpirePatch(clz = Hexaghost.class, method = "getMove")
	public static class Case2 {
		@SpireInsertPatch(rloc = 13)
		public static void Insert(Hexaghost _inst) {
			if (enemyWeapon) { AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(_inst, _inst, new Weapon6FirePower(_inst))); }
		}
	}

	@SpirePatch(clz = Hexaghost.class, method = "getMove")
	public static class Case3 {
		@SpireInsertPatch(rloc = 16)
		public static void Insert(Hexaghost _inst) {
			if (enemyWeapon) { AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(_inst, _inst, new Weapon7ThunderPower(_inst))); }
		}
	}

	@SpirePatch(clz = Hexaghost.class, method = "getMove")
	public static class Case4 {
		@SpireInsertPatch(rloc = 19)
		public static void Insert(Hexaghost _inst) {
			if (enemyWeapon) { AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(_inst, _inst, new Weapon5WindPower(_inst))); }
		}
	}

	@SpirePatch(clz = Hexaghost.class, method = "getMove")
	public static class Case5 {
		@SpireInsertPatch(rloc = 22)
		public static void Insert(Hexaghost _inst) {
			if (enemyWeapon) { AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(_inst, _inst, new Weapon6FirePower(_inst))); }
		}
	}

	@SpirePatch(clz = Hexaghost.class, method = "getMove")
	public static class Case6 {
		@SpireInsertPatch(rloc = 25)
		public static void Insert(Hexaghost _inst) {
			if (enemyWeapon) { AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(_inst, _inst, new Weapon8DarkPower(_inst))); }
		}
	}

	@SpirePatch(clz = Hexaghost.class, method = "takeTurn")
	public static class ForceDividerWeapon {
		@SpireInsertPatch(rloc = 8)
		public static void Insert(Hexaghost _inst) {
			if (enemyWeapon) { AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(_inst, _inst, new Weapon4BowPower(_inst))); }
		}
	}
} // Inferno and Divider use the exact same call... somehow? I wanted Divider to use Bow instead of Dark, but I have been forced to change my design.