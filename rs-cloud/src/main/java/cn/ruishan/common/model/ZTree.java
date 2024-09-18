package cn.ruishan.common.model;

import cn.hutool.core.map.MapUtil;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * ZTree类型数据
 * @author longgang.lei
 * @date 2019年10月11日
 */
@Data
public class ZTree implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;
	private Integer parentId;
	private List<ZTree> children;
	private String name;
	private Boolean checked = false;
	private Boolean open = true;
	private Map<String, Object> attributes = MapUtil.newHashMap();
}
