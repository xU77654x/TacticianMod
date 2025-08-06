package tactician.cards.rare;

import basemod.helpers.BaseModCardTags;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tactician.cards.TacticianCard;
import tactician.character.TacticianRobin;
import tactician.powers.GrandmasterFormPower;
import tactician.util.CardStats;

public class GrandmasterForm extends TacticianCard {
    public static final String ID = makeID(GrandmasterForm.class.getSimpleName());
    private static final CardStats info = new CardStats(
            TacticianRobin.Meta.CARD_COLOR,
            CardType.POWER,
            CardRarity.RARE,
            CardTarget.SELF,
            3
    );

    public GrandmasterForm() {
        super(ID, info);
        setMagic(1, 0);
        setInnate(false, true);
        tags.add(BaseModCardTags.FORM);
        // FlavorText.AbstractCardFlavorFields.boxColor.set(this, Color.PURPLE.cpy());
        // FlavorText.AbstractCardFlavorFields.textColor.set(this, Color.WHITE.cpy());
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new SFXAction("tactician:GrandmasterForm"));
        addToBot(new TalkAction(true, cardStrings.EXTENDED_DESCRIPTION[0], 1.0F, 2.0F));
        addToBot(new ApplyPowerAction(p, p, new GrandmasterFormPower(this.magicNumber), this.magicNumber));
    }

    @Override
    public AbstractCard makeCopy() { return new GrandmasterForm(); }
}