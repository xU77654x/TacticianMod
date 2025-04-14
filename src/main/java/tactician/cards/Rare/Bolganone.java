package tactician.cards.Rare;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.FocusPower;
import tactician.cards.BaseCard;
import tactician.character.MyCharacter;
import tactician.powers.DeflectPower;
import tactician.util.CardStats;
import tactician.util.CustomTags;

public class Bolganone extends BaseCard {
    public static final String ID = makeID(Bolganone.class.getSimpleName());
    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.RARE,
            CardTarget.ENEMY,
            3
    );

    public Bolganone() {
        super(ID, info);
        setDamage(22, 3);
        setMagic(1, 1);
        tags.add(CustomTags.FIRE);
        tags.add(CustomTags.COMBAT_ART);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int finalDamage = damage;
        addToBot(new TalkAction(true, cardStrings.EXTENDED_DESCRIPTION[0], 1.0F, 2.0F));
        addToBot(new DamageAction(m, new DamageInfo(p, finalDamage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
        addToBot(new ApplyPowerAction(p, p, new FocusPower(p, this.magicNumber), this.magicNumber));
    }

    @Override
    public void applyPowers() {
        int realDamage = baseDamage;
        if (AbstractDungeon.player.hasPower(DeflectPower.POWER_ID))
            baseDamage += 2 * AbstractDungeon.player.getPower(DeflectPower.POWER_ID).amount;
        super.applyPowers();
        baseDamage = realDamage;
        this.isDamageModified = (damage != baseDamage);
    }

    @Override
    public void calculateCardDamage(AbstractMonster m) {
        int realDamage = baseDamage;
        if (AbstractDungeon.player.hasPower(DeflectPower.POWER_ID))
            baseDamage += 2 * AbstractDungeon.player.getPower(DeflectPower.POWER_ID).amount;
        super.calculateCardDamage(m);
        baseDamage = realDamage;
        this.isDamageModified = (damage != baseDamage);
    }

    @Override
    public AbstractCard makeCopy() {
        return new Bolganone();
    }
}