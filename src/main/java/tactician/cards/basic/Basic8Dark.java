package tactician.cards.basic;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tactician.cards.BaseCard;
import tactician.character.MyCharacter;
import tactician.powers.ReekingSwapPower;
import tactician.powers.weaponscurrent.Weapon8DarkPower;
import tactician.util.CardStats;
import tactician.util.CustomTags;
import tactician.util.Wiz;

public class Basic8Dark extends BaseCard {

    public static final String ID = makeID(Basic8Dark.class.getSimpleName());
    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.BASIC,
            CardTarget.SELF_AND_ENEMY,
            1
    );


    public Basic8Dark() {
        super(ID, info);
        tags.add(CustomTags.DARK);

        if (AbstractDungeon.player != null) {
            setDamage(6, 3);
            setBlock(5, 3);
            if (AbstractDungeon.player.hasPower(ReekingSwapPower.POWER_ID)) {
                this.type = AbstractCard.CardType.ATTACK;
                tags.add(CardTags.STARTER_STRIKE);
                tags.add(CardTags.STRIKE);
            }
            else {
                this.type = AbstractCard.CardType.SKILL;
                setEthereal(false, true);
                tags.add(CardTags.STARTER_DEFEND);
            }
        }
        CardTarget a = target;
        /*
        if (a == AbstractDungeon.player) {

        } */
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new Weapon8DarkPower(p)));
        calculateCardDamage(m);
        if (!AbstractDungeon.player.hasPower(ReekingSwapPower.POWER_ID)) { addToBot(new GainBlockAction(p, p, this.block)); }
        else { addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_VERTICAL)); }
    }

    @Override
    public void calculateCardDamage(AbstractMonster m) {
        int realDamage = baseDamage;
        int realBlock = baseBlock;
        baseDamage += Wiz.playerWeaponCalc(m, 8);
        baseBlock += Wiz.playerWeaponCalc(m, 8);
        super.calculateCardDamage(m);
        baseDamage = realDamage;
        baseBlock = realBlock;
        this.isDamageModified = (damage != baseDamage);
        this.isBlockModified = (block != baseBlock);
    }

    // public void onObtain() { cardState = 1; }

    /*
    public void boop(int relicState){
        this.cardState = relicState;
		if (cardState == 0) {
            this.type = AbstractCard.CardType.ATTACK;
            // loadCardImage(IMG_24);
        }
        else if (cardState == 1) {
            this.type = AbstractCard.CardType.SKILL;
            // loadCardImage(IMG_24);
        }
	} */

    /*
    AbstractRelic reekingBox = AbstractDungeon.player.getRelic(ReekingBox.ID);
    public int state = reekingBox.counter; */

    @Override
    public AbstractCard makeCopy() {
        if (AbstractDungeon.player != null) {
            if (AbstractDungeon.player.hasPower(ReekingSwapPower.POWER_ID)) {
                this.type = AbstractCard.CardType.ATTACK;
                // TODO: Set card image?
                if (!this.upgraded) {
                    this.name = cardStrings.EXTENDED_DESCRIPTION[0];
                    this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[1];
                } else {
                    this.name = cardStrings.EXTENDED_DESCRIPTION[2];
                    this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[3];
                }
            }
            else {
                this.type = AbstractCard.CardType.SKILL;
                // TODO: Set card image?
                if (!this.upgraded) {
                    this.name = cardStrings.EXTENDED_DESCRIPTION[4];
                    this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[5];
                } else {
                    this.name = cardStrings.EXTENDED_DESCRIPTION[6];
                    this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[7];
                }
            }
        }
        return new Basic8Dark();
    }
}