package tactician.relics;

import basemod.cardmods.EtherealMod;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.red.Anger;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import tactician.character.MyCharacter;
import static tactician.TacticianMod.makeID;

public class EnergyDrop extends BaseRelic {
    private static final String NAME = "EnergyDrop";
    public static final String ID = makeID(NAME);
    private static final RelicTier RARITY = RelicTier.RARE;
    private static final LandingSound SOUND = LandingSound.CLINK;
    public AbstractPlayer p;

    public EnergyDrop() {
        super(ID, NAME, MyCharacter.Meta.CARD_COLOR, RARITY, SOUND);
        // TODO: Relic image and level up sound.
    }

    @Override
    public String getUpdatedDescription() { return this.DESCRIPTIONS[0]; }

    @Override
    public void atBattleStart() {
        flash();
        addToTop(new ApplyPowerAction(p, p, new StrengthPower(p, 1), 1));
        AbstractCard c = new Anger();
        CardModifierManager.addModifier(c, new EtherealMod());
        addToBot(new MakeTempCardInHandAction(c, 1));
    }

    @Override
    public AbstractRelic makeCopy() { return new EnergyDrop(); }
}