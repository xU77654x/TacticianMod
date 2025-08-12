package tactician.cards.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tactician.actions.PlaySoundAction;
import tactician.cards.Tactician7ThunderCard;
import tactician.character.TacticianRobin;
import tactician.effects.PlayVoiceEffect;
import tactician.powers.DeflectPower;
import tactician.powers.weapons.Weapon7ThunderPower;
import tactician.util.CardStats;
import tactician.util.CustomTags;
import tactician.util.Wiz;

public class Thunder extends Tactician7ThunderCard {
    public static final String ID = makeID(Thunder.class.getSimpleName());
    private static final CardStats info = new CardStats(
            TacticianRobin.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.COMMON,
            CardTarget.ENEMY,
            1
    );


    public Thunder() {
        super(ID, info);
        setDamage(6, 1);
        setMagic(2, 1);
        tags.add(CustomTags.THUNDER);
        tags.add(CustomTags.COMBAT_ART);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (AbstractDungeon.player instanceof TacticianRobin && !p.hasPower(Weapon7ThunderPower.POWER_ID)) { addToBot(new ApplyPowerAction(p, p, new Weapon7ThunderPower(p))); }
        calculateCardDamage(m);
        addToTop(new PlaySoundAction("tactician:Thunder", 1.25f));
        AbstractDungeon.effectList.add(new PlayVoiceEffect("Thunder"));
        addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.LIGHTNING));
        if (AbstractDungeon.player.hasPower(DeflectPower.POWER_ID) && (AbstractDungeon.player.getPower(DeflectPower.POWER_ID).amount >= 4)) {
            addToBot(new ReducePowerAction(p, p, AbstractDungeon.player.getPower(DeflectPower.POWER_ID), 4));
            addToBot(new ApplyPowerAction(m, p, new VulnerablePower(m, this.magicNumber, false), this.magicNumber));
        }
    }

    @Override
    public void calculateCardDamage(AbstractMonster m) {
        int realDamage = baseDamage;
        baseDamage += Wiz.playerWeaponCalc(m, 7);
        super.calculateCardDamage(m);
        baseDamage = realDamage;
        this.isDamageModified = (damage != baseDamage);
    }

    @Override
    public AbstractCard makeCopy() { return new Thunder(); }
}