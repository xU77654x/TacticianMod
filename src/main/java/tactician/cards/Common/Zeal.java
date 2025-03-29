package tactician.cards.Common;

import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsCenteredAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.LoseDexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import tactician.actions.EasyModalChoiceAction;
import tactician.cards.BaseCard;
import tactician.cards.Weapons.*;
import tactician.character.MyCharacter;
import tactician.util.CardStats;
import java.util.ArrayList;

public class Zeal extends BaseCard {
    public static final String ID = makeID(Zeal.class.getSimpleName());
    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.COMMON,
            CardTarget.SELF,
            1
    );

    public Zeal() {
        super(ID, info);
        setMagic(2, 1);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DrawCardAction(p, this.magicNumber)); // Draw 2 > 3.
        addToBot(new ApplyPowerAction(p, p, new DexterityPower(p, this.magicNumber), this.magicNumber));
        addToBot(new ApplyPowerAction(p, p, new LoseDexterityPower(p, this.magicNumber), this.magicNumber));

        ArrayList<AbstractCard> easyCardList = new ArrayList<>();
        easyCardList.add(new Weapon1Sword(() -> addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, 1), 1))));
        easyCardList.add(new Weapon2Lance(() -> addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, 1), 1))));
        easyCardList.add(new Weapon3Axe(() -> addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, 1), 1))));
        easyCardList.add(new Weapon4Bow(() -> addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, 1), 1))));
        easyCardList.add(new Weapon5Wind(() -> addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, 1), 1))));
        easyCardList.add(new Weapon6Fire(() -> addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, 1), 1))));
        easyCardList.add(new Weapon7Thunder( () -> addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, 1), 1))));
        easyCardList.add(new Weapon8Dark(() -> addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, 1), 1))));
        addToBot(new EasyModalChoiceAction(easyCardList));
        // TODO: Each weapon card sets your weapon type.
    }

    @Override
    public AbstractCard makeCopy() { return new Zeal(); }
}