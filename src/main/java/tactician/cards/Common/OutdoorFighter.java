package tactician.cards.Common;

import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.mod.stslib.patches.FlavorText;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tactician.actions.OutdoorFollowUpAction;
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
        setMagic(2, 0);
        setCustomVar("magicDraw", 2, 1);
        FlavorText.AbstractCardFlavorFields.boxColor.set(this, Color.PURPLE.cpy());
        FlavorText.AbstractCardFlavorFields.textColor.set(this, Color.WHITE.cpy());
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) { addToBot(new DrawCardAction(customVar("magicDraw"), (new OutdoorFollowUpAction(customVar("magicDraw"), this.block, this.magicNumber)))); }

    @Override
    public AbstractCard makeCopy() { return new OutdoorFighter(); }
}