insert into order_customer (id, current_situation, city, plaque, street, date_and_time_of_work, job_description,
                            proposed_price, customer_id, expert_id, under_service_id)
values (1,'WAITING_FOR_EXPERT_ADVICE','ben','we23','janbazan','2020.12.1','washing scarfs',12000,1,1,1);
insert into main_task (id, name)
values (1, 'home');
insert into under_service (id, base_price, brief_explanation, name_sub_service, main_task_id)
values (1, 10000, 'home', 'cleanHome', 1);