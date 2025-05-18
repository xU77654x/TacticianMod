package tactician.relics;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.FocusPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import tactician.cards.other.Hex;
import tactician.character.MyCharacter;
import static tactician.TacticianMod.makeID;


public class BookOfNaga extends BaseRelic {
	private static final String NAME = "BookOfNaga";
	public static final String ID = makeID(NAME);
	private static final RelicTier RARITY = RelicTier.STARTER;
	private static final LandingSound SOUND = LandingSound.CLINK;
	public AbstractPlayer p;

	public BookOfNaga() {
		super(ID, NAME, MyCharacter.Meta.CARD_COLOR, RARITY, SOUND);
		this.p = AbstractDungeon.player;
		// TODO: Relic image and level up sound.
	}

	@Override
	public String getUpdatedDescription() { return this.DESCRIPTIONS[0]; }

	@Override
	public void atBattleStart() {
		addToBot(new ApplyPowerAction(p, p, new FocusPower(p, 1)));
		addToBot(new MakeTempCardInHandAction(new Hex(), 1));
	}

	/*
	@Override
	public void onRightClick() {
		if (Boolean.TRUE.equals(this.used)) { addToBot(new TalkAction(true, this.DESCRIPTIONS[2], 1.0F, 2.0F)); }
		addToBot(new WaitAction(0.1F));
		if (Boolean.TRUE.equals(!this.used) && (!AbstractDungeon.getCurrRoom().isBattleOver && AbstractDungeon.getCurrRoom().monsters != null && AbstractDungeon.getCurrRoom().monsters.monsters != null && !AbstractDungeon.getCurrRoom().monsters.monsters.isEmpty() && !AbstractDungeon.getMonsters().areMonstersBasicallyDead())) {
			flash();
			stopPulse();
		}
		else { addToBot(new TalkAction(true, "Let me think for NL another moment...", 1.0F, 2.0F)); }
	} // Credit to Balls: Rubber Bouncy Ball for the code to determine if the room has living monsters as a check for right-click relics.
	  // "public class SecretBook extends BaseRelic implements ClickableRelic" */

	@Override
	public AbstractRelic makeCopy() { return new BookOfNaga(); }
}