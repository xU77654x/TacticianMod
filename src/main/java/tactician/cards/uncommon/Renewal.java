package tactician.cards.uncommon;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.FrailPower;
import tactician.actions.EasyXCostAction;
import tactician.actions.ReduceDebuffsAction;
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
        setMagic(0, 1);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new EasyXCostAction(this, (amt, params)->{
            addToTop(new ReduceDebuffsAction(p, amt + this.magicNumber));
            return true;
        }));
    }

    @Override
    public void triggerOnExhaust() {
        addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new FrailPower(AbstractDungeon.player, 1, false), 1));
    }

    @Override
    public AbstractCard makeCopy() {
        return new Renewal();
    }
}