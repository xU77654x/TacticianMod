package tactician.monsterweapons.standard.act2city;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class GremlinLeader { /*
	@SpirePatch(clz = GremlinLeader.class, method = "getMove")
	protected void getMove(int num) {
		if (numAliveGremlins() == 0) {
			if (num < 75) {
				if (!lastMove((byte)2)) {
					setMove(RALLY_NAME, (byte)2, AbstractMonster.Intent.UNKNOWN);
				} else {
					setMove((byte)4, AbstractMonster.Intent.ATTACK, this.STAB_DMG, this.STAB_AMT, true);
				}
			} else if (!lastMove((byte)4)) {
				setMove((byte)4, AbstractMonster.Intent.ATTACK, this.STAB_DMG, this.STAB_AMT, true);
			} else {
				setMove(RALLY_NAME, (byte)2, AbstractMonster.Intent.UNKNOWN);
			}
		} else if (numAliveGremlins() < 2) {
			if (num < 50) {
				if (!lastMove((byte)2)) {
					setMove(RALLY_NAME, (byte)2, AbstractMonster.Intent.UNKNOWN);
				} else {
					getMove(AbstractDungeon.aiRng.random(50, 99));
				}
			} else if (num < 80) {
				if (!lastMove((byte)3)) {
					setMove((byte)3, AbstractMonster.Intent.DEFEND_BUFF);
				} else {
					setMove((byte)4, AbstractMonster.Intent.ATTACK, this.STAB_DMG, this.STAB_AMT, true);
				}
			} else if (!lastMove((byte)4)) {
				setMove((byte)4, AbstractMonster.Intent.ATTACK, this.STAB_DMG, this.STAB_AMT, true);
			} else {
				getMove(AbstractDungeon.aiRng.random(0, 80));
			}
		} else if (numAliveGremlins() > 1) {
			if (num < 66) {
				if (!lastMove((byte)3)) {
					setMove((byte)3, AbstractMonster.Intent.DEFEND_BUFF);
				} else {
					setMove((byte)4, AbstractMonster.Intent.ATTACK, this.STAB_DMG, this.STAB_AMT, true);
				}
			} else if (!lastMove((byte)4)) {
				setMove((byte)4, AbstractMonster.Intent.ATTACK, this.STAB_DMG, this.STAB_AMT, true);
			} else {
				setMove((byte)3, AbstractMonster.Intent.DEFEND_BUFF);
			}
		}
	} */
}