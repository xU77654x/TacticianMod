package tactician.cards.uncommon;

import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tactician.cards.BaseCard;
import tactician.character.MyCharacter;
import tactician.util.CardStats;

public class Miracle extends BaseCard {
    public static final String ID = makeID(Miracle.class.getSimpleName());
    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            1
    );

    public Miracle() {
        super(ID, info);
        setBlock(5, 2);
        setMagic(3, 1);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new SelectCardsInHandAction(this.magicNumber, "Exhaust", true, true, card -> true, exhaustedCard -> {
            for (AbstractCard c : exhaustedCard) {
                addToTop(new GainBlockAction(p, this.block));
                addToTop(new ExhaustSpecificCardAction(c, AbstractDungeon.player.hand));
            }
        }));
    }

    @Override
    public AbstractCard makeCopy() { return new Miracle(); }
}