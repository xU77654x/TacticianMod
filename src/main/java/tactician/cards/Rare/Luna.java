package tactician.cards.Rare;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DoubleTapPower; // Testing only.
import tactician.cards.BaseCard;
import tactician.character.MyCharacter;
import tactician.powers.LunaPower;
import tactician.util.CardStats;

public class Luna extends BaseCard {
    public static final String ID = makeID(Luna.class.getSimpleName());
    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.RARE,
            CardTarget.SELF,
            1
    );

    public Luna() {
        super(ID, info);
        setMagic(1, 0);
        setCostUpgrade(0);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) { addToBot(new ApplyPowerAction(p, p, new LunaPower(this.magicNumber), this.magicNumber)); }

    @Override
    public AbstractCard makeCopy() {
        return new Luna();
    }
}