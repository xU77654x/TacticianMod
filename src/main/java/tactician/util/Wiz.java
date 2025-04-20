package tactician.util;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.random.Random;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import com.megacrit.cardcrawl.vfx.cardManip.ExhaustCardEffect;
import tactician.cards.BaseCard;
import tactician.character.MyCharacter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Predicate;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.*;

public class Wiz {
    public static AbstractPlayer adp() {
        return AbstractDungeon.player;
    }

    public static void forAllCardsInList(Consumer<AbstractCard> consumer, ArrayList<AbstractCard> cardsList) {
        for (AbstractCard c : cardsList)
            consumer.accept(c);
    }

    public static int getLogicalPowerAmount(AbstractCreature ac, String powerId) {
        AbstractPower pow = ac.getPower(powerId);
        if (pow == null)
            return 0;
        return pow.amount;
    }

    public static ArrayList<AbstractCard> getAllCardsInCardGroups(boolean includeHand, boolean includeExhaust) {
        ArrayList<AbstractCard> masterCardsList = new ArrayList<>();
        masterCardsList.addAll(AbstractDungeon.player.drawPile.group);
        masterCardsList.addAll(AbstractDungeon.player.discardPile.group);
        if (includeHand)
            masterCardsList.addAll(AbstractDungeon.player.hand.group);
        if (includeExhaust)
            masterCardsList.addAll(AbstractDungeon.player.exhaustPile.group);
        return masterCardsList;
    }

    public static void forAllMonstersLiving(Consumer<AbstractMonster> consumer) {
        for (AbstractMonster m : getEnemies())
            consumer.accept(m);
    }

    public static ArrayList<AbstractMonster> getEnemies() {
        ArrayList<AbstractMonster> monsters = new ArrayList<>((AbstractDungeon.getMonsters()).monsters);
        monsters.removeIf(AbstractCreature::isDeadOrEscaped);
        return monsters;
    }

    public static ArrayList<AbstractCard> getCardsMatchingPredicate(Predicate<AbstractCard> pred) {
        return getCardsMatchingPredicate(pred, false);
    }

    public static ArrayList<AbstractCard> getCardsMatchingPredicate(Predicate<AbstractCard> pred, boolean allcards) {
        if (allcards) {
            ArrayList<AbstractCard> arrayList = new ArrayList<>();
            for (AbstractCard c : CardLibrary.getAllCards()) {
                if (pred.test(c))
                    arrayList.add(c.makeStatEquivalentCopy());
            }
            return arrayList;
        }
        ArrayList<AbstractCard> cardsList = new ArrayList<>();
        for (AbstractCard c : srcCommonCardPool.group) {
            if (pred.test(c))
                cardsList.add(c.makeStatEquivalentCopy());
        }
        for (AbstractCard c : srcUncommonCardPool.group) {
            if (pred.test(c))
                cardsList.add(c.makeStatEquivalentCopy());
        }
        for (AbstractCard c : srcRareCardPool.group) {
            if (pred.test(c))
                cardsList.add(c.makeStatEquivalentCopy());
        }
        return cardsList;
    }

    public static AbstractCard returnTrulyRandomPrediCardInCombat(Predicate<AbstractCard> pred, boolean allCards) {
        return getRandomItem(getCardsMatchingPredicate(pred, allCards));
    }

    public static AbstractCard returnTrulyRandomPrediCardInCombat(Predicate<AbstractCard> pred) {
        return returnTrulyRandomPrediCardInCombat(pred, false);
    }

    public static <T> T getRandomItem(List<T> list, Random rng) {
        return list.isEmpty() ? null : list.get(rng.random(list.size() - 1));
    }

    public static <T> T getRandomItem(List<T> list) {
        return getRandomItem(list, AbstractDungeon.cardRandomRng);
    }

    public static AbstractCard getRandomItem(CardGroup group, Random rng) {
        return getRandomItem(group.group, rng);
    }

    public static AbstractCard getRandomItem(CardGroup group) {
        return getRandomItem(group, AbstractDungeon.cardRandomRng);
    }

    private static boolean actuallyHovered(Hitbox hb) {
        return (InputHelper.mX > hb.x && InputHelper.mX < hb.x + hb.width && InputHelper.mY > hb.y && InputHelper.mY < hb.y + hb.height);
    }

    public static boolean isInCombat() {
        return (CardCrawlGame.isInARun() && AbstractDungeon.currMapNode != null && AbstractDungeon.getCurrRoom() != null && (AbstractDungeon.getCurrRoom()).phase == AbstractRoom.RoomPhase.COMBAT);
    }

