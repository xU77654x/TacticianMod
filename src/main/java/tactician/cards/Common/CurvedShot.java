package tactician.cards.Common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import tactician.cards.BaseCard;
import tactician.character.MyCharacter;
import tactician.powers.DeflectPower;
import tactician.util.CardStats;
import tactician.util.CustomTags;

public class CurvedShot extends BaseCard {
    public static final String ID = makeID(CurvedShot.class.getSimpleName());
    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.COMMON,
            CardTarget.ENEMY,
            0
    );

    public CurvedShot() {
        super(ID, info);
        setDamage(5, 3);
        setMagic(0, 0);
        tags.add(CustomTags.BOW);
        tags.add(CustomTags.COMBAT_ART);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
        if (AbstractDungeon.player.hasPower(DeflectPower.POWER_ID)) {
            addToBot(new ApplyPowerAction(p, p, new VigorPower(p, this.magicNumber), this.magicNumber));
            addToBot(new ApplyPowerAction(p, p, new DeflectPower(-this.magicNumber), -this.magicNumber));
            addToBot(new RemoveSpecificPowerAction(p, p, DeflectPower.POWER_ID));
        }
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        magicNumber = baseMagicNumber;
        AbstractPower pow = AbstractDungeon.player.getPower(DeflectPower.POWER_ID);
        if (pow != null) magicNumber += pow.amount;
        isMagicNumberModified = (magicNumber != baseMagicNumber);
        // Credit to linger for the code.
        // "Block and damage are set based on baseDamage and baseBlock when applyPowers/calculateCardDamage is called in AbstractCard.
        // magicNumber has no "calculation", and so it does not do this."
    }

    @Override
    public void calculateCardDamage(AbstractMonster m) {
        int realMagic = baseMagicNumber;
        if (AbstractDungeon.player.hasPower(DeflectPower.POWER_ID))
            baseMagicNumber += AbstractDungeon.player.getPower(DeflectPower.POWER_ID).amount;
        super.calculateCardDamage(m);
        baseMagicNumber = realMagic;
        this.isMagicNumberModified = (magicNumber != baseMagicNumber);
    }

    @Override
    public AbstractCard makeCopy() {
        return new CurvedShot();
    }
}