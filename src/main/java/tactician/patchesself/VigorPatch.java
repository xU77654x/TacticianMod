package tactician.patchesself;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;

public class VigorPatch {
    @SpirePatch(clz = VigorPower.class, method = "onUseCard")
    public static class DontConsumeVigorPatch {
        @SpirePrefixPatch
        public static SpireReturn<Void> patch(VigorPower __instance, AbstractCard card, UseCardAction action) {
            if (card instanceof tactician.cards.Common.CurvedShot) {
                __instance.flash();
                return SpireReturn.Return(null);
            }
            return SpireReturn.Continue();
            // Credit to Downfall: Champ for this code.
        }
    }
}