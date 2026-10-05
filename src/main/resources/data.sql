-- Dữ liệu được chuyển từ database/store.db của sản phẩm Flask gốc.
-- INSERT IGNORE giúp ứng dụng có thể khởi động lại mà không nhân đôi dữ liệu.
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS=0;

-- permissions
INSERT IGNORE INTO permissions (id,chuc_vu,quyen) VALUES (1,'Quản lý','Toàn quyền');
INSERT IGNORE INTO permissions (id,chuc_vu,quyen) VALUES (2,'NV xác nhận đơn','Chỉ xem, Xác nhận hoá đơn');
INSERT IGNORE INTO permissions (id,chuc_vu,quyen) VALUES (3,'NV Kho','Chỉ xem, Chỉ xem sản phẩm và nhà sản xuất');

-- employees
INSERT IGNORE INTO employees (id,ma_nv,ten_nv,ma_chuc_vu,sdt,email) VALUES (1,'NV001','Nguyễn Thị Mai',2,'0901234567','mai@gmail.com');
INSERT IGNORE INTO employees (id,ma_nv,ten_nv,ma_chuc_vu,sdt,email) VALUES (2,'NV002','Bùi Lê Hậu',1,'0902345678','hau@gmail.com');
INSERT IGNORE INTO employees (id,ma_nv,ten_nv,ma_chuc_vu,sdt,email) VALUES (3,'NV003','Trần Minh Phong',3,'0903456789','phong@gmail.com');
INSERT IGNORE INTO employees (id,ma_nv,ten_nv,ma_chuc_vu,sdt,email) VALUES (4,'NV004','Đặng Quốc Hùng',2,'0904567890','quochung@gmail.com');
INSERT IGNORE INTO employees (id,ma_nv,ten_nv,ma_chuc_vu,sdt,email) VALUES (5,'NV005','Lê Thị Nhàn',2,'0905678901','nhan@gmail.com');

-- employee_login
INSERT IGNORE INTO employee_login (id,id_nv,email,password) VALUES (1,1,'mai@gmail.com','mai123');
INSERT IGNORE INTO employee_login (id,id_nv,email,password) VALUES (2,2,'hau@gmail.com','hau456');
INSERT IGNORE INTO employee_login (id,id_nv,email,password) VALUES (4,4,'hung@gmail.com','hung101');
INSERT IGNORE INTO employee_login (id,id_nv,email,password) VALUES (5,5,'nhan@gmail.com','nhan102');
INSERT IGNORE INTO employee_login (id,id_nv,email,password) VALUES (6,3,'phong@gmail.com','phong123');

-- product_categories
INSERT IGNORE INTO product_categories (id,ma_loai,ten_loai) VALUES (1,'SP001','Áo phông');
INSERT IGNORE INTO product_categories (id,ma_loai,ten_loai) VALUES (2,'SP002','Áo sơ mi');
INSERT IGNORE INTO product_categories (id,ma_loai,ten_loai) VALUES (3,'SP003','Áo polo');
INSERT IGNORE INTO product_categories (id,ma_loai,ten_loai) VALUES (4,'SP004','Áo khoác');
INSERT IGNORE INTO product_categories (id,ma_loai,ten_loai) VALUES (5,'SP005','Quần ngắn');
INSERT IGNORE INTO product_categories (id,ma_loai,ten_loai) VALUES (6,'SP006','Quần dài');

-- manufacturers
INSERT IGNORE INTO manufacturers (id,ma_nsx,ten_nsx,quoc_gia,lien_he) VALUES (1,'NSX001','Canifa','USA','nike_contact@gmail.com');
INSERT IGNORE INTO manufacturers (id,ma_nsx,ten_nsx,quoc_gia,lien_he) VALUES (2,'NSX002','Yody','Germany','adidas_contact@gmail.com');
INSERT IGNORE INTO manufacturers (id,ma_nsx,ten_nsx,quoc_gia,lien_he) VALUES (3,'NSX003','Aristino','Germany','puma_contact@gmail.com');
INSERT IGNORE INTO manufacturers (id,ma_nsx,ten_nsx,quoc_gia,lien_he) VALUES (4,'NSX004',' Biluxury','USA','vans_contact@gmail.com');
INSERT IGNORE INTO manufacturers (id,ma_nsx,ten_nsx,quoc_gia,lien_he) VALUES (5,'NSX005',' Zara','South Korea','mlb_contact@gmail.com');
INSERT IGNORE INTO manufacturers (id,ma_nsx,ten_nsx,quoc_gia,lien_he) VALUES (6,'NSX006','Uniqlo','Italy','gucci_contact@gmail.com');
INSERT IGNORE INTO manufacturers (id,ma_nsx,ten_nsx,quoc_gia,lien_he) VALUES (7,'NSX007','Owen','France','lv_contact@gmail.com');
INSERT IGNORE INTO manufacturers (id,ma_nsx,ten_nsx,quoc_gia,lien_he) VALUES (8,'NSX008','Routine','Spain','balenciaga_contact@gmail.com');
INSERT IGNORE INTO manufacturers (id,ma_nsx,ten_nsx,quoc_gia,lien_he) VALUES (9,'NSX009','Coolmate','Vietnam','ananas_contact@gmail.com');
INSERT IGNORE INTO manufacturers (id,ma_nsx,ten_nsx,quoc_gia,lien_he) VALUES (10,'NSX010','4MEN','Vietnam','4men_contact@gmail.com');

