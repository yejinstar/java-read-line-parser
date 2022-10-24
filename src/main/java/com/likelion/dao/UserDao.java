package com.likelion.dao;

import com.likelion.domain.User;
import org.springframework.dao.EmptyResultDataAccessException;

import javax.sql.DataSource;
import java.sql.*;

public class UserDao {
    private ConnectionMaker connectionMaker;

    private final DataSource dataSource;
    private final JdbcContext jdbcContext;

    public UserDao(DataSource dataSource){
        this.dataSource = dataSource;
        this.jdbcContext = new JdbcContext(dataSource);
    }

//    public void jdbcContextWithStatementStrategy(StatementStrategy stmt) throws SQLException {
//        Connection c = null;
//        PreparedStatement ps = null;
//        try {
//            c = dataSource.getConnection();
//            ps = stmt.makePreparedStatement(c);
//            ps.executeUpdate();
//        } catch (SQLException e) {
//            throw e;
//        } finally {
//            if (ps != null) {
//                try {
//                    ps.close();
//                } catch (SQLException e) {
//                }
//            }
//            if (c != null) {
//                try {
//                    c.close();
//                } catch (SQLException e) {
//                }
//            }
//        }
//    }

    public void deleteAll() throws SQLException, ClassNotFoundException {
//        StatementStrategy deleteAllStrategy = new StatementStrategy() {
//            @Override
//            public PreparedStatement makePreparedStatement(Connection connection) throws SQLException {
//                return connection.prepareStatement("delete from users");
//            }
//        };
//        jdbcContextWithStatementStrategy(new StatementStrategy() {
//            @Override
//            public PreparedStatement makePreparedStatement(Connection connection) throws SQLException {
//                return connection.prepareStatement("delete from users");
//            }
//        });
        jdbcContext.workWithStatementStrategy(new StatementStrategy() {
            @Override
            public PreparedStatement makePreparedStatement(Connection connection) throws SQLException {
                return connection.prepareStatement("delete from users");
            }
        });
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

    public void add(final User user) throws SQLException {
//        AddStrategy addStrategy = new AddStrategy(user);
//        jdbcContextWithStatementStrategy(addStrategy);
        jdbcContext.workWithStatementStrategy(new StatementStrategy() {
            @Override
            public PreparedStatement makePreparedStatement(Connection connection) throws SQLException {
                PreparedStatement pstmt = null;
                pstmt = connection.prepareStatement("INSERT INTO users(id, name, password) VALUES(?,?,?);");
                pstmt.setString(1, user.getId());
                pstmt.setString(2, user.getName());
                pstmt.setString(3, user.getPassword());
                return pstmt;
            }

        });
//        jdbcContextWithStatementStrategy(new StatementStrategy() {
//            @Override
//            public PreparedStatement makePreparedStatement(Connection connection) throws SQLException {
//                PreparedStatement pstmt = null;
//                pstmt = connection.prepareStatement("INSERT INTO users(id, name, password) VALUES(?,?,?);");
//                pstmt.setString(1, user.getId());
//                pstmt.setString(2, user.getName());
//                pstmt.setString(3, user.getPassword());
//                return pstmt;
//            }
//        });
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
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
//        UserDao userDao = new UserDao();
//        userDao.add(new User("03", "Ruru", "password"));
        //User user = userDao.get("01");
        //System.out.println(user.getName());
    }
}
