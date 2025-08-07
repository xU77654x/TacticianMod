package tactician.cards.uncommon;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tactician.actions.PlaySoundAction;
import tactician.cards.TacticianCard;
import tactician.character.TacticianRobin;
import tactician.util.CardStats;
import tactician.util.Wiz;

public class Gamble extends TacticianCard {
    public static final String ID = makeID(Gamble.class.getSimpleName());
    private static final CardStats info = new CardStats(
            TacticianRobin.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.NONE,
            1
    );

    public Gamble() {
        super(ID, info);
        this.exhaust = true;
        setCostUpgrade(0);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {}

    @Override
    public void triggerOnExhaust() {
        addToBot(new PlaySoundAction("tactician:Gamble", 1.10f));
        if (AbstractDungeon.player instanceof TacticianRobin) {
            AbstractCard c = Wiz.randomCombatArt(true);
            addToBot(new MakeTempCardInHandAction(c));
        }
        else {
            AbstractCard c = AbstractDungeon.returnTrulyRandomCardInCombat(AbstractCard.CardType.ATTACK).makeCopy();
            c.setCostForTurn(0);
            addToBot(new MakeTempCardInHandAction(c, true));
        }
    }

    @Override
    public AbstractCard makeCopy() { return new Gamble(); }
}