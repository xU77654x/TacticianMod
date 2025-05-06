package tactician.relics;

import com.evacipated.cardcrawl.mod.stslib.relics.ClickableRelic;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import tactician.character.MyCharacter;

import static tactician.TacticianMod.makeID;

public class SeraphRobe extends BaseRelic implements ClickableRelic {
    private static final String NAME = "SeraphRobe";
    public static final String ID = makeID(NAME);
    private static final RelicTier RARITY = RelicTier.COMMON;
    private static final LandingSound SOUND = LandingSound.CLINK;
    private boolean used = false;
    private static final int MAXHP = 5;
    private static final int HEAL = 7;

    public SeraphRobe() {
        super(ID, NAME, MyCharacter.Meta.CARD_COLOR, RARITY, SOUND);
        // TODO: Relic image and level up sound.
    }

    @Override
    public String getUpdatedDescription() { return this.DESCRIPTIONS[0] + MAXHP + this.DESCRIPTIONS[1] + HEAL + this.DESCRIPTIONS[2]; }

    @Override
    public void onEquip() {
        flash();
        addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        AbstractDungeon.player.increaseMaxHp(MAXHP, true);
        setCounter(-1);
        //
    }

    @Override
    public void onRightClick() {
        if (this.counter == -2) { addToBot(new TalkAction(true, this.DESCRIPTIONS[3], 1.0F, 2.0F)); }
        else {
            AbstractDungeon.player.heal(HEAL, true);
            setCounter(-2);
            flash();
            stopPulse();
        }
    }

    @Override
    public void setCounter(int counter) {
        this.counter = counter;
        if (counter == -2) {
            this.grayscale = true;
            this.usedUp = true;
        }
    }

    @Override
    public AbstractRelic makeCopy() { return new SeraphRobe(); }
}