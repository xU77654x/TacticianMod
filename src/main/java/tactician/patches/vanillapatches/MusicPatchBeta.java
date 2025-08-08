package tactician.patches.vanillapatches;

import com.badlogic.gdx.audio.Music;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.audio.MainMusic;

@SpirePatch(clz = MainMusic.class, method = "getSong")
public class MusicPatchBeta
{
	@SpirePrefixPatch
	public static SpireReturn<Music> Prefix(MainMusic __instance, String key) {
		if (true) {
			switch (key) {
				case "Exordium": return SpireReturn.Return(MainMusic.newMusic("tactician/audio/music/Fire Emblem Awakening/Prelude.ogg"));
				case "TheCity": return SpireReturn.Return(MainMusic.newMusic("tactician/audio/music/Fire Emblem Awakening/Prelude.ogg"));
				case "TheBeyond": return SpireReturn.Return(MainMusic.newMusic("tactician/audio/music/Fire Emblem Awakening/Prelude.ogg"));
				case "TheEnding": return SpireReturn.Return(MainMusic.newMusic("tactician/audio/music/Fire Emblem Awakening/Prelude.ogg"));
				case "MENU": return SpireReturn.Return(MainMusic.newMusic("tactician/audio/music/Fire Emblem Awakening/YouHavePowerLikeMine.ogg"));
				/*
				 case "SHOP": return SpireReturn.Return(MainMusic.newMusic("tactician/audio/music/Fire Emblem Awakening/OpenForBusiness.ogg"));
				 case "SHRINE": return SpireReturn.Return(MainMusic.newMusic("tactician/audio/music/Fire Emblem Awakening/YouHavePowerLikeMine.ogg"));
				 case "MINDBLOOM": return SpireReturn.Return(MainMusic.newMusic("tactician/audio/music/Fire Emblem Awakening/Menace.ogg"));
				 case "BOSS_BOTTOM": return SpireReturn.Return(MainMusic.newMusic("tactician/audio/music/Fire Emblem Awakening/ConquestAblaze.ogg"));
				 case "BOSS_CITY": return SpireReturn.Return(MainMusic.newMusic("tactician/audio/music/Fire Emblem Awakening/IdHope.ogg"));
				 case "BOSS_BEYOND": return SpireReturn.Return(MainMusic.newMusic("tactician/audio/music/Fire Emblem Awakening/DontSpeakHerName.ogg"));
				 case "BOSS_ENDING": return SpireReturn.Return(MainMusic.newMusic("tactician/audio/music/Fire Emblem Awakening/IdPurpose.ogg"));
				 case "ELITE": return SpireReturn.Return(MainMusic.newMusic("tactician/audio/music/Fire Emblem Awakening/AggressionGalvanized.ogg"));
				 case "CREDITS": return SpireReturn.Return(MainMusic.newMusic("tactician/audio/music/Fire Emblem Awakening/?????.ogg"));
				 case "ACT_4_BGM": return SpireReturn.Return(MainMusic.newMusic("tactician/audio/music/Fire Emblem Awakening/Chaos.ogg"));
				 case "LEVEL_1": return SpireReturn.Return(MainMusic.newMusic("tactician/audio/music/Fire Emblem Awakening/Prelude.ogg"));
				 case "LEVEL_1_ALT": return SpireReturn.Return(MainMusic.newMusic("tactician/audio/music/Fire Emblem Awakening/StormCloudsAblaze.ogg"));
				 case "LEVEL_2": return SpireReturn.Return(MainMusic.newMusic("tactician/audio/music/Fire Emblem Awakening/Duty.ogg"));
				 case "LEVEL_2_ALT": return SpireReturn.Return(MainMusic.newMusic("tactician/audio/music/Fire Emblem Awakening/DestinyAblaze.ogg"));
				 case "LEVEL_3": return SpireReturn.Return(MainMusic.newMusic("tactician/audio/music/Fire Emblem Awakening/DivineDecree.ogg"));
				 case "LEVEL_3_ALT": return SpireReturn.Return(MainMusic.newMusic("tactician/audio/music/Fire Emblem Awakening/Conquest.ogg"));
				 case "ENDING_STINGER": return SpireReturn.Return(MainMusic.newMusic("tactician/audio/music/Fire Emblem Awakening/???.ogg"));
				 case "BOSS_VICTORY_STINGER": return SpireReturn.Return(MainMusic.newMusic("tactician/audio/music/Fire Emblem Awakening/???.ogg"));
				*/
			}
			return SpireReturn.Continue();
		}
		return SpireReturn.Continue();
	}
}