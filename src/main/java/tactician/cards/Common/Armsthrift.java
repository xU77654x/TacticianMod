package tactician.cards.Common;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;
import tactician.actions.ArmsthriftAction;
import tactician.actions.EasyModalChoiceAction;
import tactician.cards.BaseCard;
import tactician.cards.Weapons.*;
import tactician.character.MyCharacter;
import tactician.powers.LoseFocusPower;
import tactician.util.CardStats;

import java.util.ArrayList;

public class Armsthrift extends BaseCard {
    public static final String ID = makeID(Armsthrift.class.getSimpleName());
    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.COMMON,
            CardTarget.SELF,
            1
    );

    public Armsthrift() {
        super(ID, info);
        setBlock(5, 3);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, p, this.block));

        ArrayList<AbstractCard> easyCardList = new ArrayList<>();
        easyCardList.add(new UpgradeAttacks(() -> addToBot(new ArmsthriftAction(0))));
        easyCardList.add(new UpgradeSkills(() -> addToBot(new ArmsthriftAction(1))));
        easyCardList.add(new UpgradePowers(() -> addToBot(new ArmsthriftAction(2))));
        addToBot(new EasyModalChoiceAction(easyCardList));
    }

    @Override
    public AbstractCard makeCopy() {
        return new Armsthrift();
    }
}

