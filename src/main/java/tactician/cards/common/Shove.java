package tactician.cards.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tactician.actions.PlaySoundAction;
import tactician.cards.Tactician9CopyCard;
import tactician.character.TacticianRobin;
import tactician.powers.ShovePower;
import tactician.util.CardStats;
import tactician.util.CustomTags;
import tactician.util.Wiz;

public class Shove extends Tactician9CopyCard {
    public static final String ID = makeID(Shove.class.getSimpleName());
    private static final CardStats info = new CardStats(
            TacticianRobin.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.COMMON,
            CardTarget.ALL_ENEMY,
            1
    );

    public Shove() {
        super(ID, info);
        setDamage(5, 3);
        setMagic(1, 0);
        tags.add(CustomTags.COPY);
        this.isMultiDamage = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            addToTop(new PlaySoundAction("tactician:Shove", 1.50f));
            for (AbstractMonster mo : (AbstractDungeon.getMonsters()).monsters) {
                calculateCardDamage(mo);
                addToBot(new DamageAction(mo, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
            }
        }
        addToBot(new ApplyPowerAction(p, p, new ShovePower(this.magicNumber), this.magicNumber));
    }

    @Override
    public void calculateCardDamage(AbstractMonster m) {
        if (m != null) {
            int realDamage = baseDamage;
            baseDamage += Wiz.playerWeaponCalc(m, 9);
            super.calculateCardDamage(m);
            baseDamage = realDamage;
            this.isDamageModified = (damage != baseDamage);
        }
    }

    @Override
    public AbstractCard makeCopy() { return new Shove(); }
}