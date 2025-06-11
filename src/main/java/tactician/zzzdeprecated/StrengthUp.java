package tactician.zzzdeprecated;

/*
public class StrengthUp extends BaseCard {
    public static final String ID = makeID(StrengthUp.class.getSimpleName());
    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.POWER,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            1
    );

    public StrengthUp(){
        this(0);
        int upgradeCount = this.timesUpgraded + 2;
        this.name = cardStrings.EXTENDED_DESCRIPTION[0] + upgradeCount;
        cardStrings.NAME = this.name;
    }

    public StrengthUp(int upgradeCount) {
        super(ID, info);
        this.baseMagicNumber= 2;
        this.magicNumber = this.baseMagicNumber;
        this.timesUpgraded = upgradeCount;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, this.magicNumber), this.magicNumber));
    }

    public void upgrade() {
        upgradeMagicNumber(1);
        this.timesUpgraded++;
        this.upgraded = true;
        int upgradeCount = this.timesUpgraded + 2;
        this.name = cardStrings.EXTENDED_DESCRIPTION[0] + upgradeCount;
        initializeTitle();
    }

    public boolean canUpgrade() {
        return true;
    }

    @Override
    public AbstractCard makeCopy() {
        int upgradeCount = this.timesUpgraded + 2;
        this.name = cardStrings.EXTENDED_DESCRIPTION[0] + upgradeCount;
        return new StrengthUp(this.timesUpgraded);
    }

    // "EXTENDED_DESCRIPTION": ["Strength +"]
} */