    public static void topDeck(AbstractCard c, int i) {
        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(c, i, false, true));
    }

    public static void topDeck(AbstractCard c) {
        topDeck(c, 1);
    }

    public static boolean isAttacking(AbstractCreature m) {
        if (m instanceof AbstractMonster)
            return (((AbstractMonster)m).getIntentBaseDmg() >= 0);
        return false;
    }

    public static AbstractGameAction.AttackEffect getRandomSlash() {
        int x = AbstractDungeon.miscRng.random(0, 2);
        if (x == 0)
            return AbstractGameAction.AttackEffect.SLASH_DIAGONAL;
        if (x == 1)
            return AbstractGameAction.AttackEffect.SLASH_HORIZONTAL;
        return AbstractGameAction.AttackEffect.SLASH_VERTICAL;
    }

    public static AbstractMonster getRandomEnemy() {
        return AbstractDungeon.getMonsters().getRandomMonster(null, true, AbstractDungeon.cardRandomRng);
    }

    public static AbstractMonster getLowestHealthEnemy() {
        AbstractMonster weakest = null;
        for (AbstractMonster m : getEnemies()) {
            if (weakest == null) {
                weakest = m;
                continue;
            }
            if (weakest.currentHealth > m.currentHealth)
                weakest = m;
        }
        return weakest;
    }

    public static AbstractMonster getHighestHealthEnemy() {
        AbstractMonster strongest = null;
        for (AbstractMonster m : getEnemies()) {
            if (strongest == null) {
                strongest = m;
                continue;
            }
            if (strongest.currentHealth < m.currentHealth)
                strongest = m;
        }
        return strongest;
    }

    public static AbstractMonster getFrontmostEnemy() {
        AbstractMonster foe = null;
        float bestPos = 10000.0F;
        for (AbstractMonster m : getEnemies()) {
            if (m.drawX < bestPos) {
                foe = m;
                bestPos = m.drawX;
            }
        }
        return foe;
    }

    public static int pwrAmt(AbstractCreature check, String ID) {
        AbstractPower found = check.getPower(ID);
        if (found != null)
            return found.amount;
        return 0;
    }

    public static int getLogicalCardCost(AbstractCard c) {
        if (!c.freeToPlay()) {
            if (c.cost <= -2)
                return 0;
            if (c.cost == -1)
                return EnergyPanel.totalCount;
            return c.costForTurn;
        }
        return 0;
    }

    public static int countDebuffs(AbstractCreature c) {
        return (int)c.powers.stream().filter(pow -> (pow.type == AbstractPower.PowerType.DEBUFF)).count();
    }

    public static int countBuffs(AbstractCreature c) {
        return (int)c.powers.stream().filter(pow -> (pow.type == AbstractPower.PowerType.BUFF)).count();
    }

    public void moveToExhaustPile(AbstractCard c) {
        for (AbstractRelic r : AbstractDungeon.player.relics)
            r.onExhaust(c);
        for (AbstractPower p : AbstractDungeon.player.powers)
            p.onExhaust(c);
        c.triggerOnExhaust();
        // resetCardBeforeMoving(c);
        AbstractDungeon.effectList.add(new ExhaustCardEffect(c));
        AbstractDungeon.player.exhaustPile.addToTop(c);
        AbstractDungeon.player.onCardDrawOrDiscard();
    }


    // TODO: This lets cards be filtered by card tag, but fails if used off-class.
    public static AbstractCard randomCombatArt(boolean costZero) {
        ArrayList<AbstractCard> list = new ArrayList<>();
        for (AbstractCard c : srcCommonCardPool.group) {
            if (c.hasTag(CustomTags.COMBAT_ART)) {
                list.add(c);
                if (costZero) {
                    c.setCostForTurn(0);
                }
            }
        }
        for (AbstractCard d : srcUncommonCardPool.group) {
            if (d.hasTag(CustomTags.COMBAT_ART)) {
                list.add(d);
                if (costZero) {
                    d.setCostForTurn(0);
                }
            }
        }
        for (AbstractCard e : srcRareCardPool.group) {
            if (e.hasTag(CustomTags.COMBAT_ART)) {
                list.add(e);
                if (costZero) {
                    e.setCostForTurn(0);
                }
            }
        }
        return list.get(cardRandomRng.random(list.size() - 1));
    }

    /*
    // TODO: This lets off-class characters pull from Robin's cards, but cannot filter by card tag.
    public static AbstractCard randomCombatArt(boolean upg) {
        ArrayList<String> tmp = new ArrayList<>();

        for (Map.Entry<String, AbstractCard> c : CardLibrary.cards.entrySet()) {
            if ((c.getValue()).color == MyCharacter.Meta.CARD_COLOR) { tmp.add(c.getKey()); }
        }
        return CardLibrary.cards.get(tmp.get(cardRandomRng.random(0, tmp.size() - 1)));
    }
    */
}