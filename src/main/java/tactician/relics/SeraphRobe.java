package tactician.relics;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import static tactician.TacticianMod.makeID;

public class SeraphRobe extends BaseRelic {
    private static final String NAME = "SeraphRobe";
    public static final String ID = makeID(NAME);
    private static final RelicTier RARITY = RelicTier.COMMON;
    private static final LandingSound SOUND = LandingSound.CLINK;
    private static final int HEAL = 10;
    private static final int GOLD = 50;

    public SeraphRobe() {
        super(ID, NAME, RARITY, SOUND);
        // TODO: Relic image and level up sound.
    }

    @Override
    public String getUpdatedDescription() { return this.DESCRIPTIONS[0] + HEAL + this.DESCRIPTIONS[1] + GOLD + this.DESCRIPTIONS[2]; }

    public void justEnteredRoom(AbstractRoom room) {
        if (room instanceof com.megacrit.cardcrawl.rooms.TreasureRoom) {
            flash();
            this.pulse = true;
        }
        else { this.pulse = false; }
    }

    @Override
    public void onEquip() {
        flash();
        AbstractDungeon.player.heal(HEAL, true);
        AbstractDungeon.player.gainGold(GOLD);
    }

    public void onChestOpen() {
        flash();
        AbstractDungeon.player.heal(HEAL, true);
        AbstractDungeon.player.gainGold(GOLD);
    }

    @Override
    public AbstractRelic makeCopy() { return new SeraphRobe(); }
}