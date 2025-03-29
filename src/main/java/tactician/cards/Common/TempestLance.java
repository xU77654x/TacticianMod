package tactician.cards.Common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.WeakPower;
import tactician.cards.BaseCard;
import tactician.character.MyCharacter;
import tactician.util.CardStats;
import tactician.util.CustomTags;

public class TempestLance extends BaseCard {
    public static final String ID = makeID(TempestLance.class.getSimpleName());
    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.COMMON,
            CardTarget.ENEMY,
            2
    );

    public TempestLance() {
        super(ID, info);
        setDamage(16, 3);
        setMagic(1, 998);
        tags.add(CustomTags.LANCE);
        tags.add(CustomTags.COMBAT_ART);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
        addToBot(new ReducePowerAction(p, p, AbstractDungeon.player.getPower(WeakPower.POWER_ID), magicNumber));
    }

    @Override
    public void applyPowers() {
        AbstractPower weak = AbstractDungeon.player.getPower(WeakPower.POWER_ID);
        int index = AbstractDungeon.player.powers.indexOf(weak);
        AbstractDungeon.player.powers.remove(weak);
        super.applyPowers();
        if (weak != null) {
            AbstractDungeon.player.powers.add(index, weak);
        }
    }

    @Override
    public void calculateCardDamage(AbstractMonster m) {
        AbstractPower weak = AbstractDungeon.player.getPower(WeakPower.POWER_ID);
        int index = AbstractDungeon.player.powers.indexOf(weak);
        AbstractDungeon.player.powers.remove(weak);
        super.calculateCardDamage(m);
        if (weak != null) {
            AbstractDungeon.player.powers.add(index, weak);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new TempestLance();
    }
}