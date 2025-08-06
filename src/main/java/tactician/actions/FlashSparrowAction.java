package tactician.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tactician.util.Wiz;

public class FlashSparrowAction extends AbstractGameAction {
	private final DamageInfo info;
	private final AbstractMonster m;

	public FlashSparrowAction(AbstractMonster target, DamageInfo info) {
		this.actionType = AbstractGameAction.ActionType.DAMAGE;
		this.target = target;
		this.m = target;
		this.info = info;
	}

	public void update() {
		addToTop(new DamageAction(this.target, this.info, AbstractGameAction.AttackEffect.BLUNT_HEAVY));
		addToTop(new SFXAction("tactician:FlashSparrow"));

		if ( Wiz.playerWeaponCalc(this.m, 9) > 0) {
			addToTop(new DrawCardAction(1));
			addToTop(new GainEnergyAction(1));
		}
		this.isDone = true;
	}
}