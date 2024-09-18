package cn.ruishan.common.model;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class FileTree implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;

    private Integer parentId;

    private String name;

    private List<FileTree> children;
}
