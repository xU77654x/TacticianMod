package tactician.cards.Common;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tactician.actions.OutdoorFighterAction;
import tactician.cards.BaseCard;
import tactician.character.MyCharacter;
import tactician.util.CardStats;

public class OutdoorFighter extends BaseCard {
    public static final String ID = makeID(OutdoorFighter.class.getSimpleName());
    private static final CardStats info = new CardStats(
        MyCharacter.Meta.CARD_COLOR,
        CardType.SKILL,
        CardRarity.COMMON,
        CardTarget.SELF,
        1
);

    public OutdoorFighter() {
        super(ID, info);
        setBlock(2, 0);
        setMagic(2, 2);
        setCustomVar("magicVigor", 2, 0);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) { addToBot(new DrawCardAction(this.magicNumber, new OutdoorFighterAction(this.block, customVar("magicVigor")))); }

    @Override
    public AbstractCard makeCopy() {
        return new OutdoorFighter();
    }
}