package com.shatteredpixel.shatteredpixeldungeon.windows;

import static com.shatteredpixel.shatteredpixeldungeon.Dungeon.hero;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Badges;
import com.shatteredpixel.shatteredpixeldungeon.Chrome;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.ShatteredPixelDungeon;
import com.shatteredpixel.shatteredpixeldungeon.Statistics;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.AscensionChallenge;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.RandomBuff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.ReloadShop;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs.Nyz;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs.Shopkeeper;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.armor.Armor;
import com.shatteredpixel.shatteredpixeldungeon.items.props.LuckyGlove;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.scenes.PixelScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.sprites.NyzSprites;
import com.shatteredpixel.shatteredpixeldungeon.ui.Icons;
import com.shatteredpixel.shatteredpixeldungeon.ui.ItemSlot;
import com.shatteredpixel.shatteredpixeldungeon.ui.RedButton;
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

public class WndNyzShop extends Window {
    private static final int WIDTH		= 120;
    private static final int BTN_SIZE	= 16;
    private static final int BTN_GAP	= 3;
    private static final int GAP		= 3;
    public WndNyzShop(Callback callback) {
        IconTitle titlebar = new IconTitle();
        titlebar.setRect(0, 0, WIDTH, 0);
        titlebar.icon(new NyzSprites());
        titlebar.label(Messages.get(WndNyzShop.class,"nayazi"));
        add( titlebar );
        RenderedTextBlock message = PixelScene.renderTextBlock( NyzBadages(), 6 );
        message.maxWidth(WIDTH);
        message.setPos(0, titlebar.bottom() + GAP);
        add( message );

        WndNyzShop.RewardButton shop1 = new WndNyzShop.RewardButton( Nyz.shop1 );
        shop1.setRect( (WIDTH - BTN_GAP) / 6f - BTN_SIZE, message.top() + message.height() + BTN_GAP, BTN_SIZE,
                BTN_SIZE );
        add( shop1 );

        WndNyzShop.RewardButton shop2 = new WndNyzShop.RewardButton( Nyz.shop2 );
        shop2.setRect( shop1.right() + BTN_GAP, shop1.top(), BTN_SIZE, BTN_SIZE );
        add(shop2);

        WndNyzShop.RewardButton shop3 = new WndNyzShop.RewardButton( Nyz.shop3 );
        shop3.setRect( shop2.right() + BTN_GAP, shop2.top(), BTN_SIZE, BTN_SIZE );
        add(shop3);

        WndNyzShop.RewardButton shop4 = new WndNyzShop.RewardButton( Nyz.shop4 );
        shop4.setRect( shop3.right() + BTN_GAP, shop3.top(), BTN_SIZE, BTN_SIZE );
        add(shop4);

        WndNyzShop.RewardButton shop5 = new WndNyzShop.RewardButton( Nyz.shop5 );
        shop5.setRect( shop4.right() + BTN_GAP, shop4.top(), BTN_SIZE, BTN_SIZE );
        add(shop5);

        WndNyzShop.RewardButton shop6 = new WndNyzShop.RewardButton( Nyz.shop6 );
        shop6.setRect( shop5.right() + BTN_GAP, shop5.top(), BTN_SIZE, BTN_SIZE );
        add(shop6);

        WndNyzShop.RewardButton2 bomb1 = new WndNyzShop.RewardButton2( Nyz.bomb1 );
        bomb1.setRect( shop1.left() , shop1.bottom(), BTN_SIZE, BTN_SIZE );
        add(bomb1);

        WndNyzShop.RewardButton2 bomb2 = new WndNyzShop.RewardButton2( Nyz.bomb2 );
        bomb2.setRect( bomb1.right()+ BTN_GAP , bomb1.top(), BTN_SIZE, BTN_SIZE );
        add(bomb2);

        WndNyzShop.RewardButton2 bomb3 = new WndNyzShop.RewardButton2( Nyz.bomb3 );
        bomb3.setRect( bomb2.right()+ BTN_GAP , bomb2.top(), BTN_SIZE, BTN_SIZE );
        add(bomb3);

        WndNyzShop.RewardButton2 bomb4 = new WndNyzShop.RewardButton2( Nyz.bomb4 );
        bomb4.setRect( bomb3.right()+ BTN_GAP , bomb3.top(), BTN_SIZE, BTN_SIZE );
        add(bomb4);

        WndNyzShop.RewardButton2 bomb5 = new WndNyzShop.RewardButton2( Nyz.bomb5 );
        bomb5.setRect( bomb4.right()+ BTN_GAP , bomb4.top(), BTN_SIZE, BTN_SIZE );
        add(bomb5);

        WndNyzShop.RewardButton2 bomb6 = new WndNyzShop.RewardButton2( Nyz.bomb6 );
        bomb6.setRect( bomb5.right() + BTN_GAP, bomb5.top(), BTN_SIZE, BTN_SIZE );
        add(bomb6);

        StyledButton btnSite = new StyledButton(Chrome.Type.WINDOW, Messages.get(this,"sellmod")){
            @Override
            protected void onClick() {
                super.onClick();
                Game.runOnRenderThread(new Callback() {
                    @Override
                    public void call() {
                        sell();
                    }
                });
            }
        };
        btnSite.icon(Statistics.bossRushMode ? new ItemSprite(ItemSpriteSheet.BOSSRUSH_GOLD) : Icons.get(Icons.GOLD));
        btnSite.textColor(Window.CYELLOW);
        btnSite.setRect(56,-2, 65, 20 );
        add(btnSite);

        resize(WIDTH, (int) bomb6.bottom());
    }

