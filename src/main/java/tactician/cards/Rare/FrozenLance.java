package tactician.cards.Rare;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.FocusPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.vfx.combat.BlizzardEffect;
import tactician.cards.BaseCard;
import tactician.character.MyCharacter;
import tactician.util.CardStats;
import tactician.util.CustomTags;

public class FrozenLance extends BaseCard {
    public static final String ID = makeID(FrozenLance.class.getSimpleName());
    AbstractPlayer p;
    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.RARE,
            CardTarget.ENEMY,
            3
    );

    public FrozenLance() {
        super(ID, info);
        setDamage(22, 0);
        setMagic(3, 2);
        this.p = AbstractDungeon.player;
        tags.add(CustomTags.LANCE);
        tags.add(CustomTags.COMBAT_ART);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new VFXAction(new BlizzardEffect((damage / 5), AbstractDungeon.getMonsters().shouldFlipVfx()), 0.2F));
        addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
    }

    @Override
    public void applyPowers() {
        AbstractPower strength = AbstractDungeon.player.getPower(StrengthPower.POWER_ID);
        if (strength != null)  { strength.amount *= this.magicNumber; }
        int focus = 0;
        if (this.p.hasPower(FocusPower.POWER_ID)) { focus = (this.p.getPower(FocusPower.POWER_ID)).amount; }
        int realBaseDamage = this.baseDamage;
        this.baseDamage += (this.magicNumber * focus);
        super.applyPowers();
        if (strength != null) { strength.amount /= this.magicNumber; }
        this.baseDamage = realBaseDamage;
        this.isDamageModified = (this.damage != this.baseDamage);
    }

    public void calculateCardDamage(AbstractMonster m) {
        AbstractPower strength = AbstractDungeon.player.getPower(StrengthPower.POWER_ID);
        if (strength != null)  { strength.amount *= this.magicNumber; }
        int focus = 0;
        if (this.p.hasPower(FocusPower.POWER_ID)) { focus = (this.p.getPower(FocusPower.POWER_ID)).amount; }
        int realBaseDamage = this.baseDamage;
        this.baseDamage += (this.magicNumber * focus);
        super.calculateCardDamage(m);
        if (strength != null) { strength.amount /= this.magicNumber; }
        this.baseDamage = realBaseDamage;
        this.isDamageModified = (this.damage != this.baseDamage);
    }

    @Override
    public AbstractCard makeCopy() { return new FrozenLance(); }
}