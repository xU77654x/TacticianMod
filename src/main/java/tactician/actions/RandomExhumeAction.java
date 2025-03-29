package tactician.actions;

import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import java.util.ArrayList;
import java.util.List;

public class RandomExhumeAction extends AbstractGameAction {
    private AbstractPlayer p;
    private int count;
    private boolean isupgraded;

    public RandomExhumeAction(int count, boolean upgraded) {
        this.count = count;
        this.p = AbstractDungeon.player;
        setValues(this.p, AbstractDungeon.player, this.amount);
        this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_FAST;
        this.isupgraded = upgraded;
    }

    private void exhumeRandom(AbstractCard c) {
        if (this.isupgraded) { c.upgrade(); }
        if (this.p.hand.size() >= BaseMod.MAX_HAND_SIZE) {
            this.p.discardPile.addToTop(c);
        } else { this.p.hand.addToHand(c); }
        c.unfadeOut();
        c.unhover();
        c.fadingOut = false;
    }

    public void update() {
        int cardsReturned = 0;
        if (this.duration == Settings.ACTION_DUR_FAST) {
            if (this.p.exhaustPile.isEmpty()) {
                this.isDone = true;
                return;
            }
            CardGroup cardsToReturn = AbstractDungeon.player.exhaustPile;
            cardsToReturn.shuffle();
            List<AbstractCard> cardsToExhaust = new ArrayList<>();
            for (AbstractCard c : cardsToReturn.group) {
                if (c.type != AbstractCard.CardType.STATUS && c.type != AbstractCard.CardType.CURSE) {
                    exhumeRandom(c);
                    cardsReturned++;
                    cardsToExhaust.add(c);
                    if (cardsReturned == this.count) { break; }
                }
            }
            if (cardsReturned < this.count)
                for (AbstractCard c : cardsToReturn.group) {
                    if (c.type != AbstractCard.CardType.STATUS && c.type != AbstractCard.CardType.CURSE) {
                        exhumeRandom(c);
                        cardsReturned++;
                        cardsToExhaust.add(c);
                        if (cardsReturned == this.count) { break; }
                    }
                }
            for (AbstractCard c : cardsToExhaust) { this.p.exhaustPile.removeCard(c); }
            this.isDone = true;
            return;
        }
        tickDuration();
    }
    // Credit to Downfall: Slimebound for this code.
}
