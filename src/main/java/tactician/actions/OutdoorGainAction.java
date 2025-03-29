package tactician.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import tactician.cards.Common.OutdoorFighter;

public class OutdoorGainAction extends AbstractGameAction {
	public AbstractPlayer p;
	public OutdoorFighter c;
	public int block;
	public int vigor;

	public OutdoorGainAction(OutdoorFighter card, int block, int vigor) {
		this.duration = Settings.ACTION_DUR_FASTER;
		this.p = AbstractDungeon.player;
		this.c = card;
		this.block = block;
		this.vigor = vigor;
	}

	public void update() {
		if (this.duration == Settings.ACTION_DUR_FASTER) {
			int blockGain = 0;
			int vigorGain = 0;
			for (AbstractCard drawn : DrawAndLogCardsAction.drawnCards) {
				if (c.type == AbstractCard.CardType.SKILL || c.type == AbstractCard.CardType.POWER) { blockGain += block; }
				else { vigorGain += vigor; }}
			DrawAndLogCardsAction.drawnCards.clear();
			this.c.gainBlock(blockGain);
			this.c.gainVigor(vigorGain);
		}
		tickDuration();
	}
}