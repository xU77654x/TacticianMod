package tactician.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class GainFragileRelicAction extends AbstractGameAction {
	private final AbstractRelic relicToAdd;
	private final boolean startOfCombat;

	public GainFragileRelicAction(AbstractRelic relicToAdd, boolean startOfCombat) {
		this.relicToAdd = relicToAdd;
		setValues(AbstractDungeon.player, AbstractDungeon.player, 1);
		this.actionType = AbstractGameAction.ActionType.SPECIAL;
		this.duration = Settings.ACTION_DUR_MED;
		this.startOfCombat = startOfCombat;
		AbstractPlayer p = AbstractDungeon.player;
	}

	public GainFragileRelicAction(AbstractRelic relicToAdd) { this(relicToAdd, false); }

	public void update() {
		if (this.duration == Settings.ACTION_DUR_MED) {
			this.relicToAdd.instantObtain();
			if (this.startOfCombat) { this.relicToAdd.atPreBattle(); }
			CardCrawlGame.metricData.addRelicObtainData(this.relicToAdd);
			this.isDone = true;
		}
		tickDuration();
	}
}