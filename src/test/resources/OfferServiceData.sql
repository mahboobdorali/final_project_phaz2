/*insert into order_customer (id, current_situation, city, plaque, street, date_and_time_of_work, job_description,
                            proposed_price, customer_id, expert_id, under_service_id)
values (1,'WAITING_FOR_EXPERT_ADVICE','ben','we23','janbazan','2020.12.1','najar',12000,1,1,1);*/
insert into expert (id, amount, date_and_time_of_registration, email_address, family, name, password, role,
                    approval_status, average_score)
values (1,30000,'2023.10.1','zahra@gmail.com','nasr','roya','god45AX@','EXPERT','NEW',23);
insert into main_task (id, name)
values (1, 'home');
insert into under_service (id, base_price, brief_explanation, name_sub_service, main_task_id)
values (1, 10000, 'home', 'cleanHome', 1);

/*insert into expert_under_service_list (expert_id, under_service_list_id)
values (1,1);*/
/*insert into customer (id, amount, date_and_time_of_registration, email_address, family, name, password, role)
values (1,3000,'2022-12-14','mona@gmail.com','rasoli','mona','$!ER56jm','CUSTOMER');*/