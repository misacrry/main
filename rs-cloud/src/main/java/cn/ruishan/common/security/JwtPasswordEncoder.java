package cn.ruishan.common.security;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import org.springframework.security.crypto.password.PasswordEncoder;

public class JwtPasswordEncoder implements PasswordEncoder {

    @Override
    public String encode(CharSequence charSequence) {

        return SecureUtil.md5(StrUtil.format("www.ruishan.cn{}", SecureUtil.md5(StrUtil.format("www.ruishan.cn{}", charSequence))));
    }

    @Override
    public boolean matches(CharSequence charSequence, String encodedPassword) {

        if(StrUtil.equals(encode(charSequence), encodedPassword)) {
            return true;
        }

        return false;
    }
}
