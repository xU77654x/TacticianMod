package tactician.cards.Uncommon;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import tactician.cards.BaseCard;
import tactician.character.MyCharacter;
import tactician.powers.DeflectPower;
import tactician.util.CardStats;
import tactician.util.CustomTags;

public class LanceJab extends BaseCard {
    public static final String ID = makeID(LanceJab.class.getSimpleName());
    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.UNCOMMON,
            CardTarget.ENEMY,
            1
    );
    public LanceJab() {
        super(ID, info);
        setDamage(3, 1);
        setBlock(3, 1);
        tags.add(CustomTags.LANCE);
        tags.add(CustomTags.COMBAT_ART);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, p, block));
        addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
        addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
    }

    @Override
    public void applyPowers() {
        int realBlock = baseBlock;
        if (AbstractDungeon.player.hasPower(StrengthPower.POWER_ID))
            baseBlock += AbstractDungeon.player.getPower(StrengthPower.POWER_ID).amount;
        if (AbstractDungeon.player.hasPower(DeflectPower.POWER_ID))
            baseBlock += AbstractDungeon.player.getPower(DeflectPower.POWER_ID).amount;
        super.applyPowers();
        baseBlock = realBlock;
        this.isBlockModified = (block != baseBlock);
    }

    @Override
    public void calculateCardDamage(AbstractMonster m) {
        int realBlock = baseBlock;
        if (AbstractDungeon.player.hasPower(StrengthPower.POWER_ID))
            baseBlock += AbstractDungeon.player.getPower(StrengthPower.POWER_ID).amount;
        if (AbstractDungeon.player.hasPower(DeflectPower.POWER_ID))
            baseBlock += AbstractDungeon.player.getPower(DeflectPower.POWER_ID).amount;
        super.calculateCardDamage(m);
        baseBlock = realBlock;
        this.isBlockModified = (block != baseBlock);
        // This section is a requirement in order for the card's displayed Block gain to be accurate when this action is directed at an enemy.
    }

    @Override
    public AbstractCard makeCopy() { return new LanceJab(); }
}