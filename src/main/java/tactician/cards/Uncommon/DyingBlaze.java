package tactician.cards.Uncommon;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ExhaustAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tactician.actions.MakeTempCardInExhaustAction;
import tactician.cards.BaseCard;
import tactician.character.MyCharacter;
import tactician.util.CardStats;
import tactician.util.CustomTags;

public class DyingBlaze extends BaseCard {
    public static final String ID = makeID(DyingBlaze.class.getSimpleName());
    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.UNCOMMON,
            CardTarget.ENEMY,
            0
    );

    public DyingBlaze() {
        super(ID, info);
        setDamage(5, 0);
        setMagic(3, 3);
        tags.add(CustomTags.FIRE);
        tags.add(CustomTags.COMBAT_ART);
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i = this.magicNumber; i > 0; i--) {
            addToBot(new MakeTempCardInExhaustAction(makeStatEquivalentCopy(), 1));
        }
        addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.FIRE));

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
         */ // TODO: This is supposed to create a pause every 3 exhausted copy, but instead causes the game to hang due to the UnlockTracker already having seen this card.
    }

    @Override
    public void applyPowers() {
        int realDamage = baseDamage;
        baseDamage += AbstractDungeon.player.exhaustPile.size() + this.magicNumber;
        super.applyPowers();
        baseDamage = realDamage;
        this.isDamageModified = (damage != baseDamage);
    }

    @Override
    public void calculateCardDamage(AbstractMonster m) {
        int realDamage = baseDamage;
        baseDamage += AbstractDungeon.player.exhaustPile.size() + this.magicNumber;
        super.calculateCardDamage(m);
        baseDamage = realDamage;
        this.isDamageModified = (damage != baseDamage);
    }

    @Override
    public AbstractCard makeCopy() { return new DyingBlaze(); }
}

