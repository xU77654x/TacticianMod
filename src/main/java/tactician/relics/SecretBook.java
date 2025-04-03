package tactician.relics;

import com.evacipated.cardcrawl.mod.stslib.relics.ClickableRelic;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.Frost;
import com.megacrit.cardcrawl.orbs.Lightning;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import tactician.character.MyCharacter;
import tactician.powers.DeflectPower;

import static tactician.TacticianMod.makeID;

public class SecretBook extends BaseRelic implements ClickableRelic {
    private static final String NAME = "SecretBook";
    public static final String ID = makeID(NAME);
    private static final RelicTier RARITY = RelicTier.STARTER;
    private static final LandingSound SOUND = LandingSound.CLINK;
    private static final int CONSUMEVIGOR = 3;
    private static final int CONSUMEDEFLECT = 3;
    private boolean used = false;
    public AbstractPlayer p;

    public SecretBook() {
        super(ID, NAME, MyCharacter.Meta.CARD_COLOR, RARITY, SOUND);
        this.p = AbstractDungeon.player;
        // TODO: Relic image and level up sound.
    }

    @Override
    public String getUpdatedDescription() { return this.DESCRIPTIONS[0] + CONSUMEVIGOR + this.DESCRIPTIONS[1] + CONSUMEDEFLECT + this.DESCRIPTIONS[2]; }

    private String setDescription(AbstractPlayer.PlayerClass c) { return this.DESCRIPTIONS[2] + this.DESCRIPTIONS[0] + '\006' + this.DESCRIPTIONS[1]; }

    @Override
    public void atTurnStart() {
        this.used = false;
        beginLongPulse();
    }

    @Override
    public void onRightClick() {
        if (this.used) { addToBot(new TalkAction(true, this.DESCRIPTIONS[4], 1.0F, 2.0F)); }
        if (!this.used && (!AbstractDungeon.getCurrRoom().isBattleOver && AbstractDungeon.getCurrRoom().monsters != null && AbstractDungeon.getCurrRoom().monsters.monsters != null && !AbstractDungeon.getCurrRoom().monsters.monsters.isEmpty() && !AbstractDungeon.getMonsters().areMonstersBasicallyDead())) {
            flash();
            if (!this.p.hasPower(VigorPower.POWER_ID) && (!this.p.hasPower(DeflectPower.POWER_ID))) {
                addToBot(new TalkAction(true, this.DESCRIPTIONS[3], 1.0F, 2.0F));
                /// (0V, 0D)
            }
            else if (this.p.hasPower(VigorPower.POWER_ID) && (this.p.getPower(VigorPower.POWER_ID).amount < CONSUMEVIGOR) && (!this.p.hasPower(DeflectPower.POWER_ID))) {
                addToBot(new TalkAction(true, this.DESCRIPTIONS[5] + CONSUMEVIGOR + this.DESCRIPTIONS[6] + CONSUMEDEFLECT + this.DESCRIPTIONS[7], 1.0F, 2.0F));
                ///  (1V, 0D)
            }
            else if (!this.p.hasPower(VigorPower.POWER_ID) && (this.p.hasPower(DeflectPower.POWER_ID) && (this.p.getPower(DeflectPower.POWER_ID).amount < CONSUMEDEFLECT))) {
                addToBot(new TalkAction(true, this.DESCRIPTIONS[5] + CONSUMEVIGOR + this.DESCRIPTIONS[6] + CONSUMEDEFLECT + this.DESCRIPTIONS[7], 1.0F, 2.0F));
                ///  (0V, 1D)
            }
            else if (this.p.hasPower(VigorPower.POWER_ID) && (this.p.getPower(VigorPower.POWER_ID).amount < CONSUMEVIGOR) && this.p.hasPower(DeflectPower.POWER_ID) && (this.p.getPower(DeflectPower.POWER_ID).amount < CONSUMEDEFLECT)) {
                addToBot(new TalkAction(true, this.DESCRIPTIONS[5] + CONSUMEVIGOR + this.DESCRIPTIONS[6] + CONSUMEDEFLECT + this.DESCRIPTIONS[7], 1.0F, 2.0F));
                /// (1V, 1D)
            }
            else {
                this.used = true;
                flash();
                stopPulse();

                if (this.p.hasPower(VigorPower.POWER_ID) && (this.p.getPower(VigorPower.POWER_ID).amount >= CONSUMEVIGOR)) {
                    addToBot(new ReducePowerAction(this.p, this.p, this.p.getPower(VigorPower.POWER_ID), CONSUMEVIGOR));
                    this.p.channelOrb(new Lightning()); /// (3V, 0D), (3V, 1D), (3V, 3D)
                }
                if (this.p.hasPower(DeflectPower.POWER_ID) && (this.p.getPower(DeflectPower.POWER_ID).amount >= CONSUMEDEFLECT)) {
                    addToBot(new ReducePowerAction(this.p, this.p, this.p.getPower(DeflectPower.POWER_ID), CONSUMEDEFLECT));
                    this.p.channelOrb(new Frost()); /// (0V, 3D), (1V, 3D), (3V, 3D repeat)
                }
            }
        } // Credit to Balls: Rubber Bouncy Ball for the code to determine if the room has living monsters as a check for right-click relics.
    }

    @Override
    public AbstractRelic makeCopy() { return new SecretBook(); }
}