SELECT CONCAT('DROP TABLE ', TABLE_NAME, ';')
FROM INFORMATION_SCHEMA.tables
WHERE TABLE_SCHEMA = 'mmoconnector';

SET foreign_key_checks = 0;

DROP TABLE batch_job_execution;
DROP TABLE batch_job_execution_context;
DROP TABLE batch_job_execution_params;
DROP TABLE batch_job_execution_seq;
DROP TABLE batch_job_instance;
DROP TABLE batch_job_seq;
DROP TABLE batch_step_execution;
DROP TABLE batch_step_execution_context;
DROP TABLE batch_step_execution_seq;
DROP TABLE calls;
DROP TABLE characters;
DROP TABLE hibernate_sequence;
DROP TABLE hibernate_sequences;
DROP TABLE item;
DROP TABLE key_values;
DROP TABLE node_types;
DROP TABLE node_types_items;
DROP TABLE privileges;
DROP TABLE roles;
DROP TABLE roles_privileges;
DROP TABLE target_systems;
DROP TABLE users;
DROP TABLE users_calls;
DROP TABLE users_roles;

SET foreign_key_checks = 1;