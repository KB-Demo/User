package cn.dao;

import cn.domain.User;

import java.util.List;
import java.util.Map;

public interface UserDao {
    public List<User> findAll();

    public User findUsernameAndPasswordByUser(String username, String password);

    public void addUser(User user);

    public void delUser(int id);

    public User findUser(int id);

    public void updateUser(User user);


    List<User> findForName(String name);

    int findCount(Map<String, String[]> condition);

    List<User> findLimit(int i, int rows, Map<String, String[]> condition);
}
