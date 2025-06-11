package tactician.cards.other;

import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.mod.stslib.actions.tempHp.AddTemporaryHPAction;
import com.evacipated.cardcrawl.mod.stslib.patches.FlavorText;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.Lightning;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.relics.BlueCandle;
import tactician.cards.BaseCard;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tactician.character.MyCharacter;
import tactician.powers.CreationPulsePower;
import tactician.powers.DeflectPower;
import tactician.powers.ShovePower;
import tactician.util.CardStats;

public class Hex extends BaseCard {
	public static final String ID = makeID(Hex.class.getSimpleName());
	private static final CardStats info = new CardStats(
			MyCharacter.Meta.CARD_COLOR,
			CardType.CURSE,
			CardRarity.CURSE,
			CardTarget.SELF,
			0
	);
	public AbstractPlayer p;

	public Hex() {
		super(ID, info);
		setMagic(3);
		this.retain = true;
		this.exhaust = true;
		FlavorText.AbstractCardFlavorFields.boxColor.set(this, Color.PURPLE.cpy());
		FlavorText.AbstractCardFlavorFields.textColor.set(this, Color.WHITE.cpy());
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		if (Boolean.TRUE.equals(p.hasPower(ShovePower.POWER_ID) && p.hasPower(CreationPulsePower.POWER_ID))) {
			addToBot(new ApplyPowerAction(p, p, new LoseStrengthPower(p, 1), 1));
			// This has the unfortunate side effect of consuming Artifact, but I don't know how to avoid this yet.
		}
		else if (Boolean.TRUE.equals(p.hasRelic(BlueCandle.ID) && p.hasPower(ShovePower.POWER_ID))) { addToTop(new AddTemporaryHPAction(p, p, 1)); } // Blue Candle won't make the player lose HP if they have ShovePower.
		else if (Boolean.FALSE.equals(p.hasRelic(BlueCandle.ID) || p.hasPower(ShovePower.POWER_ID) || p.hasPower(LoseStrengthPower.POWER_ID))) { // No Deflect is consumed or stats lost if the player has LoseStrengthPower or ShovePower.
			if (p.hasPower(ShovePower.POWER_ID)) {
				addToBot(new ApplyPowerAction(p, p, new LoseStrengthPower(p, 1), 1));
			} else if (Boolean.TRUE.equals(p.hasPower(DeflectPower.POWER_ID)) && (p.getPower(DeflectPower.POWER_ID).amount >= this.magicNumber)) {
				addToBot(new ReducePowerAction(p, p, p.getPower(DeflectPower.POWER_ID), this.magicNumber));
			} else {
				addToBot(new ApplyPowerAction(p, p, new LoseStrengthPower(p, 1), 1));
			}
		}
	}

	@Override
	public void triggerOnExhaust() { AbstractDungeon.player.channelOrb(new Lightning()); }

	@Override
	public AbstractCard makeCopy() { return new Hex(); }
}