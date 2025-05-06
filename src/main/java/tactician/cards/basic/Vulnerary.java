package tactician.cards.basic;

import basemod.cardmods.RetainMod;
import basemod.helpers.CardModifierManager;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.watcher.PressEndTurnButtonAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tactician.cards.BaseCard;
import tactician.character.MyCharacter;
import tactician.util.CardStats;

public class Vulnerary extends BaseCard {
    public static final String ID = makeID(Vulnerary.class.getSimpleName());
    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.BASIC,
            CardTarget.SELF,
            0
    );

    public Vulnerary() {
        super(ID, info);
        setMagic(3, 2);
        setCustomVar("retainCards", 1, 1);
        this.exhaust = true;
        tags.add(CardTags.HEALING);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new HealAction(p, p, this.magicNumber));

        addToBot(new SelectCardsInHandAction(customVar("retainCards"), "add Retain to for the rest of combat.", cards -> {
            for (AbstractCard c : cards) {
                addToBot(new AbstractGameAction() {
                    public void update() {
                        this.isDone = true;
                        CardModifierManager.addModifier(c, new RetainMod());
                        c.superFlash();
                    }
                });
            }
        }));

        addToBot(new PressEndTurnButtonAction());

        /*
        addToBot(new HandSelectAction(customVar("retainCards"), c -> true, list -> {
            for (AbstractCard c : list) {
                c.superFlash(Color.GREEN.cpy());
                PropertiesMod mod = new PropertiesMod();
                if (!c.selfRetain)
                    mod.addProperty(PropertiesMod.supportedProperties.RETAIN, true);
                if (!mod.bonusPropertiesForThisTurn.isEmpty())
                    CardModifierManager.addModifier(c, mod);
            }
            list.clear();
        }, null, "Retain.", false, true, true));
        addToBot(new PressEndTurnButtonAction());
        */
        // TODO: Something outside of this card is causing a crash.
    }

    @Override
    public AbstractCard makeCopy() { return new Vulnerary(); }
}