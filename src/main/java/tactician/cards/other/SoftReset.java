package tactician.cards.other;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import tactician.cards.TacticianCard;
import tactician.character.TacticianRobin;
import tactician.util.CardStats;

public class SoftReset extends TacticianCard {
    public static final String ID = makeID(SoftReset.class.getSimpleName());
    private static final CardStats info = new CardStats(
            TacticianRobin.Meta.CARD_COLOR,
            CardType.CURSE,
            CardRarity.CURSE,
            CardTarget.SELF,
            0
    );

    public SoftReset() {
        super(ID, info);
        setSelfRetain(true);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new LoseHPAction(p, p, 2147483647));
    }

    @Override
    public AbstractCard makeCopy() {
        return new SoftReset();
    }
}