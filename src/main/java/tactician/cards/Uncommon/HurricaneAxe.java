package tactician.cards.Uncommon;

import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.mod.stslib.patches.FlavorText;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.FocusPower;
import tactician.actions.EasyModalChoiceAction;
import tactician.cards.BaseCard;
import tactician.cards.Weapons.Weapon3Axe;
import tactician.cards.Weapons.Weapon5Wind;
import tactician.character.MyCharacter;
import tactician.util.CardStats;

import java.util.ArrayList;

public class HurricaneAxe extends BaseCard {
    public static final String ID = makeID(HurricaneAxe.class.getSimpleName());
    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.UNCOMMON,
            CardTarget.ENEMY,
            2
    );

    public HurricaneAxe() {
        super(ID, info);
        setDamage(16, 4);
        FlavorText.AbstractCardFlavorFields.boxColor.set(this, Color.PURPLE.cpy());
        FlavorText.AbstractCardFlavorFields.textColor.set(this, Color.WHITE.cpy());
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        ArrayList<AbstractCard> easyCardList = new ArrayList<>();
        easyCardList.add(new Weapon3Axe(() ->  {
            addToBot(new ApplyPowerAction(p, p, new DexterityPower(p, this.magicNumber), this.magicNumber)); // TODO: Set your weapon to Axe.
            addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_HEAVY));
        }));
        easyCardList.add(new Weapon5Wind(() ->  {
            addToBot(new ApplyPowerAction(p, p, new FocusPower(p, this.magicNumber), this.magicNumber)); // TODO: Set your weapon to Wind.
            addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
        }));
        addToBot(new EasyModalChoiceAction(easyCardList));
    }

    @Override
    public AbstractCard makeCopy() {
        return new HurricaneAxe();
    }
}

