package tactician.cards.Uncommon;

import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tactician.actions.NosferatuAction;
import tactician.cards.BaseCard;
import tactician.character.MyCharacter;
import tactician.util.CardStats;
import tactician.util.CustomTags;

public class Nosferatu extends BaseCard {
    public static final String ID = makeID(Nosferatu.class.getSimpleName());
    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
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
        addToBot(new TalkAction(true, cardStrings.EXTENDED_DESCRIPTION[0], 1.0F, 2.0F));
        addToBot(new NosferatuAction(m, new DamageInfo(p, this.damage, DamageInfo.DamageType.NORMAL)));
    }

    @Override
    public AbstractCard makeCopy() { return new Nosferatu(); }
}