package com.analysis.graph.common.util;

/**
 * Created by volcano on 16/10/20.
 */
@SuppressWarnings("all")
public class MaskSensitiveInfoUtils {

    private static final String MASK_CHAR = "*";

    /**
     * 功能: 敏感信息的mask
     *
     * @param oringinal  需要mask的敏感信息字符串
     * @param startIndex mask部分在原字符串中的起始位置
     * @param endIndex   mask部分在原字符串中的结束位置
     * @return 合法mask: 返回mask之后的字符串 不合法mask: 返回"MASKED_ERROR"
     * 注: startIndex、endIndex为0-based索引下标, 均包括在mask的范围内
     */
    private static String masked(String oringinal, int startIndex, int endIndex) {
        if (startIndex >= 0 && endIndex < oringinal.length() && startIndex <= endIndex) {
            String mask = "";
            String head = oringinal.substring(0, startIndex);
            String tail = oringinal.substring(endIndex + 1, oringinal.length());
            for (int i = startIndex; i <= endIndex; i++) {
                mask += MASK_CHAR;
            }
            return head + mask + tail;
        } else {
            return "MASKED_ERROR";
        }
    }

    /**
     * 功能: mask用户手机号
     *
     * @param mobile 用户手机号
     * @return mask之后的手机号
     * 例: 13754844545 -> 137****4545
     */
    public static String maskMobile(String mobile) {
        if (mobile == null) {
            return null;
        }
        return masked(mobile, mobile.length() - 8, mobile.length() - 5);
    }

    /**
     * 功能: mask用户email
     *
     * @param email 用户email
     * @return mask之后的email
     * 例: hahahaha@aliyun.com -> ha******@aliyun.com
     * 例: haha@aliyun.com -> ****@aliyun.com
     */
    public static String maskEmail(String email) {
        if (email == null) {
            return null;
        }
        int endIndex = email.indexOf("@");
        if (endIndex > 4) {
            return masked(email, 2, endIndex - 1);
        } else {
            return masked(email, 0, endIndex - 1);
        }
    }

    /**
     * 功能: mask用户银行卡号
     *
     * @param bankCardNum 用户银行卡号
     * @return mask之后的银行卡号
     * 例: 3343232332334323322 -> 3343***********3322
     */
    public static String maskBankCardNumber(String bankCardNum) {
        if (bankCardNum == null) {
            return null;
        }
        return masked(bankCardNum, 4, bankCardNum.length() - 5);
    }

    /**
     * 功能: mask用户证件号(身份证、护照)
     *
     * @param govIdNumber 用户证件号
     * @return mask之后的证件号
     * 例: 3212198309120034 -> 321*********0034
     * 例: 32128309120034 -> 321*******003
     * 例: 3212830912 -> 321***0912
     */
    public static String maskGovIdNumber(String govIdNumber) {
        if (govIdNumber == null) {
            return null;
        }
        if (govIdNumber.length() == 18) {
            return masked(govIdNumber, 3, govIdNumber.length() - 5);
        } else if (govIdNumber.length() == 15) {
            return masked(govIdNumber, 3, govIdNumber.length() - 4);
        } else if (govIdNumber.length() > 9) {
            return masked(govIdNumber, 3, govIdNumber.length() - 5);
        } else if (govIdNumber.length() > 5) {
            return masked(govIdNumber, 1, govIdNumber.length() - 3);
        } else {
            return masked(govIdNumber, 0, govIdNumber.length() - 1);
        }
    }

    /**
     * 功能: mask用户姓名
     *
     * @param name 用户姓名
     * @return mask之后的姓名
     * 例: 李世 -> 李*
     * 例: 李世民 -> 李**
     */
    public static String maskName(String name) {
        if (name == null) {
            return null;
        }
        return masked(name, 1, name.length() - 1);
    }

    /**
     * 功能: mask用户联系电话
     *
     * @param phone 用户联系电话
     * @return mask之后的联系电话
     * 例: 54844545 -> ****4545
     * 例: 01085764545 -> *******4545
     */
    public static String maskPhone(String phone) {
        if (phone == null) {
            return null;
        }
        if (phone.length() > 5) {
            return masked(phone, 0, phone.length() - 5);
        } else {
            return masked(phone, 0, phone.length() - 1);
        }
    }

    /**
     * 功能: mask用户传真号码
     *
     * @param fax 用户传真号码
     * @return mask之后的传真号码
     * 例: 54844545 -> ****4545
     * 例: 01085764545 -> *******4545
     */
    public static String maskFax(String fax) {
        if (fax == null) {
            return null;
        }
        if (fax.length() > 5) {
            return masked(fax, 0, fax.length() - 5);
        } else {
            return masked(fax, 0, fax.length() - 1);
        }
    }

    /**
     * 功能: mask用户地址
     *
     * @param address 用户地址
     * @return mask之后的地址
     * 例: 朝阳区北辰汇欣大厦B座306 -> 朝阳区*******座306
     * 例: 立水桥明天第一城 -> 立*****一城
     */
    public static String maskAddress(String address) {
        if (address == null) {
            return null;
        }
        if (address.length() > 9) {
            return masked(address, 3, address.length() - 5);
        } else if (address.length() > 5) {
            return masked(address, 1, address.length() - 3);
        } else {
            return masked(address, 0, address.length() - 1);
        }
    }

}
