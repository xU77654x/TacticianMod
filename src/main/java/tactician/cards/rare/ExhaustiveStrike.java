package tactician.cards.rare;

import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.mod.stslib.patches.FlavorText;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ExhaustAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tactician.cards.Base3AxeCard;
import tactician.character.MyCharacter;
import tactician.powers.weapons.Weapon3AxePower;
import tactician.util.CardStats;
import tactician.util.CustomTags;
import tactician.util.Wiz;

public class ExhaustiveStrike extends Base3AxeCard {
    public static final String ID = makeID(ExhaustiveStrike.class.getSimpleName());
    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.RARE,
            CardTarget.ENEMY,
            2
    );

    public ExhaustiveStrike() {
        super(ID, info);
        setDamage(10, 4);
        setMagic(1, 2);
        tags.add(CardTags.STRIKE);
        tags.add(CustomTags.AXE);
        tags.add(CustomTags.COMBAT_ART);
        FlavorText.AbstractCardFlavorFields.boxColor.set(this, Color.PURPLE.cpy());
        FlavorText.AbstractCardFlavorFields.textColor.set(this, Color.WHITE.cpy());
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (!p.hasPower(Weapon3AxePower.POWER_ID)) { addToBot(new ApplyPowerAction(p, p, new Weapon3AxePower(p))); }
        calculateCardDamage(m);
        addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        addToBot(new ExhaustAction(this.magicNumber, false, true));
    }

    @Override
    public void calculateCardDamage(AbstractMonster m) {
        if (m != null) {
            int realDamage = baseDamage;
            baseDamage += Wiz.playerWeaponCalc(m, 3);
            super.calculateCardDamage(m);
            baseDamage = realDamage;
            this.isDamageModified = (damage != baseDamage);
        }
    }

    @Override
    public AbstractCard makeCopy() { return new ExhaustiveStrike(); }
}