package tactician.cards.basic.defends;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tactician.cards.Base4BowCard;
import tactician.character.MyCharacter;
import tactician.powers.weapons.Weapon4BowPower;
import tactician.util.CardStats;
import tactician.util.CustomTags;
import tactician.util.Wiz;

public class Defend4Bow extends Base4BowCard {
	public static final String ID = makeID(Defend4Bow.class.getSimpleName());
	private static final CardStats info = new CardStats(
			MyCharacter.Meta.CARD_COLOR,
			CardType.SKILL,
			CardRarity.BASIC,
			CardTarget.ENEMY,
			1
	);

	public Defend4Bow() {
		super(ID, info);
		setBlock(4, 3);
		tags.add(CardTags.STARTER_DEFEND);
		tags.add(CustomTags.BOW);
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		addToBot(new ApplyPowerAction(p, p, new Weapon4BowPower(p)));
		calculateCardDamage(m);
		addToBot(new GainBlockAction(p, p, this.block));
	}

	@Override
	public void calculateCardDamage(AbstractMonster m) {
		int realBlock = baseBlock;
		baseBlock += Wiz.playerWeaponCalc(m, 4);
		super.calculateCardDamage(m);
		baseBlock = realBlock;
		this.isBlockModified = (block != baseBlock);
	}

	@Override
	public AbstractCard makeCopy() {
		return new Defend4Bow();
	}
}