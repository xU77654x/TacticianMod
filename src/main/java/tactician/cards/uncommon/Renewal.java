package tactician.cards.uncommon;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tactician.actions.EasyXCostAction;
import tactician.actions.PlaySoundAction;
import tactician.actions.ReduceDebuffsAction;
import tactician.cards.TacticianCard;
import tactician.cards.other.Anathema;
import tactician.character.TacticianRobin;
import tactician.util.CardStats;

public class Renewal extends TacticianCard {
    public static final String ID = makeID(Renewal.class.getSimpleName());
    private static final CardStats info = new CardStats(
            TacticianRobin.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            -1
    );

    public Renewal() {
        super(ID, info);
        setMagic(0, 1);
        this.cardsToPreview = new Anathema();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new PlaySoundAction("tactician:Renewal", 1.05f));
        addToBot(new EasyXCostAction(this, (amt, params)->{
            addToTop(new ReduceDebuffsAction(p, amt + this.magicNumber));
            return true;
        }));
        addToBot(new MakeTempCardInDrawPileAction(new Anathema(), 1, true, true));
    }

    @Override
    public AbstractCard makeCopy() {
        return new Renewal();
    }
}