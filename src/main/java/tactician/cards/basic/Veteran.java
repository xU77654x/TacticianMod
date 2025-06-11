package tactician.cards.basic;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tactician.cards.Base9CopyCard;
import tactician.character.MyCharacter;
import tactician.util.CardStats;
import tactician.util.CustomTags;

public class Veteran extends Base9CopyCard {
    public static final String ID = makeID(Veteran.class.getSimpleName());
    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.BASIC,
            CardTarget.SELF,
            1
    );

    public Veteran() {
        super(ID, info);
        tags.add(CustomTags.COPY);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        //TODO: link to the eight/ten Combat Art cards based on your equipped weapon.
    }

    /*
    private void resetCard() {
        this.cardsToPreview = null;
        this.purgeOnUse = false;
        this.name = cardStrings.NAME;
        this.rawDescription = this.upgraded ? cardStrings.UPGRADE_DESCRIPTION : cardStrings.DESCRIPTION;
        this.cost = -2;
        this.costForTurn = -2;
        this.target = TARGET;
        this.type = TYPE;
        this.exhaust = false;
        CardModifierManager.removeAllModifiers(this, true);
        // setCardImage();
        initializeTitle();
        initializeDescription();
        // TODO: This is the code that is supposed to relate to displaying the current weapon's associated Common Combat Art.
    } */

    /*
    private void setCardImage() {
        // loadCardImage(TacticianMod.???);
        // TODO: Set the card to be the shape of an Attack card in combat. Has no effect if you have no weapon type.
    } */

    @Override
    public void onMoveToDiscard() {
        // resetCard();
    }

    @Override
    public AbstractCard makeCopy() { return new Veteran(); }
}