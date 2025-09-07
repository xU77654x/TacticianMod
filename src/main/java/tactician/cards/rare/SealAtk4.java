package tactician.cards.rare;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import tactician.actions.PlaySoundAction;
import tactician.cards.TacticianCard;
import tactician.character.TacticianRobin;
import tactician.powers.ShovePower;
import tactician.util.CardStats;

public class SealAtk4 extends TacticianCard {
    public static final String ID = makeID(SealAtk4.class.getSimpleName());
    private static final CardStats info = new CardStats(
            TacticianRobin.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.RARE,
            CardTarget.ENEMY,
            1
    );
    public SealAtk4() {
        super(ID, info);
        setMagic(4, 0);
        setExhaust(true);
        setEthereal(true);
        setCostUpgrade(0);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (!m.hasPower(ArtifactPower.POWER_ID)) { addToBot(new PlaySoundAction("tactician:StatDecreaseFE", 1.00f)); }
        addToBot(new ApplyPowerAction(m, p, new StrengthPower(m, -this.magicNumber), -this.magicNumber));
        if (!p.hasPower(ArtifactPower.POWER_ID) && !p.hasPower(ShovePower.POWER_ID)) { addToBot(new PlaySoundAction("tactician:StatDecreaseFE", 1.00f)); }
        addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, -1), -1));
    }

    @Override
    public AbstractCard makeCopy() { return new SealAtk4(); }
}