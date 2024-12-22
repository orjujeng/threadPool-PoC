# mysql docker config
docker run -d \
--name mysql \
--restart unless-stopped \
-e MYSQL_ROOT_PASSWORD=123456 \
-e MYSQL_DATABASE=THREADPOC \
-v /Users/orjujeng/IdeaProjects/mysql_dev_env:/var/lib/mysql \
-e TZ=Asia/Shanghai \
-p 3306:3306 \
mysql:8.0.39

## log table ddl
-- THREADPOC.threadlog definition

CREATE TABLE `threadlog` (
`id` bigint NOT NULL AUTO_INCREMENT,
`function` varchar(100) NOT NULL,
`insert_date` TIMESTAMP(6) NOT NULL,
PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;