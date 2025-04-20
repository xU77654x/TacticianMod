package tactician.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ExhaustAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import tactician.util.Wiz;

import java.util.ArrayList;

public class QuickBurnAction extends AbstractGameAction {
	public static ArrayList<String> cache = new ArrayList<>();

	public QuickBurnAction() {
		this.actionType = AbstractGameAction.ActionType.WAIT;
	}

	/*
	public static AbstractCard randomCombatArt() {
		ArrayList<String> tmp = new ArrayList<>();
		for (Map.Entry<String, AbstractCard> c : CardLibrary.cards.entrySet()) {
			if ((c.getValue()).color == MyCharacter.Meta.CARD_COLOR) { tmp.add(c.getKey()); }
		}
		return CardLibrary.cards.get(tmp.get(cardRandomRng.random(0, tmp.size() - 1)));
	}

	public static AbstractCard singleScan() {
		if (cache == null) {
			ArrayList<String> tmp = new ArrayList<>();
			for (Map.Entry<String, AbstractCard> c : CardLibrary.cards.entrySet()) {
				if ((c.getValue()).color == MyCharacter.Meta.CARD_COLOR) { tmp.add(c.getKey()); }
			}
			return CardLibrary.cards.get(tmp.get(cardRandomRng.random(0, tmp.size() - 1)));
		}
		return null;
	} */

	public void update() {
		int count = AbstractDungeon.player.hand.size();
		int i;
		for (i = 0; i < count; i++) { addToTop(new ExhaustAction(1, true, true, false, Settings.ACTION_DUR_XFAST)); }
		for (i = 0; i < count; i++) {
			addToTop(new GainEnergyAction(1));
			AbstractCard c = Wiz.randomCombatArt(false);
			addToBot(new MakeTempCardInHandAction(c));
		}
		this.isDone = true;
	}
}