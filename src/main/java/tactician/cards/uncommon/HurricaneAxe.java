package tactician.cards.uncommon;

import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.mod.stslib.patches.FlavorText;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ExhaustAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tactician.actions.EasyModalChoiceAction;
import tactician.cards.TacticianCard;
import tactician.cards.cardchoice.Weapon3Axe;
import tactician.cards.cardchoice.Weapon5Wind;
import tactician.cards.other.Anathema;
import tactician.character.TacticianRobin;
import tactician.powers.weapons.Weapon3AxePower;
import tactician.powers.weapons.Weapon5WindPower;
import tactician.util.CardStats;
import tactician.util.Wiz;

import java.util.ArrayList;

public class HurricaneAxe extends TacticianCard {
    public static final String ID = makeID(HurricaneAxe.class.getSimpleName());
    private static final CardStats info = new CardStats(
            TacticianRobin.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.UNCOMMON,
            CardTarget.ENEMY,
            2
    );
    public int weapon;

    public HurricaneAxe() {
        super(ID, info);
        setDamage(14, 4);
        this.cardsToPreview = new Anathema();
        FlavorText.AbstractCardFlavorFields.boxColor.set(this, Color.PURPLE.cpy());
        FlavorText.AbstractCardFlavorFields.textColor.set(this, Color.WHITE.cpy());
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        weapon = 0;
        if (AbstractDungeon.player instanceof TacticianRobin) {
            ArrayList<AbstractCard> easyCardList = new ArrayList<>();
            easyCardList.add(new Weapon3Axe(() ->  {
                weapon = 3;
                if (!p.hasPower(Weapon3AxePower.POWER_ID)) { addToBot(new ApplyPowerAction(p, p, new Weapon3AxePower(p))); }
                calculateCardDamage(m);
                addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_HEAVY));
            }));
            easyCardList.add(new Weapon5Wind(() ->  {
                weapon = 5;
                if (!p.hasPower(Weapon5WindPower.POWER_ID)) { addToBot(new ApplyPowerAction(p, p, new Weapon5WindPower(p))); }
                calculateCardDamage(m);
                addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
            }));
            addToBot(new EasyModalChoiceAction(easyCardList));
        }
        else { addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_HEAVY)); }
        addToBot(new MakeTempCardInHandAction(new Anathema()));
        addToBot(new ExhaustAction(1, false));
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
        addToBot(new MakeTempCardInHandAction(new WildAbandon()));
        addToBot(new MakeTempCardInHandAction(new CuttingGale()));
    }

    @Override
    public AbstractCard makeCopy() {
        return new HurricaneAxe();
    }
}