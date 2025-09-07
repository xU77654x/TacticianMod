package tactician.cards.uncommon;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tactician.actions.EasyModalChoiceAction;
import tactician.actions.PlaySoundAction;
import tactician.cards.TacticianCard;
import tactician.cards.cardchoice.Weapon2Lance;
import tactician.cards.cardchoice.Weapon6Fire;
import tactician.character.TacticianRobin;
import tactician.powers.TempOrbSlotPower;
import tactician.powers.weapons.Weapon2LancePower;
import tactician.powers.weapons.Weapon6FirePower;
import tactician.util.CardStats;
import tactician.util.Wiz;

import java.util.ArrayList;

public class FlameLance extends TacticianCard {
    public static final String ID = makeID(FlameLance.class.getSimpleName());
    private static final CardStats info = new CardStats(
            TacticianRobin.Meta.CARD_COLOR,
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
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        weapon = 0;
        if (AbstractDungeon.player instanceof TacticianRobin) {
            ArrayList<AbstractCard> easyCardList = new ArrayList<>();
            easyCardList.add(new Weapon2Lance(() -> {
                weapon = 2;
                if (!p.hasPower(Weapon2LancePower.POWER_ID)) { addToBot(new ApplyPowerAction(p, p, new Weapon2LancePower(p))); }
                calculateCardDamage(m);
                addToBot(new PlaySoundAction("tactician:FlameLance", 1.20f));
                addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
            }));
            easyCardList.add(new Weapon6Fire(() -> {
                weapon = 6;
                if (!p.hasPower(Weapon6FirePower.POWER_ID)) { addToBot(new ApplyPowerAction(p, p, new Weapon6FirePower(p))); }
                calculateCardDamage(m);
                addToBot(new PlaySoundAction("tactician:FlameLance", 1.20f));
                addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.FIRE));
            }));
            addToBot(new EasyModalChoiceAction(easyCardList));
        }
        else {
            addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
            addToBot(new PlaySoundAction("tactician:FlameLance", 1.20f));
        }
        addToBot(new ApplyPowerAction(p, p, new TempOrbSlotPower(this.magicNumber)));
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
    public AbstractCard makeCopy() { return new FlameLance(); }
}