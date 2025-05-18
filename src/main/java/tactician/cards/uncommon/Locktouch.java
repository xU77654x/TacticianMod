package tactician.cards.uncommon;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tactician.cards.BaseCard;
import tactician.cards.other.Anathema;
import tactician.cards.other.Hex;
import tactician.character.MyCharacter;
import tactician.util.CardStats;

public class Locktouch extends BaseCard {
	public static final String ID = makeID(Locktouch.class.getSimpleName());
	private static final CardStats info = new CardStats(
			MyCharacter.Meta.CARD_COLOR,
			CardType.SKILL,
			CardRarity.UNCOMMON,
			CardTarget.SELF,
			0
	);

	public Locktouch() {
		super(ID, info);
		this.exhaust = true;
		setSelfRetain(false, true);
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		// TODO: Exhaust held Statuses and Curses.
		addToBot(new MakeTempCardInHandAction(new Anathema(), 1));
		addToBot(new MakeTempCardInHandAction(new Hex(), 1));
	}

	@Override
	public AbstractCard makeCopy() {
		return new Locktouch();
	}

}