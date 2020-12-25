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
--12/09/2020
update ekyc.tbl_pdf_orgins set column_name  = 'reduced_city' where id = 92;
update ekyc.tbl_pdf_orgins set coordinates = '485,595' where id = 117;
update ekyc.tbl_pdf_orgins set coordinates = '156,731' where id = 67;
update ekyc.tbl_pdf_orgins set coordinates = '332,720' where id = 68;
update ekyc.tbl_pdf_orgins set coordinates = '81,720' where id = 69;
insert into ekyc.tbl_pdf_orgins (column_name,coordinates,is_default,page_number,is_value_reduced) values
('nse_mf','{"nse_mf":"252,518"}',1,14,0),
('bse_mf','{"bse_mf":"467,518"}',1,14,0);
delete from ekyc.tbl_pdf_orgins where id in (23,24);
--12/12/2020
insert into ekyc.tbl_pdf_orgins (column_name,coordinates,is_default,page_number,is_value_reduced) values
('TICK','560,625',1,8,0);
--16/12/2020
insert into ekyc.tbl_pdf_orgins (column_name,coordinates,is_default,page_number,is_value_reduced) values
('DEFAULT_1','118,295',1,2,0);
update ekyc.tbl_pdf_orgins set column_name = 'DEFAULT_9' where id = 114;
update ekyc.tbl_pdf_orgins set column_name = 'full_address' where id = 37;
update ekyc.tbl_pdf_orgins set coordinates = '474,594' where  id = 117;
--18/12/2020
insert into ekyc.tbl_pdf_orgins (column_name,coordinates,is_default,page_number,is_value_reduced) values
('ref_code','105,92',0,8,0),
('ref_name','57,75',0,8,0);
--19/12/2020
update ekyc.tbl_pdf_orgins set column_name = 'applicant_pan_card',is_value_reduced = 0 where id = 20 and page_number = 16;
update ekyc.tbl_pdf_orgins set coordinates = '66,228'  where id = 27 and page_number = 12;
update ekyc.tbl_pdf_orgins set coordinates = '240,213' where id = 28 and page_number = 12;
update ekyc.tbl_pdf_orgins set coordinates = '66,214' where id = 29 and page_number = 12;
update ekyc.tbl_pdf_orgins set coordinates = '104,403'  where id = 125 and page_number = 2;
update ekyc.tbl_pdf_orgins set coordinates = '345,62'  where id = 90 and page_number = 2;
update ekyc.tbl_pdf_orgins set coordinates = '442,91'  where id = 91 and page_number = 2;
update ekyc.tbl_pdf_orgins set coordinates = '78,333'  where id = 78 and page_number = 3;
update ekyc.tbl_pdf_orgins set coordinates = '78,320'  where id = 79 and page_number = 3;
update ekyc.tbl_pdf_orgins set coordinates = '217,320'  where id = 80 and page_number = 3;
update ekyc.tbl_pdf_orgins set coordinates = '291,290'  where id = 61 and page_number = 7;
update ekyc.tbl_pdf_orgins set coordinates = '458,333'  where id = 119 and page_number = 2;
--24/12/2020
CREATE TABLE `ekyc`.`tbl_payment` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `amount` int(11) DEFAULT NULL,
  `currency` varchar(45) DEFAULT NULL,
  `receipt` varchar(45) DEFAULT NULL,
  `razorpay_signature` varchar(500) DEFAULT NULL,
  `razorpay_order_id` varchar(45) DEFAULT NULL,
  `razorpay_payment_id` varchar(45) DEFAULT NULL,
  `amount_paid` int(11) DEFAULT NULL,
  `created_at` varchar(45) DEFAULT NULL,
  `amount_due` int(11) DEFAULT NULL,
  `payment_id` varchar(45) DEFAULT NULL,
  `entity` varchar(45) DEFAULT NULL,
  `offer_id` varchar(45) DEFAULT NULL,
  `status` varchar(45) DEFAULT NULL,
  `attempts` int(11) DEFAULT NULL,
  `notes` varchar(45) DEFAULT NULL,
  `refer_id` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=latin1;
