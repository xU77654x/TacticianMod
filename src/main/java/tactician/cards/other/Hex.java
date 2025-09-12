package tactician.cards.other;

import com.evacipated.cardcrawl.mod.stslib.actions.tempHp.AddTemporaryHPAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.Lightning;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.relics.BlueCandle;
import tactician.actions.PlaySoundAction;
import tactician.cards.TacticianCard;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tactician.character.TacticianRobin;
import tactician.powers.CreationPulsePower;
import tactician.powers.DeflectPower;
import tactician.powers.ShovePower;
import tactician.util.CardStats;

public class Hex extends TacticianCard {
	public static final String ID = makeID(Hex.class.getSimpleName());
	private static final CardStats info = new CardStats(
			TacticianRobin.Meta.CARD_COLOR,
			CardType.CURSE,
			CardRarity.CURSE,
			CardTarget.SELF,
			0
	);
	public AbstractPlayer p;

	public Hex() {
		super(ID, info);
		setMagic(4);
		setSelfRetain(true);
		setExhaust(true);
		setBackgroundTexture("tactician/images/character/cardback/bg_curse.png", "tactician/images/character/cardback/bg_curse_p.png");
		// FlavorText.AbstractCardFlavorFields.boxColor.set(this, Color.PURPLE.cpy());
		// FlavorText.AbstractCardFlavorFields.textColor.set(this, Color.WHITE.cpy());
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		addToBot(new PlaySoundAction("tactician:Hex", 1.20f));
		if (Boolean.TRUE.equals(p.hasPower(ShovePower.POWER_ID) && p.hasPower(CreationPulsePower.POWER_ID))) {
			int val = p.getPower(CreationPulsePower.POWER_ID).amount;
			addToBot(new ApplyPowerAction(m, p, new VulnerablePower(m, val, false), val, true, AbstractGameAction.AttackEffect.NONE));
		}
		if (Boolean.TRUE.equals(p.hasRelic(BlueCandle.ID) && p.hasPower(ShovePower.POWER_ID))) { addToTop(new AddTemporaryHPAction(p, p, 1)); } // Blue Candle won't make the player lose HP if they have ShovePower.
		else if (Boolean.FALSE.equals(p.hasRelic(BlueCandle.ID) || p.hasPower(ShovePower.POWER_ID) /* || p.hasPower(LoseStrengthPower.POWER_ID) */ )) { // No Deflect is consumed or stats lost if the player has LoseStrengthPower or ShovePower.
			if (Boolean.TRUE.equals(p.hasPower(DeflectPower.POWER_ID)) && (p.getPower(DeflectPower.POWER_ID).amount >= this.magicNumber)) {
				addToBot(new ReducePowerAction(p, p, p.getPower(DeflectPower.POWER_ID), this.magicNumber));
			}
			else {
				if (!p.hasPower(ArtifactPower.POWER_ID)) { addToBot(new PlaySoundAction("tactician:StatDecreaseFE", 1.00f)); }
				addToBot(new ApplyPowerAction(p, p, new LoseStrengthPower(p, 1), 1));
			}
		}
	}

	@Override
	public void triggerOnExhaust() { AbstractDungeon.player.channelOrb(new Lightning()); }

	@Override
	public AbstractCard makeCopy() { return new Hex(); }
}