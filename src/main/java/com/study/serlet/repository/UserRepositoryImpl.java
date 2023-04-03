package com.study.serlet.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.study.serlet.entity.User;
import com.study.serlet.util.DBConnectionMgr;

public class UserRepositoryImpl implements UserRepository {

	// Respository 객체 Singleton 정의
	private static UserRepository instance;
	public static UserRepository getInstance() {
		if(instance == null) {
			instance = new UserRepositoryImpl();
		}
		return instance;
	}
	
	
	// DBConnectionMgr DI
	private DBConnectionMgr pool;
	
	private UserRepositoryImpl() {
		pool = DBConnectionMgr.getInstance();
	}
	
	
	@Override
	public int save(User user) {
		Connection con = null;
		PreparedStatement pstmt = null;
		int successCount = 0;
		
		try {
			con = pool.getConnection();
			String sql = "insert into user_mst\r\n"
					+ "values (0,?,?,?,?)";
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, user.getUsername());
			pstmt.setString(2, user.getPassword());
			pstmt.setString(3, user.getName());
			pstmt.setString(4, user.getEmail() );
			
			successCount = pstmt.executeUpdate();
		} catch (Exception e) {
		
			e.printStackTrace();
		}finally {
			pool.freeConnection(con,pstmt);
		}
		return successCount;
	}

	@Override
	public User findUserByUsername(String username) {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		User user = null;
		
		try {
			con = pool.getConnection(); //DB연결
			String sq = "select\r\n"
					+ "	um.user_id,\r\n"
					+ "    um.username,\r\n"
					+ "    um.password,\r\n"
					+ "    um.name,\r\n"
					+ "    um.email,\r\n"
					+ "    ud.gender,\r\n"
					+ "    ud.birthday,\r\n"
					+ "    ud.address\r\n"
					+ "from\r\n"
					+ "	user_mst um\r\n"
					+ "    left outer join  user_dtl ud on (ud.user_id = um.user_id)\r\n"
					+ "where\r\n"
					+ "	um.username = ?";
			pstmt = con.prepareStatement(sq);
			pstmt.setString(1, username);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				user = User
						.builder()
						.userId		(rs.getInt(1))
						.username	(rs.getString(2))
						.password	(rs.getString(3))
						.name		(rs.getString(4))
						.email		(rs.getNString(5))
						.build		();
				
			}
					
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			pool.freeConnection(con, pstmt, rs);
		}
		
		
		return user;
	}

}
