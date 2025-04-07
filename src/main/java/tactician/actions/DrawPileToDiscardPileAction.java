package tactician.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class DrawPileToDiscardPileAction extends AbstractGameAction {
    private final boolean chooseAny;
    private final AbstractPlayer p;

    public DrawPileToDiscardPileAction(AbstractPlayer p, boolean chooseAny, int amount) {
        this.p = p;
        this.chooseAny = chooseAny;
        this.duration = Settings.ACTION_DUR_XFAST;
        this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
        this.amount = amount;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_XFAST) {
            CardGroup tmp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
            for (AbstractCard c : this.p.drawPile.group)
                tmp.addToRandomSpot(c);
            if (tmp.isEmpty()) {
                this.isDone = true;
                return;
            }
            if (!this.chooseAny) {
                if (tmp.size() <= this.amount) {
                    for (AbstractCard c : tmp.group)
                        this.p.drawPile.moveToDiscardPile(c);
                    AbstractDungeon.gridSelectScreen.selectedCards.clear();
                    this.isDone = true;
                    return;
                }
                AbstractDungeon.gridSelectScreen.open(tmp, this.amount, false, "Select " + this.amount + " card(s) to add to your discard pile.");
                tickDuration();
            } else {
                AbstractDungeon.gridSelectScreen.open(tmp, this.amount, true, "Select up to " + this.amount + " card(s) to add to your discard pile.");
                tickDuration();
            }
        } else if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
            for (AbstractCard c : AbstractDungeon.gridSelectScreen.selectedCards)
                this.p.drawPile.moveToDiscardPile(c);
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
            this.isDone = true;
            return;
        }
        tickDuration();
    }
}