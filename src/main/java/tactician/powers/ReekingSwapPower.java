package tactician.powers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import tactician.TacticianMod;
import tactician.util.TextureLoader;

import static tactician.TacticianMod.imagePath;

public class ReekingSwapPower extends AbstractPower {
	public static final String POWER_ID = TacticianMod.makeID("ReekingSwapPower");
	private static final Texture tex84 = TextureLoader.getTexture(imagePath("Resources/images/powers/large/example.png")); // TODO: Add images here.
	private static final Texture tex32 = TextureLoader.getTexture(imagePath("Resources/images/powers/example.png")); // TODO: Add images here.
	private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
	public static final String NAME = powerStrings.NAME;
	public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

	public ReekingSwapPower() {
		this.name = NAME;
		this.ID = POWER_ID;
		this.owner = AbstractDungeon.player;
		this.amount = -1;
		this.type = AbstractPower.PowerType.BUFF;
		this.isTurnBased = false;
		this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
		this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);
		this.updateDescription();
		priority = Integer.MAX_VALUE - 2;
	}

	public void updateDescription() { this.description = DESCRIPTIONS[0]; }

	/*
	public void onInitialApplication() {
		for (AbstractCard c : AbstractDungeon.player.masterDeck.group) {
			if (c.hasTag(AbstractCard.CardTags.STARTER_STRIKE)) {
				c.type = AbstractCard.CardType.SKILL;
				c.tags.add(AbstractCard.CardTags.STARTER_DEFEND);
				c.tags.remove(AbstractCard.CardTags.STRIKE);
				c.tags.remove(AbstractCard.CardTags.STARTER_STRIKE);
				// c.var = 1;

				if (!c.upgraded) {
					c.name = cardStrings.EXTENDED_DESCRIPTION[0];
					c.rawDescription = cardStrings.EXTENDED_DESCRIPTION[1];
				} else {
					c.name = cardStrings.EXTENDED_DESCRIPTION[2];
					c.rawDescription = cardStrings.EXTENDED_DESCRIPTION[3];
				}


			} else if (c.hasTag(AbstractCard.CardTags.STARTER_DEFEND)) {
				c.type = AbstractCard.CardType.ATTACK;
				c.tags.add(AbstractCard.CardTags.STARTER_STRIKE);
				c.tags.add(AbstractCard.CardTags.STRIKE);
				c.tags.remove(AbstractCard.CardTags.STARTER_DEFEND);
				// c.var = 0;
				if (!c.upgraded) {
					c.name = cardStrings.EXTENDED_DESCRIPTION[0];
					c.rawDescription = cardStrings.EXTENDED_DESCRIPTION[1];
				} else {
					c.name = cardStrings.EXTENDED_DESCRIPTION[2];
					c.rawDescription = cardStrings.EXTENDED_DESCRIPTION[3];
				}
			}
		}
	} */
}