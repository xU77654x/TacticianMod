package tactician.relics;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.curses.Necronomicurse;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.evacipated.cardcrawl.mod.stslib.actions.tempHp.AddTemporaryHPAction;
import tactician.character.MyCharacter;
import java.util.Objects;
import static tactician.TacticianMod.makeID;

public class Talisman extends BaseRelic {
    private static final String NAME = "Talisman";
    public static final String ID = makeID(NAME);
    private static final RelicTier RARITY = RelicTier.RARE;
    private static final LandingSound SOUND = LandingSound.CLINK;
    private static final int TEMPHP = 2;

    public Talisman() {
        super(ID, NAME, MyCharacter.Meta.CARD_COLOR, RARITY, SOUND);
        // TODO: Relic image and level up sound.
    }

    @Override
    public String getUpdatedDescription() { return this.DESCRIPTIONS[0] + TEMPHP + this.DESCRIPTIONS[1]; }

    @Override
    public void onExhaust(AbstractCard card) {
        if (!Objects.equals(card.cardID, Necronomicurse.ID)) {
            flash();
            addToBot(new AddTemporaryHPAction(AbstractDungeon.player, AbstractDungeon.player, TEMPHP));
        }
    }

    @Override
    public AbstractRelic makeCopy() { return new Talisman(); }
}