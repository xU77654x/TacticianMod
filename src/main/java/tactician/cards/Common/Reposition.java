package tactician.cards.Common;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.FocusPower;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
// TODO: Import current Energy value.
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import tactician.cards.BaseCard;
import tactician.character.MyCharacter;
import tactician.powers.DeflectPower;
import tactician.util.CardStats;
import tactician.util.CustomTags;

public class Reposition extends BaseCard {
    public static final String ID = makeID(Reposition.class.getSimpleName());
    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.COMMON,
            CardTarget.SELF,
            0
    );

    public Reposition() {
        super(ID, info);
        setBlock(3, 1);
        tags.add(CustomTags.COPY);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int finalBlock = block;
        if (AbstractDungeon.player.hasPower(StrengthPower.POWER_ID))
            finalBlock += AbstractDungeon.player.getPower(StrengthPower.POWER_ID).amount;
        if (AbstractDungeon.player.hasPower(FocusPower.POWER_ID))
            finalBlock += AbstractDungeon.player.getPower(FocusPower.POWER_ID).amount;
        if (AbstractDungeon.player.hasPower(VigorPower.POWER_ID))
            finalBlock += AbstractDungeon.player.getPower(VigorPower.POWER_ID).amount;
        if (AbstractDungeon.player.hasPower(DeflectPower.POWER_ID))
            finalBlock += AbstractDungeon.player.getPower(DeflectPower.POWER_ID).amount;
        if (this.upgraded)
            finalBlock += EnergyPanel.totalCount;
        addToBot(new GainBlockAction(p, p, finalBlock));
    }

    @Override
    public void applyPowers() {
        int realBlock = baseBlock;
        if (AbstractDungeon.player.hasPower(StrengthPower.POWER_ID))
            baseBlock += AbstractDungeon.player.getPower(StrengthPower.POWER_ID).amount;
        if (AbstractDungeon.player.hasPower(FocusPower.POWER_ID))
            baseBlock += AbstractDungeon.player.getPower(FocusPower.POWER_ID).amount;
        if (AbstractDungeon.player.hasPower(VigorPower.POWER_ID))
            baseBlock += AbstractDungeon.player.getPower(VigorPower.POWER_ID).amount;
        if (AbstractDungeon.player.hasPower(DeflectPower.POWER_ID))
            baseBlock += AbstractDungeon.player.getPower(DeflectPower.POWER_ID).amount;
        if (this.upgraded)
            baseBlock += EnergyPanel.totalCount;
        super.applyPowers();
        baseBlock = realBlock;
        this.isBlockModified = (block != baseBlock);
    }

    @Override
    public AbstractCard makeCopy() {return new Reposition();}
}


