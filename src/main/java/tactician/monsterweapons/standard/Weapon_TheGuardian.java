package tactician.monsterweapons.standard;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.exordium.TheGuardian;
import tactician.character.TacticianRobin;
import tactician.powers.weapons.Weapon1SwordPower;
import tactician.powers.weapons.Weapon2LancePower;
import tactician.powers.weapons.Weapon7ThunderPower;

public class Weapon_TheGuardian {
	private static final boolean enemyWeapon = (AbstractDungeon.player instanceof TacticianRobin);
	@SpirePatch(clz = TheGuardian.class, method = "useVentSteam")
	public static class WhirlwindAfterVentSteam {
		@SpireInsertPatch(rloc = 13)
		public static void Insert(TheGuardian _inst) {
			if (enemyWeapon) { AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(_inst, _inst, new Weapon1SwordPower(_inst))); }
		}
	}

	@SpirePatch(clz = TheGuardian.class, method = "useCloseUp")
	public static class EnterDefensiveMode {
		@SpireInsertPatch(rloc = 9)
		public static void Insert(TheGuardian _inst) {
			if (enemyWeapon) { AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(_inst, _inst, new Weapon2LancePower(_inst))); }
		}
	}

	@SpirePatch(clz = TheGuardian.class, method = "useTwinSmash")
	public static class WhirlwindAfterTwinSmash {
		@SpireInsertPatch(rloc = 7)
		public static void Insert(TheGuardian _inst) {
			if (enemyWeapon) { AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(_inst, _inst, new Weapon1SwordPower(_inst))); }
		}
	}

	@SpirePatch(clz = TheGuardian.class, method = "useChargeUp")
	public static class FierceBashAfterChargeUp {
		@SpireInsertPatch(rloc = 5)
		public static void Insert(TheGuardian _inst) {
			if (enemyWeapon) { AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(_inst, _inst, new Weapon7ThunderPower(_inst))); }
		}
	}
} // Program Arguments: --out-jar
// Normal weapon assignment uses addToTop in order to avoid the previous weapon type being calculated.
// However, cases such as The Guardian require the use of addToBottom in order to not use Sword instead of Axe with Twin Slam, for example.