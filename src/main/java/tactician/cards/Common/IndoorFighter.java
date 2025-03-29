package tactician.cards.Common;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import tactician.cards.BaseCard;
import tactician.character.MyCharacter;
import tactician.powers.DeflectPower;
import tactician.util.CardStats;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;

public class IndoorFighter extends BaseCard {
    public static final String ID = makeID(IndoorFighter.class.getSimpleName());
    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.COMMON,
            CardTarget.SELF,
            1
    );

    public IndoorFighter() {
        super(ID, info);
        setBlock(7, 2);
        setMagic(4, 2);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (player.currentBlock == 0)
            addToBot(new ApplyPowerAction(p, p, new DeflectPower(magicNumber), magicNumber));
        // TODO: Increase gained Deflect by your Dexterity.
        addToBot(new GainBlockAction(p, p, this.block));
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        magicNumber = baseMagicNumber;
        AbstractPower pow = AbstractDungeon.player.getPower(DexterityPower.POWER_ID);
        if (pow != null) magicNumber += pow.amount;
        isMagicNumberModified = (magicNumber != baseMagicNumber);
        // Credit to linger for the code.
        // "Block and damage are set based on baseDamage and baseBlock when applyPowers/calculateCardDamage is called in AbstractCard.
        // magicNumber has no "calculation", and so it does not do this."
    }

    @Override
    public void calculateCardDamage(AbstractMonster m) {
        int realMagic = baseMagicNumber;
        if (AbstractDungeon.player.hasPower(DexterityPower.POWER_ID))
            baseMagicNumber += AbstractDungeon.player.getPower(DexterityPower.POWER_ID).amount;
        super.calculateCardDamage(m);
        baseMagicNumber = realMagic;
        this.isMagicNumberModified = (magicNumber != baseMagicNumber);
    }

    @Override
    public AbstractCard makeCopy() {
        return new IndoorFighter();
    }
}