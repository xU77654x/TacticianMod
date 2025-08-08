package tactician.cards.uncommon;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ExhaustAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tactician.actions.PlaySoundAction;
import tactician.actions.RandomExhumeAction;
import tactician.cards.Tactician5WindCard;
import tactician.character.TacticianRobin;
import tactician.effects.PlayVoiceEffect;
import tactician.powers.weapons.Weapon5WindPower;
import tactician.util.CardStats;
import tactician.util.CustomTags;
import tactician.util.Wiz;

public class CuttingGale extends Tactician5WindCard {
    public static final String ID = makeID(CuttingGale.class.getSimpleName());
    private static final CardStats info = new CardStats(
            TacticianRobin.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.UNCOMMON,
            CardTarget.ENEMY,
            2
    );
    public CuttingGale() {
        super(ID, info);
        setDamage(10, 3);
        tags.add(CustomTags.WIND);
        tags.add(CustomTags.COMBAT_ART);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (AbstractDungeon.player instanceof TacticianRobin && !p.hasPower(Weapon5WindPower.POWER_ID)) { addToBot(new ApplyPowerAction(p, p, new Weapon5WindPower(p))); }
        AbstractDungeon.effectList.add(new PlayVoiceEffect("CA_MiscMagic"));
        calculateCardDamage(m);
        addToBot(new PlaySoundAction("tactician:CuttingGale_Jab", 1.00f));
        addToBot(new WaitAction(0.05F));
        addToBot(new PlaySoundAction("tactician:CuttingGale_Jab", 1.00f));
        addToBot(new WaitAction(0.05F));
        addToBot(new PlaySoundAction("tactician:CuttingGale_Jab", 1.00f));
        addToBot(new WaitAction(0.05F));
        addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        addToBot(new PlaySoundAction("tactician:CuttingGale_Finish", 1.00f));
        addToBot(new RandomExhumeAction(1, false));
        if (this.upgraded) { addToBot(new ExhaustAction(1, true)); }
        else { addToBot(new ExhaustAction(1, false)); }
    }

    @Override
    public void calculateCardDamage(AbstractMonster m) {
        int realDamage = baseDamage;
        baseDamage += Wiz.playerWeaponCalc(m, 5);
        super.calculateCardDamage(m);
        baseDamage = realDamage;
        this.isDamageModified = (damage != baseDamage);
    }

    @Override
    public AbstractCard makeCopy() { return new CuttingGale(); }
}