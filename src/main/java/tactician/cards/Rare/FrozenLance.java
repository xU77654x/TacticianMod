package tactician.cards.Rare;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.FocusPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import tactician.cards.BaseCard;
import tactician.character.MyCharacter;
import tactician.util.CardStats;
import tactician.util.CustomTags;

public class FrozenLance extends BaseCard {
    public static final String ID = makeID(FrozenLance.class.getSimpleName());
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
        tags.add(CustomTags.LANCE);
        tags.add(CustomTags.COMBAT_ART);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
    }

    @Override
    public void applyPowers() {
        AbstractPower strength = AbstractDungeon.player.getPower(StrengthPower.POWER_ID);
        AbstractPower focus = AbstractDungeon.player.getPower(FocusPower.POWER_ID);
        if (strength != null)
            strength.amount *= this.magicNumber;
        if (focus != null)
            focus.amount *= this.magicNumber;
        super.applyPowers();
        if (strength != null)
            strength.amount /= this.magicNumber;
        if (focus != null)
            focus.amount /= this.magicNumber;
    }

    public void calculateCardDamage(AbstractMonster mo) {
        AbstractPower strength = AbstractDungeon.player.getPower(StrengthPower.POWER_ID);
        AbstractPower focus = AbstractDungeon.player.getPower(FocusPower.POWER_ID);
        if (strength != null)
            strength.amount *= this.magicNumber;
        if (focus != null)
            focus.amount *= this.magicNumber;
        super.calculateCardDamage(mo);
        if (strength != null)
            strength.amount /= this.magicNumber;
        if (focus != null)
            focus.amount /= this.magicNumber;
    }

    @Override
    public AbstractCard makeCopy() { return new FrozenLance(); }
}