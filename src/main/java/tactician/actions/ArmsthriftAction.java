package tactician.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;

public class ArmsthriftAction extends AbstractGameAction {
    public AbstractCard.CardType typeToUpgrade;

    public ArmsthriftAction(int category) {
        this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_FAST;

        if (category == 0) { typeToUpgrade = AbstractCard.CardType.ATTACK; }
        if (category == 1) { typeToUpgrade = AbstractCard.CardType.SKILL; }
        if (category == 2) { typeToUpgrade = AbstractCard.CardType.POWER; }
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            ArrayList<AbstractCard> cardsToUpgrade = new ArrayList<>();
            for (AbstractCard c : AbstractDungeon.player.hand.group) {
                if (c.type == typeToUpgrade) {
                    cardsToUpgrade.add(c);
                }
            }
            for (AbstractCard c : cardsToUpgrade) {
                c.upgrade();
                c.superFlash();
                c.applyPowers();
            }
        }
        this.isDone = true;
    }
}