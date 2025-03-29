package tactician.relics;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.DrawCardNextTurnPower;
import com.megacrit.cardcrawl.powers.EnergizedPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.evacipated.cardcrawl.mod.stslib.relics.OnPlayerDeathRelic;
import tactician.character.MyCharacter;
import static tactician.TacticianMod.makeID;

public class Thantophage extends BaseRelic implements OnPlayerDeathRelic {
    private static final String NAME = "Thantophage"; // Determines the filename and ID.
    public static final String ID = makeID(NAME); // Adds prefix to relic ID, resulting in modID:MyRelic
    private static final RelicTier RARITY = RelicTier.RARE;
    private static final LandingSound SOUND = LandingSound.CLINK;
    private static final int DRAW = 2;
    private static final int ENERGY = 2;

    public Thantophage() {
        super(ID, NAME, MyCharacter.Meta.CARD_COLOR, RARITY, SOUND);
        // TODO: Relic image and level up sound.
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + DRAW + this.DESCRIPTIONS[1];
    }

    @Override
    public void setCounter(int setCounter) {
        if (setCounter == -2) {
            usedUp();
            this.counter = -2;
        }
    }

    @Override
    public boolean onPlayerDeath(AbstractPlayer abstractPlayer, DamageInfo damageInfo) {
        if (this.counter != -2) {
            flash();
            addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            int healAmt = AbstractDungeon.player.maxHealth / 4;
            AbstractDungeon.player.heal(Math.max(healAmt, 1), true);
            addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new EnergizedPower(AbstractDungeon.player, ENERGY), ENERGY));
            addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new DrawCardNextTurnPower(AbstractDungeon.player, DRAW), DRAW));
            setCounter(-2);
            return false;
        }
        else {
            return true;
        }
    }

    @Override
    public AbstractRelic makeCopy() {
        return new Thantophage();
    }
}