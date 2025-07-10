package tactician.monsterweapons.standard;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.city.GremlinLeader;
import tactician.powers.weapons.Weapon1SwordPower;
import tactician.powers.weapons.Weapon2LancePower;

public class Weapon_GremlinLeader {
	private static final boolean enemyWeapon = true; // EnemyWeaponHelper.enableWeapons;
	/*
	@SpirePatch(clz = GremlinLeader.class, method = "getMove")
	public static class SummonGremlins {
		@SpireInsertPatch(rlocs = {3, 11, 19})
		public static void Insert(GremlinLeader _inst) {
			if (enemyWeapon) { AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(_inst, _inst, new Weapon1SwordPower(_inst))); }
		}
	}

	@SpirePatch(clz = GremlinLeader.class, method = "getMove")
	public static class RussianRouletteStab {
		@SpireInsertPatch(rlocs = {5, 9, 27, 31, 43, 47})
		public static void Insert(GremlinLeader _inst) {
			 if (enemyWeapon) { AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(_inst, _inst, new Weapon2LancePower(_inst))); }
		}
	}

	@SpirePatch(clz = GremlinLeader.class, method = "getMove")
	public static class NeverGonnaGiveYouUp {
		@SpireInsertPatch(rlocs = {25, 41, 49})
		public static void Insert(GremlinLeader _inst) {
			if (enemyWeapon) { AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(_inst, _inst, new Weapon1SwordPower(_inst))); }
		}
	} */
}

// Program Arguments: --out-jar