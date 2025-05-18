package tactician.cards.uncommon;

import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.mod.stslib.patches.FlavorText;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.defect.EvokeAllOrbsAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tactician.actions.EasyModalChoiceAction;
import tactician.cards.BaseCard;
import tactician.cards.cardchoice.Weapon2Lance;
import tactician.cards.cardchoice.Weapon6Fire;
import tactician.character.MyCharacter;
import tactician.powers.DeflectPower;
import tactician.powers.weaponscurrent.Weapon2LancePower;
import tactician.powers.weaponscurrent.Weapon6FirePower;
import tactician.util.CardStats;
import tactician.util.Wiz;

import java.util.ArrayList;

public class FlameLance extends BaseCard {
    public static final String ID = makeID(FlameLance.class.getSimpleName());
    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.UNCOMMON,
            CardTarget.ENEMY,
            2
    );
    public int weapon;

    public FlameLance() {
        super(ID, info);
        setDamage(14, 4);
        setMagic(3, 0);
        FlavorText.AbstractCardFlavorFields.boxColor.set(this, Color.PURPLE.cpy());
        FlavorText.AbstractCardFlavorFields.textColor.set(this, Color.WHITE.cpy());
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        weapon = 0;
        ArrayList<AbstractCard> easyCardList = new ArrayList<>();
        easyCardList.add(new Weapon2Lance(() -> {
            weapon = 2;
            addToBot(new ApplyPowerAction(p, p, new Weapon2LancePower(p)));
            calculateCardDamage(m);
            addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        }));
        easyCardList.add(new Weapon6Fire(() -> {
            weapon = 6;
            addToBot(new ApplyPowerAction(p, p, new Weapon6FirePower(p)));
            calculateCardDamage(m);
            addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.FIRE));
        }));
        addToBot(new EasyModalChoiceAction(easyCardList));
        int orbCount = AbstractDungeon.player.filledOrbCount();
        addToBot(new EvokeAllOrbsAction());
        if (orbCount > 0) { addToBot(new ApplyPowerAction(p, p, new DeflectPower(orbCount * this.magicNumber), orbCount * this.magicNumber)); }
    }

    @Override
    public void calculateCardDamage(AbstractMonster m) {
        int realDamage = baseDamage;
        baseDamage += Wiz.playerWeaponCalc(m, weapon);
        super.calculateCardDamage(m);
        baseDamage = realDamage;
        this.isDamageModified = (damage != baseDamage);
    }

    @Override
    public void triggerOnExhaust() {
        addToBot(new MakeTempCardInHandAction(new LanceJab()));
        addToBot(new MakeTempCardInHandAction(new DyingBlaze()));
    }

    @Override
    public AbstractCard makeCopy() { return new FlameLance(); }
}