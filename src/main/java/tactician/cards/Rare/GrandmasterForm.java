package tactician.cards.Rare;

import basemod.helpers.BaseModCardTags;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tactician.cards.BaseCard;
import tactician.character.MyCharacter;
import tactician.util.CardStats;

public class GrandmasterForm extends BaseCard {
    public static final String ID = makeID(GrandmasterForm.class.getSimpleName());
    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.POWER,
            CardRarity.RARE,
            CardTarget.SELF,
            2
    );

    public GrandmasterForm() {
        super(ID, info);
        tags.add(BaseModCardTags.FORM);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // TODO: Apply GrandmasterFormAction, mirroring FiendFireAction but granting Combat Arts (upgraded if upgraded) and Energy per card.
    }

    @Override
    public AbstractCard makeCopy() {
        return new GrandmasterForm();
    }
}

