package tactician.cards;

import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.UIStrings;
import tactician.TacticianMod;
import tactician.character.TacticianRobin;
import tactician.util.CardStats;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import static tactician.util.GeneralUtils.removePrefix;
import static tactician.util.TextureLoader.getCardTextureString;

public abstract class Tactician8DarkCard extends TacticianCard {
	private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(TacticianMod.makeID("Label8Dark"));

	public Tactician8DarkCard(String ID, CardStats info) {
		this(ID, info, getCardTextureString(removePrefix(ID), info.cardType));
	}

	public Tactician8DarkCard(String ID, CardStats info, String cardImage) {
		this(ID, info.baseCost, info.cardType, info.cardTarget, info.cardRarity, info.cardColor, cardImage);
	}

	public Tactician8DarkCard(String ID, int baseCost, CardType cardType, CardTarget cardTarget, CardRarity cardRarity, CardColor cardColor, String cardImage) {
		super(ID, baseCost, cardType, cardTarget, cardRarity, TacticianRobin.Meta.CARD_COLOR, cardImage);
		CardModifierManager.addModifier(this, new RarityTipModifier());
	}

	public List<String> getCardDescriptors() { return Collections.singletonList(uiStrings.TEXT[0]); }
	public void trickyInitializeTitle() { initializeTitle(); }

	public static class RarityTipModifier extends AbstractCardModifier {
		public List<TooltipInfo> additionalTooltips(AbstractCard card) {
			ArrayList<TooltipInfo> tips = new ArrayList<>();
			tips.add(new TooltipInfo(Tactician8DarkCard.uiStrings.TEXT[0], Tactician8DarkCard.uiStrings.TEXT[1]));
			return tips;
		}
		public boolean isInherent(AbstractCard card) { return true; }
		public AbstractCardModifier makeCopy() { return new RarityTipModifier(); }
	}
}