package com.saili.hzz.app.controller;

import net.sf.json.JSONObject;
import org.codehaus.jackson.map.util.JSONPObject;
import com.saili.hzz.core.util.StringUtil;
import com.saili.hzz.jwt.service.TokenManager;
import com.saili.hzz.jwt.util.Result;
import com.saili.hzz.web.system.pojo.base.TSUser;
import com.saili.hzz.web.system.service.MutiLangServiceI;
import com.saili.hzz.web.system.service.SystemService;
import com.saili.hzz.web.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/***
 * @Title:
 * @Description:
 * @author: whuab_mc
 * @date: 2019-08-02 10:34:10:34
 * @version: V1.0
 */

@Controller
@RequestMapping("/app/login/")
public class LonginAppController {
    @Autowired
    private UserService userService;
    @Autowired
    private MutiLangServiceI mutiLangService;
    @Autowired
    private SystemService systemService;
    @Autowired
    private TokenManager tokenManager;

    @RequestMapping("doLogin")
    @ResponseBody
    public Object doLogin(@RequestBody Map<String, String> reqbody, String callback, HttpServletRequest req, HttpServletResponse response) {
        try {
            String userName = reqbody.get("userName");
            String password = reqbody.get("password");
            if (StringUtil.isEmpty(callback)) {
                callback = "";
            }

            if (StringUtil.isEmpty(userName)) {
                throw new Exception("找不到");
            }
            if (StringUtil.isEmpty(password)) {
                throw new Exception("找不到");
            }

            TSUser user = new TSUser();
            user.setUserName(userName);
            user.setPassword(password);
            TSUser u = userService.checkUserExits(user);
            if (u == null) {
                throw new Exception("找不到");
            }
            if (u != null && u.getStatus() != 0) {
                if (u.getDeleteFlag() == 1) {
                    throw new Exception(mutiLangService.getLang("common.username.or.password.error"));
                }
                if ("2".equals(u.getUserType())) {
                    throw new Exception(mutiLangService.getLang("common.user.interfaceUser"));
                }

                Map<String, Object> userOrgMap = systemService.findOneForJdbc("select org_id as orgId from t_s_user_org where user_id=? LIMIT 1", u.getId());
                userService.saveLoginUserInfo(req, u, (String) userOrgMap.get("orgId"));
                JSONObject attrMap = new JSONObject();
                attrMap.put("userId", u.getId());
                attrMap.put("userOrgId", (String) userOrgMap.get("orgId"));
                attrMap.put("userName", u.getUserName());
                attrMap.put("userDutys", u.getUserDutys());
                attrMap.put("realName", u.getRealName());
                attrMap.put("mobilePhone", u.getMobilePhone());

                // 生成一个token，保存用户登录状态
                String token = tokenManager.createToken(u);
                attrMap.put("token", token);
                return new JSONPObject(callback, Result.success(attrMap));
            } else {
                throw new Exception(mutiLangService.getLang("common.lock.user"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new JSONPObject(callback, Result.error(e.getMessage()));
        }
    }
}
