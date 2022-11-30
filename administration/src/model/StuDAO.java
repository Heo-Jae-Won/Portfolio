package model;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class StuDAO {
	SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
	DecimalFormat df=new DecimalFormat("#,###원");
	Connection con=Database.CON;

	public JSONObject list(SqlVO vo) {
		JSONObject object=new JSONObject();
		try {

			String sql="call list('stu',?,?,?,?,?,?)"; //professors 대신 select * from  professor
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
				obj.put("scode", rs.getString("scode"));
				obj.put("sname", rs.getString("sname"));
				obj.put("dept", rs.getString("dept"));
				obj.put("year", rs.getString("year"));
				obj.put("birthday", sdf.format(rs.getDate("birthday")));
				obj.put("advisor", rs.getString("advisor"));
				obj.put("pname", rs.getString("pname"));
				obj.put("pdept", rs.getString("pdept"));
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
			System.out.println("student list err.."+e.toString());
		}
		return object;}

	public String getCode() {
		String code="";

		try {

			String sql="select max(scode)+1 code from students";
			PreparedStatement ps=con.prepareStatement(sql);
			ResultSet rs=ps.executeQuery();
			if(rs.next()) {
				code=rs.getString("code");
			}

		}catch(Exception e) {
			System.out.println("maxcode err...."+e.toString());
		}
		return code;}

	public void insert(StuVO vo) {

		try {
			String sql="insert into students(scode,sname,dept,year,advisor,birthday) values(?,?,?,?,?,?)";
			PreparedStatement ps=con.prepareStatement(sql);
			ps.setString(1, vo.getScode());
			ps.setString(2, vo.getSname());
			ps.setString(3, vo.getDept());
			ps.setString(4, vo.getYear());
			ps.setString(5, vo.getAdvisor());
			ps.setString(6, vo.getBirthday());
			ps.execute();

		} catch (Exception e) {
			System.out.println("insert err...."+e.toString());
		}


	}	

	public StuVO read(String scode) {
		StuVO provo=new StuVO();
		try {
			String sql="select * from stu where scode=?";
			PreparedStatement ps=con.prepareStatement(sql);
			ps.setString(1, scode);
			ResultSet rs=ps.executeQuery();
			if(rs.next()) {
				provo.setDept(rs.getString("dept"));
				provo.setSname(rs.getString("sname"));
				provo.setScode(rs.getString("scode"));
				provo.setYear(rs.getString("year"));
				provo.setAdvisor(rs.getString("advisor"));
				provo.setBirthday(rs.getString("birthday"));
				provo.setPname(rs.getString("pname"));
			
			}

		}catch(Exception e) {
			System.out.println("read err..."+e.toString());
		}

		return provo;}
	
	public void update(StuVO vo) {

		try {
			String sql="update students set dept=?, sname=?, year=?, advisor=?, birthday=? where scode=?";
			PreparedStatement ps=con.prepareStatement(sql);

			ps.setString(6, vo.getScode());
			ps.setString(2, vo.getSname());
			ps.setString(1, vo.getDept());
			ps.setString(3, vo.getYear());
			ps.setString(4, vo.getAdvisor());
			ps.setString(5, vo.getBirthday());
			ps.execute();

		} catch (Exception e) {
			System.out.println("update err...."+e.toString());
		}


	}

}
