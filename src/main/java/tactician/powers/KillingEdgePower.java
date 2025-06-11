package tactician.powers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import tactician.TacticianMod;
import tactician.util.TextureLoader;

import static tactician.TacticianMod.imagePath;

public class KillingEdgePower extends AbstractPower {
	public static final String POWER_ID = TacticianMod.makeID("KillingEdgePower");
	private static final Texture tex84 = TextureLoader.getTexture(imagePath("Resources/images/powers/large/example.png")); // TODO: Add images here.
	private static final Texture tex32 = TextureLoader.getTexture(imagePath("Resources/images/powers/example.png")); // TODO: Add images here.
	private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
	public static final String NAME = powerStrings.NAME;
	public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

	public KillingEdgePower(AbstractCreature owner, int amount) {
		this.name = NAME;
		this.ID = POWER_ID;
		this.owner = AbstractDungeon.player;
		this.amount = amount;
		this.type = AbstractPower.PowerType.BUFF;
		this.isTurnBased = false;
		this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
		this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);
		this.updateDescription();
	}

	public void onUseCard(AbstractCard card, UseCardAction action) {
		if (card.type == AbstractCard.CardType.ATTACK) { addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, this)); }
	}

	public void updateDescription() { this.description = DESCRIPTIONS[0]; }

	public float atDamageGive(float damage, DamageInfo.DamageType type) {
		if (type == DamageInfo.DamageType.NORMAL) { return damage * 3.0F; }
		return damage;
	}
}