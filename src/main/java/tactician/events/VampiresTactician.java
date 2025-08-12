package tactician.events;

import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractEvent;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.events.city.Vampires;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.relics.BloodVial;
import com.megacrit.cardcrawl.relics.Vajra;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import tactician.cards.other.Anathema;
import tactician.cards.other.Hex;
import static tactician.TacticianMod.makeID;

public class VampiresTactician extends AbstractImageEvent {
	public static final String ID = makeID(VampiresTactician.class.getSimpleName());
	public static final String NAME = CardCrawlGame.languagePack.getEventString(Vampires.ID).NAME;
	private static final EventStrings custom_ES = CardCrawlGame.languagePack.getEventString(ID);
	private static final EventStrings vanilla_ES = CardCrawlGame.languagePack.getEventString(Vampires.ID);

	private static final String HEX = custom_ES.DESCRIPTIONS[0];
	private static final String ANATHEMA = custom_ES.DESCRIPTIONS[1];
	private static final String VAJRA = custom_ES.DESCRIPTIONS[2];
	private static final String LEAVE = vanilla_ES.DESCRIPTIONS[3];

	private int screenNum = 0; // Cannot use CUR_SCREEN due to INTRO not pulling the Vampire string without crashing.
	private final int hpHeal = 5;
	private final int hpLose = 5;

	public VampiresTactician() {
		super(NAME, vanilla_ES.DESCRIPTIONS[0], "images/events/vampires.jpg");
		this.body = AbstractDungeon.player.getVampireText();
		this.imageEventText.setDialogOption(custom_ES.OPTIONS[0] + hpHeal + custom_ES.OPTIONS[1]);
		this.imageEventText.setDialogOption(custom_ES.OPTIONS[2] + hpLose + custom_ES.OPTIONS[3]);
		if (AbstractDungeon.player.hasRelic(BloodVial.ID)) { this.imageEventText.setDialogOption(custom_ES.OPTIONS[4], new Vajra()); } // Remove the "new Vajra()"?
		else { this.imageEventText.setDialogOption(custom_ES.OPTIONS[5], true); }
		this.imageEventText.setDialogOption(vanilla_ES.OPTIONS[2]);
	}

	protected void buttonEffect(int buttonPressed) {
		switch (this.screenNum) {
			case 0:
				this.imageEventText.updateBodyText(this.body);
				switch (buttonPressed) {
					case 0:
						AbstractDungeon.player.heal(this.hpHeal);
						Hex hex = new Hex();
						AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(hex, Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
						CardCrawlGame.sound.playV("tactician:Goetia", 1.25f);
						this.imageEventText.updateBodyText(HEX);
						this.imageEventText.updateDialogOption(0, vanilla_ES.OPTIONS[5]);
						this.imageEventText.clearRemainingOptions();
						this.screenNum = 2;
						return;
					case 1:
						AbstractDungeon.player.damage(new DamageInfo(AbstractDungeon.player, this.hpLose));
						Anathema ana = new Anathema();
						AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(ana, Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
						CardCrawlGame.sound.playV("tactician:Expiration", 1.25f);
						this.imageEventText.updateBodyText(ANATHEMA);
						this.imageEventText.updateDialogOption(0, vanilla_ES.OPTIONS[5]);
						this.imageEventText.clearRemainingOptions();
						this.screenNum = 2;
						return;
					case 2:
						AbstractDungeon.player.loseRelic(BloodVial.ID);
						// if (!AbstractDungeon.player.hasRelic(Vajra.ID)) {
							Vajra vajra = new Vajra();
							AbstractDungeon.getCurrRoom().spawnRelicAndObtain(this.drawX, this.drawY, vajra);
							logMetricObtainRelic("Vampires", "Vajra", vajra);
						// }
						/*else {
							Circlet worthless = new Circlet();
							AbstractDungeon.getCurrRoom().spawnRelicAndObtain(this.drawX, this.drawY, worthless);
							logMetricObtainRelic("Vampires", "Vajra", worthless);
						} */
						CardCrawlGame.sound.playV("tactician:Charm", 1.67f);
						this.imageEventText.updateBodyText(VAJRA);
						this.imageEventText.updateDialogOption(0, vanilla_ES.OPTIONS[5]);
						this.imageEventText.clearRemainingOptions();
						this.screenNum = 2;
						return;
				}
				this.imageEventText.updateBodyText(LEAVE);
				AbstractEvent.logMetricIgnored("Vampires");
				this.screenNum = 2;
				this.imageEventText.updateDialogOption(0, vanilla_ES.OPTIONS[5]);
				this.imageEventText.clearRemainingOptions();
				return;

			case 1: openMap(); return;
		}
		openMap();
	}
}