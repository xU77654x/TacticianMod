package tactician.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;

public class FlashSparrowAction extends AbstractGameAction {
	private final DamageInfo info;

	public FlashSparrowAction(AbstractCreature target, DamageInfo info) {
		this.actionType = AbstractGameAction.ActionType.BLOCK;
		this.target = target;
		this.info = info;
	}

	public void update() {
		addToTop(new DamageAction(this.target, this.info, AbstractGameAction.AttackEffect.BLUNT_HEAVY));
		if (false) {
			addToTop(new DrawCardAction(1));
			addToTop(new GainEnergyAction(1));
		} // TODO: Weapon types.
		this.isDone = true;
	}
}