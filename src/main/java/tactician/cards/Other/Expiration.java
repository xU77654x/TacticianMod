package tactician.cards.Other;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.curses.Injury;
import com.megacrit.cardcrawl.cards.status.Wound;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tactician.actions.EasyModalChoiceAction;
import tactician.cards.BaseCard;
import tactician.cards.Weapons.ExpInjury;
import tactician.cards.Weapons.ExpWound;
import tactician.character.MyCharacter;
import tactician.util.CardStats;

import java.util.ArrayList;

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

        ArrayList<AbstractCard> easyCardList = new ArrayList<>();
        easyCardList.add(new ExpInjury(() -> { addToBot(new MakeTempCardInDrawPileAction(new Injury(), 1, true, true, false)); }));
        easyCardList.add(new ExpWound(() -> { addToBot(new MakeTempCardInDrawPileAction(new Wound(), 1, true, true, false)); }));
        addToBot(new EasyModalChoiceAction(easyCardList));
    }

    @Override
    public AbstractCard makeCopy() { return new Expiration(); }
}

