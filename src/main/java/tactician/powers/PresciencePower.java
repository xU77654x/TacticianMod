package tactician.powers;
import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.orbs.Lightning;
import com.megacrit.cardcrawl.powers.AbstractPower;
import tactician.TacticianMod;
import tactician.util.TextureLoader;
import static tactician.TacticianMod.imagePath;

public class PresciencePower extends AbstractPower implements CloneablePowerInterface {
    public static final String POWER_ID = TacticianMod.makeID("PresciencePower");
    private static final Texture tex84 = TextureLoader.getTexture(imagePath("Resources/images/powers/large/example.png")); // TODO: Add images here.
    private static final Texture tex32 = TextureLoader.getTexture(imagePath("Resources/images/powers/example.png")); // TODO: Add images here.
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private int cardsPlayed = 0;

    public PresciencePower(int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = AbstractDungeon.player;
        this.amount = amount;
        this.type = AbstractPower.PowerType.BUFF;
        this.isTurnBased = false;
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);
        updateDescription();
    }

    public void atStartOfTurn() { this.cardsPlayed = 0; }

    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (AbstractDungeon.actionManager.cardsPlayedThisTurn.size() <= 1 && (card.type == AbstractCard.CardType.ATTACK)) {
            for (int i = 0; i < this.amount; i++) { addToBot(new ChannelAction(new Lightning())); }
            this.cardsPlayed++;
            flash();
        }
    }

    public void updateDescription() { this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1]; }

    public AbstractPower makeCopy() { return new PresciencePower(this.amount); }
}