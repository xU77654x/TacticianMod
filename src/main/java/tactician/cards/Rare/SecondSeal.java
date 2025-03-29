package tactician.cards.Rare;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tactician.cards.BaseCard;
import tactician.character.MyCharacter;
import tactician.util.CardStats;

public class SecondSeal extends BaseCard {
    public static final String ID = makeID(SecondSeal.class.getSimpleName());
    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.RARE,
            CardTarget.SELF,
            0
    );

    public SecondSeal() {
        super(ID, info);
        setMagic(1, 0);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // TODO: Generate the off-color card choice, and remove a given color once it is selected for the rest of the run. Resets if a new run is started.
        // TODO: The generated card is upgraded for the rest of combat if this card is upgraded.
    }

    @Override
    public AbstractCard makeCopy() {
        return new SecondSeal();
    }
}

