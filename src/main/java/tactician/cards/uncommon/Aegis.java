package tactician.cards.uncommon;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tactician.actions.EasyModalChoiceAction;
import tactician.actions.PlaySoundAction;
import tactician.cards.Tactician9CopyCard;
import tactician.cards.cardchoice.*;
import tactician.character.TacticianRobin;
import tactician.powers.weapons.*;
import tactician.util.CardStats;
import tactician.util.CustomTags;
import tactician.util.Wiz;

import java.util.ArrayList;

public class Aegis extends Tactician9CopyCard {
    public static final String ID = makeID(Aegis.class.getSimpleName());
    private static final CardStats info = new CardStats(
            TacticianRobin.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.ENEMY,
            2
    );
    int weapon = 9;

	public Aegis() {
        super(ID, info);
        setBlock(10, 4);
        tags.add(CustomTags.COPY);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (AbstractDungeon.player instanceof TacticianRobin && (Boolean.TRUE.equals(p.hasPower(Weapon1SwordPower.POWER_ID) || p.hasPower(Weapon2LancePower.POWER_ID) || p.hasPower(Weapon3AxePower.POWER_ID) || p.hasPower(Weapon4BowPower.POWER_ID)))) {
            ArrayList<AbstractCard> easyCardList = new ArrayList<>();
            easyCardList.add(new Weapon5Wind(() ->  {
                weapon = 5; addToBot(new ApplyPowerAction(p, p, new Weapon5WindPower(p))); calculateCardDamage(m);
                addToBot(new PlaySoundAction("tactician:PaviseAegis", 0.90f));
                addToBot(new GainBlockAction(p, p, this.block));
            }));
            easyCardList.add(new Weapon6Fire(() ->  {
                weapon = 6; addToBot(new ApplyPowerAction(p, p, new Weapon6FirePower(p))); calculateCardDamage(m);
                addToBot(new PlaySoundAction("tactician:PaviseAegis", 0.90f));
                addToBot(new GainBlockAction(p, p, this.block));
            }));
            easyCardList.add(new Weapon7Thunder(() ->  {
                weapon = 7; addToBot(new ApplyPowerAction(p, p, new Weapon7ThunderPower(p))); calculateCardDamage(m);
                addToBot(new PlaySoundAction("tactician:PaviseAegis", 0.90f));
                addToBot(new GainBlockAction(p, p, this.block));
            }));
            easyCardList.add(new Weapon8Dark(() ->  {
                weapon = 8; addToBot(new ApplyPowerAction(p, p, new Weapon8DarkPower(p))); calculateCardDamage(m);
                addToBot(new PlaySoundAction("tactician:PaviseAegis", 0.90f));
                addToBot(new GainBlockAction(p, p, this.block));
            }));
            addToBot(new EasyModalChoiceAction(easyCardList));
        }
        else { weapon = 9; calculateCardDamage(m);
            addToBot(new PlaySoundAction("tactician:PaviseAegis", 0.90f));
            addToBot(new GainBlockAction(p, p, this.block));
        }
    }

    @Override
    public void applyPowers() {
        boolean favor = Boolean.TRUE.equals(AbstractDungeon.player.hasPower(Weapon5WindPower.POWER_ID) || AbstractDungeon.player.hasPower(Weapon6FirePower.POWER_ID) || AbstractDungeon.player.hasPower(Weapon7ThunderPower.POWER_ID) || AbstractDungeon.player.hasPower(Weapon8DarkPower.POWER_ID));
		if (!this.freeToPlayOnce && this.costForTurn != 0) {
            this.costForTurn = 2;
            if (favor || !(AbstractDungeon.player instanceof TacticianRobin)) { this.costForTurn = 1; }
        }
        // Aegis costs 1 less if used off-class.
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
    public AbstractCard makeCopy() { return new Aegis(); }
}