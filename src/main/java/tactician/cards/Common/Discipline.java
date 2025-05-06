package tactician.cards.Common;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tactician.actions.EasyModalChoiceAction;
import tactician.cards.BaseCard;
import tactician.cards.CardChoice.*;
import tactician.character.MyCharacter;
import tactician.powers.weaponscurrent.*;
import tactician.util.CardStats;

import java.util.ArrayList;

public class Discipline extends BaseCard {
    public static final String ID = makeID(Discipline.class.getSimpleName());
    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.COMMON,
            CardTarget.SELF,
            1
    );

    public Discipline() {
        super(ID, info);
        setBlock(6, 3);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, p, this.block));

        ArrayList<AbstractCard> easyCardList = new ArrayList<>();
        easyCardList.add(new Weapon1Sword(() -> addToBot(new ApplyPowerAction(p, p, new Weapon1SwordPower(p)))));
        easyCardList.add(new Weapon2Lance(() -> addToBot(new ApplyPowerAction(p, p, new Weapon2LancePower(p)))));
        easyCardList.add(new Weapon3Axe(() -> addToBot(new ApplyPowerAction(p, p, new Weapon3AxePower(p)))));
        easyCardList.add(new Weapon4Bow(() -> addToBot(new ApplyPowerAction(p, p, new Weapon4BowPower(p)))));
        easyCardList.add(new Weapon5Wind(() -> addToBot(new ApplyPowerAction(p, p, new Weapon5WindPower(p)))));
        easyCardList.add(new Weapon6Fire(() -> addToBot(new ApplyPowerAction(p, p, new Weapon6FirePower(p)))));
        easyCardList.add(new Weapon7Thunder(() -> addToBot(new ApplyPowerAction(p, p, new Weapon7ThunderPower(p)))));
        easyCardList.add(new Weapon8Dark(() -> addToBot(new ApplyPowerAction(p, p, new Weapon8DarkPower(p)))));
        addToBot(new EasyModalChoiceAction(easyCardList));
    }

    @Override
    public AbstractCard makeCopy() { return new Discipline(); }
}