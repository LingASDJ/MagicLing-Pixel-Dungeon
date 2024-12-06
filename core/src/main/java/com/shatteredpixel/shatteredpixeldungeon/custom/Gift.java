package com.shatteredpixel.shatteredpixeldungeon.custom;

import com.shatteredpixel.shatteredpixeldungeon.SPDSettings;

import net.iharder.Base64;

import java.util.ArrayList;
import java.util.List;

public class Gift {

    public static final String KEY_ARRAY	= "TUxQRFpFUk8sSEVMTE9aRVJPRUlHSFQ=";
    public static final String[] Gift_ARRAY	= {
            "TUxQRFpFUk8sMTczNTU3NTE0MixmYWxzZTs==",//正常兑换码
            "SEVMTE9aRVJPRUlHSFQsMTczMzQxNTE0MixmYWxzZTs=",//已过期兑换码
            "VEVTVCwxNzM1NTc1MTQyLHRydWU7"//已使用兑换码
    };

    private static int GIFT_Code = 0;
    private static int GIFT_Expiration_Date = 1;
    private static int Gift_Used = 2;

    //将兑换码导入本地数据中
    public static void GiftTime() {
        try {
            int length = Gift_ARRAY.length;
            String decodedString = "";
            byte[] decoded;
            List<String> saveData = new ArrayList<>();

            for(int i = 0; i < length; i++) {
                decoded = Base64.decode( Gift_ARRAY[i] );
                decodedString = new String( decoded) ;

                if( SPDSettings.queryGiftExist( decodedString.split(",")[0] ) )
                    continue;

                saveData.add( decodedString );
            }

            if( !saveData.isEmpty() ){
                String[] result = new String[saveData.size()];
                SPDSettings.saveGift( saveData.toArray( result ) );
            }
        } catch (Exception ignored) {
        }
    }

    //玩家使用兑换码
    public static int ActivateGift( String key ) {
        String keyExist = SPDSettings.queryGiftPart( key, GIFT_Code );
        if( keyExist == null || keyExist.isEmpty() || !key.contains(keyExist) )
            return 0;

        long currentTime = System.currentTimeMillis() / 1000;
        long expirationDate = Long.parseLong( SPDSettings.queryGiftPart( key, GIFT_Expiration_Date ) );
        if( currentTime > expirationDate )
            return 2;

        boolean keyUsed = Boolean.parseBoolean( SPDSettings.queryGiftPart( key, Gift_Used ) );
        if( keyUsed )
            return 3;

        SPDSettings.modifyGiftPart( key, Gift_Used, String.valueOf(true) );
        return 1;
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