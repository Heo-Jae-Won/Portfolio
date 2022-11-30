package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class EnrollDAO {
	Connection con=Database.CON;
	SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");

	//특정학생의 수강신청 목록
	public JSONArray ecoulist(String scode) {
		JSONArray jArray=new JSONArray();
		try {
			String sql="select * from ecou where scode=?";
			PreparedStatement ps=con.prepareStatement(sql);
			ps.setString(1, scode);
			ResultSet rs=ps.executeQuery();
			while(rs.next()) {
				JSONObject obj=new JSONObject();
				obj.put("lcode", rs.getString("lcode"));
				obj.put("lname", rs.getString("lname"));
				obj.put("pname", rs.getString("pname"));
				obj.put("room", rs.getString("room"));
				obj.put("hours", rs.getString("hours"));
				obj.put("persons", rs.getInt("persons"));
				obj.put("capacity", rs.getInt("capacity"));
				String edate=sdf.format(rs.getDate("edate"));
				obj.put("edate", edate);
				jArray.add(obj);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}




		return jArray;}


	//강좌목록
	public JSONArray alist(){
		JSONArray jArray=new JSONArray();

		try {
			String sql="select * from cou";
			PreparedStatement ps=con.prepareStatement(sql);
			ResultSet rs=ps.executeQuery();
			while(rs.next()) {
				JSONObject obj=new JSONObject();
				obj.put("lcode", rs.getString("lcode"));
				obj.put("lname", rs.getString("lname"));
				obj.put("pname", rs.getString("pname"));
				obj.put("persons", rs.getInt("persons"));
				obj.put("capacity", rs.getInt("capacity"));
				jArray.add(obj);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		return jArray;}


	//중복체크
	public int count(String lcode, String scode) {
		int check=0;
		
		try {
			String sql="select count(*) from estu where lcode=? and scode=?";
			PreparedStatement ps=con.prepareStatement(sql);
			ps.setString(1, lcode);
			ps.setString(2, scode);
			ResultSet rs=ps.executeQuery();
			if(rs.next()) {
				check=rs.getInt("count(*)");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		


		return check;}
	//삽입. VO없어도 되는 이유는 lcode하고 scode만 받으면 되기 때문에 굳이 만들지 않았음.
	public void insert(String lcode, String scode) {
		
		try {
			String sql="insert into enrollments(lcode,scode) values(?,?)";
			PreparedStatement ps=con.prepareStatement(sql);
			ps.setString(1, lcode);
			ps.setString(2, scode);
			ps.execute();
			//강좌를 등록했으면 course에도 사람 한명이 추가되었음을 update해주어야 함.
			sql="update courses set persons=persons+1 where lcode=?";
			ps=con.prepareStatement(sql);
			ps.setString(1, lcode);
			ps.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		

	}

	
	//특정강좌의 특정학생 점수 수정
	public void update(String lcode, String scode, int grade) {
		
		try {
			String sql="update enrollments set grade=? where lcode=? and scode=?";
			PreparedStatement ps=con.prepareStatement(sql);
			ps.setInt(1, grade);
			ps.setString(2, lcode);
			ps.setString(3, scode);
			ps.execute();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	
	
	
	//특정 강좌의 학생목록
	public JSONArray slist(String lcode) {
		JSONArray jArray=new JSONArray();
		
		try {
			String sql="SELECT * FROM estu WHERE lcode=?";
			PreparedStatement ps=con.prepareStatement(sql);
			ps.setString(1, lcode);
			ResultSet rs=ps.executeQuery();
			while(rs.next()) {
				JSONObject obj=new JSONObject();
				obj.put("sname", rs.getString("sname"));
				obj.put("scode", rs.getString("scode"));
				obj.put("dept", rs.getString("dept"));
				obj.put("year", rs.getString("year"));
				obj.put("edate", sdf.format(rs.getDate("edate")));
				obj.put("grade", rs.getInt("grade"));
				jArray.add(obj);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	return jArray;}
	
	
public void delete(String lcode, String scode) {
		
		try {
			String sql="delete from enrollments where lcode=? and scode=?";
			PreparedStatement ps=con.prepareStatement(sql);
			ps.setString(1, lcode);
			ps.setString(2, scode);
			ps.execute();
			//강좌를 등록했으면 course에도 사람 한명이 추가되었음을 update해주어야 함.
			sql="update courses set persons=persons-1 where lcode=?";
			ps=con.prepareStatement(sql);
			ps.setString(1, lcode);
			ps.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
	
}
