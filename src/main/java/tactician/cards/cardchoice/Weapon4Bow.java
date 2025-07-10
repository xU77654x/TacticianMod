package tactician.cards.cardchoice;

import basemod.AutoAdd;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tactician.cards.Tactician4BowCard;
import tactician.character.TacticianRobin;
import tactician.util.CardStats;

@AutoAdd.Ignore
public class Weapon4Bow extends Tactician4BowCard {
    public static final String ID = makeID(Weapon4Bow.class.getSimpleName());
    private static final CardStats info = new CardStats(
            TacticianRobin.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.SPECIAL,
            CardTarget.NONE,
            -2
    );
    private final Runnable onUseOrChosen;

    public Weapon4Bow(Runnable onUseOrChosen) {
        super(ID, info);
        this.onUseOrChosen = onUseOrChosen;
    }

    @Override
    public void onChoseThisOption() { onUseOrChosen.run(); }

    @Override
    public void use(AbstractPlayer p, AbstractMonster M) { onUseOrChosen.run(); }

    @Override
    public AbstractCard makeCopy() { return new Weapon4Bow(onUseOrChosen); }
}