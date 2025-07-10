package tactician.cards.uncommon;

import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tactician.cards.TacticianCard;
import tactician.character.TacticianRobin;
import tactician.util.CardStats;

public class Healtouch extends TacticianCard {
    public static final String ID = makeID(Healtouch.class.getSimpleName());
    private static final CardStats info = new CardStats(
            TacticianRobin.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            1
    );

    public Healtouch() {
        super(ID, info);
        setBlock(5, 2);
        setMagic(3, 0);
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
    public AbstractCard makeCopy() { return new Healtouch(); }
}