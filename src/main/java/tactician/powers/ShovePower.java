package tactician.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import tactician.TacticianMod;
import tactician.util.TextureLoader;

import static tactician.TacticianMod.imagePath;

public class ShovePower extends AbstractPower implements CloneablePowerInterface {
	public static final String POWER_ID = TacticianMod.makeID("ShovePower");
	private static final Texture tex84 = TextureLoader.getTexture(imagePath("Resources/images/powers/large/example.png")); // TODO: Add images here.
	private static final Texture tex32 = TextureLoader.getTexture(imagePath("Resources/images/powers/example.png")); // TODO: Add images here.
	private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
	public static final String NAME = powerStrings.NAME;
	public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

	public ShovePower(int amount) {
		this.name = NAME;
		this.ID = POWER_ID;
		this.owner = AbstractDungeon.player;
		this.amount = amount;
		this.type = AbstractPower.PowerType.BUFF;
		this.isTurnBased = true;
		this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
		this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);
		updateDescription();
	}

	public void atEndOfRound() {
		if (this.amount == 0) { addToBot((new RemoveSpecificPowerAction(this.owner, this.owner, ShovePower.POWER_ID))); }
		else { addToBot(new ReducePowerAction(this.owner, this.owner, ShovePower.POWER_ID, 1)); }
	}

	// TODO: Having ShovePower prevents you from receiving SealAtkPower.

	public void updateDescription() { this.description = DESCRIPTIONS[0]; }

	@Override
	public AbstractPower makeCopy() { return new ShovePower(this.amount); }
}