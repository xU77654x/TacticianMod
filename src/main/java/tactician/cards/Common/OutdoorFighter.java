package tactician.cards.Common;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import tactician.actions.DrawAndLogCardsAction;
import tactician.actions.OutdoorGainAction;
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
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // addToBot(new DrawCardAction(this.magicNumber, new OutdoorFighterAction(this.block, customVar("magicVigor"))));

        addToBot(new DrawAndLogCardsAction(p, customVar("magicDraw")));
        addToBot(new WaitAction(0.3F));
        addToBot(new OutdoorGainAction(this, this.block, this.magicNumber));
    }

    public void gainBlock(int blockGain) {
        AbstractPlayer p = AbstractDungeon.player;
        this.baseBlock = this.block = blockGain;
        applyPowers();
        AbstractDungeon.actionManager.addToTop(new GainBlockAction(p, p, this.block));
        this.baseBlock = 0;
    }

    public void gainVigor(int vigorGain) {
        AbstractPlayer p = AbstractDungeon.player;
        int realMagic = baseMagicNumber;
        baseMagicNumber += this.magicNumber = vigorGain;
        applyPowers();
        AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(p, p, new VigorPower(p, this.magicNumber), this.magicNumber));
        baseMagicNumber = realMagic;
    }

    @Override
    public AbstractCard makeCopy() {
        return new OutdoorFighter();
    }
}