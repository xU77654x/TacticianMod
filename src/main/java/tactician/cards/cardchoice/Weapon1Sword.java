package tactician.cards.cardchoice;

import basemod.AutoAdd;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tactician.cards.Tactician1SwordCard;
import tactician.character.TacticianRobin;
import tactician.util.CardStats;

@AutoAdd.Ignore
public class Weapon1Sword extends Tactician1SwordCard {
    public static final String ID = makeID(Weapon1Sword.class.getSimpleName());
    private static final CardStats info = new CardStats(
            TacticianRobin.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.SPECIAL,
            CardTarget.NONE,
            -2
    );
    private final Runnable onUseOrChosen;

    public Weapon1Sword(Runnable onUseOrChosen) {
        super(ID, info);
        this.onUseOrChosen = onUseOrChosen;
    }

    @Override
    public void onChoseThisOption() { onUseOrChosen.run(); }

    @Override
    public void use(AbstractPlayer p, AbstractMonster M) { onUseOrChosen.run(); }

    @Override
    public AbstractCard makeCopy() { return new Weapon1Sword(onUseOrChosen); }
}