-- products
INSERT IGNORE INTO products (id,ma_sp,hinh_anh,ten_sp,loai_sp,mau_sac,size,gia_ban,gia_nhap,so_luong,ma_vach,mo_ta,ma_nsx,trang_thai) VALUES (1,'SP001','https://image.hm.com/assets/hm/d0/ec/d0ec56b1ee41d0f8a80ef50784e345230635f037.jpg?imwidth=2160','Áo phông basic trắng',1,'Trắng','M',199000,0,2,'1000000000001','Áo phông cotton basic, mềm mại, thoáng mát.',10,'Đang bán');
INSERT IGNORE INTO products (id,ma_sp,hinh_anh,ten_sp,loai_sp,mau_sac,size,gia_ban,gia_nhap,so_luong,ma_vach,mo_ta,ma_nsx,trang_thai) VALUES (2,'SP002','https://image.hm.com/assets/hm/e6/52/e652bbf15d515fd819a6d5576181cae256222d30.jpg?imwidth=2160','Áo phông basic đen',1,'Đen','L',199000,0,10,'1000000000002','Áo phông màu đen dễ phối đồ, phù hợp mọi hoàn cảnh.',10,'Đang bán');
INSERT IGNORE INTO products (id,ma_sp,hinh_anh,ten_sp,loai_sp,mau_sac,size,gia_ban,gia_nhap,so_luong,ma_vach,mo_ta,ma_nsx,trang_thai) VALUES (3,'SP003','https://image.hm.com/assets/hm/0a/44/0a4422a0aca4c583c2ed2eac509737479abd05cf.jpg?imwidth=2160','Áo phông in hình',1,'Trắng','M',229000,0,5,'1000000000003','Áo phông in graphic trẻ trung, phong cách năng động.',9,'Đang bán');
INSERT IGNORE INTO products (id,ma_sp,hinh_anh,ten_sp,loai_sp,mau_sac,size,gia_ban,gia_nhap,so_luong,ma_vach,mo_ta,ma_nsx,trang_thai) VALUES (4,'SP004','https://deme.vn/wp-content/uploads/2025/08/155.png','Áo phông oversize',1,'Xám','XL',249000,0,7,'1000000000004','Áo phông form rộng, phong cách streetwear.',5,'Đang bán');
INSERT IGNORE INTO products (id,ma_sp,hinh_anh,ten_sp,loai_sp,mau_sac,size,gia_ban,gia_nhap,so_luong,ma_vach,mo_ta,ma_nsx,trang_thai) VALUES (5,'SP005','https://n7media.coolmate.me/uploads/2026/03/19/ao-thun-nam-phoi-tay-exdry-proactive-wc-18-xanh-la_46.jpg?aio=w-1100','Áo phông thể thao',1,'Xanh','M',219000,0,1,'1000000000005','Áo phông thể thao co giãn, thấm hút mồ hôi tốt.',9,'Đang bán');
INSERT IGNORE INTO products (id,ma_sp,hinh_anh,ten_sp,loai_sp,mau_sac,size,gia_ban,gia_nhap,so_luong,ma_vach,mo_ta,ma_nsx,trang_thai) VALUES (6,'SP006','','Áo phông cổ tròn',1,'Be','L',189000,0,1,'1000000000006','Áo phông cổ tròn basic, phù hợp mặc hàng ngày.',2,'Đang bán');
INSERT IGNORE INTO products (id,ma_sp,hinh_anh,ten_sp,loai_sp,mau_sac,size,gia_ban,gia_nhap,so_luong,ma_vach,mo_ta,ma_nsx,trang_thai) VALUES (7,'SP007','','Áo phông cao cấp',1,'Đen','M',299000,0,16,'1000000000007','Áo phông chất liệu cao cấp, form chuẩn, bền đẹp.',3,'Đang bán');
INSERT IGNORE INTO products (id,ma_sp,hinh_anh,ten_sp,loai_sp,mau_sac,size,gia_ban,gia_nhap,so_luong,ma_vach,mo_ta,ma_nsx,trang_thai) VALUES (8,'SP008','','Áo phông unisex',1,'Trắng','L',209000,0,30,'1000000000008','Áo phông unisex phù hợp cả nam và nữ.',10,'Đang bán');
INSERT IGNORE INTO products (id,ma_sp,hinh_anh,ten_sp,loai_sp,mau_sac,size,gia_ban,gia_nhap,so_luong,ma_vach,mo_ta,ma_nsx,trang_thai) VALUES (9,'SP009','','Áo phông form rộng',1,'Nâu','XL',239000,0,26,'1000000000009','Áo phông form rộng thoải mái, phong cách Hàn Quốc.',6,'Đang bán');
INSERT IGNORE INTO products (id,ma_sp,hinh_anh,ten_sp,loai_sp,mau_sac,size,gia_ban,gia_nhap,so_luong,ma_vach,mo_ta,ma_nsx,trang_thai) VALUES (10,'SP010','','Áo phông phối màu',1,'Xanh navy','M',259000,0,12,'1000000000010','Áo phông phối màu nổi bật, phong cách hiện đại.',8,'Đang bán');
INSERT IGNORE INTO products (id,ma_sp,hinh_anh,ten_sp,loai_sp,mau_sac,size,gia_ban,gia_nhap,so_luong,ma_vach,mo_ta,ma_nsx,trang_thai) VALUES (11,'SP011','','Áo sơ mi trắng công sở',2,'Trắng','M',299000,0,12,'1000000000011','Áo sơ mi trắng lịch sự, phù hợp môi trường công sở.',2,'Đang bán');
INSERT IGNORE INTO products (id,ma_sp,hinh_anh,ten_sp,loai_sp,mau_sac,size,gia_ban,gia_nhap,so_luong,ma_vach,mo_ta,ma_nsx,trang_thai) VALUES (12,'SP012',NULL,'Áo sơ mi xanh nhạt',2,'Xanh nhạt','L',319000,0,75,'1000000000012','Áo sơ mi màu xanh nhạt trẻ trung, dễ phối đồ.',2,'Đang bán');
INSERT IGNORE INTO products (id,ma_sp,hinh_anh,ten_sp,loai_sp,mau_sac,size,gia_ban,gia_nhap,so_luong,ma_vach,mo_ta,ma_nsx,trang_thai) VALUES (13,'SP013',NULL,'Áo sơ mi kẻ sọc',2,'Xanh sọc','M',339000,0,69,'1000000000013','Áo sơ mi kẻ sọc phong cách Hàn Quốc.',5,'Đang bán');
INSERT IGNORE INTO products (id,ma_sp,hinh_anh,ten_sp,loai_sp,mau_sac,size,gia_ban,gia_nhap,so_luong,ma_vach,mo_ta,ma_nsx,trang_thai) VALUES (14,'SP014',NULL,'Áo sơ mi ngắn tay',2,'Trắng','M',279000,0,85,'1000000000014','Áo sơ mi ngắn tay thoáng mát, phù hợp mùa hè.',10,'Đang bán');
INSERT IGNORE INTO products (id,ma_sp,hinh_anh,ten_sp,loai_sp,mau_sac,size,gia_ban,gia_nhap,so_luong,ma_vach,mo_ta,ma_nsx,trang_thai) VALUES (15,'SP015',NULL,'Áo sơ mi slimfit',2,'Đen','L',359000,0,60,'1000000000015','Áo sơ mi form slimfit tôn dáng, lịch lãm.',7,'Đang bán');
INSERT IGNORE INTO products (id,ma_sp,hinh_anh,ten_sp,loai_sp,mau_sac,size,gia_ban,gia_nhap,so_luong,ma_vach,mo_ta,ma_nsx,trang_thai) VALUES (16,'SP016',NULL,'Áo sơ mi caro',2,'Đỏ caro','XL',329000,0,65,'1000000000016','Áo sơ mi caro trẻ trung, phong cách casual.',3,'Đang bán');
INSERT IGNORE INTO products (id,ma_sp,hinh_anh,ten_sp,loai_sp,mau_sac,size,gia_ban,gia_nhap,so_luong,ma_vach,mo_ta,ma_nsx,trang_thai) VALUES (17,'SP017',NULL,'Áo sơ mi cao cấp',2,'Trắng','L',399000,0,50,'1000000000017','Áo sơ mi chất liệu cao cấp, mềm mại, thoải mái.',6,'Đang bán');
INSERT IGNORE INTO products (id,ma_sp,hinh_anh,ten_sp,loai_sp,mau_sac,size,gia_ban,gia_nhap,so_luong,ma_vach,mo_ta,ma_nsx,trang_thai) VALUES (18,'SP018',NULL,'Áo sơ mi denim',2,'Xanh đậm','M',379000,0,55,'1000000000018','Áo sơ mi denim cá tính, phù hợp đi chơi.',5,'Đang bán');
INSERT IGNORE INTO products (id,ma_sp,hinh_anh,ten_sp,loai_sp,mau_sac,size,gia_ban,gia_nhap,so_luong,ma_vach,mo_ta,ma_nsx,trang_thai) VALUES (19,'SP019',NULL,'Áo sơ mi họa tiết',2,'Họa tiết','M',349000,0,70,'1000000000019','Áo sơ mi họa tiết nổi bật, phong cách thời trang.',8,'Đang bán');
INSERT IGNORE INTO products (id,ma_sp,hinh_anh,ten_sp,loai_sp,mau_sac,size,gia_ban,gia_nhap,so_luong,ma_vach,mo_ta,ma_nsx,trang_thai) VALUES (20,'SP020',NULL,'Áo sơ mi form rộng',2,'Be','XL',289000,0,90,'1000000000020','Áo sơ mi form rộng thoải mái, phong cách hiện đại.',10,'Đang bán');
INSERT IGNORE INTO products (id,ma_sp,hinh_anh,ten_sp,loai_sp,mau_sac,size,gia_ban,gia_nhap,so_luong,ma_vach,mo_ta,ma_nsx,trang_thai) VALUES (21,'SP021',NULL,'Áo polo basic trắng',3,'Trắng','M',259000,0,90,'1000000000021','Áo polo basic, thiết kế đơn giản, lịch sự.',10,'Đang bán');
INSERT IGNORE INTO products (id,ma_sp,hinh_anh,ten_sp,loai_sp,mau_sac,size,gia_ban,gia_nhap,so_luong,ma_vach,mo_ta,ma_nsx,trang_thai) VALUES (22,'SP022',NULL,'Áo polo đen',3,'Đen','L',269000,0,85,'1000000000022','Áo polo màu đen dễ phối đồ, phù hợp nhiều hoàn cảnh.',3,'Đang bán');
INSERT IGNORE INTO products (id,ma_sp,hinh_anh,ten_sp,loai_sp,mau_sac,size,gia_ban,gia_nhap,so_luong,ma_vach,mo_ta,ma_nsx,trang_thai) VALUES (23,'SP023',NULL,'Áo polo thể thao',3,'Xanh','M',279000,0,80,'1000000000023','Áo polo thể thao co giãn, thấm hút mồ hôi tốt.',9,'Đang bán');
INSERT IGNORE INTO products (id,ma_sp,hinh_anh,ten_sp,loai_sp,mau_sac,size,gia_ban,gia_nhap,so_luong,ma_vach,mo_ta,ma_nsx,trang_thai) VALUES (24,'SP024',NULL,'Áo polo kẻ sọc',3,'Xanh sọc','L',299000,0,70,'1000000000024','Áo polo kẻ sọc trẻ trung, phong cách năng động.',2,'Ngừng kinh doanh');
INSERT IGNORE INTO products (id,ma_sp,hinh_anh,ten_sp,loai_sp,mau_sac,size,gia_ban,gia_nhap,so_luong,ma_vach,mo_ta,ma_nsx,trang_thai) VALUES (25,'SP025',NULL,'Áo polo cao cấp',3,'Trắng','XL',349000,0,60,'1000000000025','Áo polo chất liệu cao cấp, mềm mại, thoáng khí.',6,'Ngừng kinh doanh');
INSERT IGNORE INTO products (id,ma_sp,hinh_anh,ten_sp,loai_sp,mau_sac,size,gia_ban,gia_nhap,so_luong,ma_vach,mo_ta,ma_nsx,trang_thai) VALUES (26,'SP026',NULL,'Áo polo form slimfit',3,'Đen','M',319000,0,75,'1000000000026','Áo polo slimfit tôn dáng, phong cách hiện đại.',7,'Đang bán');
INSERT IGNORE INTO products (id,ma_sp,hinh_anh,ten_sp,loai_sp,mau_sac,size,gia_ban,gia_nhap,so_luong,ma_vach,mo_ta,ma_nsx,trang_thai) VALUES (27,'SP027',NULL,'Áo polo phối màu',3,'Xanh navy','L',329000,0,65,'1000000000027','Áo polo phối màu nổi bật, thời trang.',5,'Đang bán');
INSERT IGNORE INTO products (id,ma_sp,hinh_anh,ten_sp,loai_sp,mau_sac,size,gia_ban,gia_nhap,so_luong,ma_vach,mo_ta,ma_nsx,trang_thai) VALUES (28,'SP028',NULL,'Áo polo cổ bẻ',3,'Be','M',289000,0,85,'1000000000028','Áo polo cổ bẻ cổ điển, phù hợp đi làm và đi chơi.',2,'Đang bán');
INSERT IGNORE INTO products (id,ma_sp,hinh_anh,ten_sp,loai_sp,mau_sac,size,gia_ban,gia_nhap,so_luong,ma_vach,mo_ta,ma_nsx,trang_thai) VALUES (29,'SP029',NULL,'Áo polo họa tiết',3,'Họa tiết','XL',339000,0,70,'1000000000029','Áo polo họa tiết độc đáo, phong cách trẻ trung.',8,'Đang bán');
INSERT IGNORE INTO products (id,ma_sp,hinh_anh,ten_sp,loai_sp,mau_sac,size,gia_ban,gia_nhap,so_luong,ma_vach,mo_ta,ma_nsx,trang_thai) VALUES (30,'SP030',NULL,'Áo polo unisex',3,'Xám','L',299000,0,90,'1000000000030','Áo polo unisex phù hợp cả nam và nữ.',10,'Đang bán');
INSERT IGNORE INTO products (id,ma_sp,hinh_anh,ten_sp,loai_sp,mau_sac,size,gia_ban,gia_nhap,so_luong,ma_vach,mo_ta,ma_nsx,trang_thai) VALUES (31,'SP031',NULL,'Áo khoác gió basic',4,'Đen','L',499000,0,70,'1000000000031','Áo khoác gió nhẹ, chống nước, phù hợp thời tiết se lạnh.',10,'Đang bán');
INSERT IGNORE INTO products (id,ma_sp,hinh_anh,ten_sp,loai_sp,mau_sac,size,gia_ban,gia_nhap,so_luong,ma_vach,mo_ta,ma_nsx,trang_thai) VALUES (32,'SP032',NULL,'Áo khoác bomber',4,'Xanh rêu','M',599000,0,60,'1000000000032','Áo khoác bomber phong cách trẻ trung, năng động.',5,'Đang bán');
INSERT IGNORE INTO products (id,ma_sp,hinh_anh,ten_sp,loai_sp,mau_sac,size,gia_ban,gia_nhap,so_luong,ma_vach,mo_ta,ma_nsx,trang_thai) VALUES (33,'SP033',NULL,'Áo khoác denim',4,'Xanh đậm','L',650000,0,55,'1000000000033','Áo khoác denim cá tính, phù hợp phong cách streetwear.',5,'Đang bán');
INSERT IGNORE INTO products (id,ma_sp,hinh_anh,ten_sp,loai_sp,mau_sac,size,gia_ban,gia_nhap,so_luong,ma_vach,mo_ta,ma_nsx,trang_thai) VALUES (34,'SP034',NULL,'Áo khoác hoodie',4,'Xám','XL',450000,0,80,'1000000000034','Áo khoác hoodie giữ ấm tốt, thoải mái khi mặc.',10,'Đang bán');
INSERT IGNORE INTO products (id,ma_sp,hinh_anh,ten_sp,loai_sp,mau_sac,size,gia_ban,gia_nhap,so_luong,ma_vach,mo_ta,ma_nsx,trang_thai) VALUES (35,'SP035',NULL,'Áo khoác dù thể thao',4,'Đen','M',520000,0,75,'1000000000035','Áo khoác thể thao chống gió, nhẹ và bền.',9,'Đang bán');
INSERT IGNORE INTO products (id,ma_sp,hinh_anh,ten_sp,loai_sp,mau_sac,size,gia_ban,gia_nhap,so_luong,ma_vach,mo_ta,ma_nsx,trang_thai) VALUES (36,'SP036','','Áo khoác dạ',4,'Nâu','XL',750000,0,300,'1000000000036','Áo khoác dạ cao cấp, giữ ấm tốt mùa đông.',6,'Đang bán');
INSERT IGNORE INTO products (id,ma_sp,hinh_anh,ten_sp,loai_sp,mau_sac,size,gia_ban,gia_nhap,so_luong,ma_vach,mo_ta,ma_nsx,trang_thai) VALUES (37,'SP037','','Áo khoác kaki',4,'Be','L',580000,0,100,'1000000000037','Áo khoác kaki form đứng, lịch lãm.',7,'Đang bán');
INSERT IGNORE INTO products (id,ma_sp,hinh_anh,ten_sp,loai_sp,mau_sac,size,gia_ban,gia_nhap,so_luong,ma_vach,mo_ta,ma_nsx,trang_thai) VALUES (38,'SP038',NULL,'Áo khoác phao',4,'Đen','XL',820000,0,50,'1000000000038','Áo khoác phao siêu nhẹ, giữ nhiệt tốt.',1,'Đang bán');
INSERT IGNORE INTO products (id,ma_sp,hinh_anh,ten_sp,loai_sp,mau_sac,size,gia_ban,gia_nhap,so_luong,ma_vach,mo_ta,ma_nsx,trang_thai) VALUES (39,'SP039','','Áo khoác cardigan',4,'Xám','M',430000,0,10,'1000000000039','Áo khoác cardigan nhẹ nhàng, phong cách Hàn Quốc.',8,'Đang bán');
INSERT IGNORE INTO products (id,ma_sp,hinh_anh,ten_sp,loai_sp,mau_sac,size,gia_ban,gia_nhap,so_luong,ma_vach,mo_ta,ma_nsx,trang_thai) VALUES (40,'SP040','','Áo khoác blazer',4,'Đen','L',690000,0,9,'1000000000040','Áo khoác blazer lịch sự, phù hợp công sở.',3,'Đang bán');
INSERT IGNORE INTO products (id,ma_sp,hinh_anh,ten_sp,loai_sp,mau_sac,size,gia_ban,gia_nhap,so_luong,ma_vach,mo_ta,ma_nsx,trang_thai) VALUES (41,'SP041','','Quần short thể thao',5,'Đen','M',199000,0,8,'1000000000041','Quần short thể thao co giãn, thoáng mát, phù hợp vận động.',9,'Đang bán');
INSERT IGNORE INTO products (id,ma_sp,hinh_anh,ten_sp,loai_sp,mau_sac,size,gia_ban,gia_nhap,so_luong,ma_vach,mo_ta,ma_nsx,trang_thai) VALUES (42,'SP042','','Quần short kaki',5,'Be','L',249000,0,7,'1000000000042','Quần short kaki thoải mái, phù hợp đi chơi.',2,'Đang bán');
INSERT IGNORE INTO products (id,ma_sp,hinh_anh,ten_sp,loai_sp,mau_sac,size,gia_ban,gia_nhap,so_luong,ma_vach,mo_ta,ma_nsx,trang_thai) VALUES (43,'SP043','','Quần short jean',5,'Xanh','M',279000,0,6,'1000000000043','Quần short jean trẻ trung, phong cách năng động.',5,'Đang bán');
INSERT IGNORE INTO products (id,ma_sp,hinh_anh,ten_sp,loai_sp,mau_sac,size,gia_ban,gia_nhap,so_luong,ma_vach,mo_ta,ma_nsx,trang_thai) VALUES (44,'SP044','','Quần short basic',5,'Xám','L',189000,0,5,'1000000000044','Quần short basic dễ mặc, phù hợp hàng ngày.',10,'Đang bán');
INSERT IGNORE INTO products (id,ma_sp,hinh_anh,ten_sp,loai_sp,mau_sac,size,gia_ban,gia_nhap,so_luong,ma_vach,mo_ta,ma_nsx,trang_thai) VALUES (45,'SP045','','Quần short thể thao cao cấp',5,'Đen','XL',299000,0,4,'1000000000045','Quần short thể thao cao cấp, thấm hút mồ hôi tốt.',9,'Đang bán');
INSERT IGNORE INTO products (id,ma_sp,hinh_anh,ten_sp,loai_sp,mau_sac,size,gia_ban,gia_nhap,so_luong,ma_vach,mo_ta,ma_nsx,trang_thai) VALUES (46,'SP046','','Quần short lưng thun',5,'Xanh navy','M',219000,0,3,'1000000000046','Quần short lưng thun co giãn, thoải mái khi mặc.',3,'Đang bán');
INSERT IGNORE INTO products (id,ma_sp,hinh_anh,ten_sp,loai_sp,mau_sac,size,gia_ban,gia_nhap,so_luong,ma_vach,mo_ta,ma_nsx,trang_thai) VALUES (47,'SP047','','Quần short cargo',5,'Xanh rêu','L',329000,0,2,'1000000000047','Quần short cargo nhiều túi, phong cách cá tính.',7,'Đang bán');
INSERT IGNORE INTO products (id,ma_sp,hinh_anh,ten_sp,loai_sp,mau_sac,size,gia_ban,gia_nhap,so_luong,ma_vach,mo_ta,ma_nsx,trang_thai) VALUES (48,'SP048','','Quần short unisex',5,'Trắng','M',209000,0,1,'1000000000048','Quần short unisex phù hợp cả nam và nữ.',10,'Đang bán');
INSERT IGNORE INTO products (id,ma_sp,hinh_anh,ten_sp,loai_sp,mau_sac,size,gia_ban,gia_nhap,so_luong,ma_vach,mo_ta,ma_nsx,trang_thai) VALUES (49,'SP049','','Quần short họa tiết',5,'Họa tiết','XL',259000,0,11,'1000000000049','Quần short họa tiết nổi bật, phong cách trẻ trung.',8,'Đang bán');
INSERT IGNORE INTO products (id,ma_sp,hinh_anh,ten_sp,loai_sp,mau_sac,size,gia_ban,gia_nhap,so_luong,ma_vach,mo_ta,ma_nsx,trang_thai) VALUES (50,'SP050','','Quần short thể thao nhẹ',5,'Xám','L',189000,0,88,'1000000000050','Quần short nhẹ, thoáng khí, phù hợp tập luyện.',9,'Đang bán');
INSERT IGNORE INTO products (id,ma_sp,hinh_anh,ten_sp,loai_sp,mau_sac,size,gia_ban,gia_nhap,so_luong,ma_vach,mo_ta,ma_nsx,trang_thai) VALUES (51,'SP051','','Quần dài kaki slimfit',6,'Be','L',399000,0,77,'1000000000051','Quần kaki form slimfit, phù hợp đi làm và đi chơi.',7,'Đang bán');
INSERT IGNORE INTO products (id,ma_sp,hinh_anh,ten_sp,loai_sp,mau_sac,size,gia_ban,gia_nhap,so_luong,ma_vach,mo_ta,ma_nsx,trang_thai) VALUES (52,'SP052','','Quần jean xanh đậm',6,'Xanh đậm','M',499000,0,26,'1000000000052','Quần jean bền đẹp, phong cách hiện đại.',5,'Đang bán');
INSERT IGNORE INTO products (id,ma_sp,hinh_anh,ten_sp,loai_sp,mau_sac,size,gia_ban,gia_nhap,so_luong,ma_vach,mo_ta,ma_nsx,trang_thai) VALUES (53,'SP053','','Quần tây công sở',6,'Đen','L',420000,0,300,'1000000000053','Quần tây lịch sự, phù hợp môi trường công sở.',3,'Đang bán');
INSERT IGNORE INTO products (id,ma_sp,hinh_anh,ten_sp,loai_sp,mau_sac,size,gia_ban,gia_nhap,so_luong,ma_vach,mo_ta,ma_nsx,trang_thai) VALUES (54,'SP054','','Quần jogger thể thao',6,'Xám','M',350000,0,66,'1000000000054','Quần jogger co giãn, phong cách năng động.',9,'Đang bán');
INSERT IGNORE INTO products (id,ma_sp,hinh_anh,ten_sp,loai_sp,mau_sac,size,gia_ban,gia_nhap,so_luong,ma_vach,mo_ta,ma_nsx,trang_thai) VALUES (55,'SP055','','Quần kaki basic',6,'Nâu','XL',379000,0,60,'1000000000055','Quần kaki basic dễ mặc, phù hợp hàng ngày.',2,'Đang bán');
INSERT IGNORE INTO products (id,ma_sp,hinh_anh,ten_sp,loai_sp,mau_sac,size,gia_ban,gia_nhap,so_luong,ma_vach,mo_ta,ma_nsx,trang_thai) VALUES (56,'SP056','','Quần jean slimfit',6,'Xanh','L',520000,0,50,'1000000000056','Quần jean slimfit tôn dáng, phong cách trẻ trung.',5,'Đang bán');
INSERT IGNORE INTO products (id,ma_sp,hinh_anh,ten_sp,loai_sp,mau_sac,size,gia_ban,gia_nhap,so_luong,ma_vach,mo_ta,ma_nsx,trang_thai) VALUES (57,'SP057','','Quần thể thao dài',6,'Đen','M',300000,0,90,'1000000000057','Quần thể thao thoải mái, phù hợp tập luyện.',9,'Đang bán');
INSERT IGNORE INTO products (id,ma_sp,hinh_anh,ten_sp,loai_sp,mau_sac,size,gia_ban,gia_nhap,so_luong,ma_vach,mo_ta,ma_nsx,trang_thai) VALUES (58,'SP058','https://cdn.hstatic.net/products/200000053174/9qavc539ttt-799k-1080x1080_420cf83c501240af874b7e1d9e2565d9_master.jpg','Quần tây cao cấp',6,'Xanh navy','L',480000,0,200,'1000000000999999','Quần tây cao cấp, chất liệu mềm mại, lịch lãm.',6,'Đang bán');
INSERT IGNORE INTO products (id,ma_sp,hinh_anh,ten_sp,loai_sp,mau_sac,size,gia_ban,gia_nhap,so_luong,ma_vach,mo_ta,ma_nsx,trang_thai) VALUES (59,'SP059','https://4menshop.com/images/thumbs/2023/10/quan-jogger-ra-cheo-form-slimfit-jo012-mau-den-18272-slide-products-653a08da453f9.jpg','Quần jogger unisex',6,'Be','M',360000,0,190,'10000000000888888','Quần jogger unisex, phù hợp cả nam và nữ.',10,'Đang bán');
INSERT IGNORE INTO products (id,ma_sp,hinh_anh,ten_sp,loai_sp,mau_sac,size,gia_ban,gia_nhap,so_luong,ma_vach,mo_ta,ma_nsx,trang_thai) VALUES (60,'SP060','https://bizweb.dktcdn.net/100/527/490/files/quan-xanh-reu-phoi-ao-mau-gi-anh-8.jpg?v=1753431782373','Quần kaki form rộng',6,'Xanh rêu','XL',410000,0,80,'1000000000060','Quần kaki form rộng, phong cách hiện đại.',7,'Ngừng kinh doanh');

