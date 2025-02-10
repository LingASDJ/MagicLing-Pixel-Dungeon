package com.shatteredpixel.shatteredpixeldungeon.windows;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.Statistics;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs.Shopkeeper;
import com.shatteredpixel.shatteredpixeldungeon.items.EquipableItem;
import com.shatteredpixel.shatteredpixeldungeon.items.Heap;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.KingGold;
import com.shatteredpixel.shatteredpixeldungeon.items.LiquidMetal;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.ui.RedButton;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;

public class WndRushTradeItem extends WndInfoItem {

    private static final float GAP		= 2;
    private static final int BTN_HEIGHT	= 18;

    private WndBag owner;

    private boolean selling = false;

    //selling
    public WndRushTradeItem( final Item item, WndBag owner ) {

        super(item);

        selling = true;

        this.owner = owner;

        float pos = height;
        Shopkeeper shop = null;
        for (Char ch : Actor.chars()){
            if (ch instanceof Shopkeeper){
                shop = (Shopkeeper) ch;
                break;
            }
        }
        final Shopkeeper finalShop = shop;
        if (item.quantity() == 1) {

            RedButton btnSell = new RedButton( Messages.get(this, "sell", 0) ) {
                @Override
                protected void onClick() {
                    sell( item,finalShop );
                    hide();
                }
            };
            //tnSell.setHeight( BTN_HEIGHT );
            btnSell.setRect( 0, pos + GAP, width, BTN_HEIGHT );
            btnSell.icon(new ItemSprite(ItemSpriteSheet.BOSSRUSH_GOLD));
            add( btnSell );

            pos = btnSell.bottom();

        } else {

            int priceAll= item.quantity/5;



            RedButton btnSellAll = new RedButton( Messages.get(this, "sell_all", priceAll ) ) {
                @Override
                protected void onClick() {
                    sell( item,finalShop );
                    hide();
                }
            };
            btnSellAll.setRect( 0, pos + GAP, width, BTN_HEIGHT );
            btnSellAll.icon(new ItemSprite(ItemSpriteSheet.BOSSRUSH_GOLD));


            add( btnSellAll );

            pos = btnSellAll.bottom();

        }

        resize( width, (int)pos );
    }

    //buying
    public WndRushTradeItem( final Heap heap ) {

        super(heap);

        selling = false;

        Item item = heap.peek();

        float pos = height;

        final int price = Shopkeeper.sellRushPrice( item );

        RedButton btnBuy = new RedButton( Messages.get(this, "buy", price) ) {
            @Override
            protected void onClick() {
                hide();
                buy( heap );
            }
        };
        btnBuy.setRect( 0, pos + GAP, width, BTN_HEIGHT );
        btnBuy.icon(new ItemSprite(ItemSpriteSheet.BOSSRUSH_GOLD));
        btnBuy.enable( price <= Dungeon.rushgold );
        add( btnBuy );

        pos = btnBuy.bottom();

        resize(width, (int) pos);
    }

    @Override
    public void hide() {

        super.hide();

        if (owner != null) {
            owner.hide();
        }
        if (selling) Shopkeeper.sell();
    }

    public static void sell( Item item, Shopkeeper shop ) {

        Hero hero = Dungeon.hero;

        if (item.isEquipped( hero ) && !((EquipableItem)item).doUnequip( hero, false )) {
            return;
        }

        if(item instanceof LiquidMetal){
            if(item.quantity()>=50 && !Statistics.LiquidMatalOnlyTen){
                item.detachAll( hero.belongings.backpack );
            }
        } else {
            item.detachAll( hero.belongings.backpack );
        }


        //selling items in the sell interface doesn't spend time
        hero.spend(-hero.cooldown());

        //特判液金 至多10个
        if(item instanceof LiquidMetal){
            if(item.quantity >= 50 && !Statistics.LiquidMatalOnlyTen){
                Statistics.LiquidMatalOnlyTen = true;
                new KingGold(10 ).doPickUp( hero );
            } else if(!Statistics.LiquidMatalOnlyTen){
                GLog.n(Messages.get(WndRushTradeItem.class,"max_nomore"));
            } else {
                GLog.n(Messages.get(WndRushTradeItem.class,"max_ten"));
            }
        } else {
            new KingGold(item.quantity/5 ).doPickUp( hero );
        }



        if (shop != null){
            shop.buybackItems.add(item);
            while (shop.buybackItems.size() > Shopkeeper.MAX_BUYBACK_HISTORY){
                shop.buybackItems.remove(0);
            }
        }
    }

    public static void sellOne( Item item ) {
        sellOne( item, null );
    }

    public static void sellOne( Item item, Shopkeeper shop ) {

        if (item.quantity() <= 1) {
            sell( item, shop );
        } else {

            Hero hero = Dungeon.hero;

            item = item.detach( hero.belongings.backpack );

            //selling items in the sell interface doesn't spend time
            hero.spend(-hero.cooldown());

            new KingGold( item.RushValue() ).doPickUp( hero );

            if (shop != null){
                shop.buybackItems.add(item);
                while (shop.buybackItems.size() > Shopkeeper.MAX_BUYBACK_HISTORY){
                    shop.buybackItems.remove(0);
                }
            }
        }
    }

    private void buy( Heap heap ) {

        Item item = heap.pickUp();
        if (item == null) return;

        int price = Shopkeeper.sellRushPrice( item );
        Dungeon.rushgold -= price;

        if (!item.doPickUp( Dungeon.hero )) {
            Dungeon.level.drop( item, heap.pos ).sprite.drop();
        }
    }
}
