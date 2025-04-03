package tactician.relics;

import basemod.BaseMod;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import tactician.character.MyCharacter;

import static tactician.TacticianMod.makeID;

public class StatueFragment extends BaseRelic{
    private static final String NAME = "StatueFragment";
    public static final String ID = makeID(NAME);
    private static final RelicTier RARITY = RelicTier.SPECIAL;
    private static final LandingSound SOUND = LandingSound.CLINK;
    private static final int HANDSIZE = 3;

    public StatueFragment() {
        super(ID, NAME, MyCharacter.Meta.CARD_COLOR, RARITY, SOUND);

        // TODO: Relic image and level up sound.
    }

    @Override
    public String getUpdatedDescription() { return this.DESCRIPTIONS[0] + HANDSIZE + "."; }

    @Override
    public void onEquip() { BaseMod.MAX_HAND_SIZE += HANDSIZE; }

    @Override
    public void onUnequip() { BaseMod.MAX_HAND_SIZE -= HANDSIZE; }

    @Override
    public AbstractRelic makeCopy() {
        return new StatueFragment();
    }
}