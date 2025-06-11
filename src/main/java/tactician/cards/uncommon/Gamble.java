package tactician.cards.uncommon;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tactician.cards.BaseCard;
import tactician.character.MyCharacter;
import tactician.util.CardStats;
import tactician.util.Wiz;

public class Gamble extends BaseCard {
    public static final String ID = makeID(Gamble.class.getSimpleName());
    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.NONE,
            1
    );

    public Gamble() {
        super(ID, info);
        this.exhaust = true;
        setCostUpgrade(0);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {}

    @Override
    public void triggerOnExhaust() {
        AbstractCard c = Wiz.randomCombatArt(true);
        addToBot(new MakeTempCardInHandAction(c));
    }

    @Override
    public AbstractCard makeCopy() {
        return new Gamble();
    }
}