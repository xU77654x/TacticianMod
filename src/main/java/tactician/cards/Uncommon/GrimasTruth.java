package tactician.cards.Uncommon;

import com.megacrit.cardcrawl.actions.common.ExhaustAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tactician.actions.HandSelectAction;
import tactician.cards.BaseCard;
import tactician.cards.Other.Expiration;
import tactician.character.MyCharacter;
import tactician.util.CardStats;

public class GrimasTruth extends BaseCard {
    public static final String ID = makeID(GrimasTruth.class.getSimpleName());
    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.POWER,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            0
    );

    public GrimasTruth() {
        super(ID, info);
        this.cardsToPreview = new Expiration();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new HandSelectAction(1, c -> true, list -> {
            for (AbstractCard c : list) {
                if (c.type == AbstractCard.CardType.CURSE) {
                    addToBot(new GainEnergyAction(1));
                }
                p.hand.moveToExhaustPile(c);
            }
            list.clear();
        },
        null, "Exhaust.", false, false, false));
        if (this.upgraded) {
            AbstractCard s = (new Expiration()).makeCopy();
            s.upgrade();
            addToBot(new MakeTempCardInHandAction(this.cardsToPreview.makeStatEquivalentCopy()));
        }
        if (!this.upgraded) { addToBot(new MakeTempCardInHandAction(this.cardsToPreview.makeStatEquivalentCopy())); }
    }

    @Override
    public AbstractCard makeCopy() {
        return new GrimasTruth();
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            Expiration exp = new Expiration();
            exp.upgrade();
            this.cardsToPreview = exp;
            this.rawDescription = this.cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
            // This is required to make the previewed Expiration+ actually show upgraded stats.
        }
    }
}

