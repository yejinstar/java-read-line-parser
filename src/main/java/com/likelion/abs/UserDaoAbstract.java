package com.likelion.abs;

import com.likelion.domain.User;

import java.sql.*;

public abstract class UserDaoAbstract {

    public abstract Connection makeConnection() throws SQLException, ClassNotFoundException;

    public void add(User user){
        try{
            Connection c = makeConnection();
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
        }catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    public User findById(String id){
        try{
            Connection c = makeConnection();
            PreparedStatement ps = c.prepareStatement("SELECT id, name, password FROM users WHERE id = ?");
            ps.setString(1,id);
            ResultSet rs = ps.executeQuery();
            rs.next();
            User user = new User(rs.getString("id"),rs.getString("name"),
                    rs.getString("password"));
            rs.close();
            ps.close();
            c.close();
            System.out.println("success");
            return user;
        } catch(SQLException | ClassNotFoundException e){
            throw new RuntimeException(e);
        }
    }
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
//        UserDaoAbstract userDao = new UserDaoAbstract();
//        userDao.add(new User("03","Ruru","password"));
//        //User user = userDao.get("01");
//        //System.out.println(user.getName());
    }
}
