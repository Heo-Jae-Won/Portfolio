package model;

import org.json.simple.JSONArray;

import org.json.simple.JSONObject;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;


public class ProDAO {
	SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
	DecimalFormat df=new DecimalFormat("#,###원");
	Connection con=Database.CON;


	//특정교수가 강의하는 강의목록
	public JSONArray clist(String pcode) {
		JSONArray jArray=new JSONArray();
		try {

			String sql="select * from courses where instructor=?";
			PreparedStatement ps=con.prepareStatement(sql);
			ps.setString(1, pcode);
			ResultSet rs=ps.executeQuery();
			while(rs.next()) {
				JSONObject obj=new JSONObject();
				obj.put("lcode", rs.getString("lcode"));
				obj.put("lname", rs.getString("lname"));
				obj.put("hours", rs.getString("hours"));
				obj.put("room", rs.getString("room"));
				obj.put("capacity", rs.getInt("capacity"));
				obj.put("persons", rs.getInt("persons"));
				jArray.add(obj);

			}
		}catch(Exception e) {
			System.out.println("course list err..."+e.toString());
		}

		return jArray;}

	//특정 교수가 담당하는 학생목록
	public JSONArray slist(String pcode) {
		JSONArray jArray=new JSONArray();
		try {

			String sql="select * from students where advisor=?";
			PreparedStatement ps=con.prepareStatement(sql);
			ps.setString(1, pcode);
			ResultSet rs=ps.executeQuery();
			while(rs.next()) {
				JSONObject obj=new JSONObject();
				obj.put("scode", rs.getString("scode"));
				obj.put("sname", rs.getString("sname"));
				obj.put("dept", rs.getString("dept"));
				obj.put("year", rs.getString("year"));
				obj.put("birthday", sdf.format(rs.getDate("birthday")));
				obj.put("advisor", rs.getInt("advisor"));
				jArray.add(obj);

			}
		}catch(Exception e) {
			System.out.println("course list err..."+e.toString());
		}

		return jArray;}



	//교수목록
	public JSONObject list(SqlVO vo) {
		JSONObject object=new JSONObject();
		try {

			String sql="call list('professors',?,?,?,?,?,?)"; //professors 대신 select * from  professor
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
				obj.put("pcode", rs.getString("pcode"));
				obj.put("pname", rs.getString("pname"));
				obj.put("dept", rs.getString("dept"));
				obj.put("title", rs.getString("title"));
				obj.put("hiredate", sdf.format(rs.getDate("hiredate")));
				obj.put("salary", df.format(rs.getInt("salary")));
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




	public String getCode() {
		String code="";

		try {

			String sql="select max(pcode)+1 code from professors";
			PreparedStatement ps=con.prepareStatement(sql);
			ResultSet rs=ps.executeQuery();
			if(rs.next()) {
				code=rs.getString("code");
			}

		}catch(Exception e) {
			System.out.println("maxcode err...."+e.toString());
		}
		return code;}


	public void insert(ProVO vo) {

		try {
			String sql="insert into professors(pcode,pname,dept,title,salary,hiredate) values(?,?,?,?,?,?)";
			PreparedStatement ps=con.prepareStatement(sql);
			ps.setString(1, vo.getPcode());
			ps.setString(2, vo.getPname());
			ps.setString(3, vo.getDept());
			ps.setString(4, vo.getTitle());
			ps.setInt(5, vo.getSalary());
			ps.setString(6, vo.getHiredate());
			ps.execute();

		} catch (Exception e) {
			System.out.println("insert err...."+e.toString());
		}


	}

	public ProVO read(String pcode) {
		ProVO provo=new ProVO();
		try {
			String sql="select * from professors where pcode=?";
			PreparedStatement ps=con.prepareStatement(sql);
			ps.setString(1, pcode);
			ResultSet rs=ps.executeQuery();
			if(rs.next()) {
				provo.setDept(rs.getString("dept"));
				provo.setHiredate(sdf.format(rs.getDate("hiredate")));
				provo.setPcode(pcode);
				provo.setPname(rs.getString("pname"));
				provo.setSalary(rs.getInt("salary"));
				provo.setTitle(rs.getString("title"));

			}

		}catch(Exception e) {
			System.out.println("read err..."+e.toString());
		}

		return provo;}

	public void update(ProVO vo) {

		try {
			String sql="update professors set pname=?, dept=?, hiredate=?, title=?, salary=? where pcode=?";
			PreparedStatement ps=con.prepareStatement(sql);

			ps.setString(1, vo.getPname());
			ps.setString(2, vo.getDept());
			ps.setString(3, vo.getHiredate());
			ps.setString(4, vo.getTitle());
			ps.setInt(5, vo.getSalary());
			ps.setString(6, vo.getPcode());
			ps.execute();

		} catch (Exception e) {
			System.out.println("insert err...."+e.toString());
		}


	}

	public JSONObject count(String pcode) {
		JSONObject jObject=new JSONObject();

		try {
			String sql="select count(*) cnt from procount where instructor=?";
			PreparedStatement ps=con.prepareStatement(sql);
			ps.setString(1, pcode);
			ResultSet rs=ps.executeQuery();
			if(rs.next()){
				int countInstructor=rs.getInt("cnt");
				jObject.put("countInstructor", countInstructor);
				sql="select count(*) cnt from procount where advisor=?";
				ps=con.prepareStatement(sql);
				ps.setString(1, pcode);
				rs=ps.executeQuery();
				if(rs.next()) {
					int countAdvisor=rs.getInt("cnt");
					jObject.put("countAdvisor", countAdvisor);
				

				}
				
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		return jObject;}

	public void delete(String pcode) {

		try {
			String sql="delete from professors where pcode=?";
			PreparedStatement ps=con.prepareStatement(sql);
			ps.setString(1, pcode);
			ps.execute();

		}catch(Exception e) {
			System.out.println("read err..."+e.toString());
		}





	}
}




