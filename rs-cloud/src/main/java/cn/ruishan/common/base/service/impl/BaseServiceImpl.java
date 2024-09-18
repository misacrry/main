package cn.ruishan.common.base.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.ruishan.common.base.service.IBaseService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 基础 服务实现类
 * </p>
 *
 * @author longgang.lei
 * @since 2020-03-30
 */
public class BaseServiceImpl<M extends BaseMapper<T>, T> extends ServiceImpl<M, T> implements IBaseService<T> {

	@Override
	public List<Map<String, Object>> select(String idStr) {
		List<Map<String, Object>> maps = baseMapper.selectMaps(new QueryWrapper<T>()
				.select(idStr, "name")
				.orderByAsc("create_time"));

		if(CollUtil.isNotEmpty(maps)) {
			for (Map<String, Object> map : maps) {
				map.put("value", map.remove(idStr));
			}
		}

		return maps;
	}

	@Override
	public List<Map<String, Object>> select() {
		return select("id");
	}
}
