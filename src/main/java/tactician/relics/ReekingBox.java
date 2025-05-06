package tactician.relics;
import com.evacipated.cardcrawl.mod.stslib.relics.ClickableRelic;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.MonsterRoomElite;
import tactician.character.MyCharacter;

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
		// TODO: Relic image and level up sound.
	}

	@Override
	public String getUpdatedDescription() { return this.DESCRIPTIONS[0]; }

	@Override
	public void onRightClick() {
		if (Boolean.TRUE.equals(this.used)) { addToBot(new TalkAction(true, this.DESCRIPTIONS[1], 1.0F, 2.0F)); }
		addToBot(new WaitAction(0.1F));
		if (Boolean.TRUE.equals(!this.used) && (!AbstractDungeon.getCurrRoom().isBattleOver && AbstractDungeon.getCurrRoom().monsters != null && AbstractDungeon.getCurrRoom().monsters.monsters != null && !AbstractDungeon.getCurrRoom().monsters.monsters.isEmpty() && !AbstractDungeon.getMonsters().areMonstersBasicallyDead())) {
			flash();
			this.counter--;
			if (this.counter == 0) { this.used = true; }
			int basicCount = 0;
			for (AbstractCard c : AbstractDungeon.player.masterDeck.group) {
				if ((c.hasTag(AbstractCard.CardTags.STARTER_STRIKE)) || (c.hasTag(AbstractCard.CardTags.STARTER_DEFEND))) { basicCount++; }
			}
			// TODO: Play a sound effect indicating this relic's successful activation.
		}
	}

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