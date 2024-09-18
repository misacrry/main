package cn.ruishan.common.handler;

import cn.hutool.core.date.DateUtil;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;

import cn.ruishan.common.security.LoginUtil;

@Component
public class CustomMetaObjectHandler implements MetaObjectHandler {

	@Override
	public void insertFill(MetaObject metaObject) {

		Integer loginUserId = LoginUtil.getLoginUserId();
		Long now = DateUtil.current();
		this.setFieldValByName("createId", loginUserId, metaObject);
		this.setFieldValByName("createTime", now, metaObject);
	}

	@Override
	public void updateFill(MetaObject metaObject) {
		if(LoginUtil.getLoginUser()!=null) {
			Integer loginUserId = LoginUtil.getLoginUserId();
			Long now = DateUtil.current();
			this.setFieldValByName("updateId", loginUserId, metaObject);
			this.setFieldValByName("updateTime", now, metaObject);
			this.setFieldValByName("deleteId", loginUserId, metaObject);
			this.setFieldValByName("deleteTime", now, metaObject);
		}
	}
}