-- product_stock
INSERT IGNORE INTO product_stock (id,product_id,size,so_luong) VALUES (10,57,'S',90);
INSERT IGNORE INTO product_stock (id,product_id,size,so_luong) VALUES (11,56,'S',50);
INSERT IGNORE INTO product_stock (id,product_id,size,so_luong) VALUES (12,55,'S',60);
INSERT IGNORE INTO product_stock (id,product_id,size,so_luong) VALUES (13,54,'S',66);
INSERT IGNORE INTO product_stock (id,product_id,size,so_luong) VALUES (14,53,'S',300);
INSERT IGNORE INTO product_stock (id,product_id,size,so_luong) VALUES (15,52,'S',26);
INSERT IGNORE INTO product_stock (id,product_id,size,so_luong) VALUES (16,51,'S',77);
INSERT IGNORE INTO product_stock (id,product_id,size,so_luong) VALUES (17,50,'S',88);
INSERT IGNORE INTO product_stock (id,product_id,size,so_luong) VALUES (18,49,'S',11);
INSERT IGNORE INTO product_stock (id,product_id,size,so_luong) VALUES (19,48,'S',1);
INSERT IGNORE INTO product_stock (id,product_id,size,so_luong) VALUES (20,47,'S',2);
INSERT IGNORE INTO product_stock (id,product_id,size,so_luong) VALUES (21,46,'S',3);
INSERT IGNORE INTO product_stock (id,product_id,size,so_luong) VALUES (22,45,'S',4);
INSERT IGNORE INTO product_stock (id,product_id,size,so_luong) VALUES (23,44,'S',5);
INSERT IGNORE INTO product_stock (id,product_id,size,so_luong) VALUES (24,43,'S',6);
INSERT IGNORE INTO product_stock (id,product_id,size,so_luong) VALUES (25,42,'S',7);
INSERT IGNORE INTO product_stock (id,product_id,size,so_luong) VALUES (26,41,'S',8);
INSERT IGNORE INTO product_stock (id,product_id,size,so_luong) VALUES (27,40,'S',9);
INSERT IGNORE INTO product_stock (id,product_id,size,so_luong) VALUES (28,39,'S',10);
INSERT IGNORE INTO product_stock (id,product_id,size,so_luong) VALUES (29,37,'S',100);
INSERT IGNORE INTO product_stock (id,product_id,size,so_luong) VALUES (30,36,'S',300);
INSERT IGNORE INTO product_stock (id,product_id,size,so_luong) VALUES (36,6,'S',1);
INSERT IGNORE INTO product_stock (id,product_id,size,so_luong) VALUES (37,7,'S',16);
INSERT IGNORE INTO product_stock (id,product_id,size,so_luong) VALUES (38,8,'S',30);
INSERT IGNORE INTO product_stock (id,product_id,size,so_luong) VALUES (39,9,'S',26);
INSERT IGNORE INTO product_stock (id,product_id,size,so_luong) VALUES (40,10,'S',12);
INSERT IGNORE INTO product_stock (id,product_id,size,so_luong) VALUES (41,11,'S',12);
INSERT IGNORE INTO product_stock (id,product_id,size,so_luong) VALUES (52,60,'S',5);
INSERT IGNORE INTO product_stock (id,product_id,size,so_luong) VALUES (53,60,'M',5);
INSERT IGNORE INTO product_stock (id,product_id,size,so_luong) VALUES (54,60,'L',70);
INSERT IGNORE INTO product_stock (id,product_id,size,so_luong) VALUES (55,1,'S',2);
INSERT IGNORE INTO product_stock (id,product_id,size,so_luong) VALUES (56,2,'S',10);
INSERT IGNORE INTO product_stock (id,product_id,size,so_luong) VALUES (57,3,'S',5);
INSERT IGNORE INTO product_stock (id,product_id,size,so_luong) VALUES (58,4,'S',7);
INSERT IGNORE INTO product_stock (id,product_id,size,so_luong) VALUES (59,5,'S',1);
INSERT IGNORE INTO product_stock (id,product_id,size,so_luong) VALUES (60,59,'S',190);
INSERT IGNORE INTO product_stock (id,product_id,size,so_luong) VALUES (61,58,'S',200);

