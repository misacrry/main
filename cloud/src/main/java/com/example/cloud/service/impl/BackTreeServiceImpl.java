package com.example.cloud.service.impl;

import com.example.cloud.common.ApiResult;
import com.example.cloud.mapper.*;
import com.example.cloud.model.Bms;
import com.example.cloud.model.GridConnectionPoint;
import com.example.cloud.model.Pcs;
import com.example.cloud.model.Substation;
import com.example.cloud.model.vo.TreeNode;
import com.example.cloud.service.BackTreeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class BackTreeServiceImpl implements BackTreeService {

    @Resource
    private SubstationMapper substationMapper;

    @Resource
    private BmsMapper bmsMapper;

    @Resource
    private PcsMapper pcsMapper;

    @Resource
    private GridConnectionPointMapper gridConnectionPointMapper;

    @Override
    public ApiResult getTree() {

        ArrayList<Object> list = new ArrayList<>();
        int id = 0;
        List<Substation> substationList = substationMapper.selectSubList();
        // 循环储能站
        for (Substation substation : substationList) {
            // 储能站结点
            TreeNode subNode = new TreeNode();
            subNode.setId(++id);
            subNode.setPid(substation.getId());
            subNode.setType("储能站");
            subNode.setName("储能站"+substation.getId());
            subNode.setChildren(new ArrayList<>());
            subNode.setParentId(null);
            subNode.setIdx(1);
            subNode.setUrl("/substation/addSub");
            subNode.setChildren(new ArrayList<>());
            List<GridConnectionPoint> gridConnectionPointList = gridConnectionPointMapper.selectListBySubId(substation.getId());
            if (gridConnectionPointList.size() != 0) {
                subNode.setIsParent(true);
            } else {
                subNode.setIsParent(false);
            }
            list.add(subNode);
            //
            for (GridConnectionPoint gridConnectionPoint : gridConnectionPointList) {
                // 并网点结点
                TreeNode pointNode = new TreeNode();
                pointNode.setId(++id);
                pointNode.setPid(gridConnectionPoint.getId());
                pointNode.setType("并网点");
                pointNode.setName("并网点"+gridConnectionPoint.getId());
                pointNode.setChildren(new ArrayList<>());
                List<Bms> bmsList = bmsMapper.selectBmsListByPointId(gridConnectionPoint.getId());
                List<Pcs> pcsList = pcsMapper.selectPcsListByPointId(gridConnectionPoint.getId());
                if (bmsList.size() != 0 && pcsList.size() != 0) {
                    pointNode.setIsParent(true);
                } else {
                    pointNode.setIsParent(false);
                }
                subNode.getChildren().add(pointNode);

                for (Bms bms : bmsList) {
                    // bms结点
                    TreeNode bmsNode = new TreeNode();
                    bmsNode.setId(++id);
                    bmsNode.setPid(bms.getId());
                    bmsNode.setType("bms");
                    bmsNode.setName("bms"+bms.getId());
                    bmsNode.setParentId(null);
                    bmsNode.setIdx(2);
                    bmsNode.setIsParent(false);
                    pointNode.getChildren().add(bmsNode);
                }
                for (Pcs pcs : pcsList) {
                    // pcs结点
                    TreeNode pcsNode = new TreeNode();
                    pcsNode.setId(++id);
                    pcsNode.setPid(pcs.getId());
                    pcsNode.setType("pcs");
                    pcsNode.setName("pcs"+pcs.getId());
                    pcsNode.setParentId(null);
                    pcsNode.setIdx(3);
                    pcsNode.setIsParent(false);
                    pointNode.getChildren().add(pcsNode);
                }
            }
        }
        return new ApiResult(0, list);
    }
}
