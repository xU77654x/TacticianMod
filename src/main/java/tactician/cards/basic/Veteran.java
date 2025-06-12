package tactician.cards.basic;

import basemod.helpers.CardModifierManager;
import basemod.helpers.TooltipInfo;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import tactician.cards.Base9CopyCard;
import tactician.cards.common.WrathStrike;
import tactician.cards.common.TempestLance;
import tactician.cards.common.Smash;
import tactician.cards.common.CurvedShot;
import tactician.cards.common.Elwind;
import tactician.cards.common.Arcfire;
import tactician.cards.common.Thunder;
import tactician.cards.common.Flux;
import tactician.cards.other.Anathema;
import tactician.character.MyCharacter;
import tactician.powers.DeflectPower;
import tactician.powers.LoseFocusPower;
import tactician.powers.weapons.*;
import tactician.util.CardStats;
import tactician.util.CustomTags;
import tactician.util.Wiz;

import java.util.ArrayList;
import java.util.List;

/*
       private void setDuplicateCard(AbstractMonster targetMonster) {
        if (AbstractDungeon.player.hasPower(Weapon1SwordPower.POWER_ID)) { VeteranClone = new WrathStrike(); }
        else if (AbstractDungeon.player.hasPower(Weapon2LancePower.POWER_ID)) { VeteranClone = new TempestLance(); }
        else if (AbstractDungeon.player.hasPower(Weapon3AxePower.POWER_ID)) { VeteranClone = new Smash(); }
        else if (AbstractDungeon.player.hasPower(Weapon4BowPower.POWER_ID)) { VeteranClone = new CurvedShot(); }
        else if (AbstractDungeon.player.hasPower(Weapon5WindPower.POWER_ID)) { VeteranClone = new Elwind(); }
        else if (AbstractDungeon.player.hasPower(Weapon6FirePower.POWER_ID)) { VeteranClone = new Arcfire(); }
        else if (AbstractDungeon.player.hasPower(Weapon7ThunderPower.POWER_ID)) { VeteranClone = new Thunder(); }
        else if (AbstractDungeon.player.hasPower(Weapon8DarkPower.POWER_ID)) { VeteranClone = new Flux(); }
        CardModifierManager.removeAllModifiers(this, true);
        CardModifierManager.copyModifiers(VeteranClone, this, true, false, false);
        if (this.upgraded) { VeteranClone.upgrade(); }

        if (!VeteranClone.cardID.equals(this.cardID)) {
            VeteranClone = VeteranClone.makeStatEquivalentCopy();
            this.name = VeteranClone.name;
            this.type = VeteranClone.type;
            this.target = VeteranClone.target;
            this.baseDamage = VeteranClone.baseDamage;
            this.baseBlock = VeteranClone.baseBlock;
            this.baseMagicNumber = VeteranClone.baseMagicNumber;
            this.rawDescription = VeteranClone.rawDescription;
            // setCardImage();
            initializeTitle();
            VeteranClone.initializeDescription();
            this.description = VeteranClone.description;
            return;
        }
        resetCard();
    }

    public void triggerWhenDrawn(AbstractCard c) { setDuplicateCard(null); }

    private void setDynamicVariables(AbstractMonster targetMonster) {
        VeteranClone.applyPowers();
        if (targetMonster != null) { VeteranClone.calculateCardDamage(targetMonster); }
        this.damage = VeteranClone.damage;
        this.block = VeteranClone.block;
        this.magicNumber = VeteranClone.magicNumber;
        this.isDamageModified = VeteranClone.isDamageModified;
        this.isBlockModified = VeteranClone.isBlockModified;
    }

    @Override
    public void applyPowers() {
        if (VeteranClone != null) { setDuplicateCard(null); }
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        if (VeteranClone != null) { setDynamicVariables(mo); }
        else { setDuplicateCard(mo); }
    }

    @Override
    public void onMoveToDiscard() { resetCard(); }

    @Override
    public void render(SpriteBatch sb) { super.render(sb); }

    @Override
    public AbstractCard makeCopy() { return new Veteran(); }
}
*/

