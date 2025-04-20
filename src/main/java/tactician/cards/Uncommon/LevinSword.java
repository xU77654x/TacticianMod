package tactician.cards.Uncommon;

import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.mod.stslib.patches.FlavorText;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.Lightning;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.FocusPower;
import com.megacrit.cardcrawl.vfx.combat.LightningEffect;
import tactician.actions.EasyModalChoiceAction;
import tactician.cards.BaseCard;
import tactician.cards.Weapons.Weapon1Sword;
import tactician.cards.Weapons.Weapon7Thunder;
import tactician.character.MyCharacter;
import tactician.util.CardStats;

import java.util.ArrayList;

public class LevinSword extends BaseCard {
    public static final String ID = makeID(LevinSword.class.getSimpleName());
    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.UNCOMMON,
            CardTarget.ENEMY,
            2
    );

    public LevinSword() {
        super(ID, info);
        setDamage(13, 4);
        FlavorText.AbstractCardFlavorFields.boxColor.set(this, Color.PURPLE.cpy());
        FlavorText.AbstractCardFlavorFields.textColor.set(this, Color.WHITE.cpy());
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        ArrayList<AbstractCard> easyCardList = new ArrayList<>();
        easyCardList.add(new Weapon1Sword(() ->  {
            addToBot(new ApplyPowerAction(p, p, new DexterityPower(p, this.magicNumber), this.magicNumber)); // TODO: Set your weapon to Sword.
            addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
        }));
        easyCardList.add(new Weapon7Thunder(() ->  {
            addToBot(new ApplyPowerAction(p, p, new FocusPower(p, this.magicNumber), this.magicNumber)); // TODO: Set your weapon to Thunder.
            addToBot(new VFXAction(new LightningEffect(m.drawX, m.drawY), 0.05F));
            addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.LIGHTNING));
        }));
        addToBot(new EasyModalChoiceAction(easyCardList));
        addToBot(new ChannelAction(new Lightning()));
    }

    @Override
    public void triggerOnExhaust() {
        addToBot(new MakeTempCardInHandAction(new Sunder()));
        addToBot(new MakeTempCardInHandAction(new Bolting()));
    }

    @Override
    public AbstractCard makeCopy() { return new LevinSword(); }
}