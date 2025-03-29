package tactician.cards.Uncommon;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.FocusPower;
import tactician.cards.BaseCard;
import tactician.character.MyCharacter;
import tactician.util.CardStats;

public class FocusUp extends BaseCard {
    public static final String ID = makeID(FocusUp.class.getSimpleName());
    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.POWER,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            1
    );

    public FocusUp(){
        this(0);
        this.name = cardStrings.NAME + " +" + 2;
    }

    public FocusUp(int upgradeCount) {
        super(ID, info);
        this.baseMagicNumber= 2;
        this.magicNumber = this.baseMagicNumber;
        this.timesUpgraded = upgradeCount;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new FocusPower(p, this.magicNumber), this.magicNumber));
    }

    public void upgrade() {
        upgradeMagicNumber(1);
        this.timesUpgraded++;
        this.upgraded = true;
        int upgradeCount = this.timesUpgraded + 2;
        this.name = cardStrings.NAME + " +" + upgradeCount;
        initializeTitle();
    }

    public boolean canUpgrade() {
        return true;
    }

    @Override
    public AbstractCard makeCopy() {
        int upgradeCount = this.timesUpgraded + 2;
        this.name = cardStrings.NAME + " +" + upgradeCount;
        return new FocusUp(this.timesUpgraded);
    }
}

