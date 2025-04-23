package tactician.cards.Uncommon;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
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
        this.baseMagicNumber = this.magicNumber = 0;
        setCostUpgrade(0);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DrawCardAction(p, this.magicNumber)); // Draw 1.
        // TODO: If played in a Elite or Boss... (see Packmaster: Hunter's Instincts)
        // TODO: addToBot(increase !M! by 1.)
        /* for (AbstractCard c : AbstractDungeon.player.masterDeck.group) {
                if (c.uuid.equals(this.uuid)) {
                    c.baseMagicNumber += 1;
                }
            }
        }*/

        /* Code from Slaver's Collar.
        boolean isEliteOrBoss = (AbstractDungeon.getCurrRoom()).eliteTrigger;
        for (AbstractMonster m : (AbstractDungeon.getMonsters()).monsters) {
            if (m.type == AbstractMonster.EnemyType.BOSS)
                isEliteOrBoss = true;
        }
        if (isEliteOrBoss) {
            c.baseMagicNumber += 1;
        }
        */
    }

    @Override
    public AbstractCard makeCopy() { return new Underdog(); }
}