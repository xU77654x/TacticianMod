package tactician.actions;

import basemod.BaseMod;
import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.EmptyDeckShuffleAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.SoulGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.NoDrawPower;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

public class DrawAndLogCardsAction extends AbstractGameAction {
	private boolean shuffleCheck = false;
	public AbstractPlayer p;

	public DrawAndLogCardsAction(AbstractCreature source, int amount) {
		this.p = AbstractDungeon.player;
		if (this.p.hasPower(NoDrawPower.POWER_ID)) {
			this.p.getPower(NoDrawPower.POWER_ID).flash();
			setValues(this.p, source, amount);
			this.isDone = true;
			this.duration = 0.0F;
			this.actionType = AbstractGameAction.ActionType.WAIT;
			return;
		}
		setValues(this.p, source, amount);
		this.actionType = AbstractGameAction.ActionType.DRAW;
		if (Settings.FAST_MODE) { this.duration = Settings.ACTION_DUR_XFAST; }
		else { this.duration = Settings.ACTION_DUR_FASTER; }
	}

	public void update() {
		if (this.amount <= 0) {
			this.isDone = true;
			return;
		}
		int deckSize = this.p.drawPile.size();
		int discardSize = this.p.discardPile.size();
		if (SoulGroup.isActive())
			return;
		if (deckSize + discardSize == 0) {
			this.isDone = true;
			return;
		}
		if (this.p.hand.size() == BaseMod.MAX_HAND_SIZE) {
			this.p.createHandIsFullDialog();
			this.isDone = true;
			return;
		}
		if (!this.shuffleCheck) {
			if (this.amount > deckSize) {
				int tmp = this.amount - deckSize;
				AbstractDungeon.actionManager.addToTop(new DrawAndLogCardsAction(this.p, tmp));
				AbstractDungeon.actionManager.addToTop(new EmptyDeckShuffleAction());
				if (deckSize != 0)
					AbstractDungeon.actionManager.addToTop(new DrawAndLogCardsAction(this.p, deckSize));
				this.amount = 0;
				this.isDone = true;
			}
			this.shuffleCheck = true;
		}
		this.duration -= Gdx.graphics.getDeltaTime();
		if (this.amount != 0 && this.duration < 0.0F) {
			if (Settings.FAST_MODE) {
				this.duration = Settings.ACTION_DUR_XFAST;
			} else {
				this.duration = Settings.ACTION_DUR_FASTER;
			}
			this.amount--;
			if (!AbstractDungeon.player.drawPile.isEmpty()) {
				drawnCards.add(AbstractDungeon.player.drawPile.getTopCard());
				this.p.draw();
				this.p.hand.refreshHandLayout();
			} else {
				logger.warn("Player attempted to draw from an empty drawpile mid-DrawAction?MASTER DECK: " + this.p.masterDeck.getCardNames());
				this.isDone = true;
			}
			if (this.amount == 0)
				this.isDone = true;
		}
	}

	private static final Logger logger = LogManager.getLogger(DrawAndLogCardsAction.class.getName());

	public static ArrayList<AbstractCard> drawnCards = new ArrayList<>();
}