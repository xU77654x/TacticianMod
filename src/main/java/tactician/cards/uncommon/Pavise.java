package tactician.cards.uncommon;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tactician.actions.EasyModalChoiceAction;
import tactician.cards.Base9CopyCard;
import tactician.cards.BaseCard;
import tactician.cards.cardchoice.*;
import tactician.character.MyCharacter;
import tactician.powers.weapons.*;
import tactician.util.CardStats;
import tactician.util.CustomTags;
import tactician.util.Wiz;

import java.util.ArrayList;

public class Pavise extends Base9CopyCard {
    public static final String ID = makeID(Pavise.class.getSimpleName());
    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.ENEMY,
            2
    );
    int weapon = 9;

    public Pavise() {
        super(ID, info);
        setBlock(5, 2);
        tags.add(CustomTags.COPY);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (Boolean.TRUE.equals(p.hasPower(Weapon5WindPower.POWER_ID) || p.hasPower(Weapon6FirePower.POWER_ID) || p.hasPower(Weapon7ThunderPower.POWER_ID) || p.hasPower(Weapon8DarkPower.POWER_ID))) {
            ArrayList<AbstractCard> easyCardList = new ArrayList<>();
            easyCardList.add(new Weapon1Sword(() ->  { weapon = 1; addToBot(new ApplyPowerAction(p, p, new Weapon1SwordPower(p))); }));
            easyCardList.add(new Weapon2Lance(() ->  { weapon = 2; addToBot(new ApplyPowerAction(p, p, new Weapon2LancePower(p))); }));
            easyCardList.add(new Weapon3Axe(() ->  { weapon = 3; addToBot(new ApplyPowerAction(p, p, new Weapon3AxePower(p))); }));
            easyCardList.add(new Weapon4Bow(() ->  { weapon = 4; addToBot(new ApplyPowerAction(p, p, new Weapon4BowPower(p))); }));
            addToBot(new EasyModalChoiceAction(easyCardList));
        }
        else { weapon = 9; }
        calculateCardDamage(m);
        addToBot(new GainBlockAction(p, p, this.block));
        addToBot(new GainBlockAction(p, p, this.block));
    }

    @Override
    public void applyPowers() {
        boolean favor = Boolean.TRUE.equals(AbstractDungeon.player.hasPower(Weapon1SwordPower.POWER_ID) || AbstractDungeon.player.hasPower(Weapon2LancePower.POWER_ID) || AbstractDungeon.player.hasPower(Weapon3AxePower.POWER_ID) || AbstractDungeon.player.hasPower(Weapon4BowPower.POWER_ID));
        if (!this.freeToPlayOnce && this.costForTurn != 0) {
            this.costForTurn = 2;
            if (favor) { this.costForTurn = 1; }
        }
        // This is what lets a Favored card reduce Energy cost.
    }

    @Override
    public void calculateCardDamage(AbstractMonster m) {
        int realBlock = baseBlock;
        baseBlock += Wiz.playerWeaponCalc(m, weapon);
        super.calculateCardDamage(m);
        baseBlock = realBlock;
        this.isBlockModified = (block != baseBlock);
    }

    @Override
    public AbstractCard makeCopy() { return new Pavise(); }
}