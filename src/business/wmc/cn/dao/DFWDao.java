package wmc.cn.dao;

import net.sf.json.JSONObject;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.sql.*;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-9-26
 * Time: 上午8:24
 * To change this template use File | Settings | File Templates.
 */
@Repository
public class DFWDao {

    @Resource
    private JdbcTemplate jdbcTemplate;

    public String updateStr(String str)
    {
        return  str.replace("", "'");
    }
    public int registerData(String account, String pwd, String nickName , String school, String cls, int sex, int age, String name, String tel, int pro1, int pro2, int typ, String city, int grade)
    {
        final String sql = "select id, account, pwd, nickname, bestScore, lastScore, school, cls, sex, age, grade from dfw  where account = ?";
        List<Map<String, Object>> maps =  jdbcTemplate.queryForList(sql, account);

        if(maps== null || maps.size() == 0)
        {
            //    String updateSql = "insert into dfw(account, pwd, nickname)  values("+  "'"+  account + "'" + "," +"'" +  pwd + "'" + "," + "'ABCD" + account+"'" + ")";
            String updateSql = "insert into dfw(account, pwd, nickname, school, cls, sex, age, name, tel, pro1, pro2, typ, registerTime, city, grade)  values(?, ?, ?, ?, ?, ?, ?,?,?,?,?,?,?, ?, ?)";
            java.util.Date date=new java.util.Date();
            Timestamp tt=new Timestamp(date.getTime());
            jdbcTemplate.update(updateSql, new Object[]{account, pwd,nickName, school, cls, sex, age, name, tel, pro1, pro2, typ,tt, city, grade });
            return 1;
        }
        else
        {
            return 0;
        }

    }



    public Map<String, Object> saveAndGetUserData(String account, String pwd) {
        //存储或者获取账户信息登录时使用

       // final String sql = "select id, account, pwd, nickname, bestScore, lastScore from dfw  where account = " + account;
        final String sql = "select id, account, nickname, dfwTimes,bestScore, lastScore, school, cls, sex, age, level,leaveTime,loginFlag  from dfw  where account = ? and pwd = ?";
        List<Map<String, Object>> maps =  jdbcTemplate.queryForList(sql, account,pwd);
        Map<String, Object> map = null;
        if(maps== null || maps.size() == 0)
        {
        //    String updateSql = "insert into dfw(account, pwd, nickname)  values("+  "'"+  account + "'" + "," +"'" +  pwd + "'" + "," + "'ABCD" + account+"'" + ")";
        /*    String updateSql = "insert into dfw(account, pwd, nickname)  values(?, ?, ?)";
            System.out.print(updateSql);
            jdbcTemplate.update(updateSql, account, pwd, "DFW" + account);
            maps =  jdbcTemplate.queryForList(sql, account);
            map = maps.get(0);
            */
            return null;
        }
        else
        {
            map = maps.get(0);
            int flag = (Integer)(map.get("loginFlag"));
            if(flag == 0)
                map.put("flag", 0);
            else
            {
                int leaveTime = (Integer)(map.get("leaveTime"));
                if(System.currentTimeMillis() / (1000 * 60 * 60) - leaveTime >= 12)
                {
                        map.put("flag", 0);
                        jdbcTemplate.update("update dfw set leaveTime = ?, loginFlag = ?  where id = ?", 0, 0, (Integer)(map.get("id")));
                }
                else
                {
                    map.put("flag", 1);
                }
            }
            jdbcTemplate.update("update dfw set dfwTimes = ?  where id = ?",  (Integer)(map.get("dfwTimes")) + 1, (Integer)(map.get("id")));
            return map;

        }
      //  return map;
    }


    public void updateLoginFlag(long id, int flag)
    {
        int  leaveTime = 0;
        int loginFlag = 0;
        if(flag != 0)
        {
            leaveTime = (int) (System.currentTimeMillis() / (1000 * 60 * 60)) ;
            loginFlag = 1;
        }
        jdbcTemplate.update("update dfw set leaveTime = ?, loginFlag = ?  where id = ?", leaveTime, loginFlag, id);
    }


