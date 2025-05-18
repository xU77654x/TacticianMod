package tactician.cards.other;

import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.mod.stslib.actions.tempHp.AddTemporaryHPAction;
import com.evacipated.cardcrawl.mod.stslib.patches.FlavorText;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.Frost;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.LoseDexterityPower;
import com.megacrit.cardcrawl.relics.BlueCandle;
import tactician.cards.BaseCard;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tactician.character.MyCharacter;
import tactician.powers.DeflectPower;
import tactician.powers.ShovePower;
import tactician.util.CardStats;

public class Anathema extends BaseCard {
	public static final String ID = makeID(Anathema.class.getSimpleName());
	private static final CardStats info = new CardStats(
			MyCharacter.Meta.CARD_COLOR,
			CardType.CURSE,
			CardRarity.CURSE,
			CardTarget.SELF,
			0
	);
	public AbstractPlayer p;

	public Anathema() {
		super(ID, info);
		setMagic(3);
		this.retain = true;
		this.exhaust = true;
		FlavorText.AbstractCardFlavorFields.boxColor.set(this, Color.PURPLE.cpy());
		FlavorText.AbstractCardFlavorFields.textColor.set(this, Color.WHITE.cpy());
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		if (Boolean.TRUE.equals(p.hasRelic(BlueCandle.ID))) {
			if (Boolean.TRUE.equals(p.hasPower(ShovePower.POWER_ID))) {
				addToTop(new AddTemporaryHPAction(p, p, 1));
			}
		}
		else if (Boolean.TRUE.equals(p.hasPower(ShovePower.POWER_ID))) { /* Yes, I know that having an empty if statement is "bad code" or whatever, but if I were to nest everything under here by using FALSE here, there wouldn't be text indicating that the player is immune to the decrease caused by playing this card. So I choose to leave the code as it is unless someone knows a better way which still lets the logic in this card function while the Immune text still appears. */ }
		else if (Boolean.TRUE.equals(p.hasPower(DeflectPower.POWER_ID)) && (p.getPower(DeflectPower.POWER_ID).amount >= this.magicNumber)) {
			addToBot(new ReducePowerAction(p, p, p.getPower(DeflectPower.POWER_ID), this.magicNumber));
		}
		else {
			if (Boolean.TRUE.equals(p.hasPower(LoseDexterityPower.POWER_ID))) {
				addToBot(new ApplyPowerAction(p, p, new LoseDexterityPower(p, 1), 1));
				addToBot(new ApplyPowerAction(p, p, new DexterityPower(p, 1), 1));
			}
			else { addToBot(new ApplyPowerAction(p, p, new LoseDexterityPower(p, 1), 1)); }
		}
	}

	@Override
	public void triggerOnExhaust() { AbstractDungeon.player.channelOrb(new Frost()); }

	@Override
	public AbstractCard makeCopy() { return new Anathema(); }
}