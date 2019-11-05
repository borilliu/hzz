package com.saili.hzz.backend.controller.base;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.saili.hzz.core.extend.hqlsearch.HqlGenerateUtil;
import org.apache.commons.lang.StringUtils;
import com.saili.hzz.core.common.controller.BaseController;
import com.saili.hzz.core.common.hibernate.qbc.CriteriaQuery;
import com.saili.hzz.core.common.model.json.AjaxJson;
import com.saili.hzz.core.common.model.json.ComboTree;
import com.saili.hzz.core.common.model.json.DataGrid;
import com.saili.hzz.core.common.model.json.TreeGrid;
import com.saili.hzz.core.constant.Globals;
import com.saili.hzz.core.util.MutiLangUtil;
import com.saili.hzz.core.util.MyBeanUtils;
import com.saili.hzz.core.util.StringUtil;
import com.saili.hzz.tag.vo.easyui.ComboTreeModel;
import com.saili.hzz.tag.vo.easyui.TreeGridModel;
import com.saili.hzz.web.system.pojo.base.TSIcon;
import com.saili.hzz.web.system.service.SystemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.saili.hzz.core.entity.TSLUserTypeEntity;

/**
 * @author Liuby
 * @version V1.0
 * @Title: Controller
 * @Description: 用户类型管理
 * @author Liuby
 * @author whuab_mc
 * @date 2014-09-16 21:50:55
 * @version V1.0
 *
 */