-- product_images
INSERT IGNORE INTO product_images (id,product_id,hinh_anh) VALUES (33,60,'https://thoitrangbigsize.vn/wp-content/uploads/2025/03/%E4%B8%BB%E5%9B%BE-4-7.jpg');
INSERT IGNORE INTO product_images (id,product_id,hinh_anh) VALUES (34,60,'https://thoitrangbigsize.vn/wp-content/uploads/2025/03/%E4%B8%BB%E5%9B%BE-5-5.jpg');
INSERT IGNORE INTO product_images (id,product_id,hinh_anh) VALUES (35,60,'https://thoitrangbigsize.vn/wp-content/uploads/2025/03/%E4%B8%BB%E5%9B%BE-9-Photoroom.jpg');
INSERT IGNORE INTO product_images (id,product_id,hinh_anh) VALUES (36,60,'https://thoitrangbigsize.vn/wp-content/uploads/2025/03/%E8%AF%A6%E6%83%85-5-Photoroom-1.jpg');
INSERT IGNORE INTO product_images (id,product_id,hinh_anh) VALUES (37,1,'https://image.hm.com/assets/hm/64/87/6487c21d58948af40aaffed3c48222d317270e17.jpg?imwidth=2160');
INSERT IGNORE INTO product_images (id,product_id,hinh_anh) VALUES (38,1,'https://image.hm.com/assets/hm/6f/d4/6fd49b1d73b594052e1e8e9f961b76fd2fda77ef.jpg?imwidth=2160');
INSERT IGNORE INTO product_images (id,product_id,hinh_anh) VALUES (39,1,'https://image.hm.com/assets/hm/17/34/173459e3770365bbd2835c4af366e63de07301da.jpg?imwidth=2160');
INSERT IGNORE INTO product_images (id,product_id,hinh_anh) VALUES (40,1,'https://image.hm.com/assets/hm/59/c5/59c543d309e2c05afd304218944b43cf04197fa7.jpg?imwidth=2160');
INSERT IGNORE INTO product_images (id,product_id,hinh_anh) VALUES (41,2,'https://image.hm.com/assets/hm/84/31/8431aa68871d4ec439569025616fc9b652ddd628.jpg?imwidth=2160');
INSERT IGNORE INTO product_images (id,product_id,hinh_anh) VALUES (42,2,'https://image.hm.com/assets/hm/c1/71/c171e8ee346586c00efd7954f393f58d57336b33.jpg?imwidth=2160');
INSERT IGNORE INTO product_images (id,product_id,hinh_anh) VALUES (43,2,'https://image.hm.com/assets/hm/23/42/2342fdca401658a8f32db6bde672f8e6af236aea.jpg?imwidth=2160');
INSERT IGNORE INTO product_images (id,product_id,hinh_anh) VALUES (44,2,'https://image.hm.com/assets/hm/b0/90/b0906129c1125eaa3823b2e5af2d3f987949f1c7.jpg?imwidth=2160');
INSERT IGNORE INTO product_images (id,product_id,hinh_anh) VALUES (45,3,'https://image.hm.com/assets/hm/6d/5a/6d5a8aa265040bcfe46d21b94f6aafe82763d940.jpg?imwidth=2160');
INSERT IGNORE INTO product_images (id,product_id,hinh_anh) VALUES (46,3,'https://image.hm.com/assets/hm/33/f3/33f3572a9175e573fa45327e09a050f67a879863.jpg?imwidth=2160');
INSERT IGNORE INTO product_images (id,product_id,hinh_anh) VALUES (47,3,'https://image.hm.com/assets/hm/64/96/6496b8798595fc5b6f609b4b106a14ac75ba65f8.jpg?imwidth=2160');
INSERT IGNORE INTO product_images (id,product_id,hinh_anh) VALUES (48,3,'https://image.hm.com/assets/hm/8e/dc/8edc1efb75c1a7f73d17f0bb5b9aaa3a63d063b4.jpg?imwidth=2160');
INSERT IGNORE INTO product_images (id,product_id,hinh_anh) VALUES (49,4,'https://deme.vn/wp-content/uploads/2025/08/DSC05849-scaled.jpg');
INSERT IGNORE INTO product_images (id,product_id,hinh_anh) VALUES (50,4,'https://deme.vn/wp-content/uploads/2025/08/156.png');
INSERT IGNORE INTO product_images (id,product_id,hinh_anh) VALUES (51,4,'https://deme.vn/wp-content/uploads/2025/08/DSC05853-scaled.jpg');
INSERT IGNORE INTO product_images (id,product_id,hinh_anh) VALUES (52,4,'https://deme.vn/wp-content/uploads/2025/08/DSC05852-scaled.jpg');
INSERT IGNORE INTO product_images (id,product_id,hinh_anh) VALUES (53,5,'https://n7media.coolmate.me/uploads/2026/03/19/ao-thun-nam-phoi-tay-exdry-proactive-wc-19-xanh-la_65.jpg?aio=w-1100');
INSERT IGNORE INTO product_images (id,product_id,hinh_anh) VALUES (54,5,'https://n7media.coolmate.me/uploads/2026/03/19/ao-thun-nam-phoi-tay-exdry-proactive-wc-16825-xanh-la_66.jpg?aio=w-1100');
INSERT IGNORE INTO product_images (id,product_id,hinh_anh) VALUES (55,5,'https://n7media.coolmate.me/uploads/2026/03/19/ao-thun-nam-phoi-tay-exdry-proactive-wc-64-xanh-la_55.jpg?aio=w-1100');
INSERT IGNORE INTO product_images (id,product_id,hinh_anh) VALUES (56,5,'https://n7media.coolmate.me/uploads/2026/03/19/ao-thun-nam-phoi-tay-exdry-proactive-wc-16817-xanh-la_71.jpg?aio=w-1100');
INSERT IGNORE INTO product_images (id,product_id,hinh_anh) VALUES (57,59,'https://4menshop.com/images/thumbs/2023/10/quan-jogger-ra-cheo-form-slimfit-jo012-mau-den-18272-slide-products-653a08da6ccf5.jpg');
INSERT IGNORE INTO product_images (id,product_id,hinh_anh) VALUES (58,59,'https://4menshop.com/images/thumbs/2023/10/quan-jogger-ra-cheo-form-slimfit-jo012-mau-den-18272-slide-products-653a08da8bbbf.jpg');
INSERT IGNORE INTO product_images (id,product_id,hinh_anh) VALUES (59,59,'https://4menshop.com/images/thumbs/2023/10/quan-jogger-ra-cheo-form-slimfit-jo012-mau-den-18272-slide-products-653a08dac2d73.jpg');
INSERT IGNORE INTO product_images (id,product_id,hinh_anh) VALUES (60,59,'https://4menshop.com/images/thumbs/2023/10/quan-jogger-ra-cheo-form-slimfit-jo012-mau-den-18272-slide-products-653a08daf06b4.jpg');
INSERT IGNORE INTO product_images (id,product_id,hinh_anh) VALUES (61,58,'https://cdn.hstatic.net/products/200000053174/9qavc539ttt-799k__6_-1080x1080_d2c82bd3385a4a70926c3ebbcf3b3818_master.jpg');
INSERT IGNORE INTO product_images (id,product_id,hinh_anh) VALUES (62,58,'https://cdn.hstatic.net/products/200000053174/9qavc539ttt-799k__1_-1080x1080_1a50c6f6f95b4484b5138dd1a3eee60a_master.jpg');
INSERT IGNORE INTO product_images (id,product_id,hinh_anh) VALUES (63,58,'https://cdn.hstatic.net/products/200000053174/9qavc539ttt-799k__2_-1080x1080_7734ae3af0584830ad5e266b49b89d36_master.jpg');
INSERT IGNORE INTO product_images (id,product_id,hinh_anh) VALUES (64,58,'https://cdn.hstatic.net/products/200000053174/9qavc539ttt-799k__5_-1080x1080_39bb98daf6974d5e8147330037c9fdd6_master.jpg');

