package tactician.cards.uncommon;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tactician.cards.TacticianCard;
import tactician.cards.other.Anathema;
import tactician.cards.other.Hex;
import tactician.character.TacticianRobin;
import tactician.util.CardStats;

public class Locktouch extends TacticianCard {
	public static final String ID = makeID(Locktouch.class.getSimpleName());
	private static final CardStats info = new CardStats(
			TacticianRobin.Meta.CARD_COLOR,
			CardType.SKILL,
			CardRarity.UNCOMMON,
			CardTarget.SELF,
			0
	);

	public Locktouch() {
		super(ID, info);
		setMagic(0, 1);
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		addToBot(new SFXAction("tactician:Locktouch"));
		if (this.upgraded) { addToBot(new DrawCardAction(this.magicNumber)); }
		for (AbstractCard c : AbstractDungeon.player.hand.group) {
			if (c.type == AbstractCard.CardType.CURSE || c.type == AbstractCard.CardType.STATUS) {
				addToBot(new ExhaustSpecificCardAction(c, AbstractDungeon.player.hand));
			}
		}
		addToBot(new MakeTempCardInHandAction(new Anathema(), 1));
		addToBot(new MakeTempCardInHandAction(new Hex(), 1));
	}

	@Override
	public AbstractCard makeCopy() { return new Locktouch(); }
}