package tactician.cards.uncommon;

import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.BlueCandle;
import com.megacrit.cardcrawl.relics.MedicalKit;
import tactician.actions.EasyModalChoiceAction;
import tactician.cards.BaseCard;
import tactician.cards.cardchoice.TempBlueCandle;
import tactician.cards.cardchoice.TempMedicalKit;
import tactician.character.MyCharacter;
import tactician.powers.ExpirationPower;
import tactician.util.CardStats;
import tactician.util.FragileRelics;

import java.util.ArrayList;

public class Expiration extends BaseCard {
    public static final String ID = makeID(Expiration.class.getSimpleName());
    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.POWER,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            1
    );

    public Expiration() {
        super(ID, info);
        setMagic(2, 1);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (Boolean.FALSE.equals(p.hasRelic(BlueCandle.ID)) && Boolean.FALSE.equals(p.hasRelic(MedicalKit.ID))) {
            ArrayList<AbstractCard> easyCardList = new ArrayList<>();
            easyCardList.add(new TempBlueCandle(() -> FragileRelics.obtainFragileRelic(new BlueCandle())));
            easyCardList.add(new TempMedicalKit(() -> FragileRelics.obtainFragileRelic(new MedicalKit())));
            addToTop(new EasyModalChoiceAction(easyCardList));
        }
        else if (Boolean.TRUE.equals(p.hasRelic(BlueCandle.ID)) && Boolean.FALSE.equals(p.hasRelic(MedicalKit.ID))) { FragileRelics.obtainFragileRelic(new MedicalKit()); }
        else if (Boolean.TRUE.equals(p.hasRelic(MedicalKit.ID)) && Boolean.FALSE.equals(p.hasRelic(BlueCandle.ID))) { FragileRelics.obtainFragileRelic(new BlueCandle()); }
        else { addToBot(new TalkAction(true, cardStrings.EXTENDED_DESCRIPTION[0], 1.0F, 2.0F)); }

        addToBot(new ApplyPowerAction(p, p, new ExpirationPower(this.magicNumber)));
    }

    @Override
    public AbstractCard makeCopy() { return new Expiration(); }
}