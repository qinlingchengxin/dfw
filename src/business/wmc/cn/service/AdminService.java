package wmc.cn.service;

import org.springframework.stereotype.Service;
import wmc.cn.dao.AdminDao;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-9-26
 * Time: 上午8:27
 * To change this template use File | Settings | File Templates.
 */
@Service
public class AdminService {
    @Resource
    private AdminDao adminDao;

    public List<Map<String, Object>> existsAdmin(String account, String password) {
        return adminDao.findAdmin(account, password);
    }

    public List<Map<String, Object>> getAllUserInfos() {
        return adminDao.getAllUserInfos();
    }

    public List<Map<String, Object>> getUserByPage(int pageIndex, int pageSize) {
        return adminDao.getUserByPage(pageIndex,pageSize);
    }

    public int getUsersCount() {
        return adminDao.getUsersCount();
    }
}
