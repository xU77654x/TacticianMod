package tactician.powers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import tactician.TacticianMod;
import tactician.util.CustomTags;
import tactician.util.TextureLoader;

import static tactician.TacticianMod.imagePath;

public class LunaPower extends AbstractPower {
	public static final String POWER_ID = TacticianMod.makeID("LunaPower");
	private static final Texture tex84 = TextureLoader.getTexture(imagePath("Resources/images/powers/large/example.png")); // TODO: Add images here.
	private static final Texture tex32 = TextureLoader.getTexture(imagePath("Resources/images/powers/example.png")); // TODO: Add images here.
	private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
	public static final String NAME = powerStrings.NAME;
	public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

	public LunaPower(int amount) {
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
	public void updateDescription() {
		if (this.amount == 1) { this.description = DESCRIPTIONS[2]; }
		else { this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1]; }
	}

	@Override
	public void onUseCard(AbstractCard c, UseCardAction action) {
		if (!c.purgeOnUse && c.type == AbstractCard.CardType.ATTACK && c.hasTag(CustomTags.COMBAT_ART) && this.amount > 0) {
			flash();
			AbstractMonster m = null;
			if (action.target != null) { m = (AbstractMonster)action.target; }
			AbstractCard tmp = c.makeSameInstanceOf();
			AbstractDungeon.player.limbo.addToBottom(tmp);
			tmp.current_x = c.current_x;
			tmp.current_y = c.current_y;
			tmp.target_x = Settings.WIDTH / 2.0F - 300.0F * Settings.scale;
			tmp.target_y = Settings.HEIGHT / 2.0F;
			if (m != null) { tmp.calculateCardDamage(m); }
			tmp.purgeOnUse = true;
			AbstractDungeon.actionManager.addCardQueueItem(new CardQueueItem(tmp, m, c.energyOnUse, true, true), true);
			this.amount--;
			if (this.amount == 0) { addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, LunaPower.POWER_ID)); }
		}
	}
}