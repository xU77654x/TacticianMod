package tactician.cards.rare;

import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tactician.actions.PlaySoundAction;
import tactician.cards.TacticianCard;
import tactician.character.TacticianRobin;
import tactician.effects.PlayVoiceEffect;
import tactician.powers.LunaPower;
import tactician.util.CardStats;

public class Luna extends TacticianCard {
    public static final String ID = makeID(Luna.class.getSimpleName());
    private static final CardStats info = new CardStats(
            TacticianRobin.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.RARE,
            CardTarget.SELF,
            1
    );

    public Luna() {
        super(ID, info);
        setMagic(1, 0);
        setCostUpgrade(0);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToTop(new PlaySoundAction("tactician:Luna_KillingEdgeGain", 1.00f));
        addToBot(new ApplyPowerAction(p, p, new LunaPower(this.magicNumber), this.magicNumber));
        addToBot(new WaitAction(1.00F));
        AbstractDungeon.effectList.add(new PlayVoiceEffect("Luna"));
        // addToBot(new TalkAction(true, cardStrings.EXTENDED_DESCRIPTION[0], 1.0F, 1.0F));
    }

    @Override
    public AbstractCard makeCopy() {
        return new Luna();
    }
}