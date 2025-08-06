package tactician.cards.basic.strikes;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tactician.actions.StrikeDefendSFXAction;
import tactician.cards.Tactician4BowCard;
import tactician.character.TacticianRobin;
import tactician.powers.weapons.Weapon4BowPower;
import tactician.util.CardStats;
import tactician.util.CustomTags;
import tactician.util.Wiz;

public class Strike4Bow extends Tactician4BowCard {
    public static final String ID = makeID(Strike4Bow.class.getSimpleName());
    private static final CardStats info = new CardStats(
            TacticianRobin.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.BASIC,
            CardTarget.ENEMY,
            1
    );

    public Strike4Bow() {
        super(ID, info);
        setDamage(5, 3);
        tags.add(CardTags.STARTER_STRIKE);
        tags.add(CardTags.STRIKE);
        tags.add(CustomTags.BOW);
        tags.add(CustomTags.MUTATE);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (AbstractDungeon.player instanceof TacticianRobin && !p.hasPower(Weapon4BowPower.POWER_ID)) {addToBot(new ApplyPowerAction(p, p, new Weapon4BowPower(p))); }
        calculateCardDamage(m);
        addToBot(new StrikeDefendSFXAction(1, m));
        addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
    }

    @Override
    public void calculateCardDamage(AbstractMonster m) {
        int realDamage = baseDamage;
        baseDamage += Wiz.playerWeaponCalc(m, 4);
        super.calculateCardDamage(m);
        baseDamage = realDamage;
        this.isDamageModified = (damage != baseDamage);
    }

    @Override
    public AbstractCard makeCopy() { return new Strike4Bow(); }
}