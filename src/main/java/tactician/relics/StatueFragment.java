package tactician.relics;

import basemod.BaseMod;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import tactician.character.TacticianRobin;

import static tactician.TacticianMod.makeID;

public class StatueFragment extends BaseRelic{
    private static final String NAME = "StatueFragment";
    public static final String ID = makeID(NAME);
    private static final RelicTier RARITY = RelicTier.SPECIAL;
    private static final LandingSound SOUND = LandingSound.CLINK;
    private static final int HANDSIZE = 3;

    public StatueFragment() { super(ID, NAME, TacticianRobin.Meta.CARD_COLOR, RARITY, SOUND); }

    @Override
    public String getUpdatedDescription() { return this.DESCRIPTIONS[0] + HANDSIZE + "."; }

    @Override
    public void onEquip() { BaseMod.MAX_HAND_SIZE += HANDSIZE; }

    @Override
    public void onUnequip() { BaseMod.MAX_HAND_SIZE -= HANDSIZE; }

    @Override
    public void playLandingSFX() { CardCrawlGame.sound.play("tactician:LevelUpFE8"); }

    @Override
    public AbstractRelic makeCopy() {
        return new StatueFragment();
    }
}