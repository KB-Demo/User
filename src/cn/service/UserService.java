package cn.service;

import cn.domain.PageBean;
import cn.domain.User;

import java.util.List;
import java.util.Map;

public interface UserService {
    public List<User> findAll();

    public User login(User user);

    public void addUser(User user);

    public void delUser(String id);

    public User findUser(String id);

    public void updateUser(User user);

    public List<User> findForName(String name);

    void delSelectedUser(String[] ids);

    /**
     * 分页查询
     * @param currentPage
     * @param rows
     * @param condition
     * @return
     */
    PageBean<User> findUserByPage(String currentPage, String rows, Map<String, String[]> condition);
}