public class Veteran extends Base9CopyCard {
    public static final String ID = makeID(Veteran.class.getSimpleName());
    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.BASIC,
            CardTarget.ENEMY,
            1
    );

    public Veteran() {
        super(ID, info);
        setDamage(0, 0);
        setBlock(0, 0);
        setMagic(0, 0);
        tags.add(CustomTags.COPY);
        tags.add(CustomTags.COMBAT_ART);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (AbstractDungeon.player.hasPower(Weapon1SwordPower.POWER_ID)) { // Wrath Strike
            calculateCardDamage(m);
            addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
            addToBot(new ApplyPowerAction(p, p, new DeflectPower(this.magicNumber), this.magicNumber));
        }
        else if (AbstractDungeon.player.hasPower(Weapon2LancePower.POWER_ID)) { // Tempest Lance
            calculateCardDamage(m);
            addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
            addToBot(new MakeTempCardInDrawPileAction(new Anathema(), 1, true, true));
        }
        else if (AbstractDungeon.player.hasPower(Weapon3AxePower.POWER_ID)) { // Smash
            calculateCardDamage(m);
            addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
            addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, this.magicNumber), this.magicNumber));
            addToBot(new ApplyPowerAction(p, p, new LoseStrengthPower(p, this.magicNumber), this.magicNumber));
        }
        else if (AbstractDungeon.player.hasPower(Weapon4BowPower.POWER_ID)) { // Curved Shot
            calculateCardDamage(m);
            addToBot(new GainBlockAction(p, this.block + this.magicNumber));
            addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
            if (AbstractDungeon.player.hasPower(DeflectPower.POWER_ID)) {
                addToBot(new ApplyPowerAction(p, p, new VigorPower(p, this.magicNumber), this.magicNumber));
                addToBot(new ApplyPowerAction(p, p, new DeflectPower(-this.magicNumber), -this.magicNumber));
                addToBot(new RemoveSpecificPowerAction(p, p, DeflectPower.POWER_ID));
            }
        }
        else if (AbstractDungeon.player.hasPower(Weapon5WindPower.POWER_ID)) { // Elwind
            calculateCardDamage(m);
            addToBot(new TalkAction(true, cardStrings.EXTENDED_DESCRIPTION[3], 1.0F, 2.0F));
            addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
            addToBot(new ApplyPowerAction(p, p, new DrawCardNextTurnPower(p, this.magicNumber), this.magicNumber));
        }
        else if (AbstractDungeon.player.hasPower(Weapon6FirePower.POWER_ID)) { // Arcfire
            calculateCardDamage(m);
            addToBot(new TalkAction(true, cardStrings.EXTENDED_DESCRIPTION[4], 1.0F, 2.0F));
            addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
            addToBot(new ApplyPowerAction(p, p, new FocusPower(p, this.magicNumber), this.magicNumber));
            addToBot(new ApplyPowerAction(p, p, new LoseFocusPower(this.magicNumber), this.magicNumber));
        }
        else if (AbstractDungeon.player.hasPower(Weapon7ThunderPower.POWER_ID)) { // Thunder
            calculateCardDamage(m);
            addToBot(new TalkAction(true, cardStrings.EXTENDED_DESCRIPTION[5], 1.0F, 2.0F));
            addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
            addToBot(new ApplyPowerAction(m, p, new VulnerablePower(m, this.magicNumber, false), this.magicNumber));
        }
        else if (AbstractDungeon.player.hasPower(Weapon8DarkPower.POWER_ID)) { // Flux
            calculateCardDamage(m);
            addToBot(new GainBlockAction(p, p, this.block));
            addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
        }
    }

    public void updateContents(boolean forceReset) {
        this.tags.remove(CardTags.STRIKE);
        this.tags.remove(CustomTags.SWORD);
        this.tags.remove(CustomTags.LANCE);
        this.tags.remove(CustomTags.AXE);
        this.tags.remove(CustomTags.BOW);
        this.tags.remove(CustomTags.WIND);
        this.tags.remove(CustomTags.FIRE);
        this.tags.remove(CustomTags.THUNDER);
        this.tags.remove(CustomTags.DARK);
        this.tags.remove(CustomTags.COPY);
        this.cardsToPreview = null;
        setDamage(0, 0);
        setBlock(0, 0);
        setMagic(0, 0);
        if (!forceReset) {
            if (AbstractDungeon.player.hasPower(Weapon1SwordPower.POWER_ID)) { // Wrath Strike
                setDamage(6);
                if (this.upgraded) { setMagic(6); }
                else { setMagic(3); }
                this.tags.add(CustomTags.SWORD);
                this.tags.add(CardTags.STRIKE);
                this.name = cardStrings.EXTENDED_DESCRIPTION[14];
                this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[6];
                this.glowColor = Color.FIREBRICK;
            }
            else if (AbstractDungeon.player.hasPower(Weapon2LancePower.POWER_ID)) { // Tempest Lance
                if (this.upgraded) { setDamage(16); }
                else { setDamage(12); }
                tags.add(CustomTags.LANCE);
                this.cardsToPreview = new Anathema();
                this.name = cardStrings.EXTENDED_DESCRIPTION[15];
                this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[7];
                this.glowColor = Color.NAVY;
            }
            else if (AbstractDungeon.player.hasPower(Weapon3AxePower.POWER_ID)) { // Smash
                if (this.upgraded) { setDamage(10); }
                else { setDamage(8); }
                if (this.upgraded) { setMagic(3); }
                else { setMagic(2); }
                tags.add(CustomTags.AXE);
                this.name = cardStrings.EXTENDED_DESCRIPTION[16];
                this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[8];
                this.glowColor = Color.LIME;
            }
            else if (AbstractDungeon.player.hasPower(Weapon4BowPower.POWER_ID)) { // Curved Shot
                if (this.upgraded) { setDamage(9); }
                else { setDamage(6); }
                setBlock(3, 0);
                setMagic(0, 0);
                tags.add(CustomTags.BOW);
                this.name = cardStrings.EXTENDED_DESCRIPTION[17];
                this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[9];
                this.glowColor = Color.MAROON;
            }
            else if (AbstractDungeon.player.hasPower(Weapon5WindPower.POWER_ID)) { // Elwind
                if (this.upgraded) { setDamage(9); }
                else { setDamage(7); }
                if (this.upgraded) { setMagic(3); }
                else { setMagic(2); }
                tags.add(CustomTags.WIND);
                this.name = cardStrings.EXTENDED_DESCRIPTION[18];
                this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[10];
                this.glowColor = Color.valueOf("40FF80"); // Too similar to Color.TEAL, but not much can be done about this.
            }
            else if (AbstractDungeon.player.hasPower(Weapon6FirePower.POWER_ID)) { // Arcfire
                if (this.upgraded) { setDamage(10); }
                else { setDamage(8); }
                if (this.upgraded) { setMagic(3); }
                else { setMagic(2); }
                tags.add(CustomTags.FIRE);
                this.name = cardStrings.EXTENDED_DESCRIPTION[19];
                this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[11];
                this.glowColor = Color.SCARLET;
            }
            else if (AbstractDungeon.player.hasPower(Weapon7ThunderPower.POWER_ID)) { // Thunder
                if (this.upgraded) { setDamage(7); }
                else { setDamage(6); }
                if (this.upgraded) { setMagic(2); }
                else { setMagic(1); }
                tags.add(CustomTags.THUNDER);
                this.name = cardStrings.EXTENDED_DESCRIPTION[20];
                this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[12];
                this.glowColor = Color.YELLOW;
            }
            else if (AbstractDungeon.player.hasPower(Weapon8DarkPower.POWER_ID)) { // Flux
                if (this.upgraded) { setDamage(6); }
                else { setDamage(4); }
                if (this.upgraded) { setBlock(8); }
                else { setBlock(6); }
                tags.add(CustomTags.DARK);
                this.name = Flux.class.getSimpleName(); // cardStrings.EXTENDED_DESCRIPTION[21];
                this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[13];
                this.glowColor = Color.PURPLE;
            }
            else {
                tags.add(CustomTags.COPY);
                this.name = cardStrings.NAME;
                this.rawDescription = cardStrings.DESCRIPTION;
                this.glowColor = Color.TEAL;
            }
        }
        else {
            tags.add(CustomTags.COPY);
            this.name = cardStrings.NAME;
            this.rawDescription = cardStrings.DESCRIPTION;
        }
        if (this.upgraded) { upgradeName(); }
        initializeTitle();
        initializeDescription();
    }

    @Override
    public void onMoveToDiscard() { updateContents(true); }

    @Override
    public void triggerOnExhaust() { updateContents(true); }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        boolean canUse = super.canUse(p, m);
        if (!canUse) { return false; }
        if (!AbstractDungeon.player.hasPower(Weapon1SwordPower.POWER_ID) && !AbstractDungeon.player.hasPower(Weapon2LancePower.POWER_ID) && !AbstractDungeon.player.hasPower(Weapon3AxePower.POWER_ID) && !AbstractDungeon.player.hasPower(Weapon4BowPower.POWER_ID) && !AbstractDungeon.player.hasPower(Weapon5WindPower.POWER_ID) && !AbstractDungeon.player.hasPower(Weapon6FirePower.POWER_ID) && !AbstractDungeon.player.hasPower(Weapon7ThunderPower.POWER_ID) && !AbstractDungeon.player.hasPower(Weapon8DarkPower.POWER_ID)) {
            this.cantUseMessage = cardStrings.EXTENDED_DESCRIPTION[0];
            canUse = false;
        }
        return canUse;
    }

    public List<TooltipInfo> getCustomTooltips() {
        ArrayList<TooltipInfo> toolTipList = new ArrayList<>();
        if (this.upgraded) { toolTipList.add(new TooltipInfo(cardStrings.NAME, cardStrings.EXTENDED_DESCRIPTION[2])); }
        else { toolTipList.add(new TooltipInfo(cardStrings.NAME, cardStrings.EXTENDED_DESCRIPTION[1])); }

        return toolTipList;
    }

    @Override
    public void applyPowers() {
        updateContents(false);
        int realDamage = baseDamage;
        if (AbstractDungeon.player.hasPower(DeflectPower.POWER_ID) && AbstractDungeon.player.hasPower(Weapon1SwordPower.POWER_ID)) {
            baseDamage += AbstractDungeon.player.getPower(DeflectPower.POWER_ID).amount;
        }
        super.applyPowers();
        magicNumber = baseMagicNumber;
        AbstractPower pow = AbstractDungeon.player.getPower(DeflectPower.POWER_ID);
        if (pow != null) magicNumber += pow.amount;
        isMagicNumberModified = (magicNumber != baseMagicNumber);
        baseDamage = realDamage;
        this.isDamageModified = (damage != baseDamage);
    }

    @Override
    public void calculateCardDamage(AbstractMonster m) {
        updateContents(false);
        int realDamage = baseDamage;
        int realBlock = baseBlock;
        int realMagic = baseMagicNumber;
        baseDamage += Wiz.playerWeaponCalc(m, 9);
        baseBlock += Wiz.playerWeaponCalc(m, 9);
        if (AbstractDungeon.player.hasPower(DeflectPower.POWER_ID) && AbstractDungeon.player.hasPower(Weapon1SwordPower.POWER_ID)) {
            baseDamage += AbstractDungeon.player.getPower(DeflectPower.POWER_ID).amount;
        }
        if (AbstractDungeon.player.hasPower(DeflectPower.POWER_ID) && AbstractDungeon.player.hasPower(Weapon4BowPower.POWER_ID)) {
            baseMagicNumber += AbstractDungeon.player.getPower(DeflectPower.POWER_ID).amount;
        }
        super.calculateCardDamage(m);
        baseDamage = realDamage;
        baseBlock = realBlock;
        baseMagicNumber = realMagic;
        this.isDamageModified = (damage != baseDamage);
        this.isBlockModified = (block != baseBlock);
        this.isMagicNumberModified = (magicNumber != baseMagicNumber);
    }

    @Override
    public AbstractCard makeCopy() { return new Veteran(); }
}