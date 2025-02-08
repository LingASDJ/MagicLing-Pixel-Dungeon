package com.shatteredpixel.shatteredpixeldungeon;

import static com.shatteredpixel.shatteredpixeldungeon.Challenges.CS;
import static com.shatteredpixel.shatteredpixeldungeon.Dungeon.branch;
import static com.shatteredpixel.shatteredpixeldungeon.Dungeon.depth;
import static com.shatteredpixel.shatteredpixeldungeon.Dungeon.level;

import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.LockedFloor;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.DM300;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.MoloHR;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Pylon;
import com.shatteredpixel.shatteredpixeldungeon.levels.CavesBossLevel;
import com.shatteredpixel.shatteredpixeldungeon.ui.BossHealthBar;
import com.watabou.noosa.Game;
import com.watabou.noosa.audio.Music;

public class BGMPlayer {


    //解决电脑端高质量ogg的线程安全闪退问题
    public static void playBGM(String name, boolean loop) {
        if(SPDSettings.music()){
            Game.runOnRenderThread(() -> Music.INSTANCE.play(name, loop));
        }
    }

    public static void endBGM(){
        Music.INSTANCE.end();
    }

    public static void playBGMWithDepth() {
        if (Dungeon.hero != null) {
            if (Dungeon.hero.buff(LockedFloor.class) != null) {
                BGMPlayer.playBoss();
                return;
            }
        }
        int d = depth;
        int s = branch;

        if (Statistics.bossRushMode) {
            if (d == 1) {
                playBGM(Assets.TOWN, true);
            } else if (d >= 2 && d <= 7) {
                playBGM(Assets.BGM_1, true);
            } else if (d >= 8 && d <= 14) {
                playBGM(Assets.BGM_2, true);
            } else if (d >= 15 && d <= 21) {
                playBGM(Assets.BGM_4, true);
            } else if (d >= 22 && d <= 31) {
                playBGM(Assets.BGM_5, true);
            } else
                playBGM(Assets.Music.THEME, true);
        } else {
            if(s == 10){
                if(d == 26){
                    playBGM(Assets.YOGALXY, true);
                }
            } else if(s == 5){
                if(d == 17){
                    playBGM(Assets.FL_CITY, true);
                }
                if(d == 25){
                    playBGM(Assets.Music.THEME_FINALE, true);
                }
            } else if(s == 2 || s == 3 || s == 4){
                if(d == 25){
                    playBGM(Assets.Music.THEME_FINALE, true);
                }
                if(d == 10){
                    Music.INSTANCE.playTracks(
                            new String[]{Assets.Music.DIAMAND_KING_INTRO,
                                    Assets.Music.DIAMAND_KING_LOOP,Assets.Music.DIAMAND_KING_LOOP,
                                    Assets.Music.DIAMAND_KING_LOOP,Assets.Music.DIAMAND_KING_LOOP,
                                    Assets.Music.DIAMAND_KING_LOOP,Assets.Music.DIAMAND_KING_LOOP,
                                    Assets.Music.DIAMAND_KING_LOOP,Assets.Music.DIAMAND_KING_LOOP,
                                    Assets.Music.DIAMAND_KING_LOOP,Assets.Music.DIAMAND_KING_LOOP,
                                    Assets.Music.DIAMAND_KING_LOOP,Assets.Music.DIAMAND_KING_LOOP,
                                    Assets.Music.DIAMAND_KING_LOOP,Assets.Music.DIAMAND_KING_LOOP,
                                    Assets.Music.DIAMAND_KING_LOOP,Assets.Music.DIAMAND_KING_LOOP,
                                    Assets.Music.DIAMAND_KING_LOOP,Assets.Music.DIAMAND_KING_LOOP,
                                    Assets.Music.DIAMAND_KING_LOOP,Assets.Music.DIAMAND_KING_LOOP,
                            },
                            new float[]{1
                                    ,1 ,1 ,1 ,1 ,1
                                    ,1 ,1 ,1 ,1 ,1
                                    ,1 ,1 ,1 ,1 ,1
                                    ,1 ,1 ,1 ,1 ,1
                            },
                            false);
                }
                if (d == 11 || d == 12 || d == 13 || d == 14) {
                    if(level.locked){
                        playBGM(Assets.Music.CAVES_BOSS_FINALE, true);
                    } else {
                        playBGM(Assets.Music.CAVES_TENSE, true);
                    }
                }
                if(d == 16 ||d == 17 || d == 18 || d == 19 ){
                    playBGM(Assets.Music.ANCITY, true);
                }
                if(d == 5 ){
                    playBGM(Assets.BGM_1A, true);
                }
            } else if(s == 1){
                if(d == 0) {
                    playBGM(Assets.TOWN, true);
                } else if(d == 5 ){
                    playBGM(Assets.BGM_1A, true);
                } else if(d == 16 || d == 17 || d == 18 || d == 19  ){
                    playBGM(Assets.Music.ANCITY, true);
                } else if(d == 20 ) {
                    playBGM(Assets.BGM_4, true);
                }
                if (d == 11 || d == 12 || d == 13 || d == 14) {
                    if(level.locked){
                        playBGM(Assets.Music.CAVES_BOSS_FINALE, true);
                    } else {
                        playBGM(Assets.Music.CAVES_TENSE, true);
                    }
                }
            } else {
                if (d == -1) {
                    playBGM(Assets.TOWN, true);
                }else if (d == 0) {
                    playBGM(Assets.TOWN, true);
                } else if (d > 0 && d <= 5) {
                    playBGM(Assets.BGM_1, true);
                } else if (d > 5 && d <= 10) {
                    playBGM(Assets.BGM_2, true);
                } else if (d > 10 && d <= 15) {
                    playBGM(Assets.BGM_3, true);
                } else if (d > 15 && d <= 20) {
                    playBGM(Assets.BGM_4, true);
                } else if (d > 20 && d <= 25) {
                    playBGM(Assets.BGM_5, true);
                } else if (d == 26 && Statistics.Hollow_Holiday) {
                    playBGM(Assets.HOLLOW_CITY, true);
                } else if (d >= 29 && d < 31) {
                    playBGM(Assets.HOLLOW_CITY_HARD, true);
                } else if (d > 26 && d < 29) {
                    playBGM(Assets.HOLLOW_CITY, true);
                } else if (d ==-5||d ==-15) {
                    playBGM(Assets.SNOWCYON, true);
                } else
                    //default
                    playBGM(Assets.Music.THEME, true);
            }
        }


    }

