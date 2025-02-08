package com.shatteredpixel.shatteredpixeldungeon.custom.utils.plot;

import static com.shatteredpixel.shatteredpixeldungeon.Dungeon.hero;
import static com.shatteredpixel.shatteredpixeldungeon.levels.RegularLevel.holiday;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.Statistics;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs.zero.Gudazi;
import com.shatteredpixel.shatteredpixeldungeon.items.Generator;
import com.shatteredpixel.shatteredpixeldungeon.items.Gold;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.food.ChargrilledMeat;
import com.shatteredpixel.shatteredpixeldungeon.levels.RegularLevel;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.windows.WndDialog;
import com.watabou.noosa.Image;

public class GudaziPlot extends Plot {

    private final static int maxprocess = 10;

    {
        process = 1 ;
    }

    protected String getPlotName() {
        return SEWER_NAME;
    }

    @Override
    public void reachProcess(WndDialog wndDialog) {
        diagulewindow = wndDialog;

        while(this.process < needed_process )
        {
            this.process();
        }
    }

    @Override
    public void process() {
        if(diagulewindow!=null) {
            switch (process) {
                default:
                case 1:
                    process_to_1();//Mostly process to 1 is made directly when creating,it might not be used,just in case
                    break;
                case 2:
                    process_to_2();
                    break;
                case 3:
                    process_to_3();
                    break;
                case 4:
                    process_to_4();
                    break;
                case 5:
                    process_to_5();
                    break;
                case 6:
                    process_to_6();
                    break;
                case 7:
                    process_to_7();
                    break;
                case 8:
                    process_to_8();
                    break;
                case 9:
                    process_to_9();
                    break;
                case 10:
                    process_to_10();
                    break;
            }
            diagulewindow.update();
            process ++;
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

    private void process_to_1() {
        diagulewindow.hideAll();
        Dungeon.hero.interrupt();
        if(holiday == RegularLevel.Holiday.XMAS){
            diagulewindow.setMainAvatar(new Image(Assets.Splashes.XMS_GDZ));
        } else {
            diagulewindow.setMainAvatar(new Image(Assets.Splashes.GDZ));
        }
        diagulewindow.setLeftName(Messages.get(Gudazi.class, "name"));
        diagulewindow.changeText(Messages.get(Gudazi.class, "message1"));
    }

    private void process_to_2()
    {
        diagulewindow.changeText(Messages.get(Gudazi.class,"message2"));
    }

    private void process_to_3()
    {
        if(Statistics.zeroItemLevel >=4 && Dungeon.depth == 0 && Statistics.gdzDialogLevel==0) {
            Dungeon.level.drop(new Gold(1), hero.pos);
        } else {
            Dungeon.level.drop( new ChargrilledMeat(), hero.pos );
        }
        Statistics.zeroItemLevel++;
        Statistics.gdzDialogLevel++;
        diagulewindow.changeText(Messages.get(Gudazi.class,"message3"));
    }

    private void process_to_4()
    {
        diagulewindow.changeText(Messages.get(Gudazi.class,"message4"));

    }

    private void process_to_5()
    {
        diagulewindow.changeText(Messages.get(Gudazi.class,"message5"));
    }

    private void process_to_6()
    {
        diagulewindow.changeText(Messages.get(Gudazi.class,"message6"));
    }

    private void process_to_7()
    {
        diagulewindow.changeText(Messages.get(Gudazi.class,"message7"));
    }

    private void process_to_8()
    {
        diagulewindow.changeText(Messages.get(Gudazi.class,"message8"));
    }

    private void process_to_9()
    {
        diagulewindow.changeText(Messages.get(Gudazi.class,"message9"));
    }

    private void process_to_10()
    {
        diagulewindow.changeText(Messages.get(Gudazi.class,"message10"));
    }

    public static class GudaziSecondPlot extends Plot {


        private final static int maxprocess = 3;

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
                        process_to_2();
                        break;
                    case 3:
                        process_to_3();
                        break;
                }
                diagulewindow.update();
                process++;
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

        private void process_to_1() {
            diagulewindow.hideAll();
            Dungeon.hero.interrupt();
            diagulewindow.setMainAvatar(new Image(Assets.Splashes.COON));
            diagulewindow.setLeftName(Messages.get(Gudazi.class, "name"));
            diagulewindow.changeText(Messages.get(Gudazi.class, "message2"));

            if(Statistics.zeroItemLevel >=4 && Dungeon.depth == 0) {
                Dungeon.level.drop(new Gold(1), hero.pos);
            } else {
                Item item = ( Generator.randomUsingDefaults( Generator.Category.SCROLL ));
                item.level(0);
                item.identify();
                Dungeon.level.drop( item , hero.pos );
            }

            Statistics.zeroItemLevel++;
        }

        private void process_to_2() {
            diagulewindow.hideAll();
            Dungeon.hero.interrupt();
            diagulewindow.setMainAvatar(new Image(Assets.Splashes.COON));
            diagulewindow.setLeftName(Messages.get(Gudazi.class, "name"));
            diagulewindow.changeText(Messages.get(Gudazi.class, "message3"));
        }

        private void process_to_3() {
            diagulewindow.hideAll();
            Dungeon.hero.interrupt();
            diagulewindow.setMainAvatar(new Image(Assets.Splashes.COON));
            diagulewindow.setLeftName(Messages.get(Gudazi.class, "name"));
            diagulewindow.changeText(Messages.get(Gudazi.class, "message4"));
        }
    }


    public static class GudaziRDPlot extends Plot {


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
                        process_to_2();
                        break;
                    case 3:
                        process_to_3();
                        break;
                    case 4:
                        process_to_4();
                        break;
                }
                diagulewindow.update();
                process++;
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

        private void process_to_1() {
            diagulewindow.hideAll();
            Dungeon.hero.interrupt();
            if(holiday == RegularLevel.Holiday.XMAS){
                diagulewindow.setMainAvatar(new Image(Assets.Splashes.XMS_GDZ));
            } else {
                diagulewindow.setMainAvatar(new Image(Assets.Splashes.GDZ));
            }
            diagulewindow.setLeftName(Messages.get(Gudazi.class, "name"));
            diagulewindow.changeText(Messages.get(Gudazi.class, "message11",hero.name()));
        }

        private void process_to_2() {
            diagulewindow.changeText(Messages.get(Gudazi.class, "message12"));
        }

        private void process_to_3() {
            diagulewindow.changeText(Messages.get(Gudazi.class, "message13"));
        }

        private void process_to_4() {
            diagulewindow.changeText(Messages.get(Gudazi.class, "message14",hero.name()));
            Statistics.gdzHelpDungeon++;
        }

    }

}


