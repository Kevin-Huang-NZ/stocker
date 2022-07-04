CREATE TABLE `sys_permissions` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `permission` varchar(100) NOT NULL DEFAULT '',
  `memo` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `sys_roles` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(100) NOT NULL DEFAULT '',
  `memo` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `sys_roles_permissions` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_id` bigint(20) NOT NULL,
  `permission_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_roles_permissions_0` (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(50) NOT NULL DEFAULT '',
  `avatar` varchar(200) DEFAULT NULL,
  `gender` char(1) DEFAULT NULL COMMENT '0-未提供；1-男；2-女',
  `birthday` varchar(10) DEFAULT NULL COMMENT '格式：yyyy-MM-dd',
  `phone` varchar(20) NOT NULL DEFAULT '',
  `password` varchar(100) NOT NULL DEFAULT '',
  `status` char(1) NOT NULL DEFAULT '1' COMMENT '0-禁用；1-启用',
  PRIMARY KEY (`id`),
  UNIQUE KEY `phone_UNIQUE` (`phone`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `user_roles` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `role_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_user_roles_0` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;