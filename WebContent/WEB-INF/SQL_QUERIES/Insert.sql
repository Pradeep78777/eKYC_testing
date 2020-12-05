-- 04122020
update ekyc.tbl_pdf_orgins set coordinates = '{"not a politically exposed person": "70,390","politically exposed person":"70,405"}' where id = 87;
delete FROM ekyc.tbl_pdf_orgins where id in (56,57);
insert into ekyc.tbl_pdf_orgins (column_name,coordinates,is_default,page_number,is_value_reduced) values
('DEFAULT_AMOUNTVALUE','104,386',1,3,0);