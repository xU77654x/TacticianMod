package tactician.actions;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.PowerExpireTextEffect;
import tactician.util.Wiz;

public class StrikeDefendSFXAction extends AbstractGameAction {
	private final AbstractMonster m;
	private final int card;

	public StrikeDefendSFXAction(int card, AbstractMonster target) {
		this.target = target;
		this.card = card;
		this.m = target;
	}

	@Override
	public void update() {
		if (card == 1) {
			if ( Wiz.playerWeaponCalc(this.m, 9) > 0) { addToTop(new PlaySoundAction("tactician:Strike_Strong", 1.00f)); }
			else if ( Wiz.playerWeaponCalc(this.m, 9) < 0) { addToTop(new PlaySoundAction("tactician:Strike_Weak", 1.00f)); }
			else { addToTop(new PlaySoundAction("tactician:Strike_Neutral", 1.00f)); }
		}
		else if (card == 0) {
			if ( Wiz.playerWeaponCalc(this.m, 9) > 0) { addToTop(new PlaySoundAction("tactician:Defend_Strong", 1.50f)); }
			else if ( Wiz.playerWeaponCalc(this.m, 9) < 0) { addToTop(new PlaySoundAction("tactician:Defend_Weak", 1.50f)); }
			else { addToTop(new PlaySoundAction("tactician:Defend_Neutral", 1.50f)); }
		}

		this.isDone = true;
	}
}