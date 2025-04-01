package tactician.cards.Common;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.utility.ScryAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.LoseDexterityPower;
import tactician.cards.BaseCard;
import tactician.character.MyCharacter;
import tactician.util.CardStats;

public class Tantivy extends BaseCard {
    public static final String ID = makeID(Tantivy.class.getSimpleName());
    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.COMMON,
            CardTarget.SELF,
            1
    );

    public Tantivy() {
        super(ID, info);
        setMagic(4, 3); // magicNumber is used for Scry; the customVar is used for Strength and Strength Down.
        setCustomVar("magicTempDex", 2, 1); // TempStr 2 > 3
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster M) {
        addToBot(new ScryAction(this.magicNumber)); // Scry 3 > 6
        addToBot(new DrawCardAction(p, 1)); // Draw 1.
        addToBot(new ApplyPowerAction(p, p, new DexterityPower(p, customVar("magicTempDex")), customVar("magicTempDex"))); // Gain 2 > 3 Dexterity
        addToBot(new ApplyPowerAction(p, p, new LoseDexterityPower(p, customVar("magicTempDex")), customVar("magicTempDex"))); // Gain 2 > 3 Dex Down
    }

    @Override
    public AbstractCard makeCopy() { return new Tantivy(); }
}