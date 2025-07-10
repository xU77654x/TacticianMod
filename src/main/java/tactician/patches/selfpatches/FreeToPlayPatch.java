package tactician.patches.selfpatches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import tactician.powers.QuickBurnPower;
import tactician.util.Wiz;

public class FreeToPlayPatch {
	@SpirePatch( clz = AbstractCard.class, method = "freeToPlay")
	public static class FreeToPlaySkills {
		public static boolean Postfix(boolean __result, AbstractCard __instance) {
			if (Wiz.isInCombat() && AbstractDungeon.player.hasPower(QuickBurnPower.POWER_ID) && __instance.type.equals(AbstractCard.CardType.ATTACK)) {
				return true;
			}
			return __result;
		}
	}
}