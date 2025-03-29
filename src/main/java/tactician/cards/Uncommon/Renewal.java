package tactician.cards.Uncommon;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tactician.cards.BaseCard;
import tactician.character.MyCharacter;
import tactician.util.CardStats;

public class Renewal extends BaseCard {
    public static final String ID = makeID(Renewal.class.getSimpleName());
    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            -1
    );

    public Renewal() {
        super(ID, info);
        setMagic(2, 1);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // addToBot(new DesperationAction(p, p, this.upgraded, this.freeToPlayOnce, this.energyOnUse));
        // TODO: Reduce debuffs. See Shadow Siren: Trick for the card choice code.
        // TODO: X-cost. Decide whether to create DesperationAction or use Downfall's EasyXCostAction.
        // TODO: On Exhaust, play your deck's Strikes and Defends (even if not in hand) and exhaust them.
    }

    @Override
    public AbstractCard makeCopy() {
        return new Renewal();
    }
}

