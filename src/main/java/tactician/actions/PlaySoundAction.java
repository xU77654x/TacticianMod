package tactician.actions;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;

public class PlaySoundAction extends AbstractGameAction {
	private final String soundKey;
	private final float volume;

	public PlaySoundAction(String soundKey, float volume) {
		this.soundKey = soundKey;
		this.volume = volume;
	}

	@Override
	public void update() {
		CardCrawlGame.sound.playV(soundKey, volume);
		isDone = true;
	}
}