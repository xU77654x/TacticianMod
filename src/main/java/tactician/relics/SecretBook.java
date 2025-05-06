package tactician.relics;

import com.evacipated.cardcrawl.mod.stslib.relics.ClickableRelic;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.Frost;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import tactician.character.MyCharacter;
import tactician.powers.DeflectPower;
import static tactician.TacticianMod.makeID;


public class SecretBook extends BaseRelic implements ClickableRelic {
	private static final String NAME = "SecretBook";
	public static final String ID = makeID(NAME);
	private static final RelicTier RARITY = RelicTier.STARTER;
	private static final LandingSound SOUND = LandingSound.CLINK;
	private static final int CONSUMEDEFLECT = 3;
	private boolean used = false;
	public AbstractPlayer p;

	public SecretBook() {
		super(ID, NAME, MyCharacter.Meta.CARD_COLOR, RARITY, SOUND);
		this.p = AbstractDungeon.player;
		// TODO: Relic image and level up sound.
	}

	@Override
	public String getUpdatedDescription() { return this.DESCRIPTIONS[0] + CONSUMEDEFLECT + this.DESCRIPTIONS[1]; }

	@Override
	public void atTurnStart() {
		this.used = false;
		beginLongPulse();
	}

	@Override
	// TODO: Play a sound effect indicating this relic'
	public void onRightClick() {
		if (Boolean.TRUE.equals(this.used)) { addToBot(new TalkAction(true, this.DESCRIPTIONS[2], 1.0F, 2.0F)); }
		addToBot(new WaitAction(0.1F));
		if (Boolean.TRUE.equals(!this.used) && (!AbstractDungeon.getCurrRoom().isBattleOver && AbstractDungeon.getCurrRoom().monsters != null && AbstractDungeon.getCurrRoom().monsters.monsters != null && !AbstractDungeon.getCurrRoom().monsters.monsters.isEmpty() && !AbstractDungeon.getMonsters().areMonstersBasicallyDead())) {
			flash();
			if (Boolean.TRUE.equals(!this.p.hasPower(DeflectPower.POWER_ID))) { addToBot(new TalkAction(true, this.DESCRIPTIONS[3], 1.0F, 2.0F)); }
			else if (Boolean.TRUE.equals(this.p.hasPower(DeflectPower.POWER_ID)) && (this.p.getPower(DeflectPower.POWER_ID).amount < CONSUMEDEFLECT)) {
				addToBot(new TalkAction(true, this.DESCRIPTIONS[4] + CONSUMEDEFLECT + this.DESCRIPTIONS[5], 1.0F, 2.0F));
			}
			else if (Boolean.TRUE.equals(this.p.hasPower(DeflectPower.POWER_ID)) && (this.p.getPower(DeflectPower.POWER_ID).amount >= CONSUMEDEFLECT)) {
				this.used = true;
				flash();
				stopPulse();
				addToBot(new ReducePowerAction(this.p, this.p, this.p.getPower(DeflectPower.POWER_ID), CONSUMEDEFLECT));
				this.p.channelOrb(new Frost());
			}
			else { addToBot(new TalkAction(true, "Let me think for NL another moment...", 1.0F, 2.0F)); }
		}

	} // Credit to Balls: Rubber Bouncy Ball for the code to determine if the room has living monsters as a check for right-click relics.

	@Override
	public AbstractRelic makeCopy() { return new SecretBook(); }
}