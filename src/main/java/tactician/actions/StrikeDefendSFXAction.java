package tactician.actions;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
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
			if ( Wiz.playerWeaponCalc(this.m, 9) > 0) { CardCrawlGame.sound.play("tactician:Strike_Strong"); }
			else if ( Wiz.playerWeaponCalc(this.m, 9) < 0) { CardCrawlGame.sound.play("tactician:Strike_Weak"); }
			else { CardCrawlGame.sound.play("tactician:Strike_Neutral"); }
		}
		else if (card == 0) {
			if ( Wiz.playerWeaponCalc(this.m, 9) > 0) { CardCrawlGame.sound.play("tactician:Defend_Strong"); }
			else if ( Wiz.playerWeaponCalc(this.m, 9) < 0) { CardCrawlGame.sound.play("tactician:Defend_Weak"); }
			else { CardCrawlGame.sound.play("tactician:Defend_Neutral"); }
		}

		this.isDone = true;
	}
}