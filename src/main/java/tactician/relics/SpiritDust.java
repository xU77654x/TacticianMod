package tactician.relics;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.FocusPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import tactician.character.MyCharacter;

import static tactician.TacticianMod.makeID;

public class SpiritDust extends BaseRelic {
    private static final String NAME = "SpiritDust"; // Determines the filename and ID.
    public static final String ID = makeID(NAME); // Adds prefix to relic ID, resulting in modID:MyRelic
    private static final RelicTier RARITY = RelicTier.COMMON;
    private static final LandingSound SOUND = LandingSound.CLINK;
    private static final int FOCUS = 1; // This is used rather than a hard-coded value due to the description.

    public SpiritDust() {
        super(ID, NAME, MyCharacter.Meta.CARD_COLOR, RARITY, SOUND);
        // TODO: Relic image and level up sound.
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + FOCUS + this.DESCRIPTIONS[1];
    }

    @Override
    public void atBattleStart() {
        flash();
        addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new FocusPower(AbstractDungeon.player, FOCUS), FOCUS));
        addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
    }

    @Override
    public AbstractRelic makeCopy() {
        return new SpiritDust();
    }
}
