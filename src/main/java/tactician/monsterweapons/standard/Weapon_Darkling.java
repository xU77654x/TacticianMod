package tactician.monsterweapons.standard;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.beyond.Darkling;
import tactician.character.TacticianRobin;
import tactician.powers.weapons.Weapon2LancePower;
import tactician.powers.weapons.Weapon6FirePower;

public class Weapon_Darkling {
	@SpirePatch(clz = Darkling.class, method = "getMove")
	public static class Nip {
		@SpireInsertPatch(rlocs = {13, 35, 39})
		public static void Insert(Darkling _inst) {
			if (AbstractDungeon.player instanceof TacticianRobin) { AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(_inst, _inst, new Weapon2LancePower(_inst))); }
		}
	}

	@SpirePatch(clz = Darkling.class, method = "getMove")
	public static class Chomp {
		@SpireInsertPatch(rloc = 23)
		public static void Insert(Darkling _inst) {
			if (AbstractDungeon.player instanceof TacticianRobin) { AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(_inst, _inst, new Weapon6FirePower(_inst))); }
		}
	}
}