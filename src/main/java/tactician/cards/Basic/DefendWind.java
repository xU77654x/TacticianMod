package tactician.cards.Basic;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tactician.cards.BaseCard;
import tactician.character.MyCharacter;
import tactician.util.CardStats;
import tactician.util.CustomTags;

public class DefendWind extends BaseCard {
    public static final String ID = makeID(DefendWind.class.getSimpleName());
    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.BASIC,
            CardTarget.SELF,
            1
    );

    public DefendWind() {
        super(ID, info);
        setBlock(5, 3);
        setEthereal(false, true);
        tags.add(CardTags.STARTER_DEFEND);
        tags.add(CustomTags.WIND);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {addToBot(new GainBlockAction(p, p, this.block));}

    @Override
    public AbstractCard makeCopy() {
        return new DefendWind();
    }
}