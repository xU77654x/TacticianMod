package tactician.cards.Uncommon;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tactician.actions.DrawPileToDiscardPileAction;
import tactician.cards.BaseCard;
import tactician.character.MyCharacter;
import tactician.util.CardStats;
import tactician.util.CustomTags;

public class Sunder extends BaseCard {
    public static final String ID = makeID(Sunder.class.getSimpleName());
    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.UNCOMMON,
            CardTarget.ENEMY,
            1
    );

    public Sunder() {
        super(ID, info);
        setDamage(10, 3);
        // setMagic(2, 3); If DrawPileToDiscardPileAction can let the user choose to not move cards, then !M! should increase to make this card better.
        tags.add(CustomTags.SWORD);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
        addToBot(new DrawPileToDiscardPileAction(p, true, 1));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(damageUpgrade);
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
            tags.add(CardTags.STRIKE);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new Sunder();
    }
}

