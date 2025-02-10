/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2022 Evan Debenham
 *
 * Mund Pixel Dungeon
 * Copyright (C) 2018-2023 Thliey Pen
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */

package com.shatteredpixel.shatteredpixeldungeon.custom.utils.plot;

import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs.zero.Question;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.windows.WndDialog;
import com.shatteredpixel.shatteredpixeldungeon.windows.WndOptions;
import com.watabou.noosa.Game;
import com.watabou.utils.Callback;
import com.watabou.utils.Random;

public class QuestionPlot extends Plot {


    private final static int maxprocess = 4;

    {
        process = 1;
    }

    protected String getPlotName() {
        return SEWER_NAME;
    }

    @Override
    public void reachProcess(WndDialog wndDialog) {
        diagulewindow = wndDialog;
        while (this.process < needed_process) {
            this.process();
        }
    }

    @Override
    public void process() {
        if (diagulewindow != null) {
            switch (process) {
                default:
                case 1:
                    process_to_1();
                    break;
                case 2:
                    process_to_9();
                    break;
                case 3:
                    process_to_10();
                    break;
                case 4:
                    process_to_11();
                    break;
                case 5:
                    process_to_15();
                    break;
                case 6:
                    process_to_16();
                    break;
                case 7:
                    process_to_17();
                    break;
                case 8:
                    process_to_19();
                    break;
                case 9:
                    process_to_20();
                    break;
            }
            diagulewindow.update();
        }
    }

    @Override
    public void initial(WndDialog wndDialog) {
        diagulewindow = wndDialog;
        process = 2;
        process_to_1();
    }

    @Override
    public boolean end() {
        return process > maxprocess;
    }

    @Override
    public void skip() {
    }

    Question question = new Question();

    private void process_to_1() {
        diagulewindow.changeText(Messages.get(Question.class, "message1"));
        Game.runOnRenderThread(new Callback() {
            @Override
            public void call() {

                GameScene.show(new WndOptions(
                                       question.sprite(),
                                       Messages.titleCase(question.name()),
                                       Messages.get(Question.class, "title"),
                                       Messages.get(Question.class, "option_nothing"),
                                       Messages.get(Question.class, "option_chat"),
                                       Messages.get(Question.class, "option_enquire"),
                                       Messages.get(Question.class, "option_request"),
                                       Messages.get(Question.class, "option_duel"),
                                       Messages.get(Question.class, "option_game")
                               ) {
                                   @Override
                                   protected void onSelect(int index) {
                                       if (index==1){
                                           int i = Random.Int(2);
                                           if (i==0){
                                               process_to_2();
                                           } else {
                                               if (true){
                                                   process_to_3();
                                               } else {
                                                   process_to_4();
                                               }
                                           }
                                       } else if (index==2){
                                           process_to_5();
                                       } else if (index==3){
                                           process++;
                                       } else if (index==4){
                                           if (true){
                                               process_to_14();
                                           } else if (true){
                                               process = 5;
                                           } else if (true){
                                               process_to_18();
                                           }
                                       } else if (index==5){
                                           process = 8;
                                       }
                                   }
                               }
                );
            }});
    }

    private void process_to_2() {
        diagulewindow.changeText(Messages.get(Question.class, "message2"));
    }

    private void process_to_3() {
        diagulewindow.changeText(Messages.get(Question.class, "message3"));
    }

    private void process_to_4() {
        diagulewindow.changeText(Messages.get(Question.class, "message4"));
    }

    private void process_to_5() {
        Game.runOnRenderThread(new Callback() {
            @Override
            public void call(){
                GameScene.show(new WndOptions(
                        question.sprite(),
                        Messages.titleCase(question.name()),
                        Messages.get(Question.class, "title_1"),
                        Messages.get(Question.class, "ling"),
                        Messages.get(Question.class, "text1"),
                        Messages.get(Question.class, "text2")
                ){
                    @Override
                    protected void onSelect(int index) {
                        if (index==0){
                            process_to_6();
                        } else if (index==1){
                            process_to_7();
                        } else if (index==2){
                            process_to_8();
                        }
                    }
                });
            }
        });
    }

    private void process_to_6() {
        diagulewindow.changeText(Messages.get(Question.class, "message6"));
    }

    private void process_to_7() {
        diagulewindow.changeText(Messages.get(Question.class, "message7"));
    }

    private void process_to_8() {
        diagulewindow.changeText(Messages.get(Question.class, "message8"));
    }

    private void process_to_9() {
        diagulewindow.changeText(Messages.get(Question.class, "message9"));
        process++;
    }

    private void process_to_10() {
        diagulewindow.changeText(Messages.get(Question.class, "message10"));
        process++;
    }

    private void process_to_11() {
        Game.runOnRenderThread(new Callback() {
            @Override
            public void call(){
                GameScene.show(new WndOptions(
                        question.sprite(),
                        Messages.titleCase(question.name()),
                        Messages.get(Question.class, "title_2"),
                        Messages.get(Question.class, "true"),
                        Messages.get(Question.class, "false")
                        ){
                    @Override
                    protected void onSelect(int index) {
                        process = 1;
                        if (index==0){
                            process_to_12();
                        } else if (index==1){
                            process_to_13();
                        }
                    }
                });
            }
        });
    }

    private void process_to_12() {
        diagulewindow.changeText(Messages.get(Question.class, "message12"));
    }

    private void process_to_13() {
        diagulewindow.changeText(Messages.get(Question.class, "message13"));
    }

    private void process_to_14() {
        diagulewindow.changeText(Messages.get(Question.class, "message14"));
    }

    private void process_to_15() {
        diagulewindow.changeText(Messages.get(Question.class, "message15"));
        process++;
    }

    private void process_to_16() {
        diagulewindow.changeText(Messages.get(Question.class, "message16"));
        process++;
    }

    private void process_to_17() {
        diagulewindow.changeText(Messages.get(Question.class, "message17"));
        process = 1;
    }

    private void process_to_18() {
        diagulewindow.changeText(Messages.get(Question.class, "message18"));
    }

    private void process_to_19() {
        diagulewindow.changeText(Messages.get(Question.class, "message19"));
        process++;
    }

    private void process_to_20() {
        //先不码这里了直接注释掉
    }
}