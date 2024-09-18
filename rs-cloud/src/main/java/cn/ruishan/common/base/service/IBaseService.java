package cn.ruishan.common.base.service;

import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 基础 服务接口
 * </p>
 *
 * @author longgang.lei
 * @since 2020-03-30
 */
public interface IBaseService<T> extends IService<T> {

	/**
	 * 获取下拉列表(idStr, name)
	 * @param idStr
	 * @return
	 */
	List<Map<String, Object>> select(String idStr);

	/**
	 * 获取下拉列表(id，name)
	 * @return
	 */
	List<Map<String, Object>> select();
}
