package com.example.cloud.mapper;

import com.example.cloud.model.vo.TreeNode;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BackTreeMapper {

    public TreeNode getTree();

}
