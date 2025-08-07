package tactician.cards.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import tactician.actions.PlaySoundAction;
import tactician.cards.Tactician4BowCard;
import tactician.character.TacticianRobin;
import tactician.powers.DeflectPower;
import tactician.powers.weapons.Weapon4BowPower;
import tactician.util.CardStats;
import tactician.util.CustomTags;
import tactician.util.Wiz;

public class CurvedShot extends Tactician4BowCard {
    public static final String ID = makeID(CurvedShot.class.getSimpleName());
    private static final CardStats info = new CardStats(
            TacticianRobin.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.COMMON,
            CardTarget.ENEMY,
            1
    );

    public CurvedShot() {
        super(ID, info);
        setDamage(6, 3);
        setBlock(3, 0);
        setMagic(0, 0);
        tags.add(CustomTags.BOW);
        tags.add(CustomTags.COMBAT_ART);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (AbstractDungeon.player instanceof TacticianRobin && !p.hasPower(Weapon4BowPower.POWER_ID)) { addToBot(new ApplyPowerAction(p, p, new Weapon4BowPower(p))); }
        calculateCardDamage(m);
        addToTop(new PlaySoundAction("tactician:CurvedShot", 1.25f));
        addToBot(new GainBlockAction(p, this.block + this.magicNumber));
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
        int realDamage = baseDamage;
        int realBlock = baseBlock;
        int realMagic = baseMagicNumber;
        baseDamage += Wiz.playerWeaponCalc(m, 4);
        baseBlock += Wiz.playerWeaponCalc(m, 4);
        if (AbstractDungeon.player.hasPower(DeflectPower.POWER_ID))
            baseMagicNumber += AbstractDungeon.player.getPower(DeflectPower.POWER_ID).amount;
        super.calculateCardDamage(m);
        baseDamage = realDamage;
        baseBlock = realBlock;
        baseMagicNumber = realMagic;
        this.isDamageModified = (damage != baseDamage);
        this.isBlockModified = (block != baseBlock);
        this.isMagicNumberModified = (magicNumber != baseMagicNumber);
    }

    @Override
    public AbstractCard makeCopy() { return new CurvedShot(); }
}