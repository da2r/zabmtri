select V.PaymentAmount, V.PPh23Rate, V.PPh23Amount, V.PPH23Number,
A.*, M.FiscalRate, P.* from ARINVPMT V 
inner join ARPMT A on A.PAYMENTID=V.PAYMENTID
inner join PersonData P on P.ID=A.BILLTOID
inner join ARINV M on M.ARINVOICEID=V.ARINVOICEID
where V.INVPMTID=:INVCHQID