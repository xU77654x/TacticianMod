package tactician.cards.uncommon;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tactician.actions.NosferatuAction;
import tactician.actions.PlaySoundAction;
import tactician.cards.Tactician8DarkCard;
import tactician.character.TacticianRobin;
import tactician.effects.PlayVoiceEffect;
import tactician.powers.weapons.Weapon8DarkPower;
import tactician.util.CardStats;
import tactician.util.CustomTags;
import tactician.util.Wiz;

public class Nosferatu extends Tactician8DarkCard {
    public static final String ID = makeID(Nosferatu.class.getSimpleName());
    private static final CardStats info = new CardStats(
            TacticianRobin.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.UNCOMMON,
            CardTarget.ENEMY,
            1
    );

    public Nosferatu() {
        super(ID, info);
        setDamage(5, 2);
        tags.add(CustomTags.DARK);
        tags.add(CustomTags.COMBAT_ART);
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // addToBot(new VFXAction(new OrbFlareEffect(Dark, OrbFlareEffect.OrbFlareColor.DARK), 0.6F));
        // TODO: Dark attack effect.
        if (AbstractDungeon.player instanceof TacticianRobin && !p.hasPower(Weapon8DarkPower.POWER_ID)) { addToBot(new ApplyPowerAction(p, p, new Weapon8DarkPower(p))); }
        calculateCardDamage(m);
        addToBot(new PlaySoundAction("tactician:Nosferatu", 1.50f));
        AbstractDungeon.effectList.add(new PlayVoiceEffect("Nosferatu"));
        addToBot(new NosferatuAction(m, new DamageInfo(p, this.damage, DamageInfo.DamageType.NORMAL)));
    }

    @Override
    public void calculateCardDamage(AbstractMonster m) {
        int realDamage = baseDamage;
        baseDamage += Wiz.playerWeaponCalc(m, 8);
        super.calculateCardDamage(m);
        baseDamage = realDamage;
        this.isDamageModified = (damage != baseDamage);
    }

    @Override
    public AbstractCard makeCopy() { return new Nosferatu(); }
}