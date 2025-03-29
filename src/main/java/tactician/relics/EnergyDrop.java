package tactician.relics;

import com.megacrit.cardcrawl.actions.common.ExhaustAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import tactician.character.MyCharacter;
import static tactician.TacticianMod.makeID;

public class EnergyDrop extends BaseRelic {
    private static final String NAME = "EnergyDrop";
    public static final String ID = makeID(NAME);
    private static final RelicTier RARITY = RelicTier.RARE;
    private static final LandingSound SOUND = LandingSound.CLINK;

    public EnergyDrop() {
        super(ID, NAME, MyCharacter.Meta.CARD_COLOR, RARITY, SOUND);
        // TODO: Relic image and level up sound.
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public void onEquip() {
        AbstractDungeon.player.masterHandSize++;
    }

    @Override
    public void onUnequip() {
        AbstractDungeon.player.masterHandSize--;
    }

    @Override
    public void onPlayerEndTurn() {
        flash();
        addToBot(new ExhaustAction(1, false));
        // TODO: If a card remains in the user's hand on turn end, then the relic may flash.
    }

    @Override
    public AbstractRelic makeCopy() {
        return new EnergyDrop();
    }
}
