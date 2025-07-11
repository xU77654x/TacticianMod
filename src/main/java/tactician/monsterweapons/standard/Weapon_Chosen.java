package tactician.monsterweapons.standard;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.city.Chosen;
import tactician.character.TacticianRobin;
import tactician.powers.weapons.Weapon2LancePower;
import tactician.powers.weapons.Weapon7ThunderPower;

public class Weapon_Chosen {
	private static final boolean enemyWeapon = (AbstractDungeon.player instanceof TacticianRobin);
	@SpirePatch(clz = Chosen.class, method = "getMove")
	public static class Poke {
		@SpireInsertPatch(rlocs = {22, 29, 54})
		public static void Insert(Chosen _inst) {
			if (enemyWeapon) { AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(_inst, _inst, new Weapon2LancePower(_inst))); }
		}
	}
	@SpirePatch(clz = Chosen.class, method = "getMove")
	public static class Zap {
		@SpireInsertPatch(rlocs = {19, 51})
		public static void Insert(Chosen _inst) {
			if (enemyWeapon) { AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(_inst, _inst, new Weapon7ThunderPower(_inst))); }
		}
	}
}