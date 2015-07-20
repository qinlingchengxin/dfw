package wmc.cn.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import wmc.cn.dao.DFWDao;

import javax.annotation.Resource;
import java.util.ArrayList;
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
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class DFWService {
    @Resource
    private DFWDao DFWDao;

  //  @Transactional(readOnly = true)
    public Map<String, Object> geLoginData(String account, String pwd){
        return DFWDao.saveAndGetUserData(account, pwd);
    }

    public int register(String account, String pwd, String nickname, String school, String cls, int sex, int age, String name, String tel, int pro1, int pro2,int typ, String city, int grade)
    {
        return DFWDao.registerData(account, pwd, nickname, school, cls, sex, age, name, tel, pro1, pro2, typ, city, grade);
    }

    public Map<String, Object> getSaveData(int userId) {
        return DFWDao.getSaveData(userId);
    }


    public ArrayList updateScore(long id, int score, int level) {
        return DFWDao.updateScore(id, score, level);
    }

    public List getRankList(int pid) {
        return DFWDao.getRankList(pid);
    }

    public List getMyRankList(int pid)
    {
        return DFWDao.getSaveDataList(pid);
    }

    public int changePwd(String account, String pwd, String newPwd)
    {
        return DFWDao.changePwd(account, pwd, newPwd);
    }

    public void updateLoginFlag(long id, int flag)
    {
        DFWDao.updateLoginFlag(id, flag);
    }
}