    private String NyzBadages() {
        String message = Messages.get(WndNyzShop.class,"nayaziwelcome",Statistics.naiyaziCollected);

        if(Statistics.bossRushMode) {
            message = Messages.get(WndNyzShop.class,"rush");
        } else if (!Badges.isUnlocked(Badges.Badge.NYZ_SHOP)){
            message = Messages.get(WndNyzShop.class,"notgetbadges",Statistics.naiyaziCollected);
        }
        return message;
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
                if(Statistics.bossRushMode){
                    GameScene.show( new WndRushTradeItem( item, parentWnd ) );
                } else {
                    GameScene.show( new WndTradeItem( item, parentWnd ) );
                }
            }
        }
    };

    private void tell(String text) {
        Game.runOnRenderThread(new Callback() {
                                   @Override
                                   public void call() {
                                       GameScene.show(new WndQuest(new Nyz(), text));
                                   }
                               }
        );
    }
    Nyz nyz;
    private void selectReward( Item reward ) {

        hide();

        reward.identify();
        if (reward.doPickUp( hero )) {
            GLog.i( Messages.get(hero, "you_now_have", reward.name()) );
        }

        //Ghost.Quest.complete();
    }

    private class RewardWindow extends WndInfoItem {

        public RewardWindow( Item item ) {
            super(item);

            RedButton btnConfirm = new RedButton(Messages.get(WndNyzShop.class, "buy")){
                @Override
                protected void onClick() {
                    if(Dungeon.hero.buff(RandomBuff.class) == null) {
                       tell(Messages.get(WndNyzShop.class,"maxbuy"));
                        for (Buff buff : hero.buffs()) {
                            if (buff instanceof RandomBuff) {
                                buff.detach();
                            }
                        }
                    } else if(Dungeon.gold >= 720|| Dungeon.rushgold >= 5) {

                        if(Statistics.bossRushMode){
                            Dungeon.rushgold -= 5;
                        } else {
                            if(hero.belongings.getItem(LuckyGlove.class)!=null) {
                                Dungeon.gold -= (720 * Random.Int(2) + hero.lvl / 5 + 100) * (Dungeon.hero.buff(AscensionChallenge.class) != null ? 0.7 : 1);
                            }else{
                                Messages.get(LuckyGlove.class,"lucky");
                            }
                            Statistics.naiyaziCollected += 1;
                            Badges.nyzvalidateGoldCollected();
                        }


                        WndNyzShop.this.selectReward( item );
                        Buff.prolong( hero, ReloadShop.class, 1f);

                        WndNyzShop.RewardWindow.this.hide();

                    } else {
                        tell(Messages.get(WndNyzShop.class,"nomoney"));
                        WndNyzShop.RewardWindow.this.hide();
                    }
                }
            };
            btnConfirm.setRect(0, height+2, width/2f-1, 16);
            add(btnConfirm);

            RedButton btnCancel = new RedButton(Messages.get(WndNyzShop.class, "cancel")){
                @Override
                protected void onClick() {
                    hide();
                }
            };
            btnCancel.setRect(btnConfirm.right()+2, height+2, btnConfirm.width(), 16);
            add(btnCancel);

            resize(width, (int)btnCancel.bottom());
        }
    }

    private class RewardWindow2 extends WndInfoItem {

        public RewardWindow2( Item item ) {
            super(item);

            RedButton btnConfirm = new RedButton(Messages.get(WndNyzShop.class, "buy")){
                @Override
                protected void onClick() {
                    if(Dungeon.hero.buff(RandomBuff.class) == null){
                        tell(Messages.get(WndNyzShop.class,"maxbuy"));
                        for (Buff buff : hero.buffs()) {
                            if (buff instanceof RandomBuff) {
                                buff.detach();
                            }
                        }
                    } else if(Dungeon.gold >= 270 || Dungeon.rushgold >= 5) {

                        if(Statistics.bossRushMode){
                            Dungeon.rushgold -= 5;
                        } else {
                            if(hero.belongings.getItem(LuckyGlove.class)!=null && Math.random()<0.9f) {
                                Dungeon.gold -= 270 * Random.Int(3) + 50 * (Dungeon.hero.buff(AscensionChallenge.class) != null ? 0.7 : 1);
                            }else{
                                GLog.n(Messages.get(LuckyGlove.class,"lucky"));
                            }
                            Badges.nyzvalidateGoldCollected();
                            Statistics.naiyaziCollected += 1;
                        }

                        Buff.prolong( hero, ReloadShop.class, 1f);
                        WndNyzShop.this.selectReward( item );

                        WndNyzShop.RewardWindow2.this.hide();
                    } else {
                        tell(Messages.get(WndNyzShop.class,"nomoney"));
                    }
                }
            };
            btnConfirm.setRect(0, height+2, width/2f-1, 16);
            add(btnConfirm);

            RedButton btnCancel = new RedButton(Messages.get(WndNyzShop.class, "cancel")){
                @Override
                protected void onClick() {
                    hide();
                }
            };
            btnCancel.setRect(btnConfirm.right()+2, height+2, btnConfirm.width(), 16);
            add(btnCancel);

            resize(width, (int)btnCancel.bottom());
        }
    }

    public class RewardButton2 extends Component {

        protected NinePatch bg;
        protected ItemSlot slot;

        public RewardButton2(Item item) {
            bg = Chrome.get(Chrome.Type.RED_BUTTON);
            add(bg);

            slot = new ItemSlot(item) {
                @Override
                protected void onPointerDown() {
                    bg.brightness(1.2f);
                    Sample.INSTANCE.play(Assets.Sounds.CLICK);
                }

                @Override
                protected void onPointerUp() {
                    bg.resetColor();
                }

                @Override
                protected void onClick() {
                    ShatteredPixelDungeon.scene().addToFront(new WndNyzShop.RewardWindow2(item));
                }
            };
            add(slot);
        }

        @Override
        protected void layout() {
            super.layout();

            bg.x = x;
            bg.y = y;
            bg.size(width, height);

            slot.setRect(x + 2, y + 2, width - 4, height - 4);
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
                    ShatteredPixelDungeon.scene().addToFront(new WndNyzShop.RewardWindow(item));
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
        }
    }
}
