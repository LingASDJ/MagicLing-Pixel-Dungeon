package com.shatteredpixel.shatteredpixeldungeon.windows;

import static com.shatteredpixel.shatteredpixeldungeon.Dungeon.hero;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Chrome;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.SPDSettings;
import com.shatteredpixel.shatteredpixeldungeon.ShatteredPixelDungeon;
import com.shatteredpixel.shatteredpixeldungeon.Statistics;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.AutoRandomBuff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.ReloadShop;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs.Shopkeeper;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs.zero.WaloKe;
import com.shatteredpixel.shatteredpixeldungeon.items.Generator;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.armor.Armor;
import com.shatteredpixel.shatteredpixeldungeon.items.dlcitem.BossRushBloodGold;
import com.shatteredpixel.shatteredpixeldungeon.items.dlcitem.DLCItem;
import com.shatteredpixel.shatteredpixeldungeon.items.dlcitem.RushMobScrollOfRandom;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.scenes.PixelScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ShopkKingSprite;
import com.shatteredpixel.shatteredpixeldungeon.ui.ItemSlot;
import com.shatteredpixel.shatteredpixeldungeon.ui.RenderedTextBlock;
import com.shatteredpixel.shatteredpixeldungeon.ui.StyledButton;
import com.shatteredpixel.shatteredpixeldungeon.ui.Window;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.noosa.Game;
import com.watabou.noosa.NinePatch;
import com.watabou.noosa.audio.Sample;
import com.watabou.noosa.ui.Component;
import com.watabou.utils.Callback;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class WndWaloKe extends Window {
    private static final int WIDTH		= 120;
    private static final int BTN_SIZE	= 32;
    private static final int BTN_GAP	= 6;
    private static final int GAP		= 6;


    public WndWaloKe() {
        WaloKe.shop1 = new BossRushBloodGold ();
        WaloKe.shop2 = new RushMobScrollOfRandom();

        IconTitle titlebar = new IconTitle();
        titlebar.setRect(0, 0, WIDTH, 0);
        titlebar.icon(new ShopkKingSprite());
        titlebar.label(Messages.get(WaloKe.class,"name"));
        add( titlebar );
        RenderedTextBlock message =
                PixelScene.renderTextBlock( (Messages.get(WaloKe.class,"descx",hero.name())), 6 );
        message.maxWidth(WIDTH);
        message.setPos(0, titlebar.bottom() + GAP);
        add( message );

        WndWaloKe.RewardButton shop1 = new WndWaloKe.RewardButton( WaloKe.shop1 );
        shop1.setRect( (WIDTH - BTN_GAP) / 3f - BTN_SIZE, message.top() + message.height() + BTN_GAP, BTN_SIZE,
                BTN_SIZE );
        add( shop1 );

        WndWaloKe.RewardButton shop2 = new WndWaloKe.RewardButton( WaloKe.shop2 );
        shop2.setRect( shop1.right() + BTN_GAP, shop1.top(), BTN_SIZE, BTN_SIZE );
        add(shop2);

        WndWaloKe.RewardButton shop3 = new WndWaloKe.RewardButton( WaloKe.shop3 );
        shop3.setRect( shop2.right() + BTN_GAP, shop2.top(), BTN_SIZE, BTN_SIZE );
        add(shop3);

//        RewardButton bomb1 = new RewardButton( WaloKe.shop4 );
//        bomb1.setRect( shop1.left() , shop1.bottom(), BTN_SIZE, BTN_SIZE );
//        add(bomb1);
//
//        RewardButton bomb2 = new RewardButton( WaloKe.shop5);
//        bomb2.setRect( bomb1.right()+ BTN_GAP , bomb1.top(), BTN_SIZE, BTN_SIZE );
//        add(bomb2);
//
//        RewardButton bomb3 = new RewardButton( WaloKe.shop6 );
//        bomb3.setRect( bomb2.right()+ BTN_GAP , bomb2.top(), BTN_SIZE, BTN_SIZE );
//        add(bomb3);

        resize(WIDTH, (int) shop3.bottom());
    }

    public static WndBag sell() {
        return GameScene.selectItem( itemSelector );
    }

    public static boolean canSell(Item item){
        if (item.value() <= 0)                                              return false;
        if (item.unique && !item.stackable)                                 return false;
        if (item instanceof Armor && ((Armor) item).checkSeal() != null)    return false;
        if (item.isEquipped(Dungeon.hero) && item.cursed)                   return false;
        return true;
    }

    private static WndBag.ItemSelector itemSelector = new WndBag.ItemSelector() {
        @Override
        public String textPrompt() {
            return Messages.get(Shopkeeper.class, "sell");
        }

        @Override
        public boolean itemSelectable(Item item) {
            return Shopkeeper.canSell(item);
        }

        @Override
        public void onSelect( Item item ) {
            if (item != null) {
                WndBag parentWnd = sell();
                GameScene.show( new WndTradeItem( item, parentWnd ) );
            }
        }
    };

    private void tell(String text) {
        Game.runOnRenderThread(new Callback() {
                                   @Override
                                   public void call() {
                                       GameScene.show(new WndQuest(new WaloKe(), text));
                                   }
                               }
        );
    }
    private void selectReward( Item reward ) {

        hide();

        reward.identify();
        if (reward.doPickUp( hero )) {
            GLog.i( Messages.get(hero, "you_now_have", reward.name()) );
        }



        //Ghost.Quest.complete();
    }

    public void itemUnlock(Item item){
        if( (item instanceof BossRushBloodGold) && !SPDSettings.isItemUnlock( item.name() ) ){
            Generator.setProbs( item, Generator.Category.WEP_T5, 0f );
            SPDSettings.unlockItem( item.getClass().getSimpleName() );
        }

        if( (item instanceof RushMobScrollOfRandom) && !SPDSettings.isItemUnlock( item.name() ) ){
            Generator.setProbs( item, Generator.Category.WEP_T5, 0f );
            SPDSettings.unlockItem( item.getClass().getSimpleName() );
        }

    }

    private class RewardWindow extends WndInfoItem {

        public RewardWindow( Item item ) {
            super(item);

            boolean locked = SPDSettings.isItemUnlock(item.getClass().getSimpleName());

            StyledButton btnConfirm = new StyledButton(SPDSettings.isItemUnlock(item.getClass().getSimpleName())? Chrome.Type.SCROLL : Chrome.Type.RED_BUTTON,Messages.get(WndWaloKe.class, (locked) ? "unlocked":"buy",item.iceCoinValue())){
                @Override
                protected void onClick() {
                    boolean onlyMode = false;
                    ArrayList<DLCItem> dlcItems = hero.belongings.getAllItems(DLCItem.class);
                    for (DLCItem w : dlcItems.toArray(new DLCItem[0])){
                        if (w != null) {
                            onlyMode = true;
                            break;
                        }
                    }
                    if(onlyMode){
                        tell(Messages.get(WndWaloKe.class,"onlymode"));
                        WndWaloKe.RewardWindow.this.hide();
                    } else if(SPDSettings.iceCoin() >= item.iceCoinValue() && !SPDSettings.isItemUnlock(item.getClass().getSimpleName())) {
                        SPDSettings.iceDownCoin(item.iceCoinValue());
                        WndWaloKe.this.selectReward( item );
                        itemUnlock(item);
                        if(Dungeon.hero.buff(AutoRandomBuff.class) != null) {
                            AutoRandomBuff.level -= Random.Int(4);
                            if(AutoRandomBuff.level <= 0) {
                                for (Buff buff : hero.buffs()) {
                                    if (buff instanceof AutoRandomBuff) {
                                        buff.detach();
                                    }
                                }
                            }
                        }
                        item.cursed = true;
                        Buff.prolong( hero, ReloadShop.class, 1f);
                        WndWaloKe.RewardWindow.this.hide();
                    } else if(SPDSettings.isItemUnlock(item.getClass().getSimpleName())) {
                        WndWaloKe.this.selectReward( item );
                        item.cursed = true;
                        Buff.prolong( hero, ReloadShop.class, 1f);
                        WndWaloKe.RewardWindow.this.hide();
                    } else {
                        tell(Messages.get(WndWaloKe.class,"nomoney"));
                        WndWaloKe.RewardWindow.this.hide();
                    }

                }
            };
            btnConfirm.setRect(0, height+2, width, 16);
            add(btnConfirm);

            resize(width, (int)btnConfirm.bottom());
        }
    }

    public class RewardButton extends Component {

        protected NinePatch bg;
        protected ItemSlot slot;

        public RewardButton( Item item ){
            bg = Chrome.get( Chrome.Type.RED_BUTTON);
            add( bg );

            slot = new ItemSlot( item ){
                @Override
                protected void onPointerDown() {
                    bg.brightness( 1.2f );
                    Sample.INSTANCE.play( Assets.Sounds.CLICK );
                }
                @Override
                protected void onPointerUp() {
                    bg.resetColor();
                }
                @Override
                protected void onClick() {
                    ShatteredPixelDungeon.scene().addToFront(new WndWaloKe.RewardWindow(item));
                }
            };
            add(slot);
        }

        @Override
        protected void layout() {
            super.layout();



            bg.x = x;
            bg.y = y;
            bg.size( width, height );

            slot.setRect( x + 2, y + 2, width - 4, height - 4 );

            if(Statistics.bossRushMode){
                slot.active = false;
                slot.alpha(0.6f);
            }

        }
    }
}


