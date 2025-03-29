package tactician.cards.Common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import tactician.cards.BaseCard;
import tactician.character.MyCharacter;
import tactician.powers.DeflectPower;
import tactician.util.CardStats;
import tactician.util.CustomTags;

public class Smash extends BaseCard {
    public static final String ID = makeID(Smash.class.getSimpleName());
    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.COMMON,
            CardTarget.ENEMY,
            1
    );

    public Smash() {
        super(ID, info);
        setDamage(10, 3);
        setMagic(0, 0);
        tags.add(CustomTags.AXE);
        tags.add(CustomTags.COMBAT_ART);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
        addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, magicNumber), magicNumber));
        // TODO: Damage ignores your negative Strength.
        // TODO: If your Strength is negative: [gain 1 Strength / clear your StrengthPower.]
    }

    @Override
    public void applyPowers() {
        int realDamage = baseDamage;
        magicNumber = baseMagicNumber;
        if (AbstractDungeon.player.hasPower("Strength") &&
                (AbstractDungeon.player.getPower("Strength")).amount < 0) {
                    baseDamage -= AbstractDungeon.player.getPower(StrengthPower.POWER_ID).amount;
                    if (this.upgraded) {
                        magicNumber -= AbstractDungeon.player.getPower(StrengthPower.POWER_ID).amount;
                    }
                    else { magicNumber += 1; } }
        super.applyPowers();
        baseDamage = realDamage;
        this.isDamageModified = (damage != baseDamage);
        // Credit to Downfall: Slime Boss for the measuring of a negative power.
    }

    @Override
    public void calculateCardDamage(AbstractMonster m) {
        int realDamage = baseDamage;
        magicNumber = baseMagicNumber;
        if (AbstractDungeon.player.hasPower("Strength") && (AbstractDungeon.player.getPower("Strength")).amount < 0) {
            baseDamage -= AbstractDungeon.player.getPower(StrengthPower.POWER_ID).amount;
            if (this.upgraded) {
                magicNumber -= AbstractDungeon.player.getPower(StrengthPower.POWER_ID).amount;
            }
            else { magicNumber += 1; } }
        super.calculateCardDamage(m);
        baseDamage = realDamage;
        this.isDamageModified = (damage != baseDamage);
    }

    @Override
    public AbstractCard makeCopy() {
        return new Smash();
    }
}

