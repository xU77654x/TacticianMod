package tactician.zzzdeprecated;

/*
public class VulneraryRetain extends BaseCard {
    public static final String ID = makeID(VulneraryRetain.class.getSimpleName());
    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.BASIC,
            CardTarget.SELF,
            0
    );

    public VulneraryRetain() {
        super(ID, info);
        setMagic(3, 2);
        setCustomVar("retainCards", 1, 1);
        this.exhaust = true;
        tags.add(CardTags.HEALING);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new HealAction(p, p, this.magicNumber));

        addToBot(new SelectCardsInHandAction(customVar("retainCards"), "add Retain to for the rest of combat.", cards -> {
            for (AbstractCard c : cards) {
                addToBot(new AbstractGameAction() {
                    public void update() {
                        this.isDone = true;
                        CardModifierManager.addModifier(c, new RetainMod());
                        c.superFlash();
                    }
                });
            }
        }));
    }

    @Override
    public AbstractCard makeCopy() { return new VulneraryRetain(); }
} */