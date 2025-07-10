package tactician.cards.rare;

import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.mod.stslib.patches.FlavorText;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DrawPower;
import tactician.cards.Tactician5WindCard;
import tactician.character.TacticianRobin;
import tactician.powers.MaxHandSizePower;
import tactician.powers.weapons.Weapon5WindPower;
import tactician.util.CardStats;
import tactician.util.CustomTags;
import tactician.util.Wiz;

import static java.lang.Math.max;

public class Excalibur extends Tactician5WindCard {
    public static final String ID = makeID(Excalibur.class.getSimpleName());
    private static final CardStats info = new CardStats(
            TacticianRobin.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.RARE,
            CardTarget.ENEMY,
            2
    );

    public Excalibur() {
        super(ID, info);
        setDamage(10, 8);
        setMagic(0, 0);
        tags.add(CustomTags.WIND);
        tags.add(CustomTags.COMBAT_ART);
        FlavorText.AbstractCardFlavorFields.boxColor.set(this, Color.PURPLE.cpy());
        FlavorText.AbstractCardFlavorFields.textColor.set(this, Color.WHITE.cpy());
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        calculateCardDamage(m);
        addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
        addToBot(new ApplyPowerAction(p, p, new DrawPower(p, 1), 1));
        addToBot(new ApplyPowerAction(p, p, new MaxHandSizePower(this.magicNumber), this.magicNumber));
        if (AbstractDungeon.player instanceof TacticianRobin && !p.hasPower(Weapon5WindPower.POWER_ID)) { addToBot(new ApplyPowerAction(p, p, new Weapon5WindPower(p))); }
    }

    @Override
    public void calculateCardDamage(AbstractMonster m) {
        int realDamage = baseDamage;
        int weaponCalc = Wiz.playerWeaponCalc(m, 9);
        baseDamage += weaponCalc;
        magicNumber = baseMagicNumber + max(0, weaponCalc);
        super.calculateCardDamage(m);
        baseDamage = realDamage;
        this.isDamageModified = (damage != baseDamage);
        this.isMagicNumberModified = (magicNumber != baseMagicNumber);
    }

    @Override
    public AbstractCard makeCopy() { return new Excalibur(); }
}