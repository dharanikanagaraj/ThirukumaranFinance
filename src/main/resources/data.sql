insert into line_details(id, line_id, line_name, created_on, updated_on) values (101, 'Ln01', 'Line 1', '2023-01-10', '2023-05-10');
insert into line_details(id, line_id, line_name, created_on, updated_on) values (202, 'Ln02', 'Line 2', '2001-02-10', '2012-05-23');
INSERT INTO user_loan_details(loan_id, loan_no,user_no, name, address, phone_no, order_no, current_loan_date, loan_amount, seetu_amount, commission_amount, interest, appx_loan_closed_date, loan_closed_date, excess_amount, line_id, balance, is_loan_active, daily_update) VALUES
(1, 'Lon01', 'US01', 'userone','123,thangavel','1234567898', '1','2023-05-30','5000','2','500','10','2023-09-07',null,'0','Ln01','5000',true,false);
INSERT INTO user_loan_details(loan_id, loan_no,user_no, name, address, phone_no, order_no, current_loan_date, loan_amount, seetu_amount, commission_amount, interest, appx_loan_closed_date, loan_closed_date, excess_amount, line_id, balance, is_loan_active, daily_update) VALUES
(2, 'Lon02', 'US02', 'usertwo','123,thangavel','1234567898', '1','2023-05-30','5000','2','500','10','2023-09-07',null,'0','Ln01','5000',true,false);
INSERT INTO user_loan_details(loan_id, loan_no,user_no, name, address, phone_no, order_no, current_loan_date, loan_amount, seetu_amount, commission_amount, interest, appx_loan_closed_date, loan_closed_date, excess_amount, line_id, balance, is_loan_active, daily_update) VALUES
(3, 'Lon03', 'US03', 'userthree','123,thangavel','1234567898', '1','2023-05-30','5000','2','500','10','2023-09-07',null,'0','Ln01','5000',true,false);
INSERT INTO user_loan_details(loan_id, loan_no,user_no, name, address, phone_no, order_no, current_loan_date, loan_amount, seetu_amount, commission_amount, interest, appx_loan_closed_date, loan_closed_date, excess_amount, line_id, balance, is_loan_active, daily_update) VALUES
(4, 'Lon01', 'US01', 'userlinelinetwo','123,thangavel','1234567898', '1','2023-05-30','5000','2','500','10','2023-09-07',null,'0','Ln02','5000',true,false);
INSERT INTO user_loan_details(loan_id, loan_no,user_no, name, address, phone_no, order_no, current_loan_date, loan_amount, seetu_amount, commission_amount, interest, appx_loan_closed_date, loan_closed_date, excess_amount, line_id, balance, is_loan_active, daily_update) VALUES
(5, 'Lon02', 'US02', 'usertwolinetwo','123,thangavel','1234567898', '1','2023-05-30','5000','2','500','10','2023-09-07',null,'0','Ln02','5000',true,false);
INSERT INTO user_loan_details(loan_id, loan_no,user_no, name, address, phone_no, order_no, current_loan_date, loan_amount, seetu_amount, commission_amount, interest, appx_loan_closed_date, loan_closed_date, excess_amount, line_id, balance, is_loan_active, daily_update) VALUES
(6, 'Lon03', 'US03', 'userthreelinetwo','123,thangavel','1234567898', '1','2023-05-30','5000','2','500','10','2023-09-07',null,'0','Ln02','5000',true,false);
--insert into daily_amount_collection(id, amount_paid, date, line_id, created_on, updated_on, loan_id) values (1, '50', '2023-05-20', 'Ln01', null, null, 1);
--insert into daily_amount_collection(id, amount_paid, date, line_id, created_on, updated_on, loan_id) values (2, '50', '2023-05-21', 'Ln01', null, null, 1);
--insert into daily_amount_collection(id, amount_paid, date, line_id, created_on, updated_on, loan_id) values (3, '50', '2023-05-22', 'Ln01', null, null, 1);
--insert into daily_amount_collection(id, amount_paid, date, line_id, created_on, updated_on, loan_id) values (4, '50', '2023-05-23', 'Ln01', null, null, 1);
--insert into daily_amount_collection(id, amount_paid, date, line_id, created_on, updated_on, loan_id) values (5, '50', '2023-05-20', 'Ln02', null, null, 4);
--insert into daily_amount_collection(id, amount_paid, date, line_id, created_on, updated_on, loan_id) values (6, '50', '2023-05-21', 'Ln02', null, null, 4);
--insert into daily_amount_collection(id, amount_paid, date, line_id, created_on, updated_on, loan_id) values (7, '50', '2023-05-22', 'Ln02', null, null, 4);
--insert into daily_amount_collection(id, amount_paid, date, line_id, created_on, updated_on, loan_id) values (8, '50', '2023-05-23', 'Ln02', null, null, 4);
insert into date_close(id, date, line_id, created_on) values (1,'2023-05-23', 'Ln01', '2023-05-23');
insert into date_close(id, date, line_id, created_on) values (2,'2023-05-24', 'Ln01', '2024-05-24');
insert into date_close(id, date, line_id, created_on) values (3,'2023-05-25', 'Ln01', '2023-05-25');
insert into date_close(id, date, line_id, created_on) values (4,'2023-05-23', 'Ln02', '2023-05-23');
insert into date_close(id, date, line_id, created_on) values (5,'2023-05-24', 'Ln02', '2023-05-24');
insert into date_close(id, date, line_id, created_on) values (6,'2023-05-25', 'Ln02', '2023-05-25');
--insert into line_member_details(id, linMem_id, phone_no, address, password, created_on, update_on, role) values (1,'Lm01', 'dharani', '13662', 'address', 'password' '2023-05-25', '2023-05-25');
--insert into line_member_details(id, linMem_id, phone_no, address, password, created_on, update_on, role) values (1,'Lm02', 'indira', '13662', 'address', 'password' '2023-05-25', '2023-05-25');