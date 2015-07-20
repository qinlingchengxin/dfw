package wmc.cn.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-9-26
 * Time: 上午8:24
 * To change this template use File | Settings | File Templates.
 */
@Repository
public class AdminDao {
    @Resource
    private JdbcTemplate jdbcTemplate;

    public List<Map<String, Object>> findAdmin(String account, String password) {
        String sql = "select * from adm where account = ? and pwd= ?";
        return jdbcTemplate.queryForList(sql, new Object[]{account, password});
    }

    public List<Map<String, Object>> getAllUserInfos() {
        String sql = "select *,DATE_FORMAT(registerTime,'%Y-%m-%d %T') as regTime from dfw order  by allScore desc ";
        List<Map<String, Object>> l = jdbcTemplate.queryForList(sql);
        for (int i = 0; i < l.size(); i++) {
            Map<String, Object> m = l.get(i);

            m.put("MC",i+1);
            if ((Integer) (m.get("sex")) == 1)
                l.get(i).put("sex", "男");
            else
                l.get(i).put("sex", "女");
            if ((Integer) (m.get("pro1")) == 1)
                l.get(i).put("pro1", "熟悉");
            else if ((Integer) (m.get("pro1")) == 2)
                l.get(i).put("pro1", "一般");
            else if ((Integer) (m.get("pro1")) == 3)
                l.get(i).put("pro1", "不熟悉");
            else
                l.get(i).put("pro1", "暂未回答");
            if ((Integer) (m.get("pro2")) == 1)
                l.get(i).put("pro2", "熟悉");
            else if ((Integer) (m.get("pro2")) == 2)
                l.get(i).put("pro2", "一般");
            else if ((Integer) (m.get("pro2")) == 3)
                l.get(i).put("pro2", "不熟悉");
            else
                l.get(i).put("pro2", "暂未回答");
            if ((Integer) (m.get("typ")) == 0)
                l.get(i).put("typ", "学生");
            else
                l.get(i).put("typ", "其他");
            int grade = (Integer) m.get("grade");
            if (grade == 1)
                l.get(i).put("grade", "小学");
            else if (grade == 2)
                l.get(i).put("grade", "初中");
            else if (grade == 3)
                l.get(i).put("grade", "高中");
            else
                l.get(i).put("grade", "级部无效");
        }
        return l;
        // return jdbcTemplate.queryForList(sql);
    }

    public List<Map<String, Object>> getUserByPage(int pageIndex, int pageSize) {
        String sql = "select *,DATE_FORMAT(registerTime,'%Y-%m-%d %T') as regTime from dfw order by allScore desc limit  " + (pageIndex - 1) * pageSize + "," + pageSize;
        List<Map<String, Object>> l = jdbcTemplate.queryForList(sql);
        for (int i = 0; i < l.size(); i++) {
            Map<String, Object> m = l.get(i);

            m.put("MC",(pageIndex - 1) * pageSize+i+1);

            if ((Integer) (m.get("sex")) == 1)
                l.get(i).put("sex", "男");
            else
                l.get(i).put("sex", "女");
            if ((Integer) (m.get("pro1")) == 1)
                l.get(i).put("pro1", "熟悉");
            else if ((Integer) (m.get("pro1")) == 2)
                l.get(i).put("pro1", "一般");
            else if ((Integer) (m.get("pro1")) == 3)
                l.get(i).put("pro1", "不熟悉");
            else
                l.get(i).put("pro1", "暂未回答");
            if ((Integer) (m.get("pro2")) == 1)
                l.get(i).put("pro2", "熟悉");
            else if ((Integer) (m.get("pro2")) == 2)
                l.get(i).put("pro2", "一般");
            else if ((Integer) (m.get("pro2")) == 3)
                l.get(i).put("pro2", "不熟悉");
            else
                l.get(i).put("pro2", "暂未回答");
            if ((Integer) (m.get("typ")) == 0)
                l.get(i).put("typ", "学生");
            else
                l.get(i).put("typ", "其他");
            int grade = (Integer) m.get("grade");
            if (grade == 1)
                l.get(i).put("grade", "小学");
            else if (grade == 2)
                l.get(i).put("grade", "初中");
            else if (grade == 3)
                l.get(i).put("grade", "高中");
            else
                l.get(i).put("grade", "级部无效");
        }
        return l;
    }

    public int getUsersCount() {
        String sql = "select count(*) from dfw";
        return jdbcTemplate.queryForInt(sql);
    }
}
