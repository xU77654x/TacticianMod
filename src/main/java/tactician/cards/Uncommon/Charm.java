package tactician.cards.Uncommon;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tactician.cards.BaseCard;
import tactician.character.MyCharacter;
import tactician.util.CardStats;

public class Charm extends BaseCard {
    public static final String ID = makeID(Charm.class.getSimpleName());
    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            1
    );

    public Charm() {
        super(ID, info);
        setBlock(4, 2);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, p, this.block));
        addToBot(new GainBlockAction(p, p, this.block));
    }

    @Override
    public void triggerOnExhaust() {
        addToTop(new GainEnergyAction(1));
        Charm c = new Charm();
        addToTop(new MakeTempCardInDrawPileAction(c, 1, true, true));
    }

    @Override
    public AbstractCard makeCopy() { return new Charm(); }
}