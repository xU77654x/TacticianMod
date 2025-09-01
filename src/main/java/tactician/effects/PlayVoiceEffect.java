package tactician.effects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import tactician.actions.PlaySoundAction;
import tactician.character.TacticianRobin;

public class PlayVoiceEffect extends AbstractGameEffect {
	private final String voiceline;
	private static float lastPlayTime = -5.0f;
	private boolean played = false;

	public PlayVoiceEffect(String voiceline) {
		this.voiceline = voiceline;
		this.duration = 2.0f;
	}

	@Override
	public void update() {
		float currentTime = com.badlogic.gdx.utils.TimeUtils.nanoTime() / 1_000_000_000.0f; // This might need revision.
		if ((!played) && (AbstractDungeon.player instanceof TacticianRobin)) {
			if (currentTime - lastPlayTime < 2.0f) {
				this.isDone = true;
				return;
			}
			switch (this.voiceline) {
				case "CA_Sword": this.played = true; AbstractDungeon.actionManager.addToTop(new PlaySoundAction("tactician:Male_CA_Sword", 1.50f)); break;
				case "CA_Lance": this.played = true; AbstractDungeon.actionManager.addToTop(new PlaySoundAction("tactician:Male_CA_Lance", 1.50f)); break;
				case "CA_Axe": this.played = true; AbstractDungeon.actionManager.addToTop(new PlaySoundAction("tactician:Male_CA_Axe", 1.50f)); break;
				case "CA_Bow": this.played = true; AbstractDungeon.actionManager.addToTop(new PlaySoundAction("tactician:Male_CA_Bow", 1.50f)); break;
				case "CA_MiscMagic": this.played = true; AbstractDungeon.actionManager.addToTop(new PlaySoundAction("tactician:Male_CA_MiscMagic", 1.50f)); break;
				case "Elwind": this.played = true; AbstractDungeon.actionManager.addToBottom(new PlaySoundAction("tactician:Male_Elwind", 1.66f)); break;
				case "Arcfire": this.played = true; AbstractDungeon.actionManager.addToTop(new PlaySoundAction("tactician:Male_Arcfire", 1.50f)); break;
				case "Thunder": this.played = true; AbstractDungeon.actionManager.addToTop(new PlaySoundAction("tactician:Male_Thunder", 1.50f)); break;
				case "Flux": this.played = true; AbstractDungeon.actionManager.addToTop(new PlaySoundAction("tactician:Male_Flux", 1.75f)); break;
				case "Nosferatu": this.played = true; AbstractDungeon.actionManager.addToTop(new PlaySoundAction("tactician:Male_Nosferatu", 2.50f)); break;
				case "Bolganone": this.played = true; AbstractDungeon.actionManager.addToTop(new PlaySoundAction("tactician:Male_Bolganone", 1.70f)); break;
				case "Thoron": this.played = true; AbstractDungeon.actionManager.addToTop(new PlaySoundAction("tactician:Male_Thoron", 1.90f)); break;
				case "Luna": this.played = true; AbstractDungeon.actionManager.addToTop(new PlaySoundAction("tactician:Male_Luna", 1.66f)); break;
				case "TipTheScales": this.played = true; AbstractDungeon.actionManager.addToTop(new PlaySoundAction("tactician:Male_TipTheScales", 1.66f)); break;
				case "QuickBurn": this.played = true; AbstractDungeon.actionManager.addToTop(new PlaySoundAction("tactician:Male_QuickBurn", 1.50f)); break;
				case "GrandmasterForm": played = true; AbstractDungeon.actionManager.addToTop(new PlaySoundAction("tactician:Male_GrandmasterForm", 2.00f)); break;
			}
			played = true;
			lastPlayTime = currentTime;
			this.duration -= Gdx.graphics.getDeltaTime();
			if (this.duration <= 0.0F) { this.isDone = true; }
		}
	}

	private String getCategory(String voiceline) { return "Robin"; }

	public void render(SpriteBatch spriteBatch) {}

	public void dispose() {}

	// For future expansion of this sheet with many categories and voice clips:
	/*
	public static final Map<String, SoundData> voicelineMap = new HashMap<>();
	static {
    	voicelineMap.put("1", new SoundData("key", 1.5f, "Robin"));
    	voicelineMap.put("2", new SoundData("other", 1.75f, "Shulk"));
    	voicelineMap.put("interrupt", new SoundData("interrupt_clip", 2.0f, "Dunban", true));
    	// 147 more...
    }
	 */
}