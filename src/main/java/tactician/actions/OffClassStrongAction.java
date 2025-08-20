package tactician.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import tactician.character.TacticianRobin;

public class OffClassStrongAction extends AbstractGameAction {
	private final int valModify = 3;
	private int effect;
	private boolean attack;
	private boolean block;

	public OffClassStrongAction(boolean attack, boolean block) {
		this.attack = attack;
		this.block = block;
	}

	@Override
	public void update() {
		if (!(AbstractDungeon.player instanceof TacticianRobin)) {
			if (!AbstractDungeon.actionManager.cardsPlayedThisCombat.isEmpty() && (AbstractDungeon.actionManager.cardsPlayedThisCombat.get(AbstractDungeon.actionManager.cardsPlayedThisCombat.size() - 2)).type == AbstractCard.CardType.POWER) {
				if (attack) { addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new VigorPower(AbstractDungeon.player, valModify))); }
				if (block) { addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new VigorPower(AbstractDungeon.player, valModify))); }
			}
		}
		this.isDone = true;
	}
}