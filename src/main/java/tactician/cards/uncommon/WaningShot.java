package tactician.cards.uncommon;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.WeakPower;
import tactician.cards.Base4BowCard;
import tactician.character.MyCharacter;
import tactician.powers.DeflectPower;
import tactician.powers.weapons.Weapon4BowPower;
import tactician.util.CardStats;
import tactician.util.CustomTags;
import tactician.util.Wiz;

public class WaningShot extends Base4BowCard {
    public static final String ID = makeID(WaningShot.class.getSimpleName());
    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.UNCOMMON,
            CardTarget.ENEMY,
            1
    );

    public WaningShot() {
        super(ID, info);
        setDamage(7, 2);
        setMagic(1, 1);
        tags.add(CustomTags.BOW);
        tags.add(CustomTags.COMBAT_ART);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (!p.hasPower(Weapon4BowPower.POWER_ID)) { addToBot(new ApplyPowerAction(p, p, new Weapon4BowPower(p))); }
        calculateCardDamage(m);
        addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
        addToBot(new ApplyPowerAction(m, p, new WeakPower(m, this.magicNumber, false), this.magicNumber));
        if (AbstractDungeon.player.hasPower(DeflectPower.POWER_ID) && (AbstractDungeon.player.getPower(DeflectPower.POWER_ID).amount >= 3)) {
            addToBot(new ReducePowerAction(p, p, AbstractDungeon.player.getPower(DeflectPower.POWER_ID), 3));
            addToBot(new ApplyPowerAction(m, p, new StrengthPower(m, -1), -1));
        }
    }

    @Override
    public void calculateCardDamage(AbstractMonster m) {
        int realDamage = baseDamage;
        baseDamage += Wiz.playerWeaponCalc(m, 4);
        super.calculateCardDamage(m);
        baseDamage = realDamage;
        this.isDamageModified = (damage != baseDamage);
    }

    @Override
    public AbstractCard makeCopy() {
        return new WaningShot();
    }
}