-- customers
INSERT IGNORE INTO customers (id,ma_kh,ten_kh,sdt,email,password,dia_chi,loai_kh,diem_tich_luy,trang_thai) VALUES (1,'KH001','Nguyễn Văn Minh','0912345678','minhnguyen@gmail.com','minh123','Hà Nội','VIP',1000,'Hoạt động');
INSERT IGNORE INTO customers (id,ma_kh,ten_kh,sdt,email,password,dia_chi,loai_kh,diem_tich_luy,trang_thai) VALUES (2,'KH002','Trần Thị Huyền','0987654321','huyentran@gmail.com','huyen123','TP. Hồ Chí Minh','Thường',200,'Hoạt động');
INSERT IGNORE INTO customers (id,ma_kh,ten_kh,sdt,email,password,dia_chi,loai_kh,diem_tich_luy,trang_thai) VALUES (3,'KH003','Lê Văn Hùng','0934567890','hunglv@gmail.com','hung123','Đà Nẵng','VIP',1500,'Hoạt động');
INSERT IGNORE INTO customers (id,ma_kh,ten_kh,sdt,email,password,dia_chi,loai_kh,diem_tich_luy,trang_thai) VALUES (4,'KH004','Phạm Hoàng Lan','0976543210','lanph@gmail.com','lan123','Hải Phòng','Thường',300,'Hoạt động');
INSERT IGNORE INTO customers (id,ma_kh,ten_kh,sdt,email,password,dia_chi,loai_kh,diem_tich_luy,trang_thai) VALUES (5,'KH005','Đặng Thị Hoa','0901234567','hoadt@gmail.com','hoa123','Huế','VIP',2000,'Hoạt động');
INSERT IGNORE INTO customers (id,ma_kh,ten_kh,sdt,email,password,dia_chi,loai_kh,diem_tich_luy,trang_thai) VALUES (6,'KH006','Ngô Văn Kiên','0962345678','kienngo@gmail.com','kien123','Cần Thơ','Thường',100,'Hoạt động');
INSERT IGNORE INTO customers (id,ma_kh,ten_kh,sdt,email,password,dia_chi,loai_kh,diem_tich_luy,trang_thai) VALUES (7,'KH007','Bùi Quốc Anh','0911111111','quocanh@gmail.com','anh123','Hà Nội','Thường',150,'Hoạt động');
INSERT IGNORE INTO customers (id,ma_kh,ten_kh,sdt,email,password,dia_chi,loai_kh,diem_tich_luy,trang_thai) VALUES (8,'KH008','Phan Thị Mai','0922222222','maiphan@gmail.com','mai123','TP. Hồ Chí Minh','VIP',2500,'Hoạt động');
INSERT IGNORE INTO customers (id,ma_kh,ten_kh,sdt,email,password,dia_chi,loai_kh,diem_tich_luy,trang_thai) VALUES (9,'KH009','Võ Minh Tâm','0933333333','tamvo@gmail.com','tam123','Đà Nẵng','Thường',400,'Hoạt động');
INSERT IGNORE INTO customers (id,ma_kh,ten_kh,sdt,email,password,dia_chi,loai_kh,diem_tich_luy,trang_thai) VALUES (10,'KH010','Đỗ Thanh Tùng','0944444444','tungdo@gmail.com','tung123','Nha Trang','VIP',1800,'Hoạt động');
INSERT IGNORE INTO customers (id,ma_kh,ten_kh,sdt,email,password,dia_chi,loai_kh,diem_tich_luy,trang_thai) VALUES (11,'KH011','Trịnh Gia Bảo','0955555555','baotg@gmail.com','bao123','Hải Dương','Thường',120,'Hoạt động');
INSERT IGNORE INTO customers (id,ma_kh,ten_kh,sdt,email,password,dia_chi,loai_kh,diem_tich_luy,trang_thai) VALUES (12,'KH012','Lý Khánh Linh','0966666666','linhly@gmail.com','linh123','Bình Dương','VIP',3000,'Hoạt động');
INSERT IGNORE INTO customers (id,ma_kh,ten_kh,sdt,email,password,dia_chi,loai_kh,diem_tich_luy,trang_thai) VALUES (13,'KH013','Huỳnh Công Đức','0977777777','duchuynh@gmail.com','duc123','Quảng Nam','Thường',250,'Hoạt động');
INSERT IGNORE INTO customers (id,ma_kh,ten_kh,sdt,email,password,dia_chi,loai_kh,diem_tich_luy,trang_thai) VALUES (14,'KH014','Nguyễn Thảo Vy','0988888888','thaovy@gmail.com','vy123','Vũng Tàu','VIP',2200,'Hoạt động');
INSERT IGNORE INTO customers (id,ma_kh,ten_kh,sdt,email,password,dia_chi,loai_kh,diem_tich_luy,trang_thai) VALUES (15,'KH015','Phạm Quốc Cường','0999999999','cuongpham@gmail.com','cuong123','Bắc Ninh','Thường',350,'Hoạt động');
INSERT IGNORE INTO customers (id,ma_kh,ten_kh,sdt,email,password,dia_chi,loai_kh,diem_tich_luy,trang_thai) VALUES (16,'KH016','Trần Nhật Nam','0901111222','namtran@gmail.com','nam123','Cần Thơ','VIP',2700,'Hoạt động');
INSERT IGNORE INTO customers (id,ma_kh,ten_kh,sdt,email,password,dia_chi,loai_kh,diem_tich_luy,trang_thai) VALUES (17,'KH017','Lê Phương Anh','0912222333','phuonganh@gmail.com','anh456','Hà Nội','Thường',180,'Hoạt động');
INSERT IGNORE INTO customers (id,ma_kh,ten_kh,sdt,email,password,dia_chi,loai_kh,diem_tich_luy,trang_thai) VALUES (18,'KH018','Đinh Tuấn Kiệt','0923333444','kietdinh@gmail.com','kiet123','TP. Hồ Chí Minh','VIP',3200,'Hoạt động');
INSERT IGNORE INTO customers (id,ma_kh,ten_kh,sdt,email,password,dia_chi,loai_kh,diem_tich_luy,trang_thai) VALUES (19,'KH019','Nguyễn Minh Khoa','0934444555','khoamn@gmail.com','khoa123','Đồng Nai','Thường',210,'Hoạt động');
INSERT IGNORE INTO customers (id,ma_kh,ten_kh,sdt,email,password,dia_chi,loai_kh,diem_tich_luy,trang_thai) VALUES (20,'KH020','Vũ Hải Yến','0945555666','yenvu@gmail.com','yen123','Huế','VIP',2600,'Hoạt động');
INSERT IGNORE INTO customers (id,ma_kh,ten_kh,sdt,email,password,dia_chi,loai_kh,diem_tich_luy,trang_thai) VALUES (21,'KH021','Bùi Lê Hậu',NULL,'hau.customer@gmail.com','123456','Hà Nội','Thường',0,'Hoạt động');

-- carts
INSERT IGNORE INTO carts (id,ma_kh,tong_tien,trang_thai) VALUES (1,1,3500000,'Đang xử lý');
INSERT IGNORE INTO carts (id,ma_kh,tong_tien,trang_thai) VALUES (2,2,1200000,'Đang xử lý');
INSERT IGNORE INTO carts (id,ma_kh,tong_tien,trang_thai) VALUES (3,3,1500000,'Đã thanh toán');
INSERT IGNORE INTO carts (id,ma_kh,tong_tien,trang_thai) VALUES (4,4,1200000,'Đang xử lý');
INSERT IGNORE INTO carts (id,ma_kh,tong_tien,trang_thai) VALUES (5,5,5000000,'Đang xử lý');
INSERT IGNORE INTO carts (id,ma_kh,tong_tien,trang_thai) VALUES (6,6,2500000,'Đã thanh toán');
INSERT IGNORE INTO carts (id,ma_kh,tong_tien,trang_thai) VALUES (7,1,4000000,'Đang xử lý');
INSERT IGNORE INTO carts (id,ma_kh,tong_tien,trang_thai) VALUES (8,2,1600000,'Đang xử lý');
INSERT IGNORE INTO carts (id,ma_kh,tong_tien,trang_thai) VALUES (9,3,3000000,'Đã thanh toán');
INSERT IGNORE INTO carts (id,ma_kh,tong_tien,trang_thai) VALUES (10,21,2817000,'Đã thanh toán');
INSERT IGNORE INTO carts (id,ma_kh,tong_tien,trang_thai) VALUES (11,21,398000,'Đã thanh toán');
INSERT IGNORE INTO carts (id,ma_kh,tong_tien,trang_thai) VALUES (12,21,199000,'Đã thanh toán');
INSERT IGNORE INTO carts (id,ma_kh,tong_tien,trang_thai) VALUES (13,21,249000,'Đã thanh toán');
INSERT IGNORE INTO carts (id,ma_kh,tong_tien,trang_thai) VALUES (14,21,199000,'Đã thanh toán');
INSERT IGNORE INTO carts (id,ma_kh,tong_tien,trang_thai) VALUES (15,21,199000,'Đã thanh toán');
INSERT IGNORE INTO carts (id,ma_kh,tong_tien,trang_thai) VALUES (16,21,229000,'Đã thanh toán');
INSERT IGNORE INTO carts (id,ma_kh,tong_tien,trang_thai) VALUES (17,21,597000,'Đã thanh toán');
INSERT IGNORE INTO carts (id,ma_kh,tong_tien,trang_thai) VALUES (18,21,816000,'Đang xử lý');
INSERT IGNORE INTO carts (id,ma_kh,tong_tien,trang_thai) VALUES (19,21,199000,'Đã thanh toán');

-- cart_details
INSERT IGNORE INTO cart_details (id,ma_gh,ma_sp,so_luong,gia_ban,size) VALUES (1,1,1,1,3500000,'M');
INSERT IGNORE INTO cart_details (id,ma_gh,ma_sp,so_luong,gia_ban,size) VALUES (2,2,2,1,1200000,'L');
INSERT IGNORE INTO cart_details (id,ma_gh,ma_sp,so_luong,gia_ban,size) VALUES (3,3,3,1,1500000,'XL');
INSERT IGNORE INTO cart_details (id,ma_gh,ma_sp,so_luong,gia_ban,size) VALUES (4,4,4,1,1500000,'M');
INSERT IGNORE INTO cart_details (id,ma_gh,ma_sp,so_luong,gia_ban,size) VALUES (5,5,5,2,1600000,'L');
INSERT IGNORE INTO cart_details (id,ma_gh,ma_sp,so_luong,gia_ban,size) VALUES (6,6,6,1,800000,'M');
INSERT IGNORE INTO cart_details (id,ma_gh,ma_sp,so_luong,gia_ban,size) VALUES (7,1,7,1,3800000,'L');
INSERT IGNORE INTO cart_details (id,ma_gh,ma_sp,so_luong,gia_ban,size) VALUES (8,2,8,1,2500000,'XL');
INSERT IGNORE INTO cart_details (id,ma_gh,ma_sp,so_luong,gia_ban,size) VALUES (9,3,9,1,1500000,'M');
INSERT IGNORE INTO cart_details (id,ma_gh,ma_sp,so_luong,gia_ban,size) VALUES (10,4,10,1,5000000,'L');
INSERT IGNORE INTO cart_details (id,ma_gh,ma_sp,so_luong,gia_ban,size) VALUES (11,5,11,1,2200000,'M');
INSERT IGNORE INTO cart_details (id,ma_gh,ma_sp,so_luong,gia_ban,size) VALUES (12,6,12,1,2700000,'L');
INSERT IGNORE INTO cart_details (id,ma_gh,ma_sp,so_luong,gia_ban,size) VALUES (13,1,13,1,3400000,'XL');
INSERT IGNORE INTO cart_details (id,ma_gh,ma_sp,so_luong,gia_ban,size) VALUES (16,10,1,4,199000,'L');
INSERT IGNORE INTO cart_details (id,ma_gh,ma_sp,so_luong,gia_ban,size) VALUES (17,10,5,4,219000,'XL');
INSERT IGNORE INTO cart_details (id,ma_gh,ma_sp,so_luong,gia_ban,size) VALUES (18,10,13,1,339000,'M');
INSERT IGNORE INTO cart_details (id,ma_gh,ma_sp,so_luong,gia_ban,size) VALUES (19,10,1,3,199000,'M');
INSERT IGNORE INTO cart_details (id,ma_gh,ma_sp,so_luong,gia_ban,size) VALUES (20,10,8,1,209000,'M');
INSERT IGNORE INTO cart_details (id,ma_gh,ma_sp,so_luong,gia_ban,size) VALUES (21,11,1,2,199000,'M');
INSERT IGNORE INTO cart_details (id,ma_gh,ma_sp,so_luong,gia_ban,size) VALUES (25,12,2,1,199000,'XL');
INSERT IGNORE INTO cart_details (id,ma_gh,ma_sp,so_luong,gia_ban,size) VALUES (26,13,4,1,249000,'M');
INSERT IGNORE INTO cart_details (id,ma_gh,ma_sp,so_luong,gia_ban,size) VALUES (29,14,1,1,199000,'M');
INSERT IGNORE INTO cart_details (id,ma_gh,ma_sp,so_luong,gia_ban,size) VALUES (38,17,2,3,199000,'S');
INSERT IGNORE INTO cart_details (id,ma_gh,ma_sp,so_luong,gia_ban,size) VALUES (39,18,1,2,199000,'S');
INSERT IGNORE INTO cart_details (id,ma_gh,ma_sp,so_luong,gia_ban,size) VALUES (40,18,2,1,199000,'S');
INSERT IGNORE INTO cart_details (id,ma_gh,ma_sp,so_luong,gia_ban,size) VALUES (41,18,5,1,219000,'S');

