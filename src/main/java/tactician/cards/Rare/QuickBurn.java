package tactician.cards.Rare;

import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tactician.actions.QuickBurnAction;
import tactician.cards.BaseCard;
import tactician.character.MyCharacter;
import tactician.util.CardStats;

public class QuickBurn extends BaseCard {
	public static final String ID = makeID(QuickBurn.class.getSimpleName());
	private static final CardStats info = new CardStats(
			MyCharacter.Meta.CARD_COLOR,
			CardType.POWER,
			CardRarity.RARE,
			CardTarget.SELF,
			3
	);

	public QuickBurn() {
		super(ID, info);
		setCostUpgrade(2);
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		addToBot(new TalkAction(true, cardStrings.EXTENDED_DESCRIPTION[0], 1.0F, 2.0F));
		addToBot(new QuickBurnAction());
	}

	@Override
	public AbstractCard makeCopy() { return new QuickBurn(); }
}