package tactician.cards.uncommon;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tactician.cards.BaseCard;
import tactician.character.MyCharacter;
import tactician.util.CardStats;
import tactician.util.CustomTags;

public class Hex extends BaseCard {
    public static final String ID = makeID(Hex.class.getSimpleName());
    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            1
    );
    public Hex() {
        super(ID, info);
        setBlock(6, 2);
        tags.add(CustomTags.COPY);
        this.exhaust = true;
        // this.cardsToPreview = (AbstractCard)new Hex(); // THIS CAUSES A CRASH AT LAUNCH
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, p, this.block));
    }

    @Override
    public void triggerOnExhaust() {
        if (!this.freeToPlayOnce) {
            Hex makeCard = new Hex();
            if (this.upgraded)
                makeCard.upgrade();
            addToBot((new MakeTempCardInHandAction(makeCard)));
        }
        // Credit to The Forsaken: Precise Strike for this code.
    }

    @Override
    public AbstractCard makeCopy() {
        return new Hex();
    }
}