package cn.service.impl;

import cn.dao.Impl.UserDaoImpl;
import cn.dao.UserDao;
import cn.domain.PageBean;
import cn.domain.User;
import cn.service.UserService;

import java.util.List;
import java.util.Map;

public class UserServiceImpl implements UserService {
    private UserDao userDao = new UserDaoImpl();
    @Override
    public List<User> findAll() {
        return userDao.findAll();
    }

    @Override
    public User login(User user) {
        return userDao.findUsernameAndPasswordByUser(user.getUsername(), user.getPassword());
    }

    @Override
    public void addUser(User user) {
        userDao.addUser(user);
    }

    @Override
    public void delUser(String id) {
        userDao.delUser(Integer.parseInt(id));
    }

    @Override
    public User findUser(String id) {
        return userDao.findUser(Integer.parseInt(id));
    }

    @Override
    public void updateUser(User user) {
        userDao.updateUser(user);
    }

    @Override
    public List<User> findForName(String name) {
        return userDao.findForName(name);
    }

    @Override
    public void delSelectedUser(String[] ids) {
        for (String id : ids) {
            userDao.delUser(Integer.parseInt(id));
        }
    }

    @Override
    public PageBean<User> findUserByPage(String currentPage, String rows, Map<String, String[]> condition) {
        int row = Integer.parseInt(rows);
        int cp = Integer.parseInt(currentPage);
        int count = userDao.findCount(condition);
        int pageCount = count % row == 0 ? count / row : count / row + 1;
        if (cp <= 0) { //判断误点
            cp = 1;
        }
        if (cp > pageCount) {
            cp = pageCount;
        }
        //1.创建空的PageBean对象
        PageBean<User> userPageBean = new PageBean<>();
        //2.设置参数
        userPageBean.setCurrentPage(cp);//当前页数
        userPageBean.setRows(row);//每页个数
        userPageBean.setTotalCount(count);//总数
        userPageBean.setTotalPage(pageCount);//总页数
        userPageBean.setList(userDao.findLimit((cp - 1) * row, row,condition));//当前页User集合
        return userPageBean;
    }
}
