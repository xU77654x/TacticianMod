package tactician.zzzdeprecated;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.defect.IncreaseMiscAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tactician.cards.BaseCard;
import tactician.character.MyCharacter;
import tactician.util.CardStats;

public class Underdog extends BaseCard {
    public static final String ID = makeID(Underdog.class.getSimpleName());
    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            1
    );

    public Underdog() {
        super(ID, info);
        this.misc = 0;
        this.baseMagicNumber = 0;
        this.magicNumber = this.baseMagicNumber;
        // this.exhaust = true;
        setCostUpgrade(0);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DrawCardAction(p, this.magicNumber)); // Draw cards.

        // TODO: If played in a Elite or Boss... (see Packmaster: Hunter's Instincts)

        boolean isEliteOrBoss = (AbstractDungeon.getCurrRoom()).eliteTrigger;
        for (AbstractMonster mo : (AbstractDungeon.getMonsters()).monsters) {
			if (mo.type == AbstractMonster.EnemyType.BOSS) {
				isEliteOrBoss = true;
				break;
			}
        }
        if (isEliteOrBoss) { addToBot(new IncreaseMiscAction(this.uuid, this.misc, 1)); }
    }

    public void applyPowers() {
        this.baseMagicNumber = this.misc;
        super.applyPowers();
        initializeDescription();
    }

    @Override
    public AbstractCard makeCopy() { return new Underdog(); }
}