package tactician.cards.Other;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.curses.Injury;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tactician.cards.BaseCard;
import tactician.util.CardStats;

public class Expiration extends BaseCard {
    public static final String ID = makeID(Expiration.class.getSimpleName());
    private static final CardStats info = new CardStats(
            AbstractCard.CardColor.COLORLESS,
            CardType.ATTACK,
            CardRarity.SPECIAL,
            CardTarget.ENEMY,
            1
    );

    public Expiration() {
        super(ID, info);
        setDamage(16, 1);
        setBlock(6, 1);
        setMagic(3, -1);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, p, this.block));
        addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.FIRE));
        addToBot(new LoseHPAction(p, p, this.magicNumber));
        addToBot(new MakeTempCardInHandAction(new Injury(), 1));
    }

    @Override
    public AbstractCard makeCopy() { return new Expiration(); }
}