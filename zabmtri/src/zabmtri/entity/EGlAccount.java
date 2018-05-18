package zabmtri.entity;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EGlAccount {
	public String glaccount;
	public Integer currencyid;
	public String currencyname;
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
	public BigDecimal balance;

	public static List<EGlAccount> readAll(Connection conn, LocalDate asOf) {
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT glaccnt.*, get_crdr.balance, currency.currencyname FROM glaccnt ");
			sql.append("LEFT JOIN currency ON glaccnt.currencyid = currency.currencyid ");
			sql.append("LEFT JOIN get_saldoaccount (glaccnt.glaccount, ? , current_date) ON glaccnt.glaccount = get_saldoaccount.glaccount ");
			sql.append("LEFT JOIN get_crdr (glaccnt.glaccount, get_saldoaccount.balance, glaccnt.ACCOUNTTYPE) ON glaccnt.glaccount = get_crdr.glaccount ");
			sql.append("WHERE glaccnt.accounttype is not null ");
			sql.append("ORDER BY glaccnt.glaccount ");

			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ps.setDate(1, Date.valueOf(asOf));

			ResultSet rs = ps.executeQuery();

			List<EGlAccount> result = new ArrayList<EGlAccount>();
			while (rs.next()) {
				result.add(EGlAccount.read(rs));
			}

			return result;
		} catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}

	public static EGlAccount read(ResultSet rs) throws SQLException {
		EGlAccount entity = new EGlAccount();
		entity.glaccount = rs.getString("glaccount");
		entity.currencyid = rs.getInt("currencyid");
		entity.currencyname = rs.getString("currencyname");
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
		entity.balance = rs.getBigDecimal("balance");

		return entity;
	}
}
