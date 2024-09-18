package cn.ruishan.common.utils;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.XmlUtil;
import cn.hutool.setting.Setting;
import cn.ruishan.common.config.SystemConfig;
import cn.ruishan.common.model.ZTree;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public class CimUtil {

    /**
     * 获取目录树页面列表
     */
    public static List<Map<String, Object>> getObjectList(Integer gwId, Integer projId) {
        Setting setting = new Setting(SystemConfig.SYSTEM_CONFIG);
        String modelFilePath = setting.getStr(SystemConfig.MODEL_FILE_PATH);
        String path = StrUtil.format("{}/P{}/gw{}.xml", modelFilePath, projId, gwId);
        List<Map<String, Object>> result = CollUtil.newArrayList();
        Document document = XmlUtil.readXML(path);
        Element element = XmlUtil.getRootElement(document);
        if (element != null) {
            // 根节点下的data节点
            if (CollUtil.isNotEmpty(XmlUtil.getElements(element, "data"))) {
                Element dataNode = XmlUtil.getElement(element, "data");
                // data节点下的page节点
                List<Element> pageNode = XmlUtil.getElements(dataNode, "page");
                if (CollUtil.isNotEmpty(pageNode)) {
                    for (Element page : pageNode) {
                        Map<String, Object> map = MapUtil.newHashMap();
                        map.put("name", page.getAttribute("name"));
                        map.put("tree", traverseElementForZTree(page, null));
                        result.add(map);
                    }
                }
            }
        }
        return result;
    }

    /**
     * 递归遍历page(ZTree格式)
     *
     * @param element
     * @param pName 父名称
     * @return
     */
    private static List<ZTree> traverseElementForZTree(Element element, String pName) {

        List<ZTree> trees = CollUtil.newArrayList();

        // page节点下的item
        List<Element> itemNode = XmlUtil.getElements(element, "item");
        /* 如果有内容则递归遍历子节点 */
        if (CollUtil.isNotEmpty(itemNode)) {
            for (Element et : itemNode) {
                String name = et.getAttribute("name");
                ZTree tree = new ZTree();
                tree.setName(name);

                Map<String, Object> attributes = MapUtil.newHashMap();
                String classID = et.getAttribute("classId");
                String objectID = et.getAttribute("objectId");
                attributes.put("classId", classID);
                attributes.put("objectId", objectID);
                String pathName = name;
                if(StrUtil.isNotBlank(pName)) {
                    pathName = StrUtil.format("{}.{}", pName, name);
                }

                attributes.put("name", pathName);
                tree.setAttributes(attributes);

                trees.add(tree);
                if (et.hasChildNodes()) {
                    List<ZTree> children = traverseElementForZTree(et, pathName);
                    if (CollUtil.isEmpty(children)) {
                        children = CollUtil.newArrayList();
                    }
                    tree.setChildren(children);
                }
            }
        }

        return trees;
    }

    /**
     * 读编辑辅助框(sys)
     *
     * @param
     * @return
     */
    public static Map<String, Object> getFuncList(String scriptHost, HttpServletRequest request) {

        String xmlStr = HttpUtil.post(StrUtil.format("http://{}/api/script/funcList", scriptHost), "{}", request);
        // String xmlStr = FileUtil.readString("E:\\ScriptToolFunCfg.xml", CharsetUtil.CHARSET_UTF_8);
        Document document = XmlUtil.readXML(xmlStr);
        Element element = XmlUtil.getRootElement(document);

        Map<String, Object> map = MapUtil.newHashMap();
        map.put("sys", getFuncListByType(element, "sys"));
        map.put("obj", getFuncListByType(element, "cimObject"));
        return map;
    }

    private static List<Map<String, Object>> getFuncListByType(Element element, String type) {
        List<Map<String, Object>> list = CollUtil.newArrayList();
        Element sysNode = XmlUtil.getElement(element, type);
        List<Element> funNode = XmlUtil.getElements(sysNode, "fun");
        if (CollUtil.isNotEmpty(funNode)) {
            for (Element et : funNode) {
                Map<String, Object> map = MapUtil.newHashMap();
                map.put("title", et.getAttribute("tooltip"));
                map.put("real", et.getAttribute("real"));
                map.put("prompt", et.getAttribute("prompt"));
                list.add(map);
            }
        }

        return list;
    }
}
