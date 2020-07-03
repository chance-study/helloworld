package org.chance.util;

import com.google.common.primitives.Longs;
import org.apache.commons.codec.binary.Hex;
import org.springframework.util.Base64Utils;

/**
 * @author GengChao &lt;catchance@163.com&gt;
 * @date 2020-07-01 17:32:41
 */
public class LongUtils {

    public static void main(String[] args) {
        System.out.println(Hex.encodeHexString(Longs.toByteArray(201314L)));
        System.out.println(Base64Utils.encodeToString(Longs.toByteArray(201314L)));
        System.out.println(Longs.fromByteArray(Base64Utils.decodeFromUrlSafeString("BhTwVL5usBA=")));
    }

}
