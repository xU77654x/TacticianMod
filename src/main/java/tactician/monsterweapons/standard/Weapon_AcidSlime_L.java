package tactician.monsterweapons.standard;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.exordium.AcidSlime_L;
import tactician.powers.weapons.*;

public class Weapon_AcidSlime_L {
	private static final boolean enemyWeapon = true; // EnemyWeaponHelper.enableWeapons;
/*
	@SpirePatch(clz = AcidSlime_L.class, method = "getMove")
	public static class LargeAcidLick {
		@SpireInsertPatch(rlocs = {7, 19, 34, 45, 57, 72})
		public static void Insert(AcidSlime_L _inst) {
			if (enemyWeapon) { AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(_inst, _inst, new Weapon3AxePower(_inst))); }
		}
	}

	@SpirePatch(clz = AcidSlime_L.class, method = "getMove")
	public static class LargeAcidTackle {
		@SpireInsertPatch(rlocs = {5, 22, 31, 43, 60, 69})
		public static void Insert(AcidSlime_L _inst) {
			if (enemyWeapon) { AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(_inst, _inst, new Weapon5WindPower(_inst))); }
		}
	}

	@SpirePatch(clz = AcidSlime_L.class, method = "getMove")
	public static class LargeAcidCorrosive {
		@SpireInsertPatch(rlocs = {10, 17, 29, 48, 55, 67})
		public static void Insert(AcidSlime_L _inst) {
			if (enemyWeapon) { AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(_inst, _inst, new Weapon8DarkPower(_inst))); }
		}
	}
 */
	// The Unknown intention has to be patched within public void damage(), but I won't touch this for slimes.
}

// ATTACK: 5, 22, 31,43,60
// ATK_DBF: 10, 17, 29, 48, 55,
// DEBUFF: 7, 19, 34, 45, 57