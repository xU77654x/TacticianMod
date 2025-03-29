package tactician.cards.Rare;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tactician.actions.HuntersVolleyAction;
import tactician.cards.BaseCard;
import tactician.character.MyCharacter;
import tactician.util.CardStats;
import tactician.util.CustomTags;

public class HuntersVolley extends BaseCard {
    public static final String ID = makeID(HuntersVolley.class.getSimpleName());
    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.RARE,
            CardTarget.ENEMY,
            3
    );

    public HuntersVolley() {
        super(ID, info);
        setDamage(15, 3);
        tags.add(CustomTags.BOW);
        tags.add(CustomTags.COMBAT_ART);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new HuntersVolleyAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL)));
        // TODO: Adjust the attack effect within the Action.
    }

    @Override
    public AbstractCard makeCopy() {
        return new HuntersVolley();
    }
}

