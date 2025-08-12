package tactician.events;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractEvent;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.events.GenericEventDialog;
import com.megacrit.cardcrawl.events.exordium.GoldenWing;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.relics.Circlet;
import com.megacrit.cardcrawl.vfx.RainingGoldEffect;
import com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;
import tactician.relics.StatueFragment;
import static tactician.TacticianMod.makeID;

public class GoldenWingTactician extends AbstractImageEvent {
	public static final String ID = makeID(GoldenWingTactician.class.getSimpleName());
	public static final String NAME = CardCrawlGame.languagePack.getEventString(GoldenWing.ID).NAME;
	private static final EventStrings custom_ES = CardCrawlGame.languagePack.getEventString(ID);
	private static final EventStrings vanilla_ES = CardCrawlGame.languagePack.getEventString(GoldenWing.ID);

	private static final int MIN_GOLD = 50;
	private static final int MAX_GOLD = 80;
	private static final int REQUIRED_DAMAGE = 10;
	private final int damage = 7;
	private final boolean canAttack;
	private boolean purgeResult = false;
	private CurScreen screen = CurScreen.INTRO;
	private enum CurScreen { INTRO, PURGE, MAP; }

	private static final String INTRO = vanilla_ES.DESCRIPTIONS[0];
	private static final String AGREE_DIALOG = vanilla_ES.DESCRIPTIONS[1];
	private static final String SPECIAL_OPTION = vanilla_ES.DESCRIPTIONS[2];
	private static final String DISAGREE_DIALOG = vanilla_ES.DESCRIPTIONS[3];
	private static final String STATUE_FRAGMENT = custom_ES.DESCRIPTIONS[0];

	public GoldenWingTactician() {
		super(NAME, vanilla_ES.DESCRIPTIONS[0], "images/events/goldenWing.jpg");
		this.canAttack = CardHelper.hasCardWithXDamage(10);
		this.imageEventText.setDialogOption(vanilla_ES.OPTIONS[0] + this.damage + vanilla_ES.OPTIONS[1]);
		if (this.canAttack) { this.imageEventText.setDialogOption(vanilla_ES.OPTIONS[2] + MIN_GOLD + vanilla_ES.OPTIONS[3] + MAX_GOLD + vanilla_ES.OPTIONS[4]); }
		else { this.imageEventText.setDialogOption(vanilla_ES.OPTIONS[5] + REQUIRED_DAMAGE + vanilla_ES.OPTIONS[6] , true); }
		this.imageEventText.setDialogOption(custom_ES.OPTIONS[0]);
		this.imageEventText.setDialogOption(vanilla_ES.OPTIONS[7]);
	}

	public void update() {
		super.update();
		purgeLogic();
		if (this.waitForInput) { buttonEffect(GenericEventDialog.getSelectedOption()); }
	}

	protected void buttonEffect(int buttonPressed) {
		switch (this.screen) {
			case INTRO:
				switch (buttonPressed) {
					case 0:
						this.imageEventText.updateBodyText(AGREE_DIALOG);
						AbstractDungeon.player.damage(new DamageInfo(AbstractDungeon.player, this.damage));
						AbstractDungeon.effectList.add(new FlashAtkImgEffect(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY, AbstractGameAction.AttackEffect.FIRE));
						this.screen = CurScreen.PURGE;
						this.imageEventText.updateDialogOption(0, vanilla_ES.OPTIONS[8]);
						this.imageEventText.clearRemainingOptions();
						return;
					case 1:
						if (this.canAttack) {
							int goldAmount = AbstractDungeon.miscRng.random(50, 80);
							AbstractDungeon.effectList.add(new RainingGoldEffect(goldAmount));
							AbstractDungeon.player.gainGold(goldAmount);
							AbstractEvent.logMetricGainGold("Golden Wing", "Gained Gold", goldAmount);
							this.imageEventText.updateBodyText(SPECIAL_OPTION);
							this.screen = CurScreen.MAP;
							this.imageEventText.updateDialogOption(0, vanilla_ES.OPTIONS[7]);
							this.imageEventText.clearRemainingOptions();
							return;
						}
					case 2:
						this.imageEventText.updateBodyText(STATUE_FRAGMENT);
						this.imageEventText.updateDialogOption(0, custom_ES.OPTIONS[0]);
						this.imageEventText.clearRemainingOptions();
						if (!AbstractDungeon.player.hasRelic(StatueFragment.ID)) {
							StatueFragment statueFrag = new StatueFragment();
							AbstractDungeon.getCurrRoom().spawnRelicAndObtain(this.drawX, this.drawY, statueFrag);
							logMetricObtainRelic("Golden Wing", "Statue Fragment", statueFrag);
						}
						else {
							Circlet worthless = new Circlet();
							AbstractDungeon.getCurrRoom().spawnRelicAndObtain(this.drawX, this.drawY, worthless);
							logMetricObtainRelic("Golden Wing", "Statue Fragment", worthless);
						}
						this.screen = CurScreen.MAP;
						this.imageEventText.updateDialogOption(0, vanilla_ES.OPTIONS[7]);
						return;
				}
				this.imageEventText.updateBodyText(DISAGREE_DIALOG);
				AbstractEvent.logMetricIgnored("Golden Wing");
				this.screen = CurScreen.MAP;
				this.imageEventText.updateDialogOption(0, vanilla_ES.OPTIONS[7]);
				this.imageEventText.clearRemainingOptions();
				return;

			case PURGE:
				AbstractDungeon.gridSelectScreen.open(CardGroup.getGroupWithoutBottledCards(AbstractDungeon.player.masterDeck.getPurgeableCards()), 1, vanilla_ES.OPTIONS[9], false, false, false, true);
				this.imageEventText.updateDialogOption(0, vanilla_ES.OPTIONS[7]);
				this.purgeResult = true;
				this.screen = CurScreen.MAP;
				return;

			case MAP: openMap(); return;
		}
		openMap();
	}



	private void purgeLogic() {
		if (this.purgeResult && !AbstractDungeon.isScreenUp && !AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
			AbstractCard c = AbstractDungeon.gridSelectScreen.selectedCards.get(0);
			AbstractDungeon.topLevelEffects.add(new PurgeCardEffect(c, ((float) Settings.WIDTH / 2), ((float) Settings.HEIGHT / 2)));
			AbstractEvent.logMetricCardRemovalAndDamage("Golden Wing", "Card Removal", c, this.damage);
			AbstractDungeon.player.masterDeck.removeCard(c);
			AbstractDungeon.gridSelectScreen.selectedCards.clear();

			this.purgeResult = false;
		}
	}
}