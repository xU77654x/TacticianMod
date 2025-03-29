package tactician.relics;

import basemod.patches.com.megacrit.cardcrawl.relics.AbstractRelic.ObtainRelicGetHook;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import tactician.character.MyCharacter;

import static tactician.TacticianMod.makeID;

public class SeraphRobe extends BaseRelic {
    private static final String NAME = "SeraphRobe";
    public static final String ID = makeID(NAME);
    private static final RelicTier RARITY = RelicTier.COMMON;
    private static final LandingSound SOUND = LandingSound.CLINK;
    private static final int MAXHP = 1;
    private static final int HEAL = 2;

    public SeraphRobe() {
        super(ID, NAME, MyCharacter.Meta.CARD_COLOR, RARITY, SOUND);
        // TODO: Relic image and level up sound.
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + MAXHP + this.DESCRIPTIONS[1] + HEAL + this.DESCRIPTIONS[2];
    }

    @Override
    public void onEquip() {
        flash();
        addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        AbstractDungeon.player.increaseMaxHp(MAXHP, true);
        AbstractDungeon.player.heal(HEAL, true);
        // TODO: BaseMod has a custom hook for this to activate when obtaining other relics.
    }

    @Override
    public AbstractRelic makeCopy() {
        return new SeraphRobe();
    }
}