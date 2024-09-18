package cn.ruishan.common.model;

import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * EasyUITree-Tree
 * @author longgang.lei
 * @date 2019年10月15日
 */
@Data
public class LayuiTree implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 节点ID
	 */
	private String id;
	/**
	 * 节点文本
	 */
	private String title;

	/**
	 * 节点全名
	 */
	private String name;

	/**
	 * 子节点
	 */
	private List<LayuiTree> children;

	/**
	 * 节点值
	 */
	private Map<String, Object> value;

	public LayuiTree() {
		super();
	}

	public LayuiTree(String title) {
		super();
		this.title = title;
	}
}