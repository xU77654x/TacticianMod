package tactician.cards.basic;

import com.megacrit.cardcrawl.actions.common.ExhaustAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tactician.cards.TacticianCard;
import tactician.character.TacticianRobin;
import tactician.util.CardStats;

public class Vulnerary extends TacticianCard {
    public static final String ID = makeID(Vulnerary.class.getSimpleName());
    private static final CardStats info = new CardStats(
            TacticianRobin.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.BASIC,
            CardTarget.SELF,
            0
    );

    public Vulnerary() {
        super(ID, info);
        setMagic(3, 2);
        this.exhaust = true;
        tags.add(CardTags.HEALING);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ExhaustAction(1, false));
        addToBot(new SFXAction("tactician:Vulnerary"));
        addToBot(new HealAction(p, p, this.magicNumber));
    }

    @Override
    public AbstractCard makeCopy() { return new Vulnerary(); }
}