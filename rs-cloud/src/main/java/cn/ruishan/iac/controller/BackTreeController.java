package cn.ruishan.iac.controller;

import cn.ruishan.common.web.ApiResult;
import cn.ruishan.iac.entity.Bms;
import cn.ruishan.iac.entity.GridConnectionPoint;
import cn.ruishan.iac.entity.Pcs;
import cn.ruishan.iac.entity.Substation;
import cn.ruishan.iac.service.*;
import cn.ruishan.iac.vo.TreeNode;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@RestController
public class BackTreeController {

    @Resource
    private SubstationService substationService;

    @Resource
    private GridConnectionPointService gridConnectionPointService;

    @Resource
    private BmsService bmsService;

    @Resource
    private PcsService pcsService;

    @GetMapping("/getBackTree")
    public ApiResult getTree() {

        ArrayList<Object> list = new ArrayList<>();
        int id = 0;
        List<Substation> substationList = substationService.selectUserSubstation();
        // 循环储能站
        for (Substation substation : substationList) {
            // 储能站结点
            TreeNode subNode = new TreeNode();
            subNode.setId(++id);
            subNode.setPid(substation.getId());
            subNode.setType("储能站");
            subNode.setName("储能站" + substation.getId());
            subNode.setChildren(new ArrayList<>());
            subNode.setParentId(null);
            subNode.setIdx(1);
            subNode.setUrl("/substation/addSub");
            subNode.setChildren(new ArrayList<>());
            LambdaQueryWrapper<GridConnectionPoint> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(GridConnectionPoint::getSubstationId, substation.getId());
            List<GridConnectionPoint> gridConnectionPointList = gridConnectionPointService.list(queryWrapper);
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
                pointNode.setName("并网点" + gridConnectionPoint.getId());
                pointNode.setChildren(new ArrayList<>());
                LambdaQueryWrapper<Bms> bmsLambdaQueryWrapper = new LambdaQueryWrapper<>();
                LambdaQueryWrapper<Pcs> pcsLambdaQueryWrapper = new LambdaQueryWrapper<>();
                bmsLambdaQueryWrapper.eq(Bms::getPointId, gridConnectionPoint.getId());
                pcsLambdaQueryWrapper.eq(Pcs::getPointId, gridConnectionPoint.getId());
                List<Bms> bmsList = bmsService.list(bmsLambdaQueryWrapper);
                List<Pcs> pcsList = pcsService.list(pcsLambdaQueryWrapper);
                if (bmsList.size() != 0 || pcsList.size() != 0) {
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
                    bmsNode.setName("bms" + bms.getId());
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
                    pcsNode.setName("pcs" + pcs.getId());
                    pcsNode.setParentId(null);
                    pcsNode.setIdx(3);
                    pcsNode.setIsParent(false);
                    pointNode.getChildren().add(pcsNode);
                }
            }
        }
        return ApiResult.ok().setData(list);
    }

}
