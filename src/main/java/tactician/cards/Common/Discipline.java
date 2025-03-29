package tactician.cards.Common;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tactician.cards.BaseCard;
import tactician.character.MyCharacter;
import tactician.util.CardStats;

public class Discipline extends BaseCard {
    public static final String ID = makeID(Discipline.class.getSimpleName());
    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.COMMON,
            CardTarget.SELF,
            1
    );

    public Discipline() {
        super(ID, info);
        setBlock(7, 3);
        setMagic(1, 0);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, p, this.block));
        addToBot(new DrawCardAction(p, this.magicNumber)); // Draw 1. Temporary.
        // TODO: Upgrade behaves like Seek.
        // TODO: Drawn and previewed cards are limited to the tag of your weapon type, as well as Copy Weapon.
    }

    @Override
    public AbstractCard makeCopy() { return new Discipline(); }
}

