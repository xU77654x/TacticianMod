package tactician.cards.Uncommon;

import com.megacrit.cardcrawl.actions.unique.CalculatedGambleAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tactician.cards.BaseCard;
import tactician.character.MyCharacter;
import tactician.util.CardStats;

public class Pass extends BaseCard {
    public static final String ID = makeID(Pass.class.getSimpleName());
    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            1
    );

    public Pass() {
        super(ID, info);
        setCostUpgrade(0);
    }

    public void use(AbstractPlayer p, AbstractMonster m) { addToBot(new CalculatedGambleAction(true)); }

    @Override
    public AbstractCard makeCopy() { return new Pass(); }
}