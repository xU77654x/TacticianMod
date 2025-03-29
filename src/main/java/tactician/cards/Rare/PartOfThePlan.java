package tactician.cards.Rare;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ExhaustAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.BarricadePower;
import tactician.cards.BaseCard;
import tactician.character.MyCharacter;
import tactician.util.CardStats;

public class PartOfThePlan extends BaseCard {
    public static final String ID = makeID(PartOfThePlan.class.getSimpleName());
    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.POWER,
            CardRarity.RARE,
            CardTarget.ALL_ENEMY,
            2
    );

    public PartOfThePlan() {
        super(ID, info);
        setMagic(2, 0);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (!this.upgraded) { addToBot(new ExhaustAction(2, true, false, false)); }
        if (this.upgraded) { addToBot(new ExhaustAction(2, false)); }
        addToBot(new ApplyPowerAction(p, p, new BarricadePower(p)));
        for (AbstractMonster mo : (AbstractDungeon.getCurrRoom()).monsters.monsters) { addToBot(new ApplyPowerAction(mo, p, new BarricadePower(p))); }
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        if (!this.upgraded && !AbstractDungeon.actionManager.cardsPlayedThisTurn.isEmpty()) {
            this.cantUseMessage = "I have already played cards on this turn!";
            return false;
        }
        return super.canUse(p, m);
    }

    @Override
    public AbstractCard makeCopy() { return new PartOfThePlan(); }
}

