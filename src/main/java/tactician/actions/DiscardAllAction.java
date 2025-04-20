package tactician.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DiscardSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class DiscardAllAction extends AbstractGameAction {
	public DiscardAllAction(AbstractCreature source) {
		this.source = source;
		this.duration = Settings.ACTION_DUR_XFAST;
	}

	public void update() {
		for (AbstractCard c : AbstractDungeon.player.hand.group) { addToTop(new DiscardSpecificCardAction(c)); }
		this.isDone = true;
	}
}