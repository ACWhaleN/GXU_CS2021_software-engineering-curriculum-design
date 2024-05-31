-- 删除表格，如果存在
DROP TABLE IF EXISTS `messageitems`;
DROP TABLE IF EXISTS change_requests;
DROP TABLE IF EXISTS `user`;
DROP TABLE IF EXISTS `department`;
DROP TABLE IF EXISTS `position`;
DROP TABLE IF EXISTS `admin`;

-- 初始化并填充 admin 表

CREATE TABLE `admin` (
  id INT NOT NULL,
  `account` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `phone` varchar(255) NOT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO `admin` (id, `account`, `password`, `phone`) VALUES
('0', 'test', '123456', '12345678901'),
('1', 'admin1', 'password1', '12345678901'),
('2', 'admin2', 'password2', '12345678902'),
('3', 'admin3', 'password3', '12345678903'),
('4', 'admin4', 'password4', '12345678904'),
('5', 'admin5', 'password5', '12345678905'),
('6', 'admin6', 'password6', '12345678906'),
('7', 'admin7', 'password7', '12345678907'),
('8', 'admin8', 'password8', '12345678908'),
('9', 'admin9', 'password9', '12345678909'),
('10', 'admin10', 'password10', '12345678910');




-- 创建 position 表
CREATE TABLE `position` (
  id INT NOT NULL,
  `name` varchar(100) NOT NULL,
  status INT,
  `description` text,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO position (id, name, status, description) VALUES
('1', '书记', 1, '学院党委书记'),
('2', '委员', 1, '学院党委委员'),
('3', '主任', 1, '院学术委员会主任'),
('4', '副主任', 1, '院学术委员会副主任'),
('5', '委员', 1, '院学术委员会委员'),
('6', '主任', 1, '院学位评定分委员会主任'),
('7', '副主任', 1, '院学位评定分委员会副主任'),
('8', '委员', 1, '院学位评定分委员会委员'),
('9', '主任', 1, '院教学指导委员会主任'),
('10', '副主任', 1, '院教学指导委员会副主任'),
('11', '委员', 1, '院教学指导委员会委员'),
('12', '主任', 1, '院教学督导委员会主任'),
('13', '副主任', 1, '院教学督导委员会副主任'),
('14', '委员', 1, '院教学督导委员会委员'),
('15', '主任', 1, '院行政办公室主任'),
('16', '副主任', 1, '院行政办公室副主任'),
('17', '秘书', 1, '院行政办公室秘书'),
('18', '部长', 1, '系部长'),
('19', '副部长', 1, '系副部长'),
('20', '教师', 1, '系教师');

-- 创建 department 表
CREATE TABLE `department` (
  id INT NOT NULL,
  `name` varchar(100) NOT NULL,
  `description` text,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO department (id, name, description) VALUES
('1', '学院党委', '学院党委部门'),
('2', '院学术委员会', '院学术委员会部门'),
('3', '院学位评定分委员会', '院学位评定分委员会部门'),
('4', '院教学指导委员会', '院教学指导委员会部门'),
('5', '院教学督导委员会', '院教学督导委员会部门'),
('6', '院行政办公室', '院行政办公室部门'),
('7', '计算机科学与技术系', '计算机科学与技术系部门'),
('8', '电子信息工程系', '电子信息工程系部门'),
('9', '通信工程系', '通信工程系部门'),
('10', '信息安全系', '信息安全系部门'),
('11', '计算机公共基础教学部', '计算机公共基础教学部部门'),
('12', '院实验教学中心', '院实验教学中心部门'),
('13', '重点实验室', '重点实验室部门'),
('14', '省级工程研究中心', '省级工程研究中心部门'),
('15', '省级实验教学平台', '省级实验教学平台部门');

-- 创建 user 表，并添加外键约束
CREATE TABLE `user` (
  id int NOT NULL,
  `account` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  position_id INT NOT NULL,
  `nickName` varchar(255) NOT NULL,
  department_id INT NOT NULL,
  `sex` char(1) NOT NULL,
  `phone` varchar(255) NOT NULL,
  `mailbox` varchar(255) NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (`position_id`) REFERENCES `position`(id),
  FOREIGN KEY (`department_id`) REFERENCES `department`(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO user (id, account, password, name, position_id, nickName, department_id, sex, phone, mailbox) VALUES
('1', 'user1', 'pass1', '张三', '1', '张老师', '1', 'M', '12345678901', 'zhangsan@example.com'),
('2', 'user2', 'pass2', '李四', '2', '李老师', '2', 'F', '12345678902', 'lisi@example.com'),
('3', 'user3', 'pass3', '王五', '3', '王主任', '3', 'M', '12345678903', 'wangwu@example.com'),
('4', 'user4', 'pass4', '赵六', '4', '赵副主任', '4', 'M', '12345678904', 'zhaoliu@example.com'),
('5', 'user5', 'pass5', '孙七', '5', '孙委员', '5', 'F', '12345678905', 'sunqi@example.com'),
('6', 'user6', 'pass6', '周八', '6', '周主任', '6', 'M', '12345678906', 'zhouba@example.com'),
('7', 'user7', 'pass7', '吴九', '7', '吴副主任', '7', 'F', '12345678907', 'wujiu@example.com'),
('8', 'user8', 'pass8', '郑十', '8', '郑委员', '8', 'M', '12345678908', 'zhengshi@example.com'),
('9', 'user9', 'pass9', '王十一', '9', '王委员', '9', 'M', '12345678909', 'wangshiyi@example.com'),
('10', 'user10', 'pass10', '李十二', '10', '李指导', '10', 'F', '12345678910', 'lishier@example.com'),
('11', 'user11', 'pass11', '陈十三', '11', '陈主任', '11', 'M', '12345678911', 'chenshisan@example.com'),
('12', 'user12', 'pass12', '刘十四', '12', '刘副主任', '12', 'M', '12345678912', 'liushisi@example.com'),
('13', 'user13', 'pass13', '杨十五', '13', '杨委员', '13', 'M', '12345678913', 'yangshiwu@example.com'),
('14', 'user14', 'pass14', '周十六', '14', '周秘书', '14', 'F', '12345678914', 'zhoushiliu@example.com'),
('15', 'user15', 'pass15', '赵十七', '15', '赵秘书', '6', 'F', '12345678915', 'zhaoshichi@example.com'),
('16', 'user16', 'pass16', '钱十八', '16', '钱副主任', '15', 'M', '12345678916', 'qianshba@example.com'),
('17', 'user17', 'pass17', '孙十九', '17', '孙主任', '7', 'M', '12345678917', 'sunshijiu@example.com'),
('18', 'user18', 'pass18', '李二十', '18', '李部长', '7', 'M', '12345678918', 'liershi@example.com'),
('19', 'user19', 'pass19', '吴三十', '19', '吴副部长', '7', 'M', '12345678919', 'wushsan@example.com'),
('20', 'user20', 'pass20', '王二一', '20', '王教师', '7', 'F', '12345678920', 'wangeryi@example.com');

-- 创建 messageitems 表，并添加外键约束
CREATE TABLE `messageitems` (
  `isUsed` int DEFAULT NULL,
  id int NOT NULL,
  adminId INT NOT NULL,
  userId INT NOT NULL,
  `message` text NOT NULL,
  `sendTime` datetime DEFAULT NULL,
  `messageType` int DEFAULT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (adminId) REFERENCES `admin`(id),
  FOREIGN KEY (userId) REFERENCES `user`(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
INSERT INTO `messageitems` (`isUsed`, id, adminId, userId, `message`, `sendTime`, `messageType`) VALUES
(0, '1', '1', '2', '下午3点开会讨论新课程设置', '2024-05-28 09:00:00', 1),
(1, '2', '3', '4', '实验室设备维护通知', '2024-05-28 10:00:00', 2),
(0, '3', '5', '6', '新生入学培训安排', '2024-05-28 11:00:00', 1),
(1, '4', '7', '8', '后勤管理工作会议', '2024-05-28 12:00:00', 1),
(0, '5', '9', '10', '学生会活动策划会议', '2024-05-28 13:00:00', 2),
(1, '6', '2', '3', '教学工作月度总结', '2024-05-28 14:00:00', 2),
(0, '7', '4', '5', '下午4点实验课准备工作', '2024-05-28 15:00:00', 1),
(1, '8', '6', '7', '新学期工作安排', '2024-05-28 16:00:00', 2),
(0, '9', '8', '9', '周六图书馆闭馆通知', '2024-05-28 17:00:00', 1),
(1, '10', '10', '1', '绩效评估通知', '2024-05-28 17:00:00', 1);


CREATE TABLE change_requests (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    requested_position_id INT NOT NULL,
    requested_department_id INT NOT NULL,
    request_message TEXT,
    request_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    status ENUM('pending', 'approved', 'rejected') DEFAULT 'pending',
    FOREIGN KEY (user_id) REFERENCES user(id),
    FOREIGN KEY (requested_position_id) REFERENCES `position`(id),
    FOREIGN KEY (requested_department_id) REFERENCES department(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


INSERT INTO change_requests (user_id, requested_position_id, requested_department_id, request_message, status) VALUES
('1', '1', '2', '请求调动到院学术委员会', 'pending'),
('2', '3', '3', '请求调动到院学位评定分委员会', 'pending'),
('3', '4', '4', '请求调动到院教学指导委员会', 'rejected'),
('4', '5', '5', '请求调动到院教学督导委员会', 'pending'),
('5', '6', '6', '请求调动到院行政办公室', 'pending'),
('6', '7', '7', '请求调动到计算机科学与技术系', 'pending'),
('7', '8', '8', '请求调动到电子信息工程系', 'rejected'),
('8', '9', '9', '请求调动到通信工程系', 'pending'),
('9', '10', '10', '请求调动到信息安全系', 'pending'),
('10', '11', '11', '请求调动到计算机公共基础教学部', 'pending'),
('11', '12', '12', '请求调动到院实验教学中心', 'rejected'),
('12', '13', '13', '请求调动到重点实验室', 'pending'),
('13', '14', '14', '请求调动到省级工程研究中心', 'pending'),
('14', '15', '15', '请求调动到省级实验教学平台', 'rejected'),
('15', '1', '3', '请求调动到院学术委员会', 'pending'),
('16', '2', '4', '请求调动到院教学指导委员会', 'pending'),
('17', '3', '5', '请求调动到院教学督导委员会', 'rejected'),
('18', '4', '6', '请求调动到院行政办公室', 'pending'),
('19', '5', '7', '请求调动到计算机科学与技术系', 'pending'),
('20', '6', '8', '请求调动到电子信息工程系', 'rejected');
