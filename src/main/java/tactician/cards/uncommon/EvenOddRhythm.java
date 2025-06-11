package tactician.cards.uncommon;

import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tactician.cards.BaseCard;
import tactician.cards.other.Anathema;
import tactician.cards.other.Hex;
import tactician.character.MyCharacter;
import tactician.powers.DeflectPower;
import tactician.util.CardStats;

public class EvenOddRhythm extends BaseCard {
    public static final String ID = makeID(EvenOddRhythm.class.getSimpleName());
    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            2
    );

    public EvenOddRhythm() {
        super(ID, info);
        setBlock(15, 3);
        setMagic(16,4);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (GameActionManager.turn % 2 == 0) {
            addToBot(new GainBlockAction(p, p, this.block));
            addToBot(new MakeTempCardInHandAction(new Hex(), 1));
        }
        else {
            addToBot(new ApplyPowerAction(p, p, new DeflectPower(this.magicNumber), this.magicNumber));
            addToBot(new MakeTempCardInHandAction(new Anathema(), 1));
        }
        // TODO: If possible, change the card art between Panne and Nowi based on the turn number. Stone Calendar may not be the way.
    }

    @Override
    public AbstractCard makeCopy() { return new EvenOddRhythm(); }
}