-- invoices
INSERT IGNORE INTO invoices (id,ma_hd,ngay_lap,ma_kh,ma_gh,tong_tien,trang_thai,phuong_thuc_tt,loai_hoa_don,ma_nv) VALUES (1,'HD001','2024-11-20 10:30:00',1,1,7000000,'Đã thanh toán','COD','Ship COD',1);
INSERT IGNORE INTO invoices (id,ma_hd,ngay_lap,ma_kh,ma_gh,tong_tien,trang_thai,phuong_thuc_tt,loai_hoa_don,ma_nv) VALUES (2,'HD002','2024-11-21 14:00:00',2,2,1200000,'Đã thanh toán','Chuyển khoản','Bank',4);
INSERT IGNORE INTO invoices (id,ma_hd,ngay_lap,ma_kh,ma_gh,tong_tien,trang_thai,phuong_thuc_tt,loai_hoa_don,ma_nv) VALUES (3,'HD003','2024-11-22 16:00:00',3,3,1500000,'Chờ thanh toán','Chuyển khoản','Bank',1);
INSERT IGNORE INTO invoices (id,ma_hd,ngay_lap,ma_kh,ma_gh,tong_tien,trang_thai,phuong_thuc_tt,loai_hoa_don,ma_nv) VALUES (4,'HD004','2024-11-23 12:00:00',4,4,1200000,'Đã thanh toán','Chuyển khoản','Bank',5);
INSERT IGNORE INTO invoices (id,ma_hd,ngay_lap,ma_kh,ma_gh,tong_tien,trang_thai,phuong_thuc_tt,loai_hoa_don,ma_nv) VALUES (5,'HD005','2024-11-24 14:30:00',5,5,5000000,'Chờ thanh toán','COD','Ship COD',4);
INSERT IGNORE INTO invoices (id,ma_hd,ngay_lap,ma_kh,ma_gh,tong_tien,trang_thai,phuong_thuc_tt,loai_hoa_don,ma_nv) VALUES (6,'HD006','2024-11-25 16:00:00',6,6,2500000,'Đã thanh toán','Chuyển khoản','Bank',1);
INSERT IGNORE INTO invoices (id,ma_hd,ngay_lap,ma_kh,ma_gh,tong_tien,trang_thai,phuong_thuc_tt,loai_hoa_don,ma_nv) VALUES (7,'HD007','2024-11-26 10:00:00',1,1,4000000,'Chờ thanh toán','COD','Ship COD',5);
INSERT IGNORE INTO invoices (id,ma_hd,ngay_lap,ma_kh,ma_gh,tong_tien,trang_thai,phuong_thuc_tt,loai_hoa_don,ma_nv) VALUES (8,'HD008','2024-11-27 09:30:00',2,2,1600000,'Chờ thanh toán','Chuyển khoản','Bank',4);
INSERT IGNORE INTO invoices (id,ma_hd,ngay_lap,ma_kh,ma_gh,tong_tien,trang_thai,phuong_thuc_tt,loai_hoa_don,ma_nv) VALUES (9,'HD009','2024-11-27 15:00:00',3,3,3000000,'Đã thanh toán','Chuyển khoản','Bank',1);
INSERT IGNORE INTO invoices (id,ma_hd,ngay_lap,ma_kh,ma_gh,tong_tien,trang_thai,phuong_thuc_tt,loai_hoa_don,ma_nv) VALUES (10,'HD010','2024-12-05 09:15:00',4,4,5000000,'Đã thanh toán','Chuyển khoản','Bank',2);
INSERT IGNORE INTO invoices (id,ma_hd,ngay_lap,ma_kh,ma_gh,tong_tien,trang_thai,phuong_thuc_tt,loai_hoa_don,ma_nv) VALUES (11,'HD011','2024-12-20 14:45:00',5,5,6100000,'Đã thanh toán','Chuyển khoản','Bank',3);
INSERT IGNORE INTO invoices (id,ma_hd,ngay_lap,ma_kh,ma_gh,tong_tien,trang_thai,phuong_thuc_tt,loai_hoa_don,ma_nv) VALUES (12,'HD012','2025-01-03 11:00:00',2,2,3800000,'Chờ thanh toán','Chuyển khoản','Bank',4);
INSERT IGNORE INTO invoices (id,ma_hd,ngay_lap,ma_kh,ma_gh,tong_tien,trang_thai,phuong_thuc_tt,loai_hoa_don,ma_nv) VALUES (13,'HD013','2025-01-18 16:20:00',1,1,7400000,'Đã thanh toán','COD','Ship COD',5);
INSERT IGNORE INTO invoices (id,ma_hd,ngay_lap,ma_kh,ma_gh,tong_tien,trang_thai,phuong_thuc_tt,loai_hoa_don,ma_nv) VALUES (14,'HD014','2025-02-02 13:30:00',6,6,2700000,'Đã thanh toán','Chuyển khoản','Bank',1);
INSERT IGNORE INTO invoices (id,ma_hd,ngay_lap,ma_kh,ma_gh,tong_tien,trang_thai,phuong_thuc_tt,loai_hoa_don,ma_nv) VALUES (15,'HD015','2025-02-14 18:10:00',3,3,6000000,'Chờ thanh toán','Chuyển khoản','Bank',2);
INSERT IGNORE INTO invoices (id,ma_hd,ngay_lap,ma_kh,ma_gh,tong_tien,trang_thai,phuong_thuc_tt,loai_hoa_don,ma_nv) VALUES (16,'HD016','2025-03-01 10:05:00',4,4,4200000,'Đã thanh toán','Chuyển khoản','Bank',3);
INSERT IGNORE INTO invoices (id,ma_hd,ngay_lap,ma_kh,ma_gh,tong_tien,trang_thai,phuong_thuc_tt,loai_hoa_don,ma_nv) VALUES (17,'HD017','2025-03-15 15:40:00',2,2,3100000,'Đã thanh toán','COD','Ship COD',4);
INSERT IGNORE INTO invoices (id,ma_hd,ngay_lap,ma_kh,ma_gh,tong_tien,trang_thai,phuong_thuc_tt,loai_hoa_don,ma_nv) VALUES (18,'HD018','2025-04-10 09:50:00',5,5,8000000,'Chờ thanh toán','Chuyển khoản','Bank',1);
INSERT IGNORE INTO invoices (id,ma_hd,ngay_lap,ma_kh,ma_gh,tong_tien,trang_thai,phuong_thuc_tt,loai_hoa_don,ma_nv) VALUES (19,'HD019','2025-05-22 17:30:00',1,1,2900000,'Đã thanh toán','Chuyển khoản','Bank',5);
INSERT IGNORE INTO invoices (id,ma_hd,ngay_lap,ma_kh,ma_gh,tong_tien,trang_thai,phuong_thuc_tt,loai_hoa_don,ma_nv) VALUES (20,'HD020','2025-06-30 20:00:00',6,6,9500000,'Đã thanh toán','Chuyển khoản','Bank',2);
INSERT IGNORE INTO invoices (id,ma_hd,ngay_lap,ma_kh,ma_gh,tong_tien,trang_thai,phuong_thuc_tt,loai_hoa_don,ma_nv) VALUES (21,'HD021','2023-01-15 10:00:00',1,1,3500000,'Đã thanh toán','COD','Ship COD',1);
INSERT IGNORE INTO invoices (id,ma_hd,ngay_lap,ma_kh,ma_gh,tong_tien,trang_thai,phuong_thuc_tt,loai_hoa_don,ma_nv) VALUES (22,'HD022','2023-03-22 14:30:00',2,2,6400000,'Đã thanh toán','Chuyển khoản','Bank',4);
INSERT IGNORE INTO invoices (id,ma_hd,ngay_lap,ma_kh,ma_gh,tong_tien,trang_thai,phuong_thuc_tt,loai_hoa_don,ma_nv) VALUES (23,'HD023','2023-06-10 16:45:00',3,3,4200000,'Đã thanh toán','Chuyển khoản','Bank',2);
INSERT IGNORE INTO invoices (id,ma_hd,ngay_lap,ma_kh,ma_gh,tong_tien,trang_thai,phuong_thuc_tt,loai_hoa_don,ma_nv) VALUES (24,'HD024','2023-09-05 11:20:00',4,4,7800000,'Đã thanh toán','Chuyển khoản','Bank',3);
INSERT IGNORE INTO invoices (id,ma_hd,ngay_lap,ma_kh,ma_gh,tong_tien,trang_thai,phuong_thuc_tt,loai_hoa_don,ma_nv) VALUES (25,'HD025','2023-12-18 19:10:00',5,5,5000000,'Chờ thanh toán','COD','Ship COD',5);
INSERT IGNORE INTO invoices (id,ma_hd,ngay_lap,ma_kh,ma_gh,tong_tien,trang_thai,phuong_thuc_tt,loai_hoa_don,ma_nv) VALUES (26,'HD026','2026-01-08 09:30:00',6,6,2900000,'Đã thanh toán','Chuyển khoản','Bank',1);
INSERT IGNORE INTO invoices (id,ma_hd,ngay_lap,ma_kh,ma_gh,tong_tien,trang_thai,phuong_thuc_tt,loai_hoa_don,ma_nv) VALUES (27,'HD027','2026-02-14 15:00:00',1,1,7000000,'Đã thanh toán','Chuyển khoản','Bank',2);
INSERT IGNORE INTO invoices (id,ma_hd,ngay_lap,ma_kh,ma_gh,tong_tien,trang_thai,phuong_thuc_tt,loai_hoa_don,ma_nv) VALUES (28,'HD028','2026-04-20 17:40:00',3,3,3800000,'Chờ thanh toán','Chuyển khoản','Bank',4);
INSERT IGNORE INTO invoices (id,ma_hd,ngay_lap,ma_kh,ma_gh,tong_tien,trang_thai,phuong_thuc_tt,loai_hoa_don,ma_nv) VALUES (29,'HD029','2026-07-11 12:10:00',2,2,8400000,'Đã thanh toán','COD','Ship COD',5);
INSERT IGNORE INTO invoices (id,ma_hd,ngay_lap,ma_kh,ma_gh,tong_tien,trang_thai,phuong_thuc_tt,loai_hoa_don,ma_nv) VALUES (30,'HD030','2026-11-25 18:25:00',4,4,9500000,'Đã thanh toán','Chuyển khoản','Bank',3);
INSERT IGNORE INTO invoices (id,ma_hd,ngay_lap,ma_kh,ma_gh,tong_tien,trang_thai,phuong_thuc_tt,loai_hoa_don,ma_nv) VALUES (31,'HD1778262015','2026-05-08 17:40:15',21,10,2817000,'Đang giao','Chuyển khoản','Bank',1);
INSERT IGNORE INTO invoices (id,ma_hd,ngay_lap,ma_kh,ma_gh,tong_tien,trang_thai,phuong_thuc_tt,loai_hoa_don,ma_nv) VALUES (32,'HD1778262060','2026-05-08 17:41:00',21,11,398000,'Đã hủy','Chuyển khoản','Bank',1);
INSERT IGNORE INTO invoices (id,ma_hd,ngay_lap,ma_kh,ma_gh,tong_tien,trang_thai,phuong_thuc_tt,loai_hoa_don,ma_nv) VALUES (33,'HD1778264385','2026-05-08 18:19:45',21,12,199000,'Đã hủy','COD','Ship COD',1);
INSERT IGNORE INTO invoices (id,ma_hd,ngay_lap,ma_kh,ma_gh,tong_tien,trang_thai,phuong_thuc_tt,loai_hoa_don,ma_nv) VALUES (34,'HD1778308317','2026-05-09 06:31:57',21,13,249000,'Đã hủy','COD','Ship COD',1);
INSERT IGNORE INTO invoices (id,ma_hd,ngay_lap,ma_kh,ma_gh,tong_tien,trang_thai,phuong_thuc_tt,loai_hoa_don,ma_nv) VALUES (35,'HD1778308626','2026-05-09 06:37:06',21,15,199000,'Đã hủy','Chuyển khoản','Bank',1);
INSERT IGNORE INTO invoices (id,ma_hd,ngay_lap,ma_kh,ma_gh,tong_tien,trang_thai,phuong_thuc_tt,loai_hoa_don,ma_nv) VALUES (36,'HD1778308687','2026-05-09 06:38:07',21,16,229000,'Đang giao','Chuyển khoản','Bank',1);
INSERT IGNORE INTO invoices (id,ma_hd,ngay_lap,ma_kh,ma_gh,tong_tien,trang_thai,phuong_thuc_tt,loai_hoa_don,ma_nv) VALUES (37,'HD1778313917','2026-05-09 08:05:17',21,14,199000,'Đã hủy','COD','Ship COD',1);
INSERT IGNORE INTO invoices (id,ma_hd,ngay_lap,ma_kh,ma_gh,tong_tien,trang_thai,phuong_thuc_tt,loai_hoa_don,ma_nv) VALUES (38,'HD1778525840','2026-05-11 18:57:20',21,17,597000,'Đã hủy','COD','Ship COD',1);
INSERT IGNORE INTO invoices (id,ma_hd,ngay_lap,ma_kh,ma_gh,tong_tien,trang_thai,phuong_thuc_tt,loai_hoa_don,ma_nv) VALUES (39,'HD1778525924','2026-05-11 18:58:44',21,19,199000,'Đã hủy','Chuyển khoản','Bank',1);

