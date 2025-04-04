package tactician.cards.Rare;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.BrutalityPower; // For testing purposes.
import tactician.cards.BaseCard;
import tactician.character.MyCharacter;
import tactician.util.CardStats;

public class Acrobat extends BaseCard {
    public static final String ID = makeID(Acrobat.class.getSimpleName());
    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.POWER,
            CardRarity.RARE,
            CardTarget.SELF,
            1
    );

    public Acrobat() {
        super(ID, info);
        setMagic(2, 1);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new BrutalityPower(p, this.magicNumber), this.magicNumber));
        // TODO: AcrobatPower. Also, all Combat Art cards must be tagged.
    }

    @Override
    public AbstractCard makeCopy() {
        return new Acrobat();
    }
}

