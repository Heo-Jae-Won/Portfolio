package model;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class CouDAO {


	public JSONObject list(SqlVO vo) {
		JSONObject object=new JSONObject();
		try {

			String sql="call list('cou',?,?,?,?,?,?)"; //professors 대신 select * from  professor
			CallableStatement cs=Database.CON.prepareCall(sql);
			cs.setString(1, vo.getKey());
			cs.setString(2, vo.getWord());
			cs.setString(3, vo.getOrder());
			cs.setString(4, vo.getDesc());
			cs.setInt(5, vo.getPage());
			cs.setInt(6, vo.getPer());
			cs.execute();

			ResultSet rs=cs.getResultSet(); //우리는 call procedure에 sql구문을 2개 넣어서 resultset이 2개가 나옴. while문이 필요함.
			JSONArray jArray=new JSONArray();
			while(rs.next()) {
				JSONObject obj=new JSONObject();
				obj.put("lcode", rs.getString("lcode"));
				obj.put("lname", rs.getString("lname"));
				obj.put("pname", rs.getString("pname"));
				obj.put("persons", rs.getInt("persons"));
				obj.put("capacity", rs.getInt("capacity"));
				obj.put("hours", rs.getString("hours"));
				obj.put("instructor", rs.getString("instructor"));
				obj.put("dept", rs.getString("dept"));
				obj.put("room", rs.getString("room"));
				jArray.add(obj);
			}
			object.put("array", jArray);

			cs.getMoreResults();
			rs=cs.getResultSet();
			int total=0;
			if(rs.next()) total=rs.getInt("total"); //DB에 count값을 total로 받아서 total을 해준것.
			object.put("total", total);

			int last=total%vo.getPer()==0 ? total/vo.getPer() : (total/vo.getPer())+1;

			object.put("last", last);
		}catch(Exception e) {
			System.out.println("list err.."+e.toString());
		}
		return object;}

	public CouVO read(String lcode) {
		CouVO couvo=new CouVO();
		try {
			String sql="select * from cou where lcode=?";
			PreparedStatement ps=Database.CON.prepareStatement(sql);
			ps.setString(1, lcode);
			ResultSet rs=ps.executeQuery();
			if(rs.next()) {
				couvo.setDept(rs.getString("dept"));
				couvo.setLname(rs.getString("lname"));
				couvo.setLcode(lcode);
				couvo.setPname(rs.getString("pname"));
				couvo.setRoom(rs.getString("room"));
				couvo.setCapacity(rs.getInt("capacity"));
				couvo.setPersons(rs.getInt("persons"));
				couvo.setHours(rs.getInt("hours"));
				couvo.setInstructor(rs.getString("instructor"));

			}

		}catch(Exception e) {
			System.out.println("read err..."+e.toString());
		}

		return couvo;}


	public void insert(CouVO vo) {

		try {
			String sql="insert into courses(lcode,lname,hours,room,instructor,capacity) values(?,?,?,?,?,?)";
			PreparedStatement ps=Database.CON.prepareStatement(sql);
			ps.setString(1, vo.getLcode());
			ps.setString(2, vo.getLname());
			ps.setInt(3, vo.getHours());
			ps.setString(4, vo.getRoom());
			ps.setString(5, vo.getInstructor());
			ps.setInt(6, vo.getCapacity());
			ps.execute();

		} catch (Exception e) {
			System.out.println("insert err...."+e.toString());
		}


	}


	public String getCode() {
		String code="";

		try {

			String sql=" select concat('N',max(substring(lcode,2,3))) code from courses";
			PreparedStatement ps=Database.CON.prepareStatement(sql);
			ResultSet rs=ps.executeQuery();
			if(rs.next()) {
				
				code=rs.getString("code");
			}

		}catch(Exception e) {
			System.out.println("maxcode err...."+e.toString());
		}
		return code;}


}
