package tactician.cards.uncommon;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tactician.actions.EasyModalChoiceAction;
import tactician.actions.PlaySoundAction;
import tactician.cards.TacticianCard;
import tactician.cards.cardchoice.*;
import tactician.character.TacticianRobin;
import tactician.powers.weapons.*;
import tactician.util.CardStats;
import java.util.ArrayList;

public class Perceptive extends TacticianCard {
    public static final String ID = makeID(Perceptive.class.getSimpleName());
    private static final CardStats info = new CardStats(
            TacticianRobin.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.NONE,
            -2
    );

    public Perceptive() {
        super(ID, info);
        setMagic(1, 0);
        setExhaust(false, true);
        setCostUpgrade(0);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {}

    @Override
    public void triggerWhenDrawn() { addToBot(new DrawCardAction(this.magicNumber)); }

    @Override
    public void triggerOnExhaust() {
        addToBot(new PlaySoundAction("tactician:Perceptive", 1.10f));
        AbstractPlayer p = AbstractDungeon.player;
        if (AbstractDungeon.player instanceof TacticianRobin) {
            ArrayList<AbstractCard> easyCardList = new ArrayList<>();
            easyCardList.add(new Weapon1Sword(() -> { if (!p.hasPower(Weapon1SwordPower.POWER_ID)) { addToBot(new ApplyPowerAction(p, p, new Weapon1SwordPower(p))); } }));
            easyCardList.add(new Weapon2Lance(() -> { if (!p.hasPower(Weapon2LancePower.POWER_ID)) { addToBot(new ApplyPowerAction(p, p, new Weapon2LancePower(p))); } }));
            easyCardList.add(new Weapon3Axe(() -> { if (!p.hasPower(Weapon3AxePower.POWER_ID)) { addToBot(new ApplyPowerAction(p, p, new Weapon3AxePower(p))); } }));
            easyCardList.add(new Weapon4Bow(() -> { if (!p.hasPower(Weapon4BowPower.POWER_ID)) { addToBot(new ApplyPowerAction(p, p, new Weapon4BowPower(p))); } }));
            easyCardList.add(new Weapon5Wind(() -> { if (!p.hasPower(Weapon5WindPower.POWER_ID)) { addToBot(new ApplyPowerAction(p, p, new Weapon5WindPower(p))); } }));
            easyCardList.add(new Weapon6Fire(() -> { if (!p.hasPower(Weapon6FirePower.POWER_ID)) { addToBot(new ApplyPowerAction(p, p, new Weapon6FirePower(p))); } }));
            easyCardList.add(new Weapon7Thunder(() -> { if (!p.hasPower(Weapon7ThunderPower.POWER_ID)) { addToBot(new ApplyPowerAction(p, p, new Weapon7ThunderPower(p))); } }));
            easyCardList.add(new Weapon8Dark(() -> { if (!p.hasPower(Weapon8DarkPower.POWER_ID)) { addToBot(new ApplyPowerAction(p, p, new Weapon8DarkPower(p))); } }));
            addToBot(new EasyModalChoiceAction(easyCardList));
        }
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        boolean canUse = super.canUse(p, m);
        if (!canUse) { return false; }
        if (!this.upgraded) {
            this.cantUseMessage = cardStrings.EXTENDED_DESCRIPTION[0];
            canUse = false;
        }
        return canUse;
    }

    @Override
    public AbstractCard makeCopy() { return new Perceptive(); }
} // TODO: Rename to Perceptive.