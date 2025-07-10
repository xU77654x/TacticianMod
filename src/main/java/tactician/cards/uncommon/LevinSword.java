package tactician.cards.uncommon;

import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.mod.stslib.patches.FlavorText;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.LightningEffect;
import tactician.actions.EasyModalChoiceAction;
import tactician.cards.TacticianCard;
import tactician.cards.cardchoice.Weapon1Sword;
import tactician.cards.cardchoice.Weapon7Thunder;
import tactician.cards.other.Hex;
import tactician.character.TacticianRobin;
import tactician.powers.DeflectPower;
import tactician.powers.weapons.Weapon1SwordPower;
import tactician.powers.weapons.Weapon7ThunderPower;
import tactician.util.CardStats;
import tactician.util.Wiz;

import java.util.ArrayList;

public class LevinSword extends TacticianCard {
    public static final String ID = makeID(LevinSword.class.getSimpleName());
    private static final CardStats info = new CardStats(
            TacticianRobin.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.UNCOMMON,
            CardTarget.ENEMY,
            2
    );
    public int weapon;

    public LevinSword() {
        super(ID, info);
        setDamage(14, 4);
        setMagic(3, 0);
        this.cardsToPreview = new Hex();
        FlavorText.AbstractCardFlavorFields.boxColor.set(this, Color.PURPLE.cpy());
        FlavorText.AbstractCardFlavorFields.textColor.set(this, Color.WHITE.cpy());
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        weapon = 0;
        if (AbstractDungeon.player instanceof TacticianRobin) {
            ArrayList<AbstractCard> easyCardList = new ArrayList<>();
            easyCardList.add(new Weapon1Sword(() ->  {
                weapon = 1;
                if (!p.hasPower(Weapon1SwordPower.POWER_ID)) { addToBot(new ApplyPowerAction(p, p, new Weapon1SwordPower(p))); }
                calculateCardDamage(m);
                addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
            }));
            easyCardList.add(new Weapon7Thunder(() ->  {
                weapon = 7;
                if (!p.hasPower(Weapon7ThunderPower.POWER_ID)) { addToBot(new ApplyPowerAction(p, p, new Weapon7ThunderPower(p))); }
                calculateCardDamage(m);
                addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.LIGHTNING));
            }));
            addToTop(new EasyModalChoiceAction(easyCardList));
        }
        else { addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_VERTICAL)); }
        addToTop(new VFXAction(new LightningEffect(m.drawX, m.drawY), 0.05F));
        addToBot(new ApplyPowerAction(p, p, new DeflectPower(this.magicNumber), this.magicNumber));
        addToBot(new MakeTempCardInHandAction(new Hex(), 1));
    }

    @Override
    public void triggerOnExhaust() {
        addToBot(new MakeTempCardInHandAction(new CrosswiseCut()));
        addToBot(new MakeTempCardInHandAction(new Bolting()));
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
    public AbstractCard makeCopy() { return new LevinSword(); }
}