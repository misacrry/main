package cn.ruishan.common.base.controller;

import cn.ruishan.common.security.JwtPasswordEncoder;
import cn.ruishan.common.security.LoginUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>
 * 基础 前端控制器
 * </p>
 *
 * @author longgang.lei
 * @since 2020-03-30
 */
@Slf4j
public class BaseController extends LoginUtil {

    @Autowired
    protected JwtPasswordEncoder passwordEncoder;
}
