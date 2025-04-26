package tactician.cards.Uncommon;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tactician.actions.FlashSparrowAction;
import tactician.cards.BaseCard;
import tactician.character.MyCharacter;
import tactician.util.CardStats;
import tactician.util.CustomTags;

public class FlashSparrow extends BaseCard {
    public static final String ID = makeID(FlashSparrow.class.getSimpleName());
    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.UNCOMMON,
            CardTarget.ENEMY,
            1
    );
    public FlashSparrow() {
        super(ID, info);
        setDamage(5, 3);
        tags.add(CustomTags.COPY);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new FlashSparrowAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL)));
        // TODO: FlashSparrowAction needs weapon types.
    }

    @Override
    public AbstractCard makeCopy() {
        return new FlashSparrow();
    }
}