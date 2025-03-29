package tactician.cards.Uncommon;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tactician.cards.BaseCard;
import tactician.character.MyCharacter;
import tactician.util.CardStats;

public class CantoControl extends BaseCard {
    public static final String ID = makeID(CantoControl.class.getSimpleName());
    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            -1
    );

    public CantoControl() {
        super(ID, info);
        setMagic(1, 0);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // addToBot(new CantoControlAction(p, p, this.upgraded, this.freeToPlayOnce, this.energyOnUse));
        // addToBot((AbstractGameAction)new GainEnergyAction(-effect));
        // TODO: X-cost. Decide whether to create CantoControlAction or use Downfall's EasyXCostAction.
        // TODO: If upgraded, draw 1 card.
    }

    @Override
    public AbstractCard makeCopy() {
        return new CantoControl();
    }
}