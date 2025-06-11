package tactician.cards.uncommon;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import tactician.cards.BaseCard;
import tactician.character.MyCharacter;
import tactician.powers.DeathBlowPower;
import tactician.util.CardStats;

public class DeathBlow extends BaseCard {
	public static final String ID = makeID(DeathBlow.class.getSimpleName());
	private static final CardStats info = new CardStats(
			MyCharacter.Meta.CARD_COLOR,
			CardType.POWER,
			CardRarity.UNCOMMON,
			CardTarget.SELF,
			1
	);

	public DeathBlow() {
		super(ID, info);
		setMagic(2, 0);
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		addToBot(new ApplyPowerAction(p, p, new DeathBlowPower(this.magicNumber), this.magicNumber));
		if (this.upgraded) {
			addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, this.magicNumber), this.magicNumber));
			addToBot(new ApplyPowerAction(p, p, new LoseStrengthPower(p, this.magicNumber), this.magicNumber));
		}
	}

	@Override
	public AbstractCard makeCopy() { return new DeathBlow(); }
}