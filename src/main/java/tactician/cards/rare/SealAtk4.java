package tactician.cards.rare;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import tactician.cards.TacticianCard;
import tactician.character.TacticianRobin;
import tactician.util.CardStats;

public class SealAtk4 extends TacticianCard {
    public static final String ID = makeID(SealAtk4.class.getSimpleName());
    private static final CardStats info = new CardStats(
            TacticianRobin.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.RARE,
            CardTarget.ENEMY,
            1
    );
    public SealAtk4() {
        super(ID, info);
        setMagic(4, 0);
        setExhaust(true);
        setCostUpgrade(0);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(m, p, new StrengthPower(m, -this.magicNumber), -this.magicNumber));
        addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, -1), -1));
    }

    @Override
    public AbstractCard makeCopy() { return new SealAtk4(); }
}