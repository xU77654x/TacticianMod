package tactician.cards.basic;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tactician.cards.BaseCard;
import tactician.cards.other.Hex;
import tactician.character.MyCharacter;
import tactician.powers.DeflectPower;
import tactician.powers.NextTurnDeflectPower;
import tactician.util.CardStats;

public class Solidarity extends BaseCard {
    public static final String ID = makeID(Solidarity.class.getSimpleName());
    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.BASIC,
            CardTarget.SELF,
            2
    );

    public Solidarity() {
        super(ID, info);
        setMagic(6, 2);
        setSelfRetain(true);
        this.cardsToPreview = new Hex();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new DeflectPower(this.magicNumber), this.magicNumber));
        addToBot(new ApplyPowerAction(p, p, new NextTurnDeflectPower(this.magicNumber), this.magicNumber));
        addToBot(new MakeTempCardInHandAction(new Hex(), 1));
    }

    @Override
    public AbstractCard makeCopy() { return new Solidarity(); }
}