package tactician.powers;
import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import tactician.TacticianMod;
import tactician.util.TextureLoader;
import static tactician.TacticianMod.powerPath;

public class PatiencePower extends AbstractPower implements CloneablePowerInterface {
    public static final String POWER_ID = TacticianMod.makeID("PatiencePower");
    private static final Texture tex84 = TextureLoader.getTexture(powerPath("large/Patience_Large.png"));
    private static final Texture tex32 = TextureLoader.getTexture(powerPath("Patience.png"));
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public PatiencePower(int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = AbstractDungeon.player;
        this.amount = amount;
        this.type = AbstractPower.PowerType.BUFF;
        this.isTurnBased = false;
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 96, 96);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);
        updateDescription();
    }

    public void atStartOfTurnPostDraw() {
        flash();
        addToBot(new ApplyPowerAction(this.owner, this.owner, new DeflectPower(this.amount), this.amount));
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }

    public AbstractPower makeCopy() {
        return new PatiencePower(this.amount);
    }
}