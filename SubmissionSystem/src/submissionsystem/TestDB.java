package submissionsystem;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ArrayList;


/**
 *
 * @author abdullah.talayhan-ug
 */
public class TestDB {
	
	public static void main(String[] args) throws SQLException {
		
		DBSystem.initializeDB("138.68.93.173:3306", "apo_callide", "apo", "duracell");
		ResultSet rs = DBSystem.executeQue("select name from joe");
                if(rs.next())
                    System.out.println(rs.getInt(1));
                /*ArrayList<String> courses = ServerSys.getInsCourses("andrew@mail.com");
                
                for(String s : courses) {
                    System.out.println(s);
                }
                
                ResultSet rs = DBSystem.executeQue("select * from course where code='phy101'");
                if(!rs.next() ) {
                    System.out.println("joıe");
                }*/
                
                //rs = DBSystem.executeQue("select name, duedate, subdate, grade, assignment.path, belongs.email from assignment join belongs on assignment.path = belongs.path where belongs.email='john@mail.com'");
                
                /*
                int columncount = rs.getMetaData().getColumnCount();
                while(rs.next())
                {
                    ArrayList singleRow = new ArrayList();
                    for(int i=1;i<=columncount;i++)
                    {
                        singleRow.add(rs.getObject(i));
                    }
                    
                    System.out.println(singleRow);
                }*/
                
                /*String sqlCode = "select code from course where c_key='" + "kmeskuyy" +"';";
                rs = DBSystem.executeQue(sqlCode);
                if(rs.next()) {
                    String courseCode = rs.getString(1);
                    System.out.println(courseCode);
                }*/
                
	}
}
