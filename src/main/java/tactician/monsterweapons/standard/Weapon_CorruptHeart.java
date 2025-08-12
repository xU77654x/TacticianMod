package tactician.monsterweapons.standard;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.ending.CorruptHeart;
import tactician.character.TacticianRobin;
import tactician.powers.weapons.Weapon4BowPower;
import tactician.powers.weapons.Weapon8DarkPower;

public class Weapon_CorruptHeart {
	@SpirePatch(clz = CorruptHeart.class, method = "getMove")
	public static class BloodShots {
		@SpireInsertPatch(rlocs = {9, 16})
		public static void Insert(CorruptHeart _inst) {
			if (AbstractDungeon.player instanceof TacticianRobin) { AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(_inst, _inst, new Weapon4BowPower(_inst))); }
		}
	}
	@SpirePatch(clz = CorruptHeart.class, method = "getMove")
	public static class HeartEcho {
		@SpireInsertPatch(rlocs = {11, 18})
		public static void Insert(CorruptHeart _inst) {
			if (AbstractDungeon.player instanceof TacticianRobin) { AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(_inst, _inst, new Weapon8DarkPower(_inst))); }
		}
	}
}