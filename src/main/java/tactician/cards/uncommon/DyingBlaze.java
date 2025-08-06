package tactician.cards.uncommon;

import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.mod.stslib.patches.FlavorText;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tactician.actions.MakeAndExhaustCopyAction;
import tactician.cards.Tactician6FireCard;
import tactician.character.TacticianRobin;
import tactician.powers.weapons.Weapon6FirePower;
import tactician.util.CardStats;
import tactician.util.CustomTags;
import tactician.util.Wiz;

public class DyingBlaze extends Tactician6FireCard {
    public static final String ID = makeID(DyingBlaze.class.getSimpleName());
    private static final CardStats info = new CardStats(
            TacticianRobin.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.UNCOMMON,
            CardTarget.ENEMY,
            0
    );

    public DyingBlaze() {
        super(ID, info);
        setDamage(4, 2);
        setMagic(2, 1);
        tags.add(CustomTags.FIRE);
        tags.add(CustomTags.COMBAT_ART);
        FlavorText.AbstractCardFlavorFields.boxColor.set(this, Color.PURPLE.cpy());
        FlavorText.AbstractCardFlavorFields.textColor.set(this, Color.WHITE.cpy());
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (AbstractDungeon.player instanceof TacticianRobin && !p.hasPower(Weapon6FirePower.POWER_ID)) { addToBot(new ApplyPowerAction(p, p, new Weapon6FirePower(p))); }
        calculateCardDamage(m);
        addToBot(new SFXAction("tactician:DyingBlaze"));
        addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.FIRE));
        addToBot(new MakeAndExhaustCopyAction(makeStatEquivalentCopy(), this.magicNumber));

        /*
        for (int i = this.magicNumber; i > 0; i--) {
            addToBot(new MakeAndExhaustCopyAction(makeStatEquivalentCopy(), 1));
        } */

        /*
        for (int x = 0; x < 3; x++) {
            for (int i = this.magicNumber; i > 0; i--) {
                addToBot(new MakeTempCardInExhaustAction(makeStatEquivalentCopy(), 1));
                x += 1;
            }
            addToBot(new WaitAction(1));
            x = 0;
        }
        addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.FIRE));
         */// This is supposed to create a pause every 3 exhausted copy, but instead causes the game to hang due to the UnlockTracker already having seen this card.
    }

    @Override
    public void applyPowers() {
        int realDamage = baseDamage;
        baseDamage += AbstractDungeon.player.exhaustPile.size();
        super.applyPowers();
        baseDamage = realDamage;
        this.isDamageModified = (damage != baseDamage);
    }

    @Override
    public void calculateCardDamage(AbstractMonster m) {
        int realDamage = baseDamage;
        baseDamage += AbstractDungeon.player.exhaustPile.size();
        baseDamage += Wiz.playerWeaponCalc(m, 6);
        super.calculateCardDamage(m);
        baseDamage = realDamage;
        this.isDamageModified = (damage != baseDamage);
    }

    @Override
    public AbstractCard makeCopy() { return new DyingBlaze(); }
}