-- invoice_details
INSERT IGNORE INTO invoice_details (id,ma_hd,ma_sp,so_luong,gia_ban,size) VALUES (1,1,1,2,3500000,'M');
INSERT IGNORE INTO invoice_details (id,ma_hd,ma_sp,so_luong,gia_ban,size) VALUES (2,2,2,1,1200000,'L');
INSERT IGNORE INTO invoice_details (id,ma_hd,ma_sp,so_luong,gia_ban,size) VALUES (3,3,3,1,1500000,'XL');
INSERT IGNORE INTO invoice_details (id,ma_hd,ma_sp,so_luong,gia_ban,size) VALUES (4,4,4,1,1500000,'M');
INSERT IGNORE INTO invoice_details (id,ma_hd,ma_sp,so_luong,gia_ban,size) VALUES (5,5,5,2,1600000,'L');
INSERT IGNORE INTO invoice_details (id,ma_hd,ma_sp,so_luong,gia_ban,size) VALUES (6,6,6,1,800000,'M');
INSERT IGNORE INTO invoice_details (id,ma_hd,ma_sp,so_luong,gia_ban,size) VALUES (7,7,7,1,3800000,'L');
INSERT IGNORE INTO invoice_details (id,ma_hd,ma_sp,so_luong,gia_ban,size) VALUES (8,8,8,1,2500000,'XL');
INSERT IGNORE INTO invoice_details (id,ma_hd,ma_sp,so_luong,gia_ban,size) VALUES (9,9,9,1,1500000,'M');
INSERT IGNORE INTO invoice_details (id,ma_hd,ma_sp,so_luong,gia_ban,size) VALUES (10,10,10,1,5000000,'L');
INSERT IGNORE INTO invoice_details (id,ma_hd,ma_sp,so_luong,gia_ban,size) VALUES (11,11,5,2,1600000,'M');
INSERT IGNORE INTO invoice_details (id,ma_hd,ma_sp,so_luong,gia_ban,size) VALUES (12,11,6,1,2900000,'L');
INSERT IGNORE INTO invoice_details (id,ma_hd,ma_sp,so_luong,gia_ban,size) VALUES (13,12,7,1,3800000,'M');
INSERT IGNORE INTO invoice_details (id,ma_hd,ma_sp,so_luong,gia_ban,size) VALUES (14,13,1,1,3500000,'M');
INSERT IGNORE INTO invoice_details (id,ma_hd,ma_sp,so_luong,gia_ban,size) VALUES (15,13,7,1,3800000,'L');
INSERT IGNORE INTO invoice_details (id,ma_hd,ma_sp,so_luong,gia_ban,size) VALUES (16,14,12,1,2700000,'L');
INSERT IGNORE INTO invoice_details (id,ma_hd,ma_sp,so_luong,gia_ban,size) VALUES (17,15,2,1,4200000,'M');
INSERT IGNORE INTO invoice_details (id,ma_hd,ma_sp,so_luong,gia_ban,size) VALUES (18,15,9,1,1500000,'XL');
INSERT IGNORE INTO invoice_details (id,ma_hd,ma_sp,so_luong,gia_ban,size) VALUES (19,16,11,1,2200000,'M');
INSERT IGNORE INTO invoice_details (id,ma_hd,ma_sp,so_luong,gia_ban,size) VALUES (20,16,13,1,2000000,'L');
INSERT IGNORE INTO invoice_details (id,ma_hd,ma_sp,so_luong,gia_ban,size) VALUES (21,17,14,1,3100000,'M');
INSERT IGNORE INTO invoice_details (id,ma_hd,ma_sp,so_luong,gia_ban,size) VALUES (22,18,10,1,5000000,'L');
INSERT IGNORE INTO invoice_details (id,ma_hd,ma_sp,so_luong,gia_ban,size) VALUES (23,18,1,1,3000000,'M');
INSERT IGNORE INTO invoice_details (id,ma_hd,ma_sp,so_luong,gia_ban,size) VALUES (24,19,6,1,2900000,'L');
INSERT IGNORE INTO invoice_details (id,ma_hd,ma_sp,so_luong,gia_ban,size) VALUES (25,20,7,2,3800000,'L');
INSERT IGNORE INTO invoice_details (id,ma_hd,ma_sp,so_luong,gia_ban,size) VALUES (26,20,3,1,1900000,'M');
INSERT IGNORE INTO invoice_details (id,ma_hd,ma_sp,so_luong,gia_ban,size) VALUES (27,21,1,1,3500000,'M');
INSERT IGNORE INTO invoice_details (id,ma_hd,ma_sp,so_luong,gia_ban,size) VALUES (28,22,5,2,1600000,'L');
INSERT IGNORE INTO invoice_details (id,ma_hd,ma_sp,so_luong,gia_ban,size) VALUES (29,22,6,1,3200000,'M');
INSERT IGNORE INTO invoice_details (id,ma_hd,ma_sp,so_luong,gia_ban,size) VALUES (30,23,2,1,4200000,'L');
INSERT IGNORE INTO invoice_details (id,ma_hd,ma_sp,so_luong,gia_ban,size) VALUES (31,24,7,2,3800000,'L');
INSERT IGNORE INTO invoice_details (id,ma_hd,ma_sp,so_luong,gia_ban,size) VALUES (32,25,10,1,5000000,'L');
INSERT IGNORE INTO invoice_details (id,ma_hd,ma_sp,so_luong,gia_ban,size) VALUES (33,26,6,1,2900000,'L');
INSERT IGNORE INTO invoice_details (id,ma_hd,ma_sp,so_luong,gia_ban,size) VALUES (34,27,1,1,3500000,'M');
INSERT IGNORE INTO invoice_details (id,ma_hd,ma_sp,so_luong,gia_ban,size) VALUES (35,27,11,1,3500000,'L');
INSERT IGNORE INTO invoice_details (id,ma_hd,ma_sp,so_luong,gia_ban,size) VALUES (36,28,7,1,3800000,'L');
INSERT IGNORE INTO invoice_details (id,ma_hd,ma_sp,so_luong,gia_ban,size) VALUES (37,29,2,2,4200000,'M');
INSERT IGNORE INTO invoice_details (id,ma_hd,ma_sp,so_luong,gia_ban,size) VALUES (38,30,7,2,3800000,'L');
INSERT IGNORE INTO invoice_details (id,ma_hd,ma_sp,so_luong,gia_ban,size) VALUES (39,30,3,1,1900000,'M');
INSERT IGNORE INTO invoice_details (id,ma_hd,ma_sp,so_luong,gia_ban,size) VALUES (40,31,1,4,199000,'L');
INSERT IGNORE INTO invoice_details (id,ma_hd,ma_sp,so_luong,gia_ban,size) VALUES (41,31,5,4,219000,'XL');
INSERT IGNORE INTO invoice_details (id,ma_hd,ma_sp,so_luong,gia_ban,size) VALUES (42,31,13,1,339000,'M');
INSERT IGNORE INTO invoice_details (id,ma_hd,ma_sp,so_luong,gia_ban,size) VALUES (43,31,1,3,199000,'M');
INSERT IGNORE INTO invoice_details (id,ma_hd,ma_sp,so_luong,gia_ban,size) VALUES (44,31,8,1,209000,'M');
INSERT IGNORE INTO invoice_details (id,ma_hd,ma_sp,so_luong,gia_ban,size) VALUES (45,32,1,2,199000,'M');
INSERT IGNORE INTO invoice_details (id,ma_hd,ma_sp,so_luong,gia_ban,size) VALUES (46,33,2,1,199000,'XL');
INSERT IGNORE INTO invoice_details (id,ma_hd,ma_sp,so_luong,gia_ban,size) VALUES (47,34,4,1,249000,'M');
INSERT IGNORE INTO invoice_details (id,ma_hd,ma_sp,so_luong,gia_ban,size) VALUES (48,35,1,1,199000,'M');
INSERT IGNORE INTO invoice_details (id,ma_hd,ma_sp,so_luong,gia_ban,size) VALUES (49,36,3,1,229000,'M');
INSERT IGNORE INTO invoice_details (id,ma_hd,ma_sp,so_luong,gia_ban,size) VALUES (50,37,1,1,199000,'M');
INSERT IGNORE INTO invoice_details (id,ma_hd,ma_sp,so_luong,gia_ban,size) VALUES (51,38,2,3,199000,'S');
INSERT IGNORE INTO invoice_details (id,ma_hd,ma_sp,so_luong,gia_ban,size) VALUES (52,39,1,1,199000,'S');

