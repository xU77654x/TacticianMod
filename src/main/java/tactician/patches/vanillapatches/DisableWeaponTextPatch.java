package tactician.patches.vanillapatches;

import com.badlogic.gdx.Gdx;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.combat.PowerExpireTextEffect;
import tactician.powers.weapons.*;
import java.util.Arrays;
import static tactician.character.TacticianRobin.Meta.TACTICIAN;

public class DisableWeaponTextPatch {
	@SpirePatch(clz = RemoveSpecificPowerAction.class, method = "update")
	public static class NoWeaponPower {
		@SpireInsertPatch(rloc = 0, localvars = {"duration", "powerToRemove", "powerInstance"})
		public static SpireReturn<Void> Insert(RemoveSpecificPowerAction __inst, float duration, String powerToRemove, AbstractPower powerInstance) {
			if (AbstractDungeon.player.chosenClass == TACTICIAN) {
				if (duration == 0.1F) {
					if (__inst.target.isDeadOrEscaped()) { __inst.isDone = true; return SpireReturn.Return(); }
					AbstractPower removeMe = null;
					if (powerToRemove != null) { removeMe = __inst.target.getPower(powerToRemove); }
					else if (powerInstance != null && __inst.target.powers.contains(powerInstance)) {
						removeMe = powerInstance;
					}
					// assert removeMe != null;
					if (removeMe != null) {
						if (!(Arrays.asList(Weapon1SwordPower.POWER_ID, Weapon2LancePower.POWER_ID, Weapon3AxePower.POWER_ID, Weapon4BowPower.POWER_ID, Weapon5WindPower.POWER_ID, Weapon6FirePower.POWER_ID, Weapon7ThunderPower.POWER_ID, Weapon8DarkPower.POWER_ID, Weapon0NeutralPower.POWER_ID).contains(powerToRemove))) {
							AbstractDungeon.effectList.add(new PowerExpireTextEffect(__inst.target.hb.cX - __inst.target.animX, __inst.target.hb.cY + __inst.target.hb.height / 2.0F, removeMe.name, removeMe.region128));
						}
						// if (Objects.equals(__inst.target.toString(), Lagavulin.ID)) { AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(__inst.target, __inst.target, "Metallicize", 8)); }
						removeMe.onRemove();
						__inst.target.powers.remove(removeMe);
						AbstractDungeon.onModifyPower();
						for (AbstractOrb o : AbstractDungeon.player.orbs) { o.updateDescription(); }
					}
					else { duration = 0.0F; }
				}
				duration -= Gdx.graphics.getDeltaTime();
				if (duration < 0.0F) { __inst.isDone = true; }
				return SpireReturn.Return();
			}
			else { return SpireReturn.Continue(); }
		}
	}
}