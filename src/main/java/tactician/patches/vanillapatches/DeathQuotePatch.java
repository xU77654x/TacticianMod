package tactician.patches.vanillapatches;

import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CharacterStrings;
import com.megacrit.cardcrawl.screens.DeathScreen;
import javassist.CtBehavior;
import java.util.ArrayList;
import static tactician.TacticianMod.makeID;
import static tactician.character.TacticianRobin.Meta.TACTICIAN;

public class DeathQuotePatch {
	@SpirePatch(clz = DeathScreen.class, method = "getDeathText")
	public static class DeathTextTactician {
		private static final CharacterStrings charStrings = CardCrawlGame.languagePack.getCharacterString(makeID("Tactician"));
		private static final String deathText = charStrings.TEXT[4]; // Use String[] for multiple quotes.

		@SpireInsertPatch(locator = Locator.class, localvars = {"list"})
		public static void Insert(DeathScreen __instance, ArrayList<String> list) {
			if (AbstractDungeon.player.chosenClass == TACTICIAN) { list.add(deathText); }
		}
	}

	private static class Locator extends SpireInsertLocator {
		public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
			Matcher.MethodCallMatcher methodCallMatcher = new Matcher.MethodCallMatcher(MathUtils.class, "random");
			return LineFinder.findInOrder(ctMethodToPatch, methodCallMatcher);
		}
	}
}