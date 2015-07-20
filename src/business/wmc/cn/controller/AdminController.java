package wmc.cn.controller;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import net.sf.jxls.transformer.XLSTransformer;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import wmc.cn.service.AdminService;
import wmc.cn.utils.Constant;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 15-3-27
 * Time: 下午10:19
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping(value = "admin")
public class AdminController {

    @Resource
    private AdminService adminService;

    /**
     * 检查登录
     *
     * @param request
     * @param session
     * @param account
     * @param password
     * @return
     * @throws net.sf.json.JSONException
     */
    @RequestMapping(value = "checkLogin")
    @ResponseBody
    public JSONObject checkLogin(HttpServletRequest request, HttpSession session, String account, String password) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        List<Map<String, Object>> admins = adminService.existsAdmin(account, password);
        if (admins != null && admins.size() > 0) {
            session.setAttribute("admin", admins.get(0));
            jsonObject.put(Constant.REQRESULT, Constant.REQSUCCESS);
        } else {
            jsonObject.put(Constant.REQRESULT, Constant.REQFAILED);
        }
        return jsonObject;
    }

    /**
     * 检查登录
     *
     * @param session
     * @return
     * @throws net.sf.json.JSONException
     */
    @RequestMapping(value = "login")
    public ModelAndView login(HttpSession session) throws JSONException {
        ModelAndView modelAndView = new ModelAndView();
        int userCount = adminService.getUsersCount();
        List<Map<String, Object>> users = adminService.getUserByPage(1, Constant.PAGENUM);
        int totalPage = userCount / Constant.PAGENUM + (userCount % Constant.PAGENUM == 0 ? 0 : 1);
        modelAndView.addObject("users", users);
        modelAndView.addObject("userCount", userCount);
        modelAndView.addObject("totalPage", totalPage);
        modelAndView.addObject("curPage", 1);
        modelAndView.setViewName("showInfo");
        return modelAndView;
    }

    /**
     * 调到指定页
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "queryUsersByPage")
    @ResponseBody
    public Map<String, Object> queryUsersByPage(HttpServletRequest request) {
        Map<String, Object> jsonObject = new HashMap<String, Object>();
        int pageIndex = Integer.parseInt(request.getParameter("curPage"));
        List<Map<String, Object>> users = adminService.getUserByPage(pageIndex, Constant.PAGENUM);
        jsonObject.put("users", users);
        return jsonObject;
    }


    /**
     * 调到指定页
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "exportReport")
    @ResponseBody
    public String exportReport(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        String fileTmpPath = request.getRealPath("report/userTemplate.xls");
        String resultFileName = "user_" + System.currentTimeMillis() + ".xls";
        Map<String, Object> jsonObject = new HashMap<String, Object>();
        List<Map<String, Object>> users = adminService.getAllUserInfos();
        jsonObject.put("users", users);
        ServletOutputStream os = response.getOutputStream();

        InputStream is = null;

        /*以下是导出图片到excel中*/
        BufferedImage bufferImg;
        /*以下是导出图片到excel中*/



        //先把读进来的图片放到一个ByteArrayOutputStream中，以便产生ByteArray
        try {
             /*以下是导出图片到excel中*/
            ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
            bufferImg = ImageIO.read(new File("F:/aaa.jpg"));
            ImageIO.write(bufferImg, "jpg", byteArrayOut);
             /*以下是导出图片到excel中*/


            XLSTransformer transformer = new XLSTransformer();
            is = new BufferedInputStream(new FileInputStream(fileTmpPath));
            HSSFWorkbook wb = transformer.transformXLS(is, jsonObject);


            /*以下是导出图片到excel中*/
            HSSFSheet sheet = wb.getSheetAt(0);
            //画图的顶级管理器，一个sheet只能获取一个（一定要注意这点）
            HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
            //anchor主要用于设置图片的属性
            HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0, 1023, 255,(short) 19, 0, (short) 20, 1);
            anchor.setAnchorType(3);
            //插入图片
            patriarch.createPicture(anchor, wb.addPicture(byteArrayOut.toByteArray(), HSSFWorkbook.PICTURE_TYPE_JPEG));
            /*以下是导出图片到excel中*/


            response.reset();
            response.setHeader("Content-disposition", "attachment; filename=" + resultFileName);
            response.setContentType("application/msexcel");
            wb.write(os);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                is.close();
            }
        }
        return "";
    }
}
