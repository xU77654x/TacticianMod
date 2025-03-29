package tactician.cards.Common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tactician.cards.BaseCard;
import tactician.character.MyCharacter;
import tactician.util.CardStats;
import tactician.util.CustomTags;

public class Shove extends BaseCard {
    public static final String ID = makeID(Shove.class.getSimpleName());
    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.COMMON,
            CardTarget.ALL_ENEMY,
            1
    );

    public Shove() {
        super(ID, info);
        setDamage(5, 3);
        setMagic(1, 0);
        tags.add(CustomTags.COPY);
        this.isMultiDamage = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAllEnemiesAction(p, this.multiDamage, DamageInfo.DamageType.NORMAL, AbstractGameAction.AttackEffect.SLASH_VERTICAL));
        // TODO: Apply 1 of the Shove power to the user. Create the Shove power. Shove power should prevent removal of Str, Dex, and Foc. View StSLib's OnReceivePowerPower, which triggers on receiving ANY power. You will check to see if the power is negative Str/Dex/Foc or Str/Dex/Foc Down and then perform the desired action.
    }

    @Override
    public AbstractCard makeCopy() {
        return new Shove();
    }
}