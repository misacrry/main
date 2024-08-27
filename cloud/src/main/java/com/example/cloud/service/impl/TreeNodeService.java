package com.example.cloud.service.impl;

import com.example.cloud.mapper.BmsMapper;
import com.example.cloud.mapper.GridConnectionPointMapper;
import com.example.cloud.mapper.PcsMapper;
import com.example.cloud.model.Bms;
import com.example.cloud.model.GridConnectionPoint;
import com.example.cloud.model.Pcs;
import com.example.cloud.model.Substation;
import com.example.cloud.model.vo.TreeNode;

import java.util.ArrayList;
import java.util.List;

public class TreeNodeService {

    private int id = 0;
    private List<TreeNode> list = new ArrayList<>();
    private  GridConnectionPointMapper gridConnectionPointMapper;
    private  BmsMapper bmsMapper;
    private  PcsMapper pcsMapper;

    public TreeNodeService(GridConnectionPointMapper gridConnectionPointMapper, BmsMapper bmsMapper, PcsMapper pcsMapper) {
        this.gridConnectionPointMapper = gridConnectionPointMapper;
        this.bmsMapper = bmsMapper;
        this.pcsMapper = pcsMapper;
    }

    public List<TreeNode> generateTreeNodes(List<Substation> substationList) {
        for (Substation substation : substationList) {
            TreeNode subNode = createSubstationNode(substation);
            list.add(subNode);
            processGridConnectionPoints(substation, subNode);
        }
        return list;
    }

    private TreeNode createSubstationNode(Substation substation) {
        TreeNode subNode = new TreeNode();
        subNode.setId(++id);
        subNode.setPid(substation.getId());
        subNode.setType("储能站");
        subNode.setName("储能站" + substation.getId());
        subNode.setChildren(new ArrayList<>());
        subNode.setParentId(null);
        subNode.setIdx(1);
        subNode.setUrl("/substation/addSub");
        subNode.setIsParent(gridConnectionPointMapper.selectListBySubId(substation.getId()).size() > 0);
        return subNode;
    }

    private void processGridConnectionPoints(Substation substation, TreeNode subNode) {
        List<GridConnectionPoint> gridConnectionPointList = gridConnectionPointMapper.selectListBySubId(substation.getId());
        for (GridConnectionPoint gridConnectionPoint : gridConnectionPointList) {
            TreeNode pointNode = createGridConnectionPointNode(gridConnectionPoint);
            subNode.getChildren().add(pointNode);
            processBmsAndPcs(gridConnectionPoint, pointNode);
        }
    }

    private TreeNode createGridConnectionPointNode(GridConnectionPoint gridConnectionPoint) {
        TreeNode pointNode = new TreeNode();
        pointNode.setId(++id);
        pointNode.setPid(gridConnectionPoint.getId());
        pointNode.setType("并网点");
        pointNode.setName("并网点" + gridConnectionPoint.getId());
        pointNode.setChildren(new ArrayList<>());
        pointNode.setIsParent(hasBmsOrPcs(gridConnectionPoint));
        return pointNode;
    }

    private boolean hasBmsOrPcs(GridConnectionPoint gridConnectionPoint) {
        List<Bms> bmsList = bmsMapper.selectBmsListByPointId(gridConnectionPoint.getId());
        List<Pcs> pcsList = pcsMapper.selectPcsListByPointId(gridConnectionPoint.getId());
        return !bmsList.isEmpty() && !pcsList.isEmpty();
    }

    private void processBmsAndPcs(GridConnectionPoint gridConnectionPoint, TreeNode pointNode) {
        List<Bms> bmsList = bmsMapper.selectBmsListByPointId(gridConnectionPoint.getId());
        List<Pcs> pcsList = pcsMapper.selectPcsListByPointId(gridConnectionPoint.getId());

        for (Bms bms : bmsList) {
            TreeNode bmsNode = createBmsNode(bms);
            pointNode.getChildren().add(bmsNode);
        }

        for (Pcs pcs : pcsList) {
            TreeNode pcsNode = createPcsNode(pcs);
            pointNode.getChildren().add(pcsNode);
        }
    }

    private TreeNode createBmsNode(Bms bms) {
        TreeNode bmsNode = new TreeNode();
        bmsNode.setId(++id);
        bmsNode.setPid(bms.getId());
        bmsNode.setType("bms");
        bmsNode.setName("bms" + bms.getId());
        bmsNode.setParentId(null);
        bmsNode.setIdx(2);
        bmsNode.setIsParent(false);
        return bmsNode;
    }

    private TreeNode createPcsNode(Pcs pcs) {
        TreeNode pcsNode = new TreeNode();
        pcsNode.setId(++id);
        pcsNode.setPid(pcs.getId());
        pcsNode.setType("pcs");
        pcsNode.setName("pcs" + pcs.getId());
        pcsNode.setParentId(null);
        pcsNode.setIdx(3);
        pcsNode.setIsParent(false);
        return pcsNode;
    }

}
