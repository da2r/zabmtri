package zabmtri.importer;

import java.io.File;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import zabmtri.AppData;
import zabmtri.Util;

public class ItemSnImporter {

	private String current = "";
	private Integer sntype = 1;
	private List<Integer> historyId = new ArrayList<Integer>();
	private List<BigDecimal> availableQuantity = new ArrayList<BigDecimal>();
	private List<BigDecimal> usedQuantity = new ArrayList<BigDecimal>();
	private int idx = 0;

	public void execute() {
		try {
			File file = new File(Util.itemSnOutputFile());
			CSVParser csv = CSVParser.parse(file, Charset.defaultCharset(), CSVFormat.DEFAULT.withFirstRecordAsHeader());
			for (CSVRecord rec : csv) {
				String itemno = rec.get(0);
				if (itemno.equals(current) == false) {
					setImportContext(itemno);
				}
				
				String serialnumber = rec.get(1);
				Date expireddate = Util.parseDate(rec.get(2));
				BigDecimal quantity = Util.parseNumber(rec.get(3));
				try {
					doImport(serialnumber, expireddate, quantity);
				} catch (Throwable t) {
					Util.printOutput("Gagal memasukan nomor seri " + serialnumber + " ke barang " + itemno);
					Util.printOutput(Util.getStackTrace(t));
					
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void setImportContext(String itemno) {
		try {
			Util.printOutput(String.format("Memasukan Nomor Seri Barang \"%s\"..", itemno));
			current = itemno;
			
			sntype = 1;
			historyId.clear();
			availableQuantity.clear();
			usedQuantity.clear();
			idx = 0;

			PreparedStatement ps = AppData.target.prepareStatement("select SERIALNUMBERTYPE from ITEM where ITEMNO = ?");
			ps.setString(1, itemno);
			ResultSet rs = ps.executeQuery();
			if (rs.next() == false) {
				throw new RuntimeException("Tidak dapat menemukan Barang : " + itemno);
			}
			sntype = rs.getInt(1);
			
			String sql = "select ITADJDET.ITEMHISTID, ITADJDET.QTYDIFFERENCE from ITEMOBLIST left join ITADJDET on ITADJDET.ITEMADJID = ITEMOBLIST.ITEMADJID and ITADJDET.ITEMNO = ITEMOBLIST.ITEMNO where ITEMOBLIST.ITEMNO = ? ";
			ps = AppData.target.prepareStatement(sql);
			ps.setString(1, itemno);
			rs = ps.executeQuery();
			while (rs.next()) {
				historyId.add(rs.getInt(1));
				availableQuantity.add(rs.getBigDecimal(2));
				usedQuantity.add(BigDecimal.ZERO);
			}
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	private void doImport(String serialnumber, Date expireddate, BigDecimal quantity) throws SQLException {
		if (isSerialNumberExists(serialnumber)) {
			Util.printOutput(String.format(" - Nomor Seri \"%s\" sudah ada, data diabaikan", serialnumber));
			return;
		}
		
		int snid = insertSerialNumber(serialnumber, expireddate);
		insertSnHistory(snid, quantity);
	}

	private boolean isSerialNumberExists(String serialnumber) throws SQLException {
		PreparedStatement ps = AppData.target.prepareStatement("select 1 from SERIALNUMBERS where SERIALNUMBER = ? and ITEMNO = ?");
		ps.setString(1, serialnumber);
		ps.setString(2, current);

		ResultSet rs = ps.executeQuery();
		return rs.next();
	}
	
	public int insertSerialNumber(String serialnumber, Date expireddate) throws SQLException {
		PreparedStatement ps = AppData.target.prepareStatement("select SNID from GET_SNID");
		ResultSet rs = ps.executeQuery();
		if (rs.next() == false) {
			throw new RuntimeException("Failed on GET_SNID");
		}
		int snid = rs.getInt(1);
		
		ps = AppData.target.prepareStatement("insert into serialnumbers(snid, serialnumber, itemno, expireddate, sntype) values (?, ?, ?, ?, ?)");
		ps.setInt(1, snid);
		ps.setString(2, serialnumber);
		ps.setString(3, current);
		ps.setDate(4, expireddate);
		ps.setInt(5, sntype);
		ps.executeUpdate();
		
		return snid;
	}

	private void insertSnHistory(int snid, BigDecimal quantity) throws SQLException {
		Integer itemhistid = historyId.get(idx);
		
		BigDecimal available = availableQuantity.get(idx);
		available = available.subtract(usedQuantity.get(idx));
		
		if (available.compareTo(quantity) >= 0) {
			doInsertSnHistory(snid, itemhistid, quantity);
		} else {
			doInsertSnHistory(snid, itemhistid, available);
			
			BigDecimal left = quantity.subtract(available);
			idx++;
			insertSnHistory(snid, left);
		}
	}
	
	public void doInsertSnHistory(int snid, int itemhistid, BigDecimal quantity) throws SQLException {
		int snsign = quantity.compareTo(BigDecimal.ZERO);
		if (snsign == 0) {
			return;
		}
		
		PreparedStatement ps = AppData.target.prepareStatement("insert into snhistory(snid, itemhistid, quantity, snsign) values (?, ?, ?, ?)");
		ps.setInt(1, snid);
		ps.setInt(2, itemhistid);
		ps.setBigDecimal(3, quantity);
		ps.setInt(4, snsign);
		ps.executeUpdate();
	}
	
}
