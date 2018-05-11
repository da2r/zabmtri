package zabmtri.entity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EGlAccount {
	public String glaccount;
	public Integer currencyid;
	public String accountname;
	public Integer accounttype;
	public Integer subaccount;
	public String parentaccount;
	public Integer suspended;
	public String memo;
	public String firstparentaccount;
	public Integer indentlevel;
	public Integer isfiscal;
	public Integer isalloctoprod;
	public Integer transactionid;
	public Integer importedtransactionid;
	public Integer branchcodeid;
	public Integer lft;
	public Integer rgt;
	public Integer isroot;
	
	public static List<EGlAccount> readAll(ResultSet rs) throws SQLException {
		List<EGlAccount> result = new ArrayList<EGlAccount>();
		while (rs.next()) {
			result.add(EGlAccount.read(rs));
		}
		
		return result;
	}
	
	public static EGlAccount read(ResultSet rs) throws SQLException {
		EGlAccount entity = new EGlAccount();
		entity.glaccount = rs.getString("glaccount");
		entity.currencyid = rs.getInt("currencyid");
		entity.accountname = rs.getString("accountname");
		entity.accounttype = rs.getInt("accounttype");
		entity.subaccount = rs.getInt("subaccount");
		entity.parentaccount = rs.getString("parentaccount");
		entity.suspended = rs.getInt("suspended");
		entity.memo = rs.getString("memo");
		entity.firstparentaccount = rs.getString("firstparentaccount");
		entity.indentlevel = rs.getInt("indentlevel");
		entity.isfiscal = rs.getInt("isfiscal");
		entity.isalloctoprod = rs.getInt("isalloctoprod");
		entity.transactionid = rs.getInt("transactionid");
		entity.importedtransactionid = rs.getInt("importedtransactionid");
		entity.branchcodeid = rs.getInt("branchcodeid");
		entity.lft = rs.getInt("lft");
		entity.rgt = rs.getInt("rgt");
		entity.isroot = rs.getInt("isroot");
		
		return entity;
	}
}
