package com.shatteredpixel.shatteredpixeldungeon.custom;

import static com.shatteredpixel.shatteredpixeldungeon.Dungeon.hero;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Challenges;
import com.shatteredpixel.shatteredpixeldungeon.SPDSettings;
import com.shatteredpixel.shatteredpixeldungeon.items.IceCyanBlueSquareCoin;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.scenes.TitleScene;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundlable;
import com.watabou.utils.Bundle;
import com.watabou.utils.Reflection;

import net.iharder.Base64;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Gift implements Bundlable {

    public static final String KEY_ARRAY	= "TUxQRFpFUk8sSEVMTE9aRVJPRUlHSFQ=";
    private static final String[] Gift_DATA	= {
            "TUxQRFpFUk8sMTczNTU3NTE0MixmYWxzZTs==",//正常兑换码
            "U1A0LVJFQURZLDE3MzU2NjA3OTksZmFsc2U=",

            //灯火
            "QmFkTGFudGVyRmlyZS1Hbyw0MDcwOTUxNzc1LGZhbHNl",

            //小年夜
            "TUxQRF9YaWFvbmlhblllaSwxNzM4MTU5MjAwLGZhbHNl",

            //春节兑换码
            "TUxQRC1TbmFrZVllYXJzT2xkLDE3MzkzNzI0MDAsZmFsc2U=",
            "TUxQRC00WWVhcnNPbGQsMTczOTM3MjQwMCxmYWxzZQ==",

            //龙泪
            "TUxQRC1EcmFnb25XYXRlciwxNzM5MzcyNDAwLGZhbHNl",

            //元宵节
            "TUxQRC1MYW50ZXJuRmVzdGl2YWw0WWVhcnNCaXJ0aGRheSwxNzQwMzEzODAwLGZhbHNl",

            //圣诞2024
            "TUxQRF9DaHJpc3RtYXMtMjAyNCwxNzM1NTc1MTQyLGZhbHNl",
            "SEVMTE9aRVJPRUlHSFQsMTczMzQxNTE0MixmYWxzZTs=",
            "VEVTVCwxNzM1NTc1MTQyLHRydWU7"//已使用兑换码
    };

    private static final HashMap<String, LinkedHashMap<String, Integer>> GIFT_ITEM ;
    static {
        HashMap<String, LinkedHashMap<String, Integer>> tempMap = new HashMap<>();

        LinkedHashMap<String, Integer> code1 = new LinkedHashMap<>();
        code1.put( "com.shatteredpixel.shatteredpixeldungeon.items.IceCyanBlueSquareCoin", 500 );

        LinkedHashMap<String, Integer> code2 = new LinkedHashMap<>();
        code2.put( "com.shatteredpixel.shatteredpixeldungeon.items.IceCyanBlueSquareCoin", 600 );
        code2.put( "com.shatteredpixel.shatteredpixeldungeon.items.food.Switch", 3 ) ;
        code2.put( "com.shatteredpixel.shatteredpixeldungeon.items.food.Cake", 2 );
        code2.put( "com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.legend.GoldLongGun", 1 );

        LinkedHashMap<String, Integer> code3 = new LinkedHashMap<>();
        code3.put( "com.shatteredpixel.shatteredpixeldungeon.items.IceCyanBlueSquareCoin", 400 );
        code3.put( "com.shatteredpixel.shatteredpixeldungeon.items.Gold", 400 ) ;

        LinkedHashMap<String, Integer> code4 = new LinkedHashMap<>();
        code4.put( "com.shatteredpixel.shatteredpixeldungeon.items.IceCyanBlueSquareCoin", 300 );
        code4.put( "com.shatteredpixel.shatteredpixeldungeon.items.quest.LanFireGo", 1 );

        LinkedHashMap<String, Integer> code5 = new LinkedHashMap<>();
        code5.put( "com.shatteredpixel.shatteredpixeldungeon.items.IceCyanBlueSquareCoin",2025);
        code5.put( "com.shatteredpixel.shatteredpixeldungeon.items.food.MeatPie", 2);
        code5.put( "com.shatteredpixel.shatteredpixeldungeon.items.potions.elixirs.ElixirOfNukeCole", 1);
        code5.put( "com.shatteredpixel.shatteredpixeldungeon.items.potions.exotic.PotionOfDragonKingBreath", 1);

        //MLPD-4YearsOld
        LinkedHashMap<String, Integer> code6 = new LinkedHashMap<>();
        code6.put( "com.shatteredpixel.shatteredpixeldungeon.items.IceCyanBlueSquareCoin",700);
        code6.put( "com.shatteredpixel.shatteredpixeldungeon.items.weapon.missiles.RedBlock", 3);
        code6.put( "com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.Seekingspear", 1);
        code6.put( "com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfTransmutation", 1);

        //MLPD-4YearsOld
        LinkedHashMap<String, Integer> code7 = new LinkedHashMap<>();
        code7.put( "com.shatteredpixel.shatteredpixeldungeon.items.IceCyanBlueSquareCoin",400);
        code7.put( "com.shatteredpixel.shatteredpixeldungeon.items.food.MeatPie", 1);
        code7.put( "com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.legend.DiedCrossBow", 1);
        code7.put( "com.shatteredpixel.shatteredpixeldungeon.items.potions.PotionOfExperience", 1);

        //MLPD-DragonWater
        LinkedHashMap<String, Integer> code8 = new LinkedHashMap<>();
        code8.put( "com.shatteredpixel.shatteredpixeldungeon.items.IceCyanBlueSquareCoin",500);
        code8.put( "com.shatteredpixel.shatteredpixeldungeon.items.food.MeatPie", 1);
        code8.put( "com.shatteredpixel.shatteredpixeldungeon.items.quest.DragonWater", 1);
        code8.put( "com.shatteredpixel.shatteredpixeldungeon.items.potions.PotionOfExperience", 1);

        //MLPD_Yuanxio
        LinkedHashMap<String, Integer> code9 = new LinkedHashMap<>();
        code9.put( "com.shatteredpixel.shatteredpixeldungeon.items.IceCyanBlueSquareCoin", 400);
        code9.put( "com.shatteredpixel.shatteredpixeldungeon.items.Torch", 114514);

        tempMap.put( "TUxQRFpFUk8=", code1 );
        tempMap.put( "TUxQRF9DaHJpc3RtYXMtMjAyNA==", code2 );
        tempMap.put( "U1A0LVJFQURZ",code3);
        tempMap.put( "QmFkTGFudGVyRmlyZS1Hbw==", code4);
        tempMap.put( "TUxQRF9YaWFvbmlhblllaQ==",code5);

        tempMap.put("TUxQRC00WWVhcnNPbGQ=",code6);
        tempMap.put("TUxQRC1TbmFrZVllYXJzT2xk",code7);

        tempMap.put("TUxQRC1EcmFnb25XYXRlcg==",code8);


        GIFT_ITEM = new HashMap<>( Collections.unmodifiableMap( tempMap ) );
    }

    private static final LinkedHashMap<String,Integer> giftBuffArray = new LinkedHashMap<>();
    private static final String giftBuffArrayKey = "gift_buff_array_key";
    private static final String giftBuffArrayValue = "gift_buff_array_value";

    private static int GIFT_Code = 0;
    private static int GIFT_Expiration_Date = 1;
    private static int Gift_Used = 2;

    @Override
    public void storeInBundle(Bundle bundle) {
        String[] giftArrayKey = new String[giftBuffArray.size()];
        int[] giftArrayValue = new int[giftBuffArray.size()];

        int i = 0;
        for ( Map.Entry<String, Integer> entry : giftBuffArray.entrySet() ) {
            giftArrayKey[i] = entry.getKey();
            giftArrayValue[i] = entry.getValue();
            i++;
        }

        bundle.put( giftBuffArrayKey, giftArrayKey );
        bundle.put( giftBuffArrayValue, giftArrayValue );
    }

    @Override
    public void restoreFromBundle(Bundle bundle) {
        ArrayList<String> giftBuffArrayKeys = new ArrayList<>();
        ArrayList<Integer> giftBuffArrayValues = new ArrayList<>();

        for ( String key : bundle.getStringArray( giftBuffArrayKey ) ){
            giftBuffArrayKeys.add( key );
        }
        for ( int value : bundle.getIntArray( giftBuffArrayValue ) ){
            giftBuffArrayValues.add( value );
        }
        for ( int i = 0; i < giftBuffArrayKeys.size(); i++ ){
            giftBuffArray.put( giftBuffArrayKeys.get( i ), giftBuffArrayValues.get( i ) );
        }
    }

    //将兑换码导入本地数据中
    public static void GiftTime() {
        try {
            int length = Gift_DATA.length;
            String decodedString;
            byte[] decoded;
            List<String> saveData = new ArrayList<>();
            long currentTime = System.currentTimeMillis() / 1000;
            long expirationDate;

            for(int i = 0; i < length; i++) {
                decoded = Base64.decode( Gift_DATA[i] );
                decodedString = new String( decoded) ;

                expirationDate = Long.parseLong( decodedString.split(",")[1] );
                if( currentTime > expirationDate )
                    continue;

                if( SPDSettings.queryGiftExist( decodedString.split(",")[0] ) )
                    continue;

                saveData.add( decodedString );
            }

            if( !saveData.isEmpty() ){
                String[] result = new String[saveData.size()];
                SPDSettings.saveGift( saveData.toArray( result ) );
            }

            SPDSettings.deleteOutdatedGift();
        } catch (Exception ignored) {
        }
    }

    //玩家使用兑换码
    public static int ActivateGift(String key) {
        if( TitleScene.NTP_NOINTER || TitleScene.NTP_ERROR || TitleScene.NTP_NOINTER_VEFY || TitleScene.NTP_ERROR_VEFY )
            return 0;

        if(Objects.equals(key, "")){
            return 4;
        }

        if( !SPDSettings.queryGiftExist( key ) )
            return 0;

        long currentTime = System.currentTimeMillis() / 1000;
        long expirationDate = Long.parseLong( SPDSettings.queryGiftPart( key, GIFT_Expiration_Date ) );
        if( currentTime > expirationDate )
            return 2;

        boolean keyUsed = Boolean.parseBoolean( SPDSettings.queryGiftPart( key, Gift_Used ) );
        if( keyUsed )
            return 3;

        String keyCheck = Base64.encodeBytes( key.getBytes() );
        if( GIFT_ITEM.containsKey( keyCheck ) ){
            LinkedHashMap<String, Integer> items = GIFT_ITEM.get( keyCheck );
            for ( Map.Entry<String, Integer> entry : items.entrySet() ) {
                if( entry.getKey().contains("GiftBuff") )
                        GiveBuff( entry.getKey(), entry.getValue() );
                else
                    GiveItem( entry.getKey(), entry.getValue() );
            }
        }

        SPDSettings.modifyGiftPart( key, Gift_Used, String.valueOf(true) );
        return 1;
    }

    //存储Buff
    private static void GiveBuff(String buffKey, int buffValue){
        GLog.i( Messages.get( Gift.class, "buff", Messages.get( Gift.class, buffKey ) ) );
        giftBuffArray.put( buffKey, buffValue);
    }

    //给予物品
    private static void GiveItem(String itemName,int quantity){
        boolean collect = false;
        Item item = null;
        try {
            item = (Item) Reflection.newInstance(Class.forName(itemName));
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        if(Challenges.isItemBlocked(item)) return;
        if (item != null) {
            //若为咕币
            if(item instanceof IceCyanBlueSquareCoin) {
                GLog.i( Messages.get( Gift.class, "you_now_have", item.name(), quantity ));
                new IceCyanBlueSquareCoin(quantity).doPickUp(hero);
                return;
            }

            if(item.stackable)
                collect = item.quantity(quantity).collect();
            else
                collect = item.collect();
            item.identify();

            if(collect){
                GLog.i( Messages.get( Gift.class, "you_now_have", item.name(), quantity ));
                Sample.INSTANCE.play( Assets.Sounds.ITEM );
                GameScene.pickUp( item, hero.pos );
            }else{
                item.doDrop(hero);
            }
        }
    }

    //Buff是否存在
    public static boolean IsGiftBuffExist( String key ){
        return giftBuffArray.containsKey( key );
    }

    //Buff状态，-1为不存在或其他
    public static int GetGiftBuffStatus( String key ){
        if( !IsGiftBuffExist( key ) )
            return -1;

        return giftBuffArray.get( key );
    }

    public static String base64() {
        String decodedString = "";
        // 初始为空字符串
        try {
            // 解码Base64字符串
            byte[] decoded = Base64.decode(KEY_ARRAY);
            // 这里的KEY_ARRAY应该是一个Base64编码的字符串
            decodedString = new String(decoded);
            // 解码后的字节数组转换为字符串
        } catch (Exception ignored) {
        }
        return decodedString;  // 返回解码后的字符串
    }

}
