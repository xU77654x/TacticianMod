package tactician.cards.CardChoice;

import basemod.AutoAdd;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tactician.cards.BaseCard;
import tactician.character.MyCharacter;
import tactician.util.CardStats;

@AutoAdd.Ignore
public class TempFocus extends BaseCard {
	public static final String ID = makeID(TempFocus.class.getSimpleName());
	private static final CardStats info = new CardStats(
			MyCharacter.Meta.CARD_COLOR,
			CardType.SKILL,
			CardRarity.SPECIAL,
			CardTarget.NONE,
			-2
	);
	private Runnable onUseOrChosen;

	public TempFocus(Runnable onUseOrChosen) {
		super(ID, info);
		setMagic(2, 1);
		this.onUseOrChosen = onUseOrChosen;
	}

	@Override
	public void onChoseThisOption() { onUseOrChosen.run(); }

	@Override
	public void use(AbstractPlayer p, AbstractMonster M) { onUseOrChosen.run(); }

	@Override
	public AbstractCard makeCopy() { return new TempFocus(onUseOrChosen); }
}