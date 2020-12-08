package com.dzh.util;

import cn.hutool.crypto.SecureUtil;
import com.dzh.Shiro.AccountProfile;
import org.apache.shiro.SecurityUtils;

public class ShiroUtil {
    public static AccountProfile getprofile(){
        return (AccountProfile) SecurityUtils.getSubject().getPrincipal();
    }
}
