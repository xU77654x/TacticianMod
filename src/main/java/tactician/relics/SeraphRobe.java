package tactician.relics;

import com.megacrit.cardcrawl.core.CardCrawlGame;
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

    public SeraphRobe() { super(ID, NAME, RARITY, SOUND); }

    @Override
    public String getUpdatedDescription() { return this.DESCRIPTIONS[0] + HEAL + this.DESCRIPTIONS[1] + GOLD + this.DESCRIPTIONS[2]; }

    public void justEnteredRoom(AbstractRoom room) {
        if (room instanceof com.megacrit.cardcrawl.rooms.TreasureRoom || room instanceof com.megacrit.cardcrawl.rooms.TreasureRoomBoss) {
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

    @Override
    public void onChestOpen(boolean bossChest) {
        flash();
        if (!bossChest) {
            AbstractDungeon.player.heal(HEAL, true);
            AbstractDungeon.player.gainGold(GOLD);
        }
        else {
            if (this.pulse) {
                AbstractDungeon.player.heal(HEAL, true);
                AbstractDungeon.player.gainGold(GOLD);
                this.pulse = false;
            }
        }

    }

    @Override
    public void playLandingSFX() { CardCrawlGame.sound.play("tactician:LevelUpFE8"); }

    @Override
    public AbstractRelic makeCopy() { return new SeraphRobe(); }
}