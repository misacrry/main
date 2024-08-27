package com.example.cloud.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TreeNode {
    private Integer id;
    private Integer pid;
    private String name;
    private String type;
    private String url;
    private Integer parentId;
    private Integer idx;
    private boolean isParent;
    private List<TreeNode> children;

    public boolean getIsParent() {
        return isParent;
    }

    public void setIsParent(boolean parent) {
        isParent = parent;
    }
}

