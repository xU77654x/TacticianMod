package tactician.cards.Basic;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.Lightning;
import tactician.cards.BaseCard;
import tactician.character.MyCharacter;
import tactician.powers.DeflectPower;
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
        setMagic(10, 6);
        setSelfRetain(true);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new DeflectPower(this.magicNumber), this.magicNumber));
        addToBot(new ChannelAction(new Lightning()));
    }

    @Override
    public AbstractCard makeCopy() { return new Solidarity(); }
}