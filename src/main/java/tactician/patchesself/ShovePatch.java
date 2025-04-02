package tactician.patchesself;

import basemod.ReflectionHacks;
import com.badlogic.gdx.Gdx;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.TextAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import tactician.powers.ShovePower;
import java.lang.reflect.Field;

@SpirePatch(clz = ApplyPowerAction.class, method = "update")
public class ShovePatch {
	@SpireInsertPatch(rloc = 35)
	public static SpireReturn Insert(ApplyPowerAction __instance) {
		AbstractPower powerToApply = ReflectionHacks.getPrivate(__instance, ApplyPowerAction.class, "powerToApply");
		if ((powerToApply.ID.equals("Strength") || powerToApply.ID.equals("Focus") || powerToApply.ID.equals("Dexterity")) && __instance.amount < 0 && __instance.target.hasPower(ShovePower.POWER_ID)) {
			__instance.target.getPower(ShovePower.POWER_ID).flash();
			AbstractDungeon.actionManager.addToTop(new TextAboveCreatureAction(__instance.target, ApplyPowerAction.TEXT[1]));
			float duration = ((Float)getPrivateInherited(__instance, ApplyPowerAction.class, "duration")).floatValue();
			duration -= Gdx.graphics.getDeltaTime();
			ReflectionHacks.setPrivateInherited(__instance, ApplyPowerAction.class, "duration", Float.valueOf(duration));
			return SpireReturn.Return(null);
		}
		return SpireReturn.Continue();
	}

	public static Object getPrivateInherited(Object obj, Class<?> objClass, String fieldName) {
		try {
			Field targetField = objClass.getSuperclass().getDeclaredField(fieldName);
			targetField.setAccessible(true);
			return targetField.get(obj);
		} catch (Exception exception) { return null; }
	}
}