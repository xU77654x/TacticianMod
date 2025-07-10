package tactician.cards.uncommon;

import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tactician.cards.TacticianCard;
import tactician.cards.other.Anathema;
import tactician.cards.other.Hex;
import tactician.character.TacticianRobin;
import tactician.powers.DeflectPower;
import tactician.util.CardStats;

public class EvenOddRhythm extends TacticianCard {
    public static final String ID = makeID(EvenOddRhythm.class.getSimpleName());
    private static final CardStats info = new CardStats(
            TacticianRobin.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            2
    );

    public EvenOddRhythm() {
        super(ID, info);
        setBlock(14, 4);
        setMagic(15,5);
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

    public void onMoveToDiscard() {
        if (upgraded) { this.name = cardStrings.NAME + "+"; }
        else { this.name = cardStrings.NAME; }
        this.rawDescription = cardStrings.DESCRIPTION;
        initializeTitle();
        initializeDescription();
        // Set card art.
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        if (GameActionManager.turn % 2 == 0) {
            this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[1];
            if (upgraded) { this.name = cardStrings.EXTENDED_DESCRIPTION[0] + "+"; }
            else { this.name = cardStrings.EXTENDED_DESCRIPTION[0]; }
            // Set card art.
        }
        else {
            this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[3];
            if (upgraded) { this.name = cardStrings.EXTENDED_DESCRIPTION[2] + "+"; }
            else { this.name = cardStrings.EXTENDED_DESCRIPTION[2]; }
            // Set card art.
        }
        initializeTitle();
        initializeDescription();
    }

    @Override
    public void calculateCardDamage(AbstractMonster m) {
        super.calculateCardDamage(m);
        if (GameActionManager.turn % 2 == 0) {
            this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[1];
            if (upgraded) { this.name = cardStrings.EXTENDED_DESCRIPTION[0] + "+"; }
            else { this.name = cardStrings.EXTENDED_DESCRIPTION[0]; }
            // Set card art.
		}
        else {
            this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[3];
            if (upgraded) { this.name = cardStrings.EXTENDED_DESCRIPTION[2] + "+"; }
            else { this.name = cardStrings.EXTENDED_DESCRIPTION[2]; }
            // Set card art.
		}
		initializeTitle();
		initializeDescription();
	}

    @Override
    public AbstractCard makeCopy() { return new EvenOddRhythm(); }
}