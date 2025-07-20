INSERT INTO
	news_tracked_area (news_tracked_area_id, name, name_en, short_code, short_name, display_name, osm_type, osm_id, admin_level, osm_class, osm_type_name, latitude, longitude, place_rank, importance, address_type, bounding_box)
VALUES
	(1, 'Thành Phố Hà Nội', 'Ha Noi', 'HN', 'Hà Nội', 'Hà Nội, Việt Nam', 'relation', 1903516, 4, 'boundary', 'administrative', 21.0283334, 105.854041, 8, 0.6813340096503014, 'city', '[20.5645154, 21.3854176, 105.2889615, 106.0200725]'),
	(2, 'Thành phố Hồ Chí Minh', 'Ho Chi Minh City', 'HCM', 'Hồ Chí Minh', 'Thành phố Hồ Chí Minh, Việt Nam', 'relation', 1973756, 4, 'boundary', 'administrative', 10.7763897, 106.7011391, 8, 0.6646943419574213, 'city', '[8.3456879, 11.5015885, 105.9769387, 108.4311207]'),
	(3, 'Thành phố Đà Nẵng', 'Da Nang', 'DN', 'Đà Nẵng', 'Thành phố Đà Nẵng, Việt Nam', 'relation', 1891418, 4, 'boundary', 'administrative', 16.0680000, 108.2120000, 8, 0.5903381326855974, 'city', '[14.9513535, 16.3340091, 107.2109165, 109.0235567]');

INSERT INTO
	news_category (news_category_id, name, name_en, description)
VALUES
	(1, 'Kinh tế', 'Economy', 'Tin tức liên quan đến kinh tế, tài chính, thị trường'),
    (2, 'An ninh - trật tự', 'Security - Order', 'Tin tức về an ninh, trật tự, pháp luật'),
    (3, 'Xã hội', 'Society', 'Các vấn đề xã hội, dân sinh, đời sống'),
    (4, 'Chính trị', 'Politics', 'Tin tức chính trị trong nước và quốc tế'),
    (5, 'Giáo dục', 'Education', 'Thông tin về giáo dục, đào tạo, trường học'),
    (6, 'Y tế', 'Health', 'Tin y tế, chăm sóc sức khỏe, dịch bệnh'),
    (7, 'Thể thao', 'Sports', 'Tin thể thao trong và ngoài nước'),
    (8, 'Văn hóa', 'Culture', 'Tin tức văn hóa, nghệ thuật, phong tục'),
    (9, 'Giải trí', 'Entertainment', 'Showbiz, điện ảnh, âm nhạc, truyền hình'),
    (10, 'Khoa học - Công nghệ', 'Science - Technology', 'Tin khoa học, công nghệ, đổi mới sáng tạo'),
    (11, 'Môi trường', 'Environment', 'Tin tức về biến đổi khí hậu, môi trường, thiên nhiên'),
    (12, 'Giao thông', 'Transportation', 'Tin về hạ tầng, giao thông, tai nạn'),
    (13, 'Du lịch', 'Tourism', 'Tin tức, xu hướng và điểm đến du lịch'),
    (14, 'Pháp luật', 'Law', 'Pháp luật, xét xử, các vụ án lớn'),
    (15, 'Quốc tế', 'World News', 'Tin tức quốc tế, thời sự thế giới'),
    (16, 'Nông nghiệp', 'Agriculture', 'Tin tức về nông nghiệp, nông thôn, nông dân'),
    (17, 'Công nghiệp', 'Industry', 'Thông tin về ngành công nghiệp, sản xuất'),
    (18, 'Bất động sản', 'Real Estate', 'Thị trường bất động sản, nhà đất'),
    (19, 'Thị trường', 'Market', 'Thị trường hàng hóa, giá cả, cung cầu');

INSERT INTO
	news_provider(news_provider_id, name, domain, source_type, code, is_active)
VALUES
	(1, 'Báo Công An Đà Nẵng', 'https://cadn.com.vn', 'rss', 1, 'false'),
	(2, 'Hà Nội Mới', 'https://hanoimoi.vn', 'rss', 2, 'true');

INSERT INTO
	news_source(news_source_id, news_provider_id, category_id, source_url, frequency, is_active)
VALUES
	(1, 2, 3, 'https://hanoimoi.vn/rss/xa-hoi', 30, 'true'),
	(2, 1, 3, 'https://cadn.com.vn/rss/xa-hoi-13.rss', 30, 'true');


INSERT INTO
	news_source_tracked_area(news_source_id, news_tracked_area_id)
VALUES
	(1, 1),
	(2, 3)
