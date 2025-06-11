package tactician.cards.common;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;
import tactician.cards.BaseCard;
import tactician.character.MyCharacter;
import tactician.util.CardStats;

public class Zeal extends BaseCard {
    public static final String ID = makeID(Zeal.class.getSimpleName());
    AbstractPlayer p;
    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.COMMON,
            CardTarget.SELF,
            1
    );

    public Zeal() {
        super(ID, info);
        setMagic(1, 0);
        setCostUpgrade(0);
        this.selfRetain = true;
        this.p = AbstractDungeon.player;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new FeelNoPainPower(p, magicNumber), magicNumber));
        // TODO: ZealPower

        // Unused code on how to cure a stat completely.
        // if (this.p.hasPower(StrengthPower.POWER_ID) && (this.p.getPower(StrengthPower.POWER_ID)).amount < 0) { this.p.powers.remove(this.p.getPower(StrengthPower.POWER_ID)); }
    }

    @Override
    public AbstractCard makeCopy() { return new Zeal(); }
}