package tactician.cards.basic.strikes;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tactician.cards.Base6FireCard;
import tactician.character.MyCharacter;
import tactician.powers.weapons.Weapon6FirePower;
import tactician.util.CardStats;
import tactician.util.CustomTags;
import tactician.util.Wiz;

public class Strike6Fire extends Base6FireCard {
	public static final String ID = makeID(Strike6Fire.class.getSimpleName());
	private static final CardStats info = new CardStats(
			MyCharacter.Meta.CARD_COLOR,
			CardType.ATTACK,
			CardRarity.BASIC,
			CardTarget.ENEMY,
			1
	);

	public Strike6Fire() {
		super(ID, info);
		setDamage(5, 3);
		tags.add(CardTags.STARTER_STRIKE);
		tags.add(CardTags.STRIKE);
		tags.add(CustomTags.FIRE);
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		addToBot(new ApplyPowerAction(p, p, new Weapon6FirePower(p)));
		calculateCardDamage(m);
		addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
	}

	@Override
	public void calculateCardDamage(AbstractMonster m) {
		int realDamage = baseDamage;
		baseDamage += Wiz.playerWeaponCalc(m, 6);
		super.calculateCardDamage(m);
		baseDamage = realDamage;
		this.isDamageModified = (damage != baseDamage);
	}

	@Override
	public AbstractCard makeCopy() {
		return new Strike6Fire();
	}
}