    public Map<String, Object> getSaveData(long id) {
        String sql = "select id, account, nickname,bestScore, lastScore,allScore from dfw where id = ?";
        return jdbcTemplate.queryForMap(sql, id);
    }

    public List<Map<String, Object>> getSaveDataList(long id) {
        String sql = "select id, account, nickname,bestScore, lastScore,allScore, times,school, cls, name from dfw where id = ?";
        List<Map<String, Object>> l = jdbcTemplate.queryForList(sql, id);
        if(l.size() > 0)
        {
             int score = (Integer)(l.get(0).get("allScore"));

            int res;
            res = jdbcTemplate.queryForObject("select count(*) from dfw where allScore > " + score,  Integer.class);
            l.get(0).put("rank", res + 1);
        }
        return   l;



    }

    public ArrayList updateScore(long id, int score, int level)
    {
        String sql = "select bestScore, lastScore, allScore,level, times from dfw where id = ?";
        Map<String, Object> map = jdbcTemplate.queryForMap(sql, id);
        ArrayList scoreList = new ArrayList();
        if(map.isEmpty())
        {
            scoreList.add(0);
            scoreList.add(0);
            scoreList.add(0);
            return scoreList;
        }
      //  JSONObject jsonObject = new JSONObject(map);
        int bestScore = (Integer)(map.get("bestScore"));
        if(bestScore < score)
            bestScore = score;

        int myLevel =   (Integer)(map.get("level"));
      //  if(myLevel + 1 != level )
       // {
       //     return  scoreList;
       // }
        int lastScore =  (Integer)(map.get("lastScore"));
        int allScore =   (Integer)(map.get("allScore"));
                //+ score;
        lastScore += score;
        if(level >= 3)
        {
            level = 0;
            if(score != 0)
                allScore +=   lastScore;
            lastScore = 0;
        }

    //    sql = "update dfw set bestScore = " + bestScore + " , " + " lastScore = " + score + " where id = " + id;
        sql = "update dfw set bestScore = ?, lastScore = ?, allScore = ?, times = ?, level = ?  where id = ?";
        jdbcTemplate.update(sql, bestScore, lastScore,allScore,(Integer)(map.get("times")) + 1,level, id);
        scoreList.add(bestScore);
        scoreList.add(score);
        scoreList.add(allScore);
        return scoreList;
    }

    public List getRankList(int pid)
    {
        String sql = "select id, account,nickname, bestScore, lastScore, allScore, times,school, cls, name from dfw order by allScore desc limit 10";
        return jdbcTemplate.queryForList(sql);
    }


    public int changePwd(String account, String pwd, String newPwd)
    {
        final String sql = "select id, account, nickname, bestScore, lastScore, school, cls, sex, age from dfw  where account = ? and pwd = ?";
        List<Map<String, Object>> maps =  jdbcTemplate.queryForList(sql, account,pwd);
        Map<String, Object> map = null;
        if(maps== null || maps.size() == 0)
        {
            //    String updateSql = "insert into dfw(account, pwd, nickname)  values("+  "'"+  account + "'" + "," +"'" +  pwd + "'" + "," + "'ABCD" + account+"'" + ")";
        /*    String updateSql = "insert into dfw(account, pwd, nickname)  values(?, ?, ?)";
            System.out.print(updateSql);
            jdbcTemplate.update(updateSql, account, pwd, "DFW" + account);
            maps =  jdbcTemplate.queryForList(sql, account);
            map = maps.get(0);
            */
            return 0;
        }
        else
        {
             String newStr = "update dfw set pwd = ? where account = ?";
            jdbcTemplate.update(newStr, newPwd, account);
            return  1;
        }
    }
}
