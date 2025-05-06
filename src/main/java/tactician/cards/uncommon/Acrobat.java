package tactician.cards.uncommon;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tactician.cards.BaseCard;
import tactician.character.MyCharacter;
import tactician.powers.AcrobatPower;
import tactician.util.CardStats;

public class Acrobat extends BaseCard {
    public static final String ID = makeID(Acrobat.class.getSimpleName());
    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.POWER,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            1
    );

    public Acrobat() {
        super(ID, info);
        setMagic(3, 2);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) { addToBot(new ApplyPowerAction(p, p, new AcrobatPower(this.magicNumber), this.magicNumber)); }

    @Override
    public AbstractCard makeCopy() {
        return new Acrobat();
    }
}