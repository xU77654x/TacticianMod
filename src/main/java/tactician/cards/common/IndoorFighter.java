package tactician.cards.common;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tactician.actions.HandSelectAction;
import tactician.cards.BaseCard;
import tactician.character.MyCharacter;
import tactician.powers.DeflectPower;
import tactician.util.CardStats;

public class IndoorFighter extends BaseCard {
    public static final String ID = makeID(IndoorFighter.class.getSimpleName());
    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.COMMON,
            CardTarget.SELF,
            1
    );

    public IndoorFighter() {
        super(ID, info);
        setBlock(8, 3);
        setMagic(8, 3);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new HandSelectAction(1, c -> true, list -> {
            for (AbstractCard c : list) {
                if (c.type == AbstractCard.CardType.SKILL || c.type == AbstractCard.CardType.POWER) {
                    addToBot(new GainBlockAction(p, p, this.block));
                }
                else { addToBot(new ApplyPowerAction(p, p, new DeflectPower(this.magicNumber), this.magicNumber)); }
                p.hand.moveToDiscardPile(c);
            }
            list.clear();
        },null, "Discard.", false, false, false));
    }

    @Override
    public AbstractCard makeCopy() { return new IndoorFighter(); }
}