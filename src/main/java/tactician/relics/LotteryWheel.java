package tactician.relics;

import com.megacrit.cardcrawl.relics.AbstractRelic;
import tactician.character.MyCharacter;
import static tactician.TacticianMod.makeID;

public class LotteryWheel extends BaseRelic {
    private static final String NAME = "LotteryWheel";
    public static final String ID = makeID(NAME);
    private static final RelicTier RARITY = RelicTier.UNCOMMON;
    private static final LandingSound SOUND = LandingSound.CLINK;

    public LotteryWheel() {
        super(ID, NAME, MyCharacter.Meta.CARD_COLOR, RARITY, SOUND);
        // TODO: Relic image and level up sound.
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public void onEquip() {
        flash();
        // TODO: Create a relic reward showing three Common relics, using the "Bossy Relics" format. This should already exist via the Blue Key's linking system.
    }

    @Override
    public AbstractRelic makeCopy() {
        return new LotteryWheel();
    }
}
