package cn.ruishan.common.base.mapper;


import org.springframework.stereotype.Component;

import cn.ruishan.common.base.entity.TreeEntity;

import java.util.List;

/**
 * 树型结构的Mapper支持类实现
 * @author longgang.lei
 * @date 2019年9月10日
 * @param <T>
 */
@Component
public interface TreeMapper<T extends TreeEntity<T>> extends CrudMapper<T> {

}