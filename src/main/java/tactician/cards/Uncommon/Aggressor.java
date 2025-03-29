package tactician.cards.Uncommon;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import tactician.cards.BaseCard;
import tactician.character.MyCharacter;
import tactician.util.CardStats;
import tactician.util.Wiz;

public class Aggressor extends BaseCard {
    public static final String ID = makeID(Aggressor.class.getSimpleName());
    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            1
    );
    public Aggressor() {
        super(ID, info);
        setMagic(0, 0);
        setCostUpgrade(0);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new VigorPower(p, this.magicNumber), this.magicNumber));
        // TODO: Ensure that weapon types for both the player and the enemy do not count as buffs or debuffs. The "end turn" powers from Zeal are normal buffs, and should count.
    }

    private int enemyBuffsDebuffs() {
        return (int) Wiz.getEnemies().stream().flatMap(m -> m.powers.stream()).map(p -> p.ID).distinct().count();
    }

    public int playerBuffsDebuffs() {
        return (int) AbstractDungeon.player.powers.stream().distinct().count();
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        magicNumber = baseMagicNumber;
        magicNumber += enemyBuffsDebuffs() + playerBuffsDebuffs();
        isMagicNumberModified = (magicNumber != baseMagicNumber);
        // Credit to linger for the code.
        // "Block and damage are set based on baseDamage and baseBlock when applyPowers/calculateCardDamage is called in AbstractCard.
        // magicNumber has no "calculation", and so it does not do this."
    }

    @Override
    public void calculateCardDamage(AbstractMonster m) {
        int realMagic = baseMagicNumber;
        baseMagicNumber += enemyBuffsDebuffs() + playerBuffsDebuffs();
        super.calculateCardDamage(m);
        baseMagicNumber = realMagic;
        this.isMagicNumberModified = (magicNumber != baseMagicNumber);
    }

    @Override
    public AbstractCard makeCopy() { return new Aggressor(); }
}

