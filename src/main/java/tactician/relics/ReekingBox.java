package tactician.relics;
import com.evacipated.cardcrawl.mod.stslib.relics.ClickableRelic;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.MonsterRoomElite;
import tactician.character.MyCharacter;
import tactician.powers.ReekingSwapPower;

import static tactician.TacticianMod.makeID;

public class ReekingBox extends BaseRelic implements ClickableRelic {
	private static final String NAME = "ReekingBox";
	public static final String ID = makeID(NAME);
	private static final RelicTier RARITY = RelicTier.STARTER;
	private static final LandingSound SOUND = LandingSound.CLINK;
	private boolean used = false;
	public AbstractPlayer p;

	public ReekingBox() {
		super(ID, NAME, MyCharacter.Meta.CARD_COLOR, RARITY, SOUND);
		this.p = AbstractDungeon.player;
		// TODO: Relic image and level up sound.
	}

	@Override
	public String getUpdatedDescription() { return this.DESCRIPTIONS[0] + this.counter + this.DESCRIPTIONS[1]; }

	public void onEquip() {
		this.grayscale = true;
		this.counter = 5;
		this.used = false;
	}

	public void setCounter(int c) {
		this.counter = c;
		if (this.counter == 0) { this.description = this.DESCRIPTIONS[3]; }
		else if (this.counter == 1) { this.description = this.DESCRIPTIONS[0] + this.counter + this.DESCRIPTIONS[2]; }
		else { this.description = this.DESCRIPTIONS[0] + this.counter + this.DESCRIPTIONS[1]; }
	}

	@Override
	public void onRightClick() {
		if (Boolean.TRUE.equals(this.used)) { addToBot(new TalkAction(true, this.DESCRIPTIONS[4], 1.0F, 2.0F)); }
		else if (Boolean.TRUE.equals(!this.used)) {
			flash();
			this.counter--;
			if (this.counter == 0) { this.used = true; }
			if (this.grayscale) {
				this.grayscale = false;
				addToBot(new ApplyPowerAction(p, p, new ReekingSwapPower()));
			}
			else if (!this.grayscale) {
				this.grayscale = true;
				addToBot(new RemoveSpecificPowerAction(p, p, p.getPower(ReekingSwapPower.POWER_ID)));
			}
			// TODO: Play a sound effect indicating this relic's successful activation.
		}
	}

	@Override
	public void atBattleStart() { if (!this.grayscale) { addToBot(new ApplyPowerAction(p, p, new ReekingSwapPower())); } }

	@Override
	public void onVictory() {
		if (AbstractDungeon.getCurrRoom() instanceof MonsterRoomElite) {
			flash();
			if (this.counter == 0) { this.used = false; }
			this.counter++;
		}
	}

	@Override
	public AbstractRelic makeCopy() { return new ReekingBox(); }
}