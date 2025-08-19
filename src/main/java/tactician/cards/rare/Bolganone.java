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
import tactician.actions.PlaySoundAction;
import tactician.cards.Tactician6FireCard;
import tactician.character.TacticianRobin;
import tactician.effects.PlayVoiceEffect;
import tactician.powers.DeflectPower;
import tactician.powers.weapons.Weapon6FirePower;
import tactician.util.CardStats;
import tactician.util.CustomTags;
import tactician.util.Wiz;

import static java.lang.Math.max;

public class Bolganone extends Tactician6FireCard {
    public static final String ID = makeID(Bolganone.class.getSimpleName());
    private static final CardStats info = new CardStats(
            TacticianRobin.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.RARE,
            CardTarget.ENEMY,
            2
    );

    public Bolganone() {
        super(ID, info);
        setDamage(10, 4);
        setMagic(2, 0);
        tags.add(CustomTags.FIRE);
        tags.add(CustomTags.COMBAT_ART);
        FlavorText.AbstractCardFlavorFields.boxColor.set(this, Color.PURPLE.cpy());
        FlavorText.AbstractCardFlavorFields.textColor.set(this, Color.WHITE.cpy());
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.effectList.add(new PlayVoiceEffect("Bolganone"));
        calculateCardDamage(m);
        addToBot(new PlaySoundAction("tactician:Bolganone", 1.50f));
        addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.FIRE));
        int deflect = 0;
        if (AbstractDungeon.player.hasPower(DeflectPower.POWER_ID)) { deflect = AbstractDungeon.player.getPower(DeflectPower.POWER_ID).amount; }
        addToBot(new ApplyPowerAction(p, p, new DeflectPower(this.magicNumber)));
        if (this.upgraded) { deflect += deflect + (this.magicNumber * 2); }
        else { deflect += this.magicNumber; }
        addToBot(new ApplyPowerAction(p, p, new DeflectPower(deflect)));
        if (AbstractDungeon.player instanceof TacticianRobin && !p.hasPower(Weapon6FirePower.POWER_ID)) { addToBot(new ApplyPowerAction(p, p, new Weapon6FirePower(p))); }
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
    public AbstractCard makeCopy() { return new Bolganone(); }
}