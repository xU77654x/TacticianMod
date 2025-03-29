package tactician.actions;

import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsCenteredAction;
import com.megacrit.cardcrawl.cards.AbstractCard;

import java.util.ArrayList;

public class EasyModalChoiceAction extends SelectCardsCenteredAction {

    public EasyModalChoiceAction(ArrayList<AbstractCard> list, int amount, String textforSelect) {
        super(list, amount, textforSelect, (cards) -> {
            for (AbstractCard q : cards) {
                q.onChoseThisOption();
            }
        });
    }

    public EasyModalChoiceAction(ArrayList<AbstractCard> list, int amount) {
        this(list, amount, "Select a weapon to equip.");
    }

    public EasyModalChoiceAction(ArrayList<AbstractCard> list) {
        this(list, 1, "Select a weapon to equip.");
    }
}
