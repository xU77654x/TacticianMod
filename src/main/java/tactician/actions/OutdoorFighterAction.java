package tactician.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;

public class OutdoorFighterAction extends AbstractGameAction {

    public AbstractPlayer p;
    public int draw;
    public int block;
    public int vigor;

    public OutdoorFighterAction(int block, int vigor) {
        this.block = block;
        this.vigor = vigor;
        this.p = AbstractDungeon.player;
        this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
    }

    public void update() {
        for (AbstractCard c : DrawCardAction.drawnCards) {
            if (c.type == AbstractCard.CardType.SKILL || c.type == AbstractCard.CardType.POWER) { AbstractDungeon.actionManager.addToTop(new GainBlockAction(this.p, this.p, this.block)); }
            else { AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(this.p, this.p, new VigorPower(this.p, this.vigor), this.vigor)); }
            break;
        }
        this.isDone = true;
    }
}