package tactician.cards.cardchoice;

import basemod.AutoAdd;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tactician.cards.Base8DarkCard;
import tactician.cards.BaseCard;
import tactician.character.MyCharacter;
import tactician.util.CardStats;

@AutoAdd.Ignore
public class Weapon8Dark extends Base8DarkCard {
    public static final String ID = makeID(Weapon8Dark.class.getSimpleName());
    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.SPECIAL,
            CardTarget.NONE,
            -2
    );
    private Runnable onUseOrChosen;

    public Weapon8Dark(Runnable onUseOrChosen) {
        super(ID, info);
        this.onUseOrChosen = onUseOrChosen;
    }

    @Override
    public void onChoseThisOption() { onUseOrChosen.run(); }

    @Override
    public void use(AbstractPlayer p, AbstractMonster M) { onUseOrChosen.run(); }

    @Override
    public AbstractCard makeCopy() { return new Weapon8Dark(onUseOrChosen); }
}