Select
--a.BILLID,
NULL PINO, --a.INVOICENO PINO,
a.ITEMNO, a.ITEMOVDESC, SUM(a.QUANTITY) QUANTITY,
a.ITEMDISCPC, a.BRUTOUNITPRICE, a.UNITPRICE,
a.ITEMUNIT, a.UNITRATIO, a.ITEMTYPE,
a.UNIT1, a.UNIT2, a.UNIT3, a.RATIO2, a.RATIO3, a.NOTES,
a.INVENTORYGLACCNT, a.COGSGLACCNT, a.SALESGLACCNT, a.UNBILLEDACCOUNT,
a.TAXCODES, --a.TAXABLEAMOUNT1, a.TAXABLEAMOUNT2,
a.ITEMRESERVED1, a.ITEMRESERVED2, a.ITEMRESERVED3, a.ITEMRESERVED4,
a.ITEMRESERVED5, a.ITEMRESERVED6, a.ITEMRESERVED7, a.ITEMRESERVED8,
a.ITEMRESERVED9, a.ITEMRESERVED10,
NULL PONO, --a.PONO,
a.DEPTID, a.DEPTNO, a.DEPTNAME,
a.PROJECTID, a.PROJECTNO, a.PROJECTNAME,
a.WAREHOUSEID, a.NAME WPIName
From TEMPLATE_APITMDET(:APInvoiceID) a
Where 1=1
Group by
--a.BILLID,
--a.INVOICENO,
a.ITEMNO, a.ITEMOVDESC,
a.ITEMDISCPC, a.BRUTOUNITPRICE, a.UNITPRICE,
a.ITEMUNIT, a.UNITRATIO, a.ITEMTYPE,
a.UNIT1, a.UNIT2, a.UNIT3, a.RATIO2, a.RATIO3, a.NOTES,
a.INVENTORYGLACCNT, a.COGSGLACCNT, a.SALESGLACCNT, a.UNBILLEDACCOUNT,
a.TAXCODES, --a.TAXABLEAMOUNT1, a.TAXABLEAMOUNT2,
a.ITEMRESERVED1, a.ITEMRESERVED2, a.ITEMRESERVED3, a.ITEMRESERVED4,
a.ITEMRESERVED5, a.ITEMRESERVED6, a.ITEMRESERVED7, a.ITEMRESERVED8,
a.ITEMRESERVED9, a.ITEMRESERVED10,
--a.PONO,
a.DEPTID, a.DEPTNO, a.DEPTNAME,
a.PROJECTID, a.PROJECTNO, a.PROJECTNAME,
a.WAREHOUSEID, a.NAME
Order by a.ITEMNO