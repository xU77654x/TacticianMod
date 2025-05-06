package tactician.cards.rare;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tactician.cards.BaseCard;
import tactician.character.MyCharacter;
import tactician.powers.DeflectPower;
import tactician.powers.weaponscurrent.Weapon6FirePower;
import tactician.util.CardStats;
import tactician.util.CustomTags;
import tactician.util.Wiz;

public class Bolganone extends BaseCard {
    public static final String ID = makeID(Bolganone.class.getSimpleName());
    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.RARE,
            CardTarget.ENEMY,
            2
    );

    public Bolganone() {
        super(ID, info);
        setDamage(10, 1);
        setMagic(2, 1);
        tags.add(CustomTags.FIRE);
        tags.add(CustomTags.COMBAT_ART);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new Weapon6FirePower(p)));
        calculateCardDamage(m);
        addToBot(new TalkAction(true, cardStrings.EXTENDED_DESCRIPTION[0], 1.0F, 2.0F));
        addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.FIRE));
        if (AbstractDungeon.player.hasPower(DeflectPower.POWER_ID)) {
            int deflect = AbstractDungeon.player.getPower(DeflectPower.POWER_ID).amount * (this.magicNumber - 1);
            addToBot(new ApplyPowerAction(p, p, new DeflectPower(deflect), deflect));
        }
    }

    @Override
    public void calculateCardDamage(AbstractMonster m) {
            int realDamage = baseDamage;
            baseDamage += Wiz.playerWeaponCalc(m, 6);
            super.calculateCardDamage(m);
            baseDamage = realDamage;
            this.isDamageModified = (damage != baseDamage);
    }

    @Override
    public AbstractCard makeCopy() { return new Bolganone(); }
}