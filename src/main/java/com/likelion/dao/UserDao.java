package com.likelion.dao;

import com.likelion.domain.User;
import org.springframework.dao.EmptyResultDataAccessException;

import java.sql.*;
import java.util.EmptyStackException;

public class UserDao {
    private ConnectionMaker connectionMaker;

    public UserDao() {
        this.connectionMaker = new LocalConnectionMaker();
    }
    public UserDao(ConnectionMaker connectionMaker) {
        this.connectionMaker = connectionMaker;
    }

    public void deleteAll() throws SQLException, ClassNotFoundException {
        Connection c = connectionMaker.makeConnection();
        PreparedStatement ps = c.prepareStatement("DELETE FROM users");
        ps.executeUpdate();
        c.close();
    }

    public int getCount() throws SQLException, ClassNotFoundException {
        Connection c = connectionMaker.makeConnection();
        PreparedStatement ps = c.prepareStatement("SELECT count(*) FROM users");

        ResultSet rs = ps.executeQuery();
        rs.next();
        int count = rs.getInt(1);

        rs.close();
        ps.close();
        c.close();
        return count;
    }

    public void add(User user) {
        try {
            Connection c = connectionMaker.makeConnection();
            PreparedStatement ps = c.prepareStatement(
                    "INSERT INTO users(id, name, password) values(?, ?, ?)");
            ps.setString(1, user.getId());
            ps.setString(2, user.getName());
            ps.setString(3, user.getPassword());

            int status = ps.executeUpdate();
            System.out.println(status);
            ps.close();
            c.close();
            System.out.println("success");
        } catch (SQLException | ClassNotFoundException e) {

            throw new RuntimeException(e);
        }

    }

    public User findById(String id) {
        try {
            Connection c = connectionMaker.makeConnection();
            PreparedStatement ps = c.prepareStatement("SELECT id, name, password FROM users WHERE id = ?");
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();

            User user = null;
            if(rs.next()){
                user = new User(rs.getString("id"), rs.getString("name"),
                        rs.getString("password"));
            }
            rs.close();
            ps.close();
            c.close();

            if(user == null) throw new EmptyResultDataAccessException(1);

            System.out.println("success");
            return user;
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        UserDao userDao = new UserDao();
        userDao.add(new User("03", "Ruru", "password"));
        //User user = userDao.get("01");
        //System.out.println(user.getName());
    }
}
