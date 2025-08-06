package tactician.effects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import tactician.character.TacticianRobin;

public class VoicelineEffect extends AbstractGameEffect {
	private final String voiceline;
	private int variation;
	private boolean played;

	public VoicelineEffect(String voiceline) {
		this.voiceline = voiceline;
		this.duration = 1.5F;
	}

	public void update() {
		if (!this.played && (AbstractDungeon.player instanceof TacticianRobin)) {
			for (AbstractGameEffect effect : AbstractDungeon.effectList) {
				if (effect instanceof VoicelineEffect) {
					if (effect == this) { break; }
					this.isDone = true;
					return;
				}
			}
			switch (this.voiceline) {
				case "Thunder": this.played = true; CardCrawlGame.sound.play("tactician/sfx/voice/Male_Thunder.wav"); break;
				case "Elwind": this.played = true; CardCrawlGame.sound.play("tactician/sfx/voice/Male_Elwind.wav"); break;
				case "Arcfire": this.played = true; CardCrawlGame.sound.play("tactician/sfx/voice/Male_Arcfire.wav"); break;
				case "Nosferatu": this.played = true; CardCrawlGame.sound.play("tactician/sfx/voice/Male_Nosferatu.wav"); break;
				case "Bolganone": this.played = true; CardCrawlGame.sound.play("tactician/sfx/voice/Male_Bolganone.wav"); break;
				case "Thoron": this.played = true; CardCrawlGame.sound.play("tactician/sfx/voice/Male_Thoron.wav"); break;
				// TODO: Robin's voice will not occur if he spoke within the past 5.0F  of time.
			}
		}
		this.duration -= Gdx.graphics.getDeltaTime();
		if (this.duration <= 0.0F) { this.isDone = true; }
	}

	public void render(SpriteBatch spriteBatch) {}

	public void dispose() {}
}