package tactician.relics;

import basemod.cardmods.EtherealMod;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.red.Anger;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import static tactician.TacticianMod.makeID;

public class EnergyDrop extends BaseRelic {
    private static final String NAME = "EnergyDrop";
    public static final String ID = makeID(NAME);
    private static final RelicTier RARITY = RelicTier.RARE;
    private static final LandingSound SOUND = LandingSound.CLINK;
    public AbstractPlayer p;

    public EnergyDrop() {
        super(ID, NAME, RARITY, SOUND);
        Anger c = new Anger();
        CardModifierManager.addModifier(c, new EtherealMod());
    }

    @Override
    public String getUpdatedDescription() { return this.DESCRIPTIONS[0]; }

    @Override
    public void atBattleStart() {
        flash();
        this.p = AbstractDungeon.player;
        addToTop(new ApplyPowerAction(p, p, new StrengthPower(p, 1), 1));
        AbstractCard c = new Anger();
        CardModifierManager.addModifier(c, new EtherealMod());
        addToBot(new MakeTempCardInHandAction(c, 1));
        addToTop(new RelicAboveCreatureAction(p, this));
    }

    @Override
    public void playLandingSFX() { CardCrawlGame.sound.play("tactician:LevelUpFE8"); }

    @Override
    public AbstractRelic makeCopy() { return new EnergyDrop(); }
}