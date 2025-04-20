package tactician.relics;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import tactician.character.MyCharacter;
import tactician.powers.DeflectPower;

import static tactician.TacticianMod.makeID;

public class Speedwing extends BaseRelic {
    private static final String NAME = "Speedwing"; // Determines the filename and ID.
    public static final String ID = makeID(NAME); // Adds prefix to relic ID, resulting in modID:MyRelic
    private static final RelicTier RARITY = RelicTier.COMMON;
    private static final LandingSound SOUND = LandingSound.CLINK;
    private static final int DEFLECT = 5; // This is used rather than a hard-coded value due to the description.
    private static final int ENERGY = 1;
    private static final int TURN = 2;

    public Speedwing() {
        super(ID, NAME, MyCharacter.Meta.CARD_COLOR, RARITY, SOUND);
        // TODO: Relic image and level up sound.
    }

    @Override
    public String getUpdatedDescription() { return this.DESCRIPTIONS[0] + DEFLECT + this.DESCRIPTIONS[1]; }

    @Override
    public void atBattleStart() { this.counter = 0; }

    @Override
    public void atTurnStart() {
        if (!this.grayscale) { this.counter++; }
        if (this.counter == TURN) {
            flash();
            addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            addToBot(new GainEnergyAction(ENERGY));
            addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new DeflectPower(DEFLECT), DEFLECT));
            this.counter = -1;
            this.grayscale = true;
        }
    }

    @Override
    public void onVictory() {
        this.counter = -1;
        this.grayscale = false;
    }

    @Override
    public AbstractRelic makeCopy() { return new Speedwing(); }
}