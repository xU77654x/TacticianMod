package tactician.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect;

public class TacticianMutateAction extends AbstractGameAction {
	private final AbstractCard mutatedCard;
	private final AbstractCard resultCard;
	private final boolean applyUpgrade;

	public TacticianMutateAction(AbstractCard mutatedCard, AbstractCard resultCard, boolean upgraded) {
		this.actionType = AbstractGameAction.ActionType.SPECIAL;
		this.duration = Settings.ACTION_DUR_XFAST;
		this.mutatedCard = mutatedCard;
		this.resultCard = resultCard;
		this.applyUpgrade = upgraded;
	}

	public void update() {
		boolean found = false;
		for (AbstractCard card : AbstractDungeon.player.hand.group) {
			if (card == this.mutatedCard) {
				found = true;
				break;
			}
		}
		if (found && this.mutatedCard != null) {
			if (AbstractDungeon.player.hoveredCard == this.mutatedCard) { AbstractDungeon.player.releaseCard(); }
			AbstractDungeon.actionManager.cardQueue.removeIf(q -> (q.card == this.mutatedCard));
			AbstractDungeon.effectList.add(new PurgeCardEffect(mutatedCard)); // Remove the old card from the hand.

			AbstractCard card = resultCard; // Add the new card to the hand.
			if (applyUpgrade) { card.upgrade(); }
			addToBot(new MakeTempCardInHandAction(card, 1));
			AbstractDungeon.player.hand.removeCard(this.mutatedCard);
		}
		this.isDone = true;
	}
}