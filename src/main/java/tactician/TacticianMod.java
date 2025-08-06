package tactician;

import basemod.AutoAdd;
import basemod.BaseMod;
import basemod.interfaces.*;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import tactician.cards.TacticianCard;
import tactician.character.TacticianRobin;
import tactician.potions.BasePotion;
import tactician.relics.BaseRelic;
import tactician.util.GeneralUtils;
import tactician.util.KeywordInfo;
import tactician.util.TextureLoader;
import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglFileHandle;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.evacipated.cardcrawl.modthespire.Loader;
import com.evacipated.cardcrawl.modthespire.ModInfo;
import com.evacipated.cardcrawl.modthespire.Patcher;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.localization.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.scannotation.AnnotationDB;
import java.nio.charset.StandardCharsets;
import java.util.*;

@SpireInitializer
public class TacticianMod implements
        EditCardsSubscriber,
        EditRelicsSubscriber,
        EditCharactersSubscriber,
        EditStringsSubscriber,
        EditKeywordsSubscriber,
        PostInitializeSubscriber,
        AddAudioSubscriber

{
    public static ModInfo info;
    public static String modID; // Edit your pom.xml to change this
    static { loadModInfo(); }
    private static final String resourcesFolder = checkResourcesPath();
    public static final Logger logger = LogManager.getLogger(modID); // Used to output to the console.

    // This is used to prefix the IDs of various objects like cards and relics, to avoid conflicts between different mods using the same name for things.
    public static String makeID(String id) { return modID + ":" + id; }

    // This will be called by ModTheSpire because of the @SpireInitializer annotation at the top of the class.
    public static void initialize() {
        new TacticianMod();
        TacticianRobin.Meta.registerColor();
    }

    public TacticianMod() {
        BaseMod.subscribe(this); // This will make BaseMod trigger all the subscribers at their appropriate times.
        logger.info(modID + " subscribed to BaseMod.");
    }

    @Override
    public void receivePostInitialize() {
        // This loads the image used as an icon in the in-game mods menu.
        Texture badgeTexture = TextureLoader.getTexture(imagePath("badge.png"));
        // Set up the mod information displayed in the in-game mods menu. The information used is taken from your pom.xml file.
        // If you want to set up a config panel, that will be done here. The Mod Badges page has a basic example of this, but setting up config is overall a bit complex.
        BaseMod.registerModBadge(badgeTexture, info.Name, GeneralUtils.arrToString(info.Authors), info.Description, null);
        registerPotions();
    }

    /*----------Localization----------*/

    // This is used to load the appropriate localization files based on language.
    private static String getLangString() { return Settings.language.name().toLowerCase(); }
    private static final String defaultLanguage = "eng";
    public static final Map<String, KeywordInfo> keywords = new HashMap<>();

    @Override
    public void receiveEditStrings() {
        // First, load the default localization. Then, if the current language is different, attempt to load localization for that language.
        // This results in the default localization being used for anything that might be missing. The same process is used to load keywords slightly below.
        loadLocalization(defaultLanguage); // There is no exception catching for default localization.
        if (!defaultLanguage.equals(getLangString())) {
            try { loadLocalization(getLangString()); }
            catch (GdxRuntimeException e) { e.printStackTrace(); }
        }
    }

    public static void registerPotions() {
        new AutoAdd(modID) // Loads files from this mod.
                .packageFilter(BasePotion.class) // In the same package as this class.
                .any(BasePotion.class, (info, potion) -> { // Run this code for any classes that extend this class.
                    // These three null parameters are colors. If they're not null, they'll overwrite the color set in the potions themselves.
                    // This is an old feature added before having potions determine their own color was possible.
                    BaseMod.addPotion(potion.getClass(), null, null, null, potion.ID, potion.playerClass);
                    // playerClass will make a potion character-specific. By default, it's null and will do nothing.
                });
    }

    private void loadLocalization(String lang) {
        // While this does load every type of localization, most of these files are just outlines so that you can see how they're formatted.
        // Feel free to comment out/delete any that you don't end up using.
        BaseMod.loadCustomStringsFile(CardStrings.class, localizationPath(lang, "CardStrings.json"));
        BaseMod.loadCustomStringsFile(CharacterStrings.class, localizationPath(lang, "CharacterStrings.json"));
        BaseMod.loadCustomStringsFile(EventStrings.class, localizationPath(lang, "EventStrings.json"));
        BaseMod.loadCustomStringsFile(OrbStrings.class, localizationPath(lang, "OrbStrings.json"));
        BaseMod.loadCustomStringsFile(PotionStrings.class, localizationPath(lang, "PotionStrings.json"));
        BaseMod.loadCustomStringsFile(PowerStrings.class, localizationPath(lang, "PowerStrings.json"));
        BaseMod.loadCustomStringsFile(RelicStrings.class, localizationPath(lang, "RelicStrings.json"));
        BaseMod.loadCustomStringsFile(UIStrings.class, localizationPath(lang, "UIStrings.json"));
    }

    @Override
    public void receiveEditKeywords() {
        Gson gson = new Gson();
        String json = Gdx.files.internal(localizationPath(defaultLanguage, "Keywords.json")).readString(String.valueOf(StandardCharsets.UTF_8));
        KeywordInfo[] keywords = gson.fromJson(json, KeywordInfo[].class);
        for (KeywordInfo keyword : keywords) {
            keyword.prep();
            registerKeyword(keyword);
        }

        if (!defaultLanguage.equals(getLangString())) {
            try {
                json = Gdx.files.internal(localizationPath(getLangString(), "Keywords.json")).readString(String.valueOf(StandardCharsets.UTF_8));
                keywords = gson.fromJson(json, KeywordInfo[].class);
                for (KeywordInfo keyword : keywords) {
                    keyword.prep();
                    registerKeyword(keyword);
                }
            }
            catch (Exception e) { logger.warn(modID + " does not support " + getLangString() + " keywords."); }
        }
    }

    private void registerKeyword(KeywordInfo info) {
        BaseMod.addKeyword(modID.toLowerCase(), info.PROPER_NAME, info.NAMES, info.DESCRIPTION);
        if (!info.ID.isEmpty()) { keywords.put(info.ID, info); }
    }

    // These methods are used to generate the correct filepaths to various parts of the resources folder.
    public static String localizationPath(String lang, String file) { return resourcesFolder + "/localization/" + lang + "/" + file; }
    public static String imagePath(String file) { return resourcesFolder + "/images/" + file; }
    public static String characterPath(String file) { return resourcesFolder + "/images/character/" + file; }
    public static String powerPath(String file) { return resourcesFolder + "/images/powers/" + file; }
    public static String relicPath(String file) { return resourcesFolder + "/images/relics/" + file; }

    /**
     * Checks the expected resources path based on the package name.
     */
    private static String checkResourcesPath() {
        String name = TacticianMod.class.getName(); //getPackage can be iffy with patching, so class name is used instead.
        int separator = name.indexOf('.');
        if (separator > 0)
            name = name.substring(0, separator);

        FileHandle resources = new LwjglFileHandle(name, Files.FileType.Internal);

        if (!resources.exists()) {
            throw new RuntimeException("\n\tFailed to find resources folder; expected it to be named \"" + name + "\"." +
                    " Either make sure the folder under resources has the same name as your mod's package, or change the line\n" +
                    "\t\"private static final String resourcesFolder = checkResourcesPath();\"\n" +
                    "\tat the top of the " + TacticianMod.class.getSimpleName() + " java file.");
        }
        if (!resources.child("images").exists()) {
            throw new RuntimeException("\n\tFailed to find the 'images' folder in the mod's 'resources/" + name + "' folder; Make sure the " +
                    "images folder is in the correct location.");
        }
        if (!resources.child("localization").exists()) {
            throw new RuntimeException("\n\tFailed to find the 'localization' folder in the mod's 'resources/" + name + "' folder; Make sure the " +
                    "localization folder is in the correct location.");
        }
        return name;
    }

    /**
     * This determines the mod's ID based on information stored by ModTheSpire.
     */
    private static void loadModInfo() {
        Optional<ModInfo> infos = Arrays.stream(Loader.MODINFOS).filter((modInfo)->{
            AnnotationDB annotationDB = Patcher.annotationDBMap.get(modInfo.jarURL);
            if (annotationDB == null)
                return false;
            Set<String> initializers = annotationDB.getAnnotationIndex().getOrDefault(SpireInitializer.class.getName(), Collections.emptySet());
            return initializers.contains(TacticianMod.class.getName());
        }).findFirst();
        if (infos.isPresent()) {
            info = infos.get();
            modID = info.ID;
        }
        else { throw new RuntimeException("Failed to determine mod info/ID based on initializer."); }
    }

    @Override
    public void receiveEditCharacters() { TacticianRobin.Meta.registerCharacter(); }

    @Override
    public void receiveEditCards() {
        new AutoAdd(modID) // Loads files from this mod
            .packageFilter(TacticianCard.class) // In the same package as this class
            .setDefaultSeen(true) // And marks them as seen in the compendium
            .cards(); // Adds the cards
    }

    @Override
    public void receiveEditRelics() {
        new AutoAdd(modID).any(BaseRelic.class, (info, relic) -> { // Loads files from this mod. Run this code for any classes that extend this class.
            if (relic.pool != null) { BaseMod.addRelicToCustomPool(relic, relic.pool); } // Register a custom character specific relic
            else { BaseMod.addRelic(relic, relic.relicType); } // Register a shared or base game character specific relic
            UnlockTracker.markRelicAsSeen(relic.relicId);
            // If the class is annotated with @AutoAdd.Seen, it will be marked as seen, making it visible in the relic library.
        });
    }

    @Override
    public void receiveAddAudio() {
        // Basic Cards
        BaseMod.addAudio("tactician:Strike_Strong", "tactician/audio/effect/Strike_Strong.wav");
        BaseMod.addAudio("tactician:Strike_Neutral", "tactician/audio/effect/Strike_Neutral.wav");
        BaseMod.addAudio("tactician:Strike_Weak", "tactician/audio/effect/Strike_Weak.wav");
        BaseMod.addAudio("tactician:Defend_Strong", "tactician/audio/effect/Defend_Strong.wav");
        BaseMod.addAudio("tactician:Defend_Neutral", "tactician/audio/effect/Defend_Neutral.wav");
        BaseMod.addAudio("tactician:Defend_Weak", "tactician/audio/effect/Defend_Weak.wav");
        BaseMod.addAudio("tactician:Solidarity", "tactician/audio/effect/Solidarity.wav");
        BaseMod.addAudio("tactician:Vulnerary", "tactician/audio/effect/Vulnerary.wav");

        // Common Cards
        BaseMod.addAudio("tactician:WrathStrike", "tactician/audio/effect/WrathStrike.wav");
        BaseMod.addAudio("tactician:TempestLance", "tactician/audio/effect/TempestLance.wav");
        BaseMod.addAudio("tactician:Smash", "tactician/audio/effect/Smash.wav");
        BaseMod.addAudio("tactician:CurvedShot", "tactician/audio/effect/CurvedShot.wav");
        BaseMod.addAudio("tactician:Elwind", "tactician/audio/effect/Elwind.wav");
        BaseMod.addAudio("tactician:Arcfire_Cast", "tactician/audio/effect/Arcfire_Cast.wav");
        BaseMod.addAudio("tactician:Arcfire_Hit", "tactician/audio/effect/Arcfire_Hit.wav");
        BaseMod.addAudio("tactician:Thunder", "tactician/audio/effect/Thunder.wav");
        BaseMod.addAudio("tactician:Flux", "tactician/audio/effect/Flux.wav");
        BaseMod.addAudio("tactician:Shove", "tactician/audio/effect/Shove.wav");
        BaseMod.addAudio("tactician:Reposition", "tactician/audio/effect/Reposition.wav");
        BaseMod.addAudio("tactician:Discipline", "tactician/audio/effect/Discipline.wav");
        BaseMod.addAudio("tactician:Zeal", "tactician/audio/effect/Zeal.wav");
        BaseMod.addAudio("tactician:AlertStance", "tactician/audio/effect/AlertStance.wav");
        BaseMod.addAudio("tactician:Tantivy", "tactician/audio/effect/Tantivy.wav");
        BaseMod.addAudio("tactician:Blossom", "tactician/audio/effect/Blossom.wav");
        BaseMod.addAudio("tactician:Armsthrift", "tactician/audio/effect/Armsthrift.wav");
        BaseMod.addAudio("tactician:OutdoorFighter", "tactician/audio/effect/OutdoorFighter.wav");
        BaseMod.addAudio("tactician:IndoorFighter", "tactician/audio/effect/IndoorFighter.wav");

        // Uncommon Cards
        BaseMod.addAudio("tactician:CrosswiseCut1", "tactician/audio/effect/CrosswiseCut1.wav");
        BaseMod.addAudio("tactician:CrosswiseCut2", "tactician/audio/effect/CrosswiseCut2.wav");
        BaseMod.addAudio("tactician:FrozenLance", "tactician/audio/effect/FrozenLance.wav");
        BaseMod.addAudio("tactician:WildAbandon", "tactician/audio/effect/WildAbandon.wav");
        BaseMod.addAudio("tactician:WaningShot_Draw", "tactician/audio/effect/WaningShot_Draw.wav");
        BaseMod.addAudio("tactician:WaningShot_Hit", "tactician/audio/effect/WaningShot_Hit.wav");
        BaseMod.addAudio("tactician:CuttingGale_Jab", "tactician/audio/effect/CuttingGale_Jab.wav");
        BaseMod.addAudio("tactician:CuttingGale_Finish", "tactician/audio/effect/CuttingGale_Finish.wav");
        BaseMod.addAudio("tactician:DyingBlaze", "tactician/audio/effect/DyingBlaze.wav");
        BaseMod.addAudio("tactician:Bolting", "tactician/audio/effect/Bolting.wav");
        BaseMod.addAudio("tactician:Nosferatu", "tactician/audio/effect/Nosferatu.wav");
        BaseMod.addAudio("tactician:LevinSword", "tactician/audio/effect/LevinSword.wav");
        BaseMod.addAudio("tactician:FlameLance", "tactician/audio/effect/FlameLance.wav");
        BaseMod.addAudio("tactician:HurricaneAxe", "tactician/audio/effect/HurricaneAxe.wav");
        BaseMod.addAudio("tactician:BeguilingBow", "tactician/audio/effect/BeguilingBow.wav");
        BaseMod.addAudio("tactician:FlashSparrow", "tactician/audio/effect/FlashSparrow.wav");
        BaseMod.addAudio("tactician:Relief", "tactician/audio/effect/Relief.wav");
        BaseMod.addAudio("tactician:PaviseAegis", "tactician/audio/effect/PaviseAegis.wav");
        BaseMod.addAudio("tactician:Charm", "tactician/audio/effect/Charm.wav");
        BaseMod.addAudio("tactician:Healtouch", "tactician/audio/effect/Healtouch.wav");
        BaseMod.addAudio("tactician:EvenRhythm", "tactician/audio/effect/EvenOddRhythm_Even.wav");
        BaseMod.addAudio("tactician:OddRhythm", "tactician/audio/effect/EvenOddRhythm_Odd.wav");
        BaseMod.addAudio("tactician:SurpriseAttack", "tactician/audio/effect/SurpriseAttack.wav");
        BaseMod.addAudio("tactician:Pass", "tactician/audio/effect/Pass.wav");
        BaseMod.addAudio("tactician:Locktouch", "tactician/audio/effect/Locktouch.wav");
        BaseMod.addAudio("tactician:Gamble", "tactician/audio/effect/Gamble.wav");
        BaseMod.addAudio("tactician:RallySpectrum", "tactician/audio/effect/RallySpectrum.wav");
        BaseMod.addAudio("tactician:SpecialDance", "tactician/audio/effect/SpecialDance.wav");
        BaseMod.addAudio("tactician:Renewal", "tactician/audio/effect/Renewal.wav");
        BaseMod.addAudio("tactician:Acrobat", "tactician/audio/effect/Acrobat.wav");
        BaseMod.addAudio("tactician:MastersTactics", "tactician/audio/effect/MastersTactics.wav");
        BaseMod.addAudio("tactician:Prescience", "tactician/audio/effect/Prescience.wav");
        BaseMod.addAudio("tactician:Patience", "tactician/audio/effect/Patience.wav");
        BaseMod.addAudio("tactician:Vantage", "tactician/audio/effect/Vantage.wav");
        BaseMod.addAudio("tactician:Expiration", "tactician/audio/effect/Expiration.wav");
        BaseMod.addAudio("tactician:CreationPulse", "tactician/audio/effect/CreationPulse.wav");
        BaseMod.addAudio("tactician:StatIncreaseFE", "tactician/audio/effect/StatIncreaseFE.wav");

        // Rare Cards
        BaseMod.addAudio("tactician:Astra_Hit1", "tactician/audio/effect/Astra_Hit1.wav");
        BaseMod.addAudio("tactician:Astra_Hit2", "tactician/audio/effect/Astra_Hit2.wav");
        BaseMod.addAudio("tactician:Astra_Hit3", "tactician/audio/effect/Astra_Hit3.wav");
        BaseMod.addAudio("tactician:Astra_Hit4", "tactician/audio/effect/Astra_Hit4.wav");
        BaseMod.addAudio("tactician:Astra_Hit5", "tactician/audio/effect/Astra_Hit5.wav");
        BaseMod.addAudio("tactician:Astra_Hit1", "tactician/audio/effect/Astra_Hit1.wav");
        BaseMod.addAudio("tactician:SwiftStrikes_Hit1", "tactician/audio/effect/SwiftStrikes_Hit1.wav");
        BaseMod.addAudio("tactician:SwiftStrikes_Hit2", "tactician/audio/effect/SwiftStrikes_Hit2.wav");
        BaseMod.addAudio("tactician:ExhaustiveStrike_Hit1", "tactician/audio/effect/ExhaustiveStrike_Hit1.wav");
        BaseMod.addAudio("tactician:ExhaustiveStrike_Hit2", "tactician/audio/effect/ExhaustiveStrike_Hit2.wav");
        BaseMod.addAudio("tactician:HuntersVolley_Hit1", "tactician/audio/effect/HuntersVolley_Hit1.wav");
        BaseMod.addAudio("tactician:HuntersVolley_Hit2", "tactician/audio/effect/HuntersVolley_Hit2.wav");
        BaseMod.addAudio("tactician:Excalibur_Cast", "tactician/audio/effect/Excalibur_Cast.wav");
        BaseMod.addAudio("tactician:Excalibur_Hit", "tactician/audio/effect/Excalibur_Hit.wav");
        BaseMod.addAudio("tactician:Bolganone", "tactician/audio/effect/Bolganone.wav");
        BaseMod.addAudio("tactician:Thoron_Cast", "tactician/audio/effect/Thoron_Cast.wav");
        BaseMod.addAudio("tactician:Thoron_Glint", "tactician/audio/effect/Thoron_Glint.wav");
        BaseMod.addAudio("tactician:Goetia", "tactician/audio/effect/Goetia.wav");
        BaseMod.addAudio("tactician:Luna_KillingEdgeGain", "tactician/audio/effect/Luna_KillingEdgeGain.wav");
        BaseMod.addAudio("tactician:Ignis", "tactician/audio/effect/Ignis.wav");
        BaseMod.addAudio("tactician:StatDecreaseFE", "tactician/audio/effect/StatDecreaseFE.wav");
        BaseMod.addAudio("tactician:MasterSeal", "tactician/audio/effect/MasterSeal.wav");
        BaseMod.addAudio("tactician:TipTheScales", "tactician/audio/effect/TipTheScales.wav");
        BaseMod.addAudio("tactician:UnplayableFE", "tactician/audio/effect/UnplayableFE.wav");
        BaseMod.addAudio("tactician:Despoil", "tactician/audio/effect/Despoil.wav");
        BaseMod.addAudio("tactician:PartOfThePlan", "tactician/audio/effect/PartOfThePlan.wav");
        BaseMod.addAudio("tactician:ChaosStyle", "tactician/audio/effect/ChaosStyle.wav");
        BaseMod.addAudio("tactician:QuickBurn", "tactician/audio/effect/QuickBurn.wav");
        BaseMod.addAudio("tactician:GrandmasterForm", "tactician/audio/effect/GrandmasterForm.wav");

        // Other Cards and Powers
        BaseMod.addAudio("tactician:Hex", "tactician/audio/effect/Hex.wav");
        BaseMod.addAudio("tactician:Anathema", "tactician/audio/effect/Anathema.wav");
        BaseMod.addAudio("tactician:LevelUpFE8", "tactician/audio/effect/LevelUpFE8.wav");
        BaseMod.addAudio("tactician:CriticalHitFE8", "tactician/audio/effect/CriticalHitFE8.wav");
        BaseMod.addAudio("tactician:DeflectReceiveHit", "tactician/audio/effect/DeflectReceiveHit.wav");
    }
}