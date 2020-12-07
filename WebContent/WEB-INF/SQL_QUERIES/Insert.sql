-- 04122020
update ekyc.tbl_pdf_orgins set coordinates = '{"not a politically exposed person": "70,390","politically exposed person":"70,405"}' where id = 87;
delete FROM ekyc.tbl_pdf_orgins where id in (56,57);
insert into ekyc.tbl_pdf_orgins (column_name,coordinates,is_default,page_number,is_value_reduced) values
('DEFAULT_ANNUAL_INCOME','64,498',1,3,1);
insert into ekyc.tbl_pdf_orgins (column_name,coordinates,is_default,page_number,is_value_reduced) values
('micr_code','370,694',0,8,0),
('branch_address','108,678',0,8,0),
('aadhar_no','105,659',0,2,0);
insert into ekyc.tbl_pdf_orgins (column_name,coordinates,is_default,page_number,is_value_reduced) values
('nse_eq','{"nse_eq":"160,560"}',1,8,0),
('bse_eq','{"bse_eq":"160,526"}',1,8,0),
('nse_fo','{"nse_fo":"290,560"}',1,8,0),
('bse_fo','{"bse_fo":"290,526"}',1,8,0),
('cds','{"cds":"410,560"}',1,8,0),
('bcd','{"bcd":"410,526"}',1,8,0),
('mcx','{"mcx":"530,490"}',1,8,0),
('icex','{"icex":"530,450"}',1,8,0),
('nse_com','{"nse_com":"530,560"}',1,8,0),
('bse_com','{"bse_com":"530,526"}',1,8,0);
delete from ekyc.tbl_pdf_orgins where id in (44,45,46,47,48,49,50,51,52,53) and page_number = 8; -- check 10 rows deleted
update ekyc.tbl_pdf_orgins set  column_name = 'flatNo_street_city' where id = 21 and  page_number = 16; -- check 1 rows deleted
update ekyc.tbl_pdf_orgins set  column_name = 'district_state_country' where id = 22 and  page_number = 16; -- check 1 rows deleted
update ekyc.tbl_pdf_orgins set coordinates = '105,737' where id = 94 and page_number = 2;
update ekyc.tbl_pdf_orgins set coordinates = '105,723' where id = 95 and page_number = 2;
update ekyc.tbl_pdf_orgins set coordinates = '104,539' where id = 98 and page_number = 2;
update ekyc.tbl_pdf_orgins set coordinates = '104,527' where id = 99  and page_number = 2;
update ekyc.tbl_pdf_orgins set coordinates = '104,515' where id = 100 and page_number = 2;
update ekyc.tbl_pdf_orgins set coordinates = '104,503' where id = 101  and page_number = 2;
update ekyc.tbl_pdf_orgins set coordinates = '104,492' where id = 102 and page_number = 2;
update ekyc.tbl_pdf_orgins set coordinates = '520,504' where id = 103 and page_number = 2;
update ekyc.tbl_pdf_orgins set coordinates = '104,295' where id = 114 and page_number = 2;
update ekyc.tbl_pdf_orgins set coordinates = '104,320' where id = 124 and page_number = 2;
update ekyc.tbl_pdf_orgins set coordinates = '62,751' where id = 63  and page_number = 6;
update ekyc.tbl_pdf_orgins set coordinates = '75,21' where id = 39 and page_number = 9;
