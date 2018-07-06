package zabmtri.entity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ESalesman {
	public Integer salesmanid;
	public String lastname;
	public String firstname;
	public String jobtitle;
	public String businessphone;
	public String businessphoneext;
	public String businessphone2;
	public String businessphone2ext;
	public String homephone;
	public String fax;
	public String cellular;
	public String pager;
	public String email;
	public String notes;
	public boolean suspended;
	public String branchcode;
	public Integer transactionid;
	public Integer importedtransactionid;
	public Integer branchcodeid;
	public String salesmanname;

	public static List<ESalesman> readAll(Connection conn) {
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT salesman.* FROM salesman ");
			sql.append("ORDER BY salesman.salesmanname");

			PreparedStatement ps = conn.prepareStatement(sql.toString());

			ResultSet rs = ps.executeQuery();

			List<ESalesman> result = new ArrayList<ESalesman>();
			while (rs.next()) {
				result.add(ESalesman.read(rs));
			}

			return result;
		} catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}

	public static ESalesman read(ResultSet rs) throws SQLException {
		ESalesman entity = new ESalesman();
		entity.salesmanid = rs.getInt("salesmanid");
		entity.lastname = rs.getString("lastname");
		entity.firstname = rs.getString("firstname");
		entity.jobtitle = rs.getString("jobtitle");
		entity.businessphone = rs.getString("businessphone");
		entity.businessphoneext = rs.getString("businessphoneext");
		entity.businessphone2 = rs.getString("businessphone2");
		entity.businessphone2ext = rs.getString("businessphone2ext");
		entity.homephone = rs.getString("homephone");
		entity.fax = rs.getString("fax");
		entity.cellular = rs.getString("cellular");
		entity.pager = rs.getString("pager");
		entity.email = rs.getString("email");
		entity.notes = rs.getString("notes");
		entity.suspended = rs.getInt("suspended") == 1;
		entity.branchcode = rs.getString("branchcode");
		entity.transactionid = rs.getInt("transactionid");
		entity.importedtransactionid = rs.getInt("importedtransactionid");
		entity.branchcodeid = rs.getInt("branchcodeid");
		entity.salesmanname = rs.getString("salesmanname");

		return entity;
	}

	public static int write(Connection conn, ESalesman entity) {
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("insert into SALESMAN(FIRSTNAME, LASTNAME, SALESMANNAME, JOBTITLE, SUSPENDED) values(?, ?, ?, ?, ?)");

			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ps.setString(1, entity.firstname);
			ps.setString(2, entity.lastname);
			ps.setString(3, entity.salesmanname);
			ps.setString(4, entity.jobtitle);
			ps.setInt(5, entity.suspended ? 1 : 0);

			return ps.executeUpdate();
		} catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}
	
	public static boolean isExists(Connection conn, String salesmanname) {
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT salesman.salesmanname FROM salesman ");
			sql.append("WHERE salesman.salesmanname = ?");

			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ps.setString(1, salesmanname);

			ResultSet rs = ps.executeQuery();
			return rs.next();
		} catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}
}
