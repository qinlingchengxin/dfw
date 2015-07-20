package wmc.cn.controller;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import wmc.cn.service.DFWService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.ArrayList;
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
@RequestMapping(value = "dfw")
public class DFWController {

    @Resource
    private DFWService DFWService;
    public static int MAX_STRING_LENGTH = 102400;
     String  constStr =  "ggfsxxxxxvfdfasfdfbvbc2f20dfg8fs4t1tr1te0sf0s1we4e23fd0gvdz100csc";

    @RequestMapping(value = "register")
    @ResponseBody
    public  Map<String, Object> register(HttpServletRequest request, String account, String pwd,String time, String nickname,String school, String cls, int sex,int age, String key, String name, String tel, int pro1, int pro2, int typ, String city, int grade) {

        MessageDigest md5 = null;
        try{
            md5 = MessageDigest.getInstance("MD5");
           // nickname = new String(nickname.getBytes("iso-"))
        }catch(Exception e)
        {
            JSONObject map = new JSONObject();
            map.put("ret", -2);
            map.put("xh", 0);
            return  map;
        }
        int res = 0;
        String hexStr = toHexString(md5,account + pwd + time + constStr + nickname);
    //    System.out.print(hexStr + "\n");
        try
        {
            nickname = new String(nickname.getBytes("iso8859-1"),"utf-8");
            school = new String(school.getBytes("iso8859-1"), "utf-8");
            cls = new String(cls.getBytes("iso8859-1"), "utf-8");
            name =   new String(name.getBytes("iso8859-1"), "utf-8");
            city =   new String(city.getBytes("iso8859-1"), "utf-8");
        }
        catch (Exception e)
        {

        }
        if(pwd.length() < 6 || pwd.length() > 30)
            res = -3;
        else if(!hexStr.equals(key))
            res = -1;
        else
        {
            res = DFWService.register(account, pwd, nickname, school, cls, sex, age, name, tel, pro1, pro2, typ, city, grade);
        }
        JSONObject map = new JSONObject();
        map.put("ret", res);
        map.put("xh", 0);
        return  map;
    }




    @RequestMapping(value = "login")
    @ResponseBody
    public  Map<String, Object> login(HttpServletRequest request, String account, String pwd, long time, String key) {
     //   System.out.print(account + "\n");
        MessageDigest md5 = null;
       try{
           md5 = MessageDigest.getInstance("MD5");
       }catch(Exception e)
       {
           JSONObject map = new JSONObject();
           map.put("ret", -2);
           map.put("xh", 1);
           return  map;
       }
        int res = 0;
        String hexStr =  toHexString(md5,account + pwd + time + constStr);
        System.out.print(hexStr + "\n");
        if(!hexStr.equals(key))
            res = -1;
        if(res!=-1)
        {
            Map<String, Object> map =   DFWService.geLoginData(account, pwd);
            if(map == null||map.isEmpty())
                res = 0;
            else res = 1;
            if(map == null)
                map = new HashMap<String, Object>();
            map.put("ret", res);
            map.put("xh", 1);
            return  map;
        }
        else
        {
            JSONObject map = new JSONObject();
            map.put("ret", res);
            map.put("xh", 1);
            return  map;
        }
    }



    @RequestMapping(value = "changePwd")
    @ResponseBody
    public  JSONObject changePwd(HttpServletRequest request, String account, String pwd, String newPwd, long time, String key) {
        MessageDigest md5 = null;
        try{
            md5 = MessageDigest.getInstance("MD5");
        }catch(Exception e)
        {
            JSONObject map = new JSONObject();
            map.put("ret", -2);
            map.put("xh", 4);
            return  map;
        }
        int res = 0;
        if(newPwd.length() < 6 || newPwd.length() > 30)
            res = -3;
        else
        {
            String hexStr =  toHexString(md5,account + pwd + newPwd+ time + constStr);
            System.out.print(hexStr + "\n");
            if(!hexStr.equals(key))
                 res = -1;
            if(res!=-1)
            {
                res =   DFWService.changePwd(account, pwd, newPwd);
            }
        }
        JSONObject map = new JSONObject();
        map.put("ret", res);
        map.put("xh", 4);
        return  map;
    }




    @RequestMapping(value = "getData")
         @ResponseBody
         public  Map<String, Object> getData(HttpServletRequest request, int userId) throws JSONException {
        return DFWService.getSaveData(userId);
    }


    @RequestMapping(value = "updateFlag")
    @ResponseBody
    public  void getData(HttpServletRequest request, int userId, int flag) throws JSONException {
        DFWService.updateLoginFlag(userId, flag);
    }

    @RequestMapping(value = "updateScore", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject updateScore(HttpServletRequest request,long pid, int score, String time, String key, int level) throws JSONException {
        if(level < 1 || level > 3)
        {
            JSONObject map = new JSONObject();
            map.put("ret",0);
            map.put("xh", 3);
            return  map;
        }
        MessageDigest md5 = null;
        try{
            md5 = MessageDigest.getInstance("MD5");
       }catch(Exception e)
       {
           JSONObject map = new JSONObject();
           map.put("ret", -2);
           map.put("xh", 3);
           return  map;
       }
        int res = 1;
        String hexStr = toHexString(md5,"" + pid + score + level +time  + constStr);
        System.out.print(hexStr);
        JSONObject json = new JSONObject();

        if(!hexStr.equals(key))
        {
            res = 0;
         //   l = DFWService.updateScore(pid, score);

        }
        else
        {
            ArrayList l = DFWService.updateScore(pid, score, level);
            if(l.size() <= 0)
                res = 0;
            else
            {
                json.put("bestScore",l.get(0));
                json.put("lastScore", l.get(1));
                json.put("alLScore", l.get(2));
            }
        }

        json.put("ret", res);
        json.put("xh", 3);

        return json;
    }


    @RequestMapping(value = "getRank")
    @ResponseBody
    public JSONObject getRank(HttpServletRequest request, int id)  {
        JSONObject json = new JSONObject();
        json.put("rank", DFWService.getRankList(id));
        json.put("myRank", DFWService.getMyRankList(id));
        json.put("xh", 2);
        return json;
    }



    public static String toHexString( MessageDigest md5,String key)
    {
        char []md = key.toCharArray();
        byte[] arr = new byte[md.length];
        for (int i = 0; i < md.length; i++)
            arr[i] = (byte) md[i];
        byte[] bs = md5.digest(arr);
        StringBuilder sb = new StringBuilder();
        int i;
        char c;
        byte b;
        int max = 0;
        for ( i = 0; i < bs.length; i ++)
        {
            max ++;
            if ( max >= MAX_STRING_LENGTH )
            {
                break;
            }
            b = bs[i];
            c = Character.forDigit((b >>> 4) & 0x0F, 16);
            sb.append(c);
            c = Character.forDigit(b & 0x0F, 16);
            sb.append(c);
        }
        return sb.toString();
    }
}
