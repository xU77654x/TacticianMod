package tactician.cards.uncommon;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tactician.cards.BaseCard;
import tactician.character.MyCharacter;
import tactician.powers.CreationPulsePower;
import tactician.util.CardStats;

public class CreationPulse extends BaseCard {
    public static final String ID = makeID(CreationPulse.class.getSimpleName());
    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.POWER,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            1
    );

    public CreationPulse() {
        super(ID, info);
        setMagic(1, 1);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) { addToBot(new ApplyPowerAction(p, p, new CreationPulsePower(this.magicNumber), this.magicNumber)); }

    @Override
    public AbstractCard makeCopy() { return new CreationPulse(); }
}