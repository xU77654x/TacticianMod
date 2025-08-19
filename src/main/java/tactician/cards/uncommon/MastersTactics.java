package tactician.cards.uncommon;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tactician.actions.PlaySoundAction;
import tactician.cards.TacticianCard;
import tactician.character.TacticianRobin;
import tactician.powers.MaxHandSizePower;
import tactician.util.CardStats;

public class MastersTactics extends TacticianCard {
    public static final String ID = makeID(MastersTactics.class.getSimpleName());
    private static final CardStats info = new CardStats(
            TacticianRobin.Meta.CARD_COLOR,
            CardType.POWER,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            1
    );

    public MastersTactics() {
        super(ID, info);
        setMagic(2, 1);

    }
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new PlaySoundAction("tactician:MastersTactics", 1.00f));
        addToBot(new ApplyPowerAction(p, p, new MaxHandSizePower(this.magicNumber), this.magicNumber));
        addToBot(new DrawCardAction(this.magicNumber));
    }

    @Override
    public AbstractCard makeCopy() {
        return new MastersTactics();
    }
}