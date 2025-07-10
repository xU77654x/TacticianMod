package tactician.cards.common;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ExhaustAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tactician.cards.TacticianCard;
import tactician.character.TacticianRobin;
import tactician.powers.DeflectPower;
import tactician.util.CardStats;

public class IndoorFighter extends TacticianCard {
    public static final String ID = makeID(IndoorFighter.class.getSimpleName());
    private static final CardStats info = new CardStats(
            TacticianRobin.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.COMMON,
            CardTarget.SELF,
            1
    );

    public IndoorFighter() {
        super(ID, info);
        setMagic(9, 3);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ExhaustAction(1, false));
        addToBot(new ApplyPowerAction(p, p, new DeflectPower(this.magicNumber), this.magicNumber));

        /* Discard a card:
        addToBot(new HandSelectAction(3, c -> true, list -> {
        },null, "Discard.", false, true, true));
         */
    }

    @Override
    public AbstractCard makeCopy() { return new IndoorFighter(); }
}