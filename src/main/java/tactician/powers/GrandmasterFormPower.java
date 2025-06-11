package tactician.powers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import tactician.TacticianMod;
import tactician.util.TextureLoader;

import static tactician.TacticianMod.imagePath;

public class GrandmasterFormPower extends AbstractPower {
	public static final String POWER_ID = TacticianMod.makeID("GrandmasterFormPower");
	private static final Texture tex84 = TextureLoader.getTexture(imagePath("Resources/images/powers/large/example.png")); // TODO: Add images here.
	private static final Texture tex32 = TextureLoader.getTexture(imagePath("Resources/images/powers/example.png")); // TODO: Add images here.
	private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
	public static final String NAME = powerStrings.NAME;
	public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

	public GrandmasterFormPower(int amount) {
		this.name = NAME;
		this.ID = POWER_ID;
		this.owner = AbstractDungeon.player;
		this.amount = amount;
		this.type = AbstractPower.PowerType.BUFF;
		this.isTurnBased = false;
		this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
		this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);
		updateDescription();
	}

	@Override
	public void onExhaust(AbstractCard card) {
		if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
			flash();
			addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new ArtifactPower(AbstractDungeon.player, this.amount), this.amount));
		}
	}

	@Override
	public void updateDescription() {
		this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
	}

	public AbstractPower makeCopy() {
		return new GrandmasterFormPower(this.amount);
	}
}