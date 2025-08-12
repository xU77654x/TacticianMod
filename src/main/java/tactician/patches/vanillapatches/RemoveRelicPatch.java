package tactician.patches.vanillapatches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.RunicDome;
import javassist.CannotCompileException;
import javassist.CtBehavior;

import java.util.ArrayList;
import java.util.Iterator;

import static tactician.character.TacticianRobin.Meta.TACTICIAN;

@SpirePatch2(clz = AbstractDungeon.class, method = "initializeRelicList")
public class RemoveRelicPatch {
	@SpireInsertPatch(locator = Locator.class)
	public static void Insert() {
		if (AbstractDungeon.player.chosenClass == TACTICIAN) {
			AbstractDungeon.relicsToRemoveOnStart.add(RunicDome.ID); // Take a wild guess.
			AbstractDungeon.relicsToRemoveOnStart.add("Elizabethan Collar"); // Vampires event guarantees an Injury.
			AbstractDungeon.relicsToRemoveOnStart.add("Runic Blindfold"); // Same reason as Runic Dome.
			AbstractDungeon.relicsToRemoveOnStart.add("Deflecting Bracers"); // I don't want both Deflect and Counter used together.


			for (String remove : AbstractDungeon.relicsToRemoveOnStart) {
				Iterator<String> s; /*
				for (s = AbstractDungeon.commonRelicPool.iterator(); s.hasNext(); ) {
					String derp = s.next();
					if (derp.equals(remove)) { s.remove(); }
				}
				for (s = AbstractDungeon.uncommonRelicPool.iterator(); s.hasNext(); ) {
					String derp = s.next();
					if (derp.equals(remove)) { s.remove(); }
				}
				for (s = AbstractDungeon.rareRelicPool.iterator(); s.hasNext(); ) {
					String derp = s.next();
					if (derp.equals(remove)) { s.remove(); }
				}
				for (s = AbstractDungeon.shopRelicPool.iterator(); s.hasNext(); ) {
					String derp = s.next();
					if (derp.equals(remove))  s.remove();
				} */
				for (s = AbstractDungeon.bossRelicPool.iterator(); s.hasNext(); ) {
					String derp = s.next();
					if (derp.equals(remove)) { s.remove(); }
				}
			}
		}
	}

	private static class Locator extends SpireInsertLocator {
		public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
			Matcher.MethodCallMatcher methodCallMatcher = new Matcher.MethodCallMatcher(ArrayList.class, "clear");
			return LineFinder.findInOrder(ctMethodToPatch, methodCallMatcher);
		}
	}
} // Credit to Prismatic for this patch.