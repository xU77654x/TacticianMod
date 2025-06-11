package tactician.cards.rare;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.BarricadePower;
import tactician.cards.Base7ThunderCard;
import tactician.cards.BaseCard;
import tactician.character.MyCharacter;
import tactician.powers.weapons.Weapon7ThunderPower;
import tactician.util.CardStats;
import tactician.util.CustomTags;
import tactician.util.Wiz;

public class Thoron extends Base7ThunderCard {
    public static final String ID = makeID(Thoron.class.getSimpleName());
    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.RARE,
            CardTarget.ALL_ENEMY,
            2
    );

    public Thoron() {
        super(ID, info);
        setDamage(10, 4);
        tags.add(CustomTags.THUNDER);
        tags.add(CustomTags.COMBAT_ART);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new TalkAction(true, cardStrings.EXTENDED_DESCRIPTION[0], 1.0F, 2.0F));
        if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            for (AbstractMonster mo : (AbstractDungeon.getMonsters()).monsters) {
                calculateCardDamage(mo);
                addToBot(new DamageAction(mo, new DamageInfo(p, damage, DamageInfo.DamageType.HP_LOSS), AbstractGameAction.AttackEffect.SMASH));
                addToBot(new DamageAction(mo, new DamageInfo(p, damage, DamageInfo.DamageType.HP_LOSS), AbstractGameAction.AttackEffect.SMASH));
                if (this.upgraded) { addToBot(new RemoveSpecificPowerAction(mo, mo, BarricadePower.POWER_ID)); }
            }
        }
        if (!p.hasPower(Weapon7ThunderPower.POWER_ID)) { addToBot(new ApplyPowerAction(p, p, new Weapon7ThunderPower(p))); }
    }

    @Override
    public void calculateCardDamage(AbstractMonster m) {
        if (m != null) {
            int realDamage = baseDamage;
            baseDamage += Wiz.playerWeaponCalc(m, 7);
            super.calculateCardDamage(m);
            baseDamage = realDamage;
            this.isDamageModified = (damage != baseDamage);
        }
    }

    @Override
    public AbstractCard makeCopy() { return new Thoron(); }
}