package tactician.relics;

import com.evacipated.cardcrawl.mod.stslib.relics.ClickableRelic;
import com.megacrit.cardcrawl.actions.common.ExhaustAction;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import tactician.character.MyCharacter;

import static tactician.TacticianMod.makeID;

public class DragonFeather extends BaseRelic implements ClickableRelic {
    private static final String NAME = "DragonFeather";
    public static final String ID = makeID(NAME);
    private static final RelicTier RARITY = RelicTier.UNCOMMON;
    private static final LandingSound SOUND = LandingSound.CLINK;

    public DragonFeather() {
        super(ID, NAME, MyCharacter.Meta.CARD_COLOR, RARITY, SOUND);
        // TODO: Relic image and level up sound.
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public void onRightClick() {
        flash();
        addToBot(new ExhaustAction(1, false));
        // TODO: Calculate the current cost of the Exhausted card. Gain Vigor and Deflect equal to twice that value. Deflect.
        // TODO: Limit to one click per turn. Colored grey for the turn once clicked on.
    }

    @Override
    public AbstractRelic makeCopy() {
        return new DragonFeather();
    }
}
