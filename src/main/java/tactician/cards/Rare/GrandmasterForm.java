package tactician.cards.Rare;

import basemod.helpers.BaseModCardTags;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tactician.actions.GrandmasterFormAction;
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
            3
    );

    public GrandmasterForm() {
        super(ID, info);
        setCostUpgrade(2);
        tags.add(BaseModCardTags.FORM);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new TalkAction(true, cardStrings.EXTENDED_DESCRIPTION[0], 1.0F, 2.0F));
        addToBot(new GrandmasterFormAction(this.upgraded)); }

    @Override
    public AbstractCard makeCopy() { return new GrandmasterForm(); }
}