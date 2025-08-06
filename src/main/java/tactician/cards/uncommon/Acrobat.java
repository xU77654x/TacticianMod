package tactician.cards.uncommon;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tactician.actions.EasyXCostAction;
import tactician.cards.TacticianCard;
import tactician.cards.other.Hex;
import tactician.character.TacticianRobin;
import tactician.util.CardStats;

public class Acrobat extends TacticianCard {
    public static final String ID = makeID(Acrobat.class.getSimpleName());
    private static final CardStats info = new CardStats(
            TacticianRobin.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            -1
    );

    public Acrobat() {
        super(ID, info);
        setMagic(0, 1);
        this.cardsToPreview = new Hex();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new SFXAction("tactician:Acrobat"));
        addToBot(new EasyXCostAction(this, (amt, params)->{
            addToTop(new GainEnergyAction(amt + 1));
            return true;
        }));
        if (this.upgraded) { addToBot(new DrawCardAction(this.magicNumber)); }
        addToBot(new MakeTempCardInDrawPileAction(new Hex(), 1, true, true));
    }

    @Override
    public AbstractCard makeCopy() { return new Acrobat(); }
}