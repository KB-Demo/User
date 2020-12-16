package cn.dao.Impl;

import cn.dao.UserDao;
import cn.domain.User;
import cn.util.JDBCUtlis;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class UserDaoImpl implements UserDao {

    private JdbcTemplate template = new JdbcTemplate(JDBCUtlis.getDataSource());

    @Override
    public List<User> findAll() {
        String sql = "select * from user";
        List<User> users = template.query(sql, new BeanPropertyRowMapper<User>(User.class));
        return users;
    }

    @Override
    public User findUsernameAndPasswordByUser(String username, String password) {
        try {
            String sql = "select * from user where username = ? and password = ?";
            User user = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), username, password);
            return user;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public void addUser(User user) {
        /*String sql = "insert into user values('" + user.getId() + "','" + user.getName() + "','" + user.getGender() + "','" + user.getAge() + "','" + user.getAddress() + "','" + user.getQq() + "','" + user.getEmail() + "','" + user.getUsername() + "','" + user.getPassword() + "')";
        template.update(sql);*/
        String sql = "insert into user values(null,?,?,?,?,?,?,null ,null )";
        template.update(sql, user.getName(), user.getGender(), user.getAge(), user.getAddress(), user.getQq(), user.getEmail());
    }

    @Override
    public void delUser(int id) {
        String sql = "delete from user where id = ?";
        template.update(sql, id);
    }

    @Override
    public User findUser(int id) {
        String sql = "select * from user where id = ?";
        User user = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), id);
        return user;
    }

    @Override
    public void updateUser(User user) {
        String sql = "update user set name = ?, gender = ?, age = ?, address=?, qq = ?, email = ? where id = ?";
        template.update(sql, user.getName(), user.getGender(), user.getAge(), user.getAddress(), user.getQq(), user.getEmail(), user.getId());
    }

    @Override
    public List<User> findForName(String name) {
        String sql = "select * from user where name like ?";
        List<User> users = template.query(sql, new BeanPropertyRowMapper<User>(User.class), "%" + name + "%");
        return users;

    }

    @Override
    public int findCount(Map<String, String[]> condition) {
                //1.定义模板初始化sql
                String sql = "select count(*) from user where 1 = 1 ";
                StringBuilder sb = new StringBuilder(sql);
                //2.遍历map
                Set<String> keySet = condition.keySet();
                //定义参数集合
                List<Object> params = new ArrayList<Object>();
                for (String key : keySet) {
                    //排除分页条件参数
                    if ("currentPage".equals(key) || "rows".equals(key)) {
                continue;
            }

            //获取value
            String value = condition.get(key)[0];
            //判断value是否有值
            if (value != null && !"".equals(value)) {
                //有值
                sb.append(" and " + key + " like ? ");
                params.add("%" + value + "%");//?条件的值  提问：直接问好赋value不可以吗？？？？？？？？
            }
        }
        System.out.println(sb.toString());

        int count = template.queryForObject(sb.toString(), Integer.class, params.toArray());
        return count;
    }

    @Override
    public List<User> findLimit(int i, int rows, Map<String, String[]> condition) {
        String sql = "select * from user where 1 = 1 ";
        StringBuilder sb = new StringBuilder(sql);
        //2.遍历map
        Set<String> keySet = condition.keySet();
        //定义参数集合
        List<Object> params = new ArrayList<Object>();
        for (String key : keySet) {
            //排除分页条件参数
            if ("currentPage".equals(key) || "rows".equals(key)) {
                continue;
            }

            //获取value
            String value = condition.get(key)[0];
            //判断value是否有值
            if (value != null && !"".equals(value)) {
                //有值
                sb.append(" and " + key + " like ? ");
                params.add("%" + value + "%");//?条件的值  提问：直接问好赋value不可以吗？？？？？？？？
            }
        }

        //添加分页查询
        sb.append(" limit ?,? ");
        //添加分页查询参数
        params.add(i);
        params.add(rows);

        sql = sb.toString();

        System.out.println(sql);
        List<User> users = template.query(sql, new BeanPropertyRowMapper<User>(User.class),params.toArray());
        return users;

    }


}
