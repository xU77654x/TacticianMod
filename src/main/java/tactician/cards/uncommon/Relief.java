package tactician.cards.uncommon;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tactician.cards.Tactician9CopyCard;
import tactician.character.TacticianRobin;
import tactician.util.CardStats;
import tactician.util.CustomTags;
import tactician.util.Wiz;

public class Relief extends Tactician9CopyCard {
    public static final String ID = makeID(Relief.class.getSimpleName());
    private static final CardStats info = new CardStats(
            TacticianRobin.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.ENEMY,
            1
    );
    public Relief() {
        super(ID, info);
        setBlock(6, 2);
        tags.add(CustomTags.COPY);
        this.exhaust = true;
        // this.cardsToPreview = (AbstractCard)new Hex(); // THIS CAUSES A CRASH AT LAUNCH
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        calculateCardDamage(m);
        addToBot(new SFXAction("tactician:Relief"));
        addToBot(new GainBlockAction(p, p, this.block));
    }

    @Override
    public void triggerOnExhaust() {
        if (!this.freeToPlayOnce) {
            Relief makeCard = new Relief();
            if (this.upgraded) { makeCard.upgrade(); }
            addToBot((new MakeTempCardInHandAction(makeCard)));
        }
        // Credit to The Forsaken: Precise Strike for this code.
    }

    @Override
    public void calculateCardDamage(AbstractMonster m) {
        int realBlock = baseBlock;
        baseBlock += Wiz.playerWeaponCalc(m, 9);
        super.calculateCardDamage(m);
        baseBlock = realBlock;
        this.isBlockModified = (block != baseBlock);
    }

    @Override
    public AbstractCard makeCopy() { return new Relief(); }
}