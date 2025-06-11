package tactician.relics;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.FocusPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import tactician.cards.other.Anathema;
import tactician.character.MyCharacter;
import static tactician.TacticianMod.makeID;


public class SecretBook extends BaseRelic {
	private static final String NAME = "SecretBook";
	public static final String ID = makeID(NAME);
	private static final RelicTier RARITY = RelicTier.STARTER;
	private static final LandingSound SOUND = LandingSound.CLINK;
	public AbstractPlayer p;

	public SecretBook() {
		super(ID, NAME, MyCharacter.Meta.CARD_COLOR, RARITY, SOUND);
		this.p = AbstractDungeon.player;
		// TODO: Relic image and level up sound.
	}

	@Override
	public String getUpdatedDescription() { return this.DESCRIPTIONS[0]; }

	@Override
	public void atBattleStart() {
		flash();
		addToTop(new ApplyPowerAction(p, p, new FocusPower(p, 1), 1));
		addToBot(new MakeTempCardInHandAction(new Anathema(), 1));
	}

	@Override
	public AbstractRelic makeCopy() { return new SecretBook(); }
}