@Controller
@RequestMapping("/slUserTypeController")
public class SlUserTypeController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(SlUserTypeController.class);
    private static final String CONTROLLER_LIST = "modules/hzz/slUserType/slUserTypeList";
    private static final String CONTROLLER_ADD_OR_UPDATE = "modules/hzz/slUserType/slUserType";

    @Autowired
    private SystemService systemService;

    /**
     * 管理列表 页面跳转
     *
     * @return
     */
    @RequestMapping(params = "list")
    public String category(HttpServletRequest request) {
        return CONTROLLER_LIST;
    }

    /**
     * easyui AJAX请求数据
     *
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */

    @SuppressWarnings("unchecked")
    @RequestMapping(params = "datagrid")
    @ResponseBody
    public List<TreeGrid> datagrid(TSLUserTypeEntity entity,
                                   HttpServletRequest request, HttpServletResponse response,
                                   DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(TSLUserTypeEntity.class, dataGrid);
        if (entity.getId() == null || StringUtils.isEmpty(entity.getId())) {
            cq.isNull("parent");
        } else {
            cq.eq("parent.code", entity.getId());
            entity.setId(null);
        }
        // 查询条件组装器
        HqlGenerateUtil.installHql(cq,
                entity, request.getParameterMap());
        List<TSLUserTypeEntity> list = systemService.getListByCriteriaQuery(cq, false);
        List<TreeGrid> treeGrids = new ArrayList<TreeGrid>();
        TreeGridModel treeGridModel = new TreeGridModel();
        treeGridModel.setIdField("code");
        treeGridModel.setSrc("id");
        treeGridModel.setTextField("name");
        treeGridModel.setIcon("icon_iconPath");
        treeGridModel.setParentText("parent_name");
        treeGridModel.setParentId("parent_code");
        treeGridModel.setChildList("list");
        treeGrids = systemService.treegrid(list, treeGridModel);
        return treeGrids;
    }

    /**
     * 删除分类管理
     *
     * @return
     */
    @RequestMapping(params = "del")
    @ResponseBody
    public AjaxJson del(TSLUserTypeEntity entity, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        entity = systemService.getEntity(TSLUserTypeEntity.class,
                entity.getId());
        j.setMsg("分类管理删除成功");
        systemService.delete(entity);
        systemService.addLog(j.getMsg(), Globals.Log_Type_DEL,
                Globals.Log_Leavel_INFO);
        return j;
    }

    /**
     * 添加分类管理
     *
     * @param ids
     * @return
     */
    @RequestMapping(params = "save")
    @ResponseBody
    public AjaxJson save(TSLUserTypeEntity entity, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        if (StringUtil.isNotEmpty(entity.getId())) {
            j.setMsg("分类管理更新成功");
            TSLUserTypeEntity t = systemService.get(TSLUserTypeEntity.class,
                    entity.getId());

            entity.getParent().setCode(t.getParent() == null || "".equals(t.getParent().getCode()) ? null : t.getParent().getCode());

            try {
                MyBeanUtils.copyBeanNotNull2Bean(entity, t);
                systemService.saveOrUpdate(t);
                systemService.addLog(j.getMsg(), Globals.Log_Type_UPDATE,
                        Globals.Log_Leavel_INFO);
            } catch (Exception e) {
                logger.error(e.getMessage(), e.fillInStackTrace());
                j.setMsg("分类管理更新失败");
            }
        } else {
            j.setMsg("分类管理添加成功");
            systemService.saveUserType(entity);
            systemService.addLog(j.getMsg(), Globals.Log_Type_INSERT,
                    Globals.Log_Leavel_INFO);
        }
        return j;
    }

    /**
     * 分类管理列表页面跳转
     *
     * @return
     */
    @RequestMapping(params = "addorupdate")
    public String addorupdate(ModelMap map, TSLUserTypeEntity entity) {
        if (StringUtil.isNotEmpty(entity.getCode())) {
            entity = systemService.findUniqueByProperty(TSLUserTypeEntity.class,
                    "code", entity.getCode());
            map.put("categoryPage", entity);
        }
        map.put("iconlist", systemService.findByProperty(TSIcon.class,
                "iconType", (short) 1));
        if (entity.getParent() != null
                && StringUtil.isNotEmpty(entity.getParent().getCode())) {
            TSLUserTypeEntity parent = systemService.findUniqueByProperty(TSLUserTypeEntity.class, "code", entity.getParent().getCode());
            entity.setParent(parent);
            map.put("categoryPage", entity);
        }
        return CONTROLLER_ADD_OR_UPDATE;
    }

    /**
     * 获取河长级别
     *
     * @return
     */
    @RequestMapping("/level")
    @ResponseBody
    public List<TSLUserTypeEntity> riverChiefLevel() {
        CriteriaQuery cq = new CriteriaQuery(TSLUserTypeEntity.class);
        cq.isNull("parent");
        cq.add();
        List<TSLUserTypeEntity> levelList = systemService.getListByCriteriaQuery(cq, false);

        TSLUserTypeEntity defaultEntity = new TSLUserTypeEntity();
        defaultEntity.setId("");
        defaultEntity.setName("请选择河长等级");
        levelList.add(0, defaultEntity);

        return levelList;
    }

    /**
     * 获取河长类型
     * @return
     */
    @RequestMapping("/riverChiefType")
    @ResponseBody
    public List<TSLUserTypeEntity> riverChiefType(String parentId) {
        if (StringUtils.isEmpty(parentId)) {
            return null;
        }
        CriteriaQuery cq = new CriteriaQuery(TSLUserTypeEntity.class);
        cq.createAlias("parent", "parent");
        cq.eq("parent.code", parentId);
        cq.add();
        List<TSLUserTypeEntity> typeList = systemService.getListByCriteriaQuery(cq, false);

        if (StringUtils.isEmpty(parentId)) {
            TSLUserTypeEntity defaultEntity = new TSLUserTypeEntity();
            defaultEntity.setId("");
            defaultEntity.setName("请选择用户类型");
            typeList.add(0, defaultEntity);
        }

        return typeList;
    }

    @RequestMapping(params = "combotree")
    @ResponseBody
    public List<ComboTree> combotree(String selfCode, ComboTree comboTree) {
        CriteriaQuery cq = new CriteriaQuery(TSLUserTypeEntity.class);
        if (StringUtils.isNotEmpty(comboTree.getId())) {
            cq.createAlias("parent", "parent");
            cq.eq("parent.code", comboTree.getId());
        } else if (StringUtils.isNotEmpty(selfCode)) {
            cq.eq("code", selfCode);
        } else {
            cq.isNull("parent");
        }
        cq.add();
        List<TSLUserTypeEntity> categoryList = systemService
                .getListByCriteriaQuery(cq, false);

        if (StringUtils.isEmpty(comboTree.getId())) {
            TSLUserTypeEntity defaultEntity = new TSLUserTypeEntity();
            defaultEntity.setId("");
            defaultEntity.setName("请选择用户类型");
            categoryList.add(0, defaultEntity);
        }


        List<ComboTree> comboTrees = new ArrayList<ComboTree>();
        ComboTreeModel comboTreeModel = new ComboTreeModel("code", "name", "list");
        comboTrees = systemService.ComboTree(categoryList, comboTreeModel,
                null, false);
        MutiLangUtil.setMutiTree(comboTrees);
        return comboTrees;
    }

    /**
     * 鉴于树的问题,这里自己加载全部数据来做同步树
     *
     * @param comboTree
     * @return
     */
    @RequestMapping(params = "tree")
    @ResponseBody
    public List<ComboTree> tree(String selfCode, ComboTree comboTree, boolean isNew) {
        CriteriaQuery cq = new CriteriaQuery(TSLUserTypeEntity.class);
        if (StringUtils.isNotEmpty(comboTree.getId())) {
            cq.createAlias("parent", "parent");
            cq.eq("parent.code", comboTree.getId());
        } else if (StringUtils.isNotEmpty(selfCode)) {
            cq.eq("code", selfCode);
        } else {
            cq.isNull("parent");
        }
        cq.add();
        List<TSLUserTypeEntity> categoryList = systemService
                .getListByCriteriaQuery(cq, false);
        List<ComboTree> comboTrees = new ArrayList<ComboTree>();
        for (int i = 0; i < categoryList.size(); i++) {
            comboTrees.add(entityConvertToTree(categoryList.get(i)));
        }
        return comboTrees;
    }

    private ComboTree entityConvertToTree(TSLUserTypeEntity entity) {
        ComboTree tree = new ComboTree();
        tree.setId(entity.getCode());
        tree.setText(entity.getName());
        if (entity.getList() != null && entity.getList().size() > 0) {
            List<ComboTree> comboTrees = new ArrayList<ComboTree>();
            for (int i = 0; i < entity.getList().size(); i++) {
                comboTrees.add(entityConvertToTree(entity.getList().get(
                        i)));
            }
            tree.setChildren(comboTrees);
        }
        return tree;
    }
}
