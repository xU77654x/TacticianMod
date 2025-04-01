package tactician.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;

public class OutdoorFollowUpAction extends AbstractGameAction {
	public AbstractPlayer p;
	public int draw;
	public int block;
	public int vigor;

	public OutdoorFollowUpAction (int draw, int block, int vigor) {
		this.draw = draw;
		this.block = block;
		this.vigor = vigor;
		this.p = AbstractDungeon.player;
		this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
	}

	public void update() {
		AbstractDungeon.actionManager.addToTop(new WaitAction(0.4F));
		tickDuration();
		int difference = this.draw - DrawCardAction.drawnCards.size();
		if (this.isDone) {
			for (AbstractCard c : DrawCardAction.drawnCards) {
				if (c.type == AbstractCard.CardType.SKILL || c.type == AbstractCard.CardType.POWER) {
					AbstractDungeon.actionManager.addToTop(new GainBlockAction(this.p, this.p, this.block));
				}
				else { AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(this.p, this.p, new VigorPower(this.p, this.vigor), this.vigor)); }
			}
			while (difference > 0) {
				AbstractDungeon.actionManager.addToTop(new GainBlockAction(this.p, this.p, this.block));
				AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(this.p, this.p, new VigorPower(this.p, this.vigor), this.vigor));
				difference--;
			}
		}
	}
}