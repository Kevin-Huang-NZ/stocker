show variables like 'validate_password%';
SET GLOBAL validate_password.length = 6;
SET GLOBAL validate_password.mixed_case_count = 0;
SET GLOBAL validate_password.number_count = 1;
SET GLOBAL validate_password.policy = LOW;
SET GLOBAL validate_password.special_char_count = 0;