package tactician.cards.rare;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tactician.cards.Base8DarkCard;
import tactician.character.MyCharacter;
import tactician.powers.weapons.Weapon8DarkPower;
import tactician.util.CardStats;
import tactician.util.CustomTags;
import tactician.util.Wiz;

public class Goetia extends Base8DarkCard {
    public static final String ID = makeID(Goetia.class.getSimpleName());
    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.RARE,
            CardTarget.ENEMY,
            2
    );

    public Goetia() {
        super(ID, info);
        setDamage(10, 4);
        setBlock(7, 3);
        tags.add(CustomTags.DARK);
        tags.add(CustomTags.COMBAT_ART);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        calculateCardDamage(m);
        addToBot(new GainBlockAction(p, p, this.block));
        addToBot(new GainBlockAction(p, p, this.block));
        addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
        if (!p.hasPower(Weapon8DarkPower.POWER_ID)) { addToBot(new ApplyPowerAction(p, p, new Weapon8DarkPower(p))); }
    }

    @Override
    public void calculateCardDamage(AbstractMonster m) {
        int realDamage = baseDamage;
        int realBlock = baseBlock;
        baseDamage += Wiz.playerWeaponCalc(m, 8);
        baseBlock += Wiz.playerWeaponCalc(m, 8);
        super.calculateCardDamage(m);
        baseDamage = realDamage;
        baseBlock = realBlock;
        this.isDamageModified = (damage != baseDamage);
        this.isBlockModified = (block != baseBlock);
    }

    @Override
    public AbstractCard makeCopy() { return new Goetia(); }
}