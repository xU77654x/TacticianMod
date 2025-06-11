package tactician.zzzdeprecated;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.MawBank;
import basemod.ReflectionHacks;
import tactician.character.MyCharacter;
import tactician.relics.BaseRelic;
import static tactician.TacticianMod.makeID;
/*
public class AsheraIcon extends BaseRelic {
    private static final String NAME = "AsheraIcon";
    public static final String ID = makeID(NAME);
    private static final RelicTier RARITY = RelicTier.UNCOMMON;
    private static final LandingSound SOUND = LandingSound.CLINK;
    private static final int MOOLAH = 200;

    public AsheraIcon() {
        super(ID, NAME, MyCharacter.Meta.CARD_COLOR, RARITY, SOUND);
        // TODO: Relic image and level up sound.
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + MOOLAH + this.DESCRIPTIONS[1];
    }

    @Override
    public void onEquip() {
        CardCrawlGame.sound.play("GOLD_GAIN");
        AbstractDungeon.player.gainGold(MOOLAH);
        AbstractRelic mawBank = AbstractDungeon.player.getRelic(MawBank.ID);
        if (mawBank != null && mawBank.usedUp) {
            flash();
            mawBank.setCounter(-1);
            mawBank.grayscale = false;
            mawBank.usedUp = false;
            mawBank.tips.clear();
            mawBank.tips.add(new PowerTip(mawBank.name, mawBank.description));
            ReflectionHacks.privateMethod(AbstractRelic.class, "initializeTips");
            mawBank.getUpdatedDescription();
            mawBank.description = mawBank.getUpdatedDescription();
        }
    }

    public void usedUp() {
        this.grayscale = true;
        this.usedUp = true;
        this.description = MSG[2];
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
        initializeTips();
    }

    @Override
    public AbstractRelic makeCopy() {
        return new AsheraIcon();
    }
}*/