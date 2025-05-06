package tactician.cards.basic;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tactician.cards.BaseCard;
import tactician.character.MyCharacter;
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
            CardTarget.ENEMY,
            1
    );


    public Basic8Dark() {
        super(ID, info);
        // setDamage(6, 3);
        setBlock(5, 3);
        setEthereal(false, true);
        tags.add(CardTags.STARTER_DEFEND);
        tags.add(CustomTags.DARK);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // TODO: Reeking Box.
        addToBot(new ApplyPowerAction(p, p, new Weapon8DarkPower(p)));
        calculateCardDamage(m);
        // int cardState = 1;
        /* if (false) {  */addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_VERTICAL)); /*}
        else if (cardState == 1) { addToBot(new GainBlockAction(p, p, this.block)); } */
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
    public AbstractCard makeCopy() { return new Basic8Dark(); }
}