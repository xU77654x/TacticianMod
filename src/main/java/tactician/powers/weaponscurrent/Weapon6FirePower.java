package tactician.powers.weaponscurrent;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import tactician.TacticianMod;
import tactician.powers.ZealPower;
import tactician.util.TextureLoader;
import static tactician.TacticianMod.imagePath;

public class Weapon6FirePower extends AbstractPower {

	public static final String POWER_ID = TacticianMod.makeID("Weapon6FirePower");
	private static final Texture tex84 = TextureLoader.getTexture(imagePath("Resources/images/powers/large/example.png")); // TODO: Add images here.
	private static final Texture tex32 = TextureLoader.getTexture(imagePath("Resources/images/powers/example.png")); // TODO: Add images here.
	private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
	public static final String NAME = powerStrings.NAME;
	public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
	public AbstractPlayer p;

	public Weapon6FirePower(AbstractCreature owner) {
		this.name = NAME;
		this.ID = POWER_ID;
		this.owner = owner;
		this.amount = -1;
		this.type = AbstractPower.PowerType.BUFF;
		this.isTurnBased = false;
		this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
		this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);
		this.updateDescription();
		this.p = AbstractDungeon.player;
		priority = 1;
	}

	@Override
	public void onInitialApplication() {
		super.onInitialApplication();
		if (owner.hasPower(Weapon1SwordPower.POWER_ID)) { addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, Weapon1SwordPower.POWER_ID)); }
		if (owner.hasPower(Weapon2LancePower.POWER_ID)) { addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, Weapon2LancePower.POWER_ID)); }
		if (owner.hasPower(Weapon3AxePower.POWER_ID)) { addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, Weapon3AxePower.POWER_ID)); }
		if (owner.hasPower(Weapon4BowPower.POWER_ID)) { addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, Weapon4BowPower.POWER_ID)); }
		if (owner.hasPower(Weapon5WindPower.POWER_ID)) { addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, Weapon5WindPower.POWER_ID)); }
		if (owner.hasPower(Weapon7ThunderPower.POWER_ID)) { addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, Weapon7ThunderPower.POWER_ID)); }
		if (owner.hasPower(Weapon8DarkPower.POWER_ID)) { addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, Weapon8DarkPower.POWER_ID)); }
	}

	public float atDamageGive(float damage, DamageInfo.DamageType type) {
		if (type == DamageInfo.DamageType.NORMAL) {
			if (owner != this.p) {
				if (this.p.hasPower(ZealPower.POWER_ID)) { return damage -3; }
				else if (this.p.hasPower(Weapon7ThunderPower.POWER_ID) || this.p.hasPower(Weapon8DarkPower.POWER_ID)) { return damage -3; }
				else if (this.p.hasPower(Weapon5WindPower.POWER_ID) || this.p.hasPower(Weapon4BowPower.POWER_ID)) { return damage +3; }
			}
		}
		return damage;
	}

	public void updateDescription() { this.description = DESCRIPTIONS[0];}
}