-- vouchers
INSERT IGNORE INTO vouchers (id,ma_voucher,loai_giam,gia_tri,don_toi_thieu,so_luong,ngay_het_han) VALUES (2,'400K','Tiền mặt',10000,400000,1,NULL);
INSERT IGNORE INTO vouchers (id,ma_voucher,loai_giam,gia_tri,don_toi_thieu,so_luong,ngay_het_han) VALUES (3,'200K','Phần trăm',10000,200000,100,'2026-05-15');
INSERT IGNORE INTO vouchers (id,ma_voucher,loai_giam,gia_tri,don_toi_thieu,so_luong,ngay_het_han) VALUES (4,'HE26','Tiền mặt',2000,50000,100,'2026-05-28');
INSERT IGNORE INTO vouchers (id,ma_voucher,loai_giam,gia_tri,don_toi_thieu,so_luong,ngay_het_han) VALUES (5,'FREESHIP','Tiền mặt',5000,300000,100,'2026-05-13');

-- customer_vouchers
INSERT IGNORE INTO customer_vouchers (id,customer_id,voucher_id,is_used,saved_at) VALUES (1,21,2,0,'2026-05-11 18:54:24');
INSERT IGNORE INTO customer_vouchers (id,customer_id,voucher_id,is_used,saved_at) VALUES (2,21,4,0,'2026-05-12 07:13:33');

-- favorites
INSERT IGNORE INTO favorites (id,ma_kh,ma_sp,ngay_them) VALUES (2,21,2,'2026-05-07 06:45:33');
INSERT IGNORE INTO favorites (id,ma_kh,ma_sp,ngay_them) VALUES (3,21,17,'2026-05-09 06:32:40');

-- notifications
INSERT IGNORE INTO notifications (id,customer_id,title,content,is_read,created_at) VALUES (1,21,'Phản hồi về: eee','Quản trị viên đã tiếp nhận, kiểm tra và xử lý xong báo cáo của bạn. Cảm ơn bạn đã đóng góp ý kiến!',1,'2026-05-11 19:21:30');
INSERT IGNORE INTO notifications (id,customer_id,title,content,is_read,created_at) VALUES (2,21,'Phản hồi về: Bổ sung bộ sưu tập mùa hè','Quản trị viên đã tiếp nhận, kiểm tra và xử lý xong báo cáo của bạn. Cảm ơn bạn đã đóng góp ý kiến!',1,'2026-05-12 07:14:57');

-- feedbacks
INSERT IGNORE INTO feedbacks (id,customer_id,ten_kh,email,chu_de,noi_dung,ngay_gui,trang_thai) VALUES (1,NULL,'abc','builehau2005@gmail.com','12345677','dưqwqq','2026-05-10 20:46:23','Đã xử lý');
INSERT IGNORE INTO feedbacks (id,customer_id,ten_kh,email,chu_de,noi_dung,ngay_gui,trang_thai) VALUES (2,NULL,'abcê','builehau2005@gmail.xn--com-hma','eee','eeee','2026-05-10 20:52:11','Đã xử lý');
INSERT IGNORE INTO feedbacks (id,customer_id,ten_kh,email,chu_de,noi_dung,ngay_gui,trang_thai) VALUES (3,NULL,'hậu lê','hau@gmail.com','12345677','ưddwqqwwdqwd','2026-05-11 18:56:05','Đã xử lý');
INSERT IGNORE INTO feedbacks (id,customer_id,ten_kh,email,chu_de,noi_dung,ngay_gui,trang_thai) VALUES (4,21,'hậu lê','hau@gmail.com','eee','ddd','2026-05-11 19:21:03','Đã xử lý');
INSERT IGNORE INTO feedbacks (id,customer_id,ten_kh,email,chu_de,noi_dung,ngay_gui,trang_thai) VALUES (5,21,'Bùi Hậu','hau@gmail.com','Bổ sung bộ sưu tập mùa hè','thêm các sp áo thun, quần ngắn','2026-05-12 07:01:39','Đã xử lý');

-- audit_logs
INSERT IGNORE INTO audit_logs (id,ma_nv,hanh_dong,chi_tiet,thoi_gian) VALUES (1,1,'Cập nhật Đơn hàng','Chuyển trạng thái đơn HD1778262060 thành: Đã hủy','2026-05-11 18:21:28');
INSERT IGNORE INTO audit_logs (id,ma_nv,hanh_dong,chi_tiet,thoi_gian) VALUES (2,1,'Cập nhật Đơn hàng','Chuyển trạng thái đơn HD1778525924 thành: Đang xử lý','2026-05-11 18:59:30');
INSERT IGNORE INTO audit_logs (id,ma_nv,hanh_dong,chi_tiet,thoi_gian) VALUES (3,2,'Phản hồi báo cáo','Đã trả lời Report ID: 4','2026-05-11 19:21:30');
INSERT IGNORE INTO audit_logs (id,ma_nv,hanh_dong,chi_tiet,thoi_gian) VALUES (4,3,'Sửa Sản Phẩm','Mã SP: SP060 | Tên: Quần kaki form rộng','2026-05-11 19:23:09');
INSERT IGNORE INTO audit_logs (id,ma_nv,hanh_dong,chi_tiet,thoi_gian) VALUES (5,2,'Sửa Sản Phẩm','Mã SP: SP001 | Tên: Áo phông basic trắng','2026-05-12 06:38:18');
INSERT IGNORE INTO audit_logs (id,ma_nv,hanh_dong,chi_tiet,thoi_gian) VALUES (6,2,'Sửa Sản Phẩm','Mã SP: SP002 | Tên: Áo phông basic đen','2026-05-12 06:39:34');
INSERT IGNORE INTO audit_logs (id,ma_nv,hanh_dong,chi_tiet,thoi_gian) VALUES (7,2,'Sửa Sản Phẩm','Mã SP: SP003 | Tên: Áo phông in hình','2026-05-12 06:41:24');
INSERT IGNORE INTO audit_logs (id,ma_nv,hanh_dong,chi_tiet,thoi_gian) VALUES (8,2,'Sửa Sản Phẩm','Mã SP: SP004 | Tên: Áo phông oversize','2026-05-12 06:42:51');
INSERT IGNORE INTO audit_logs (id,ma_nv,hanh_dong,chi_tiet,thoi_gian) VALUES (9,2,'Sửa Sản Phẩm','Mã SP: SP005 | Tên: Áo phông thể thao','2026-05-12 06:44:40');
INSERT IGNORE INTO audit_logs (id,ma_nv,hanh_dong,chi_tiet,thoi_gian) VALUES (10,2,'Tạo Voucher Mới','Mã: 200K | Giảm: 10000','2026-05-12 07:04:06');
INSERT IGNORE INTO audit_logs (id,ma_nv,hanh_dong,chi_tiet,thoi_gian) VALUES (11,2,'Tạo Voucher Mới','Mã: HE26 | Giảm: 2000','2026-05-12 07:04:38');
INSERT IGNORE INTO audit_logs (id,ma_nv,hanh_dong,chi_tiet,thoi_gian) VALUES (12,2,'Phản hồi báo cáo','Đã trả lời Report ID: 5','2026-05-12 07:14:57');
INSERT IGNORE INTO audit_logs (id,ma_nv,hanh_dong,chi_tiet,thoi_gian) VALUES (13,2,'Tạo Voucher Mới','Mã: FREESHIP | Giảm: 5000','2026-05-12 07:16:31');
INSERT IGNORE INTO audit_logs (id,ma_nv,hanh_dong,chi_tiet,thoi_gian) VALUES (14,2,'Cập nhật Đơn hàng','Chuyển trạng thái đơn HD1778525924 thành: Đang giao','2026-05-12 07:17:41');
INSERT IGNORE INTO audit_logs (id,ma_nv,hanh_dong,chi_tiet,thoi_gian) VALUES (15,2,'Cập nhật Đơn hàng','Chuyển trạng thái đơn HD1778525924 thành: Đã hủy','2026-05-12 07:17:49');
INSERT IGNORE INTO audit_logs (id,ma_nv,hanh_dong,chi_tiet,thoi_gian) VALUES (16,2,'Sửa Sản Phẩm','Mã SP: SP059 | Tên: Quần jogger unisex','2026-05-12 07:18:29');
INSERT IGNORE INTO audit_logs (id,ma_nv,hanh_dong,chi_tiet,thoi_gian) VALUES (17,3,'Sửa Sản Phẩm','Mã SP: SP058 | Tên: Quần tây cao cấp','2026-05-12 07:20:19');
INSERT IGNORE INTO audit_logs (id,ma_nv,hanh_dong,chi_tiet,thoi_gian) VALUES (18,2,'Đổi trạng thái Sản phẩm','ID SP: 60 -> Ngừng kinh doanh','2026-05-12 07:20:49');

-- warehouses
INSERT IGNORE INTO warehouses (id,ma_kho,vi_tri_kho,ma_sp,so_luong_ton) VALUES (1,'KHO001','Hà Nội',1,30);
INSERT IGNORE INTO warehouses (id,ma_kho,vi_tri_kho,ma_sp,so_luong_ton) VALUES (2,'KHO002','TP. Hồ Chí Minh',1,20);
INSERT IGNORE INTO warehouses (id,ma_kho,vi_tri_kho,ma_sp,so_luong_ton) VALUES (3,'KHO001','Hà Nội',2,15);
INSERT IGNORE INTO warehouses (id,ma_kho,vi_tri_kho,ma_sp,so_luong_ton) VALUES (4,'KHO002','TP. Hồ Chí Minh',2,15);
INSERT IGNORE INTO warehouses (id,ma_kho,vi_tri_kho,ma_sp,so_luong_ton) VALUES (5,'KHO001','Hà Nội',3,10);
INSERT IGNORE INTO warehouses (id,ma_kho,vi_tri_kho,ma_sp,so_luong_ton) VALUES (6,'KHO002','TP. Hồ Chí Minh',3,10);
INSERT IGNORE INTO warehouses (id,ma_kho,vi_tri_kho,ma_sp,so_luong_ton) VALUES (7,'KHO001','Hà Nội',4,5);
INSERT IGNORE INTO warehouses (id,ma_kho,vi_tri_kho,ma_sp,so_luong_ton) VALUES (8,'KHO002','TP. Hồ Chí Minh',4,5);
INSERT IGNORE INTO warehouses (id,ma_kho,vi_tri_kho,ma_sp,so_luong_ton) VALUES (9,'KHO001','Hà Nội',5,12);
INSERT IGNORE INTO warehouses (id,ma_kho,vi_tri_kho,ma_sp,so_luong_ton) VALUES (10,'KHO002','TP. Hồ Chí Minh',5,13);

SET FOREIGN_KEY_CHECKS=1;