    /*
    第1层 补给层 T1
    第2层 毒苹果
    第3层 补给层 T1
    第4层 粘咕
    第5层 史莱姆王

    第6层 补给层 T1
    第7层 补给层 T1
    第8层 天狗
    第9层 补给层 T2

    第10层 钻石宝箱王
    第11层 补给层T2
    第12层 DM300
    第13层 补给层T2

    第14层 DM720
    第15层 补给层T2
    第16层 冰雪魔女

    第17层 初始层

    第18层 补给层T3
    第19层 矮人国王
    第20层 补给层T3
    第21层 矮人将军

    第22层 恶魔层
    第23层 补给层T3
    第24层 浊焰魔女
    第25层 补给层T3

    第26层 Yog-Zot
    第27层 初始层
    第28层 DM-ZERO
    */

    public static void playBoss() {
        int t = depth;
        int s = branch;

        if (Statistics.bossRushMode) {
            switch (depth){
                case 2: case 4: case 6: case 13: case 7:
                    playBGM(Assets.BGM_BOSSA, true);
                    break;
                case 11:
                    playBGM(Assets.BGM_BOSSB, true);
                    break;
                case 14:
                    playBGM(Assets.BGM_YOU, true);
                    break;
                case 9:
                    playBGM(Assets.BGM_BOSSB2, true);
                    break;
                case 16: case 17:
                    try {
                        CavesBossLevel level = (CavesBossLevel) Dungeon.level;
                        for (Mob m : Dungeon.level.mobs.toArray(new Mob[0])){
                            if (m instanceof Pylon){
                                if(BossHealthBar.isAssigned() && level.pylonsRemaining >= 2){
                                    playBGM(Assets.Music.CAVES_BOSS_FINALE, true);
                                } else {
                                    playBGM(Assets.Music.CAVES_BOSS, true);
                                }
                            }
                        }
                    } catch (Exception e) {
                        for (Mob m : Dungeon.level.mobs.toArray(new Mob[0])){
                            if (m instanceof DM300){
                                if(BossHealthBar.isAssigned()){
                                    playBGM(Assets.Music.CAVES_BOSS_FINALE, true);
                                } else {
                                    playBGM(Assets.Music.CAVES_BOSS, true);
                                }
                            } else {
                                if(Statistics.bossRushMode && depth == 17){
                                    playBGM(Assets.Music.PRISON_TENSE, true);
                                }
                            }
                        }
                    }
                    break;
                case 18:
                    playBGM(Assets.BGM_BOSSC, true);
                    break;
                case 21:
                    playBGM(Assets.BGM_FRBOSS, true);
                    break;
                case 23:
                    playBGM(Assets.SKBJY, true);
                    break;
                case 24:
                    playBGM(Assets.BGM_BOSSD, true);
                    break;
                case 26:
                    playBGM(Assets.BOSSDOG, true);
                    break;
                case 27:
                    playBGM(Assets.BGM_BOSSD2, true);
                    break;
                case 29:
                    playBGM( Assets.Music.HALLS_BOSS, true );
                    break;
                case 31:
                    playBGM( Assets.BGM_BOSSE4, true );
                    break;

            }
        } else {
            if(s == 10) {
                if (t == 26 && !Statistics.TrueYogNoDied) {
                    playBGM(Assets.STARLXY, true);
                } else {
                    playBGM(Assets.YOGGOD, true);
                }
            } else if(s == 7){
                playBGM(Assets.BGM_SHOP, true);
            }
            if(s == 3 && t == 16 ||s == 3 && t == 17 || s == 3 && t == 18) {
                playBGM(Assets.SKBJY, true);
            } else if(t == 5 && s == 3){
                playBGM(Assets.BGM_BOSSA3, true);
            } else if (Dungeon.bossLevel() && t == 5 || t == 4 && s == 2) {
                playBGM(Assets.BGM_BOSSA, true);
            } else if (Dungeon.bossLevel() && t == 10) {
                if(((Statistics.boss_enhance & 0x2) != 0 || Statistics.mimicking) && !Statistics.mustTengu){
                    Music.INSTANCE.playTracks(
                            new String[]{Assets.Music.DIAMAND_KING_INTRO,
                                    Assets.Music.DIAMAND_KING_LOOP,Assets.Music.DIAMAND_KING_LOOP,
                                    Assets.Music.DIAMAND_KING_LOOP,Assets.Music.DIAMAND_KING_LOOP,
                                    Assets.Music.DIAMAND_KING_LOOP,Assets.Music.DIAMAND_KING_LOOP,
                                    Assets.Music.DIAMAND_KING_LOOP,Assets.Music.DIAMAND_KING_LOOP,
                                    Assets.Music.DIAMAND_KING_LOOP,Assets.Music.DIAMAND_KING_LOOP,
                                    Assets.Music.DIAMAND_KING_LOOP,Assets.Music.DIAMAND_KING_LOOP,
                                    Assets.Music.DIAMAND_KING_LOOP,Assets.Music.DIAMAND_KING_LOOP,
                                    Assets.Music.DIAMAND_KING_LOOP,Assets.Music.DIAMAND_KING_LOOP,
                                    Assets.Music.DIAMAND_KING_LOOP,Assets.Music.DIAMAND_KING_LOOP,
                                    Assets.Music.DIAMAND_KING_LOOP,Assets.Music.DIAMAND_KING_LOOP,
                            },
                            new float[]{1
                                    ,1 ,1 ,1 ,1 ,1
                                    ,1 ,1 ,1 ,1 ,1
                                    ,1 ,1 ,1 ,1 ,1
                                    ,1 ,1 ,1 ,1 ,1
                            },
                            false);
                } else {
                    playBGM(Assets.BGM_BOSSB2, true);
                }
            } else if (t == 14) {
                playBGM(Assets.BGM_BOSSC, true);
            } else if (Dungeon.bossLevel() && t == 15) {
                if(Statistics.RandMode){
                    playBGM(Assets.BGM_YOU, true);
                } else if((Statistics.boss_enhance & 0x4) != 0) {
                    playBGM(Assets.BGM_BOSSC, true);
                } else {
                   //最抽象的一集
                   try {
                       CavesBossLevel level = (CavesBossLevel) Dungeon.level;
                       for (Mob m : Dungeon.level.mobs.toArray(new Mob[0])){
                           if (m instanceof Pylon){
                               if(BossHealthBar.isAssigned() && level.pylonsRemaining >= (Dungeon.isChallenged(Challenges.STRONGER_BOSSES) ? 4 : 2)) {
                                   playBGM(Assets.Music.CAVES_BOSS_FINALE, true);
                               } else {
                                   playBGM(Assets.Music.CAVES_BOSS_FINALE, true);
                               }
                           }
                       }
                   } catch (Exception e) {
                       for (Mob m : Dungeon.level.mobs.toArray(new Mob[0])){
                           if (m instanceof DM300){
                               if(BossHealthBar.isAssigned()){
                                   playBGM(Assets.Music.CAVES_BOSS_FINALE, true);
                               } else {
                                   playBGM(Assets.Music.CAVES_BOSS, true);
                               }
                           } else {
                               if(m instanceof MoloHR){
                                   playBGM(Assets.Music.CAVES_BOSS_FINALE, true);
                               }
                           }
                       }
                   }
                }
            } else if (Dungeon.bossLevel() && t == 20) {
                if(s == 1) playBGM(Assets.BGM_BOSSD2, true);
                else  playBGM(Assets.BGM_BOSSD, true);
            } else if (Dungeon.bossLevel() && t == 25 && Dungeon.isChallenged(CS) && !Statistics.NoTime) {
                playBGM(Assets.BGM_BOSSE3, true);
            } else if(Dungeon.bossLevel() && t == 25 && Dungeon.isChallenged(CS) && Statistics.NoTime) {
                playBGM(Assets.BGM_BOSSE4, true);
            } else if (Dungeon.bossLevel() && t == 25){
                if(Statistics.RandMode)playBGM( Assets.BGM_BOSSE4, true );
                level.playLevelMusic();
            } else if (Dungeon.bossLevel() && t == 30) {
                playBGM(Assets.BOSSDOG, true);
            } else if (Dungeon.bossLevel() && t == -15) {
                playBGM(Assets.BGM_FRBOSS, true);
            }   else if (Dungeon.bossLevel() && t == -31) {
                playBGM(Assets.SKBJY, true);
            }
        }
    }
}
