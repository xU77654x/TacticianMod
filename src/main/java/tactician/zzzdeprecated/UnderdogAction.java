package tactician.zzzdeprecated;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.GetAllInBattleInstances;
import java.util.UUID;

public class UnderdogAction extends AbstractGameAction {

	private final UUID uuid;
	public UnderdogAction(UUID targetUUID, int miscValue) {
		this.uuid = targetUUID;
	}

	public void update() {
		for (AbstractCard c : AbstractDungeon.player.masterDeck.group) {
			if (!c.uuid.equals(this.uuid)) { continue; }
			c.misc += 1;
			c.applyPowers();
			c.baseMagicNumber = c.misc;
			c.isMagicNumberModified = false;
		}
		for (AbstractCard c : GetAllInBattleInstances.get(this.uuid)) {
			c.misc += 1;
			c.applyPowers();
			c.baseMagicNumber = c.misc;
		}
		this.isDone = true;
	}
}