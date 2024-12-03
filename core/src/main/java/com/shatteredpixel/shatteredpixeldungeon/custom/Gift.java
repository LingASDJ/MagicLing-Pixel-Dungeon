package com.shatteredpixel.shatteredpixeldungeon.custom;

import net.iharder.Base64;

public class Gift {

    public static final String KEY_ARRAY	= "TUxQRFpFUk8sSEVMTE9aRVJPRUlHSFQ=";

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