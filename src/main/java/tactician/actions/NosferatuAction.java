package tactician.actions;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.evacipated.cardcrawl.mod.stslib.actions.tempHp.AddTemporaryHPAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.combat.DarkOrbActivateEffect;

public class NosferatuAction extends AbstractGameAction {
    private DamageInfo info;

    public NosferatuAction(AbstractCreature target, DamageInfo info) {
        this.info = info;
        setValues(target, info);
        this.actionType = AbstractGameAction.ActionType.DAMAGE;
        this.startDuration = Settings.ACTION_DUR_FAST;
        this.duration = this.startDuration;
    }

    public void update() {
        if (shouldCancelAction()) {
            this.isDone = true;
            return;
        }
        tickDuration();
        if (this.isDone) {
            // AbstractDungeon.effectList.add(new FlashAtkImgEffect(this.target.hb.cX, this.target.hb.cY, AbstractGameAction.AttackEffect.BLUNT_HEAVY, false));
            addToBot(new VFXAction(new DarkOrbActivateEffect(this.target.drawX, this.target.drawY + 133), 0.05F));
            this.target.damage(this.info);
            if (this.target.lastDamageTaken > 0) { addToTop(new AddTemporaryHPAction(this.source, this.source, this.target.lastDamageTaken)); }
            if ((AbstractDungeon.getCurrRoom()).monsters.areMonstersBasicallyDead()) { AbstractDungeon.actionManager.clearPostCombatActions(); }
            else { addToTop(new WaitAction(0.1F)); }
        }
    }
}