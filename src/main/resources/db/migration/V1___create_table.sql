---- Create Table
CREATE TABLE IF NOT EXISTS geocoding_location (
	geocoding_location_id BIGSERIAL PRIMARY KEY,
    place_id BIGINT UNIQUE,
    latitude DOUBLE PRECISION,
    longitude DOUBLE PRECISION,
    place_rank INTEGER,
    importance DOUBLE PRECISION,
    address_type VARCHAR(100),
    name VARCHAR(255),
    display_name VARCHAR(255),
    bounding_box JSONB,
	created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS news_tracked_area (
	news_tracked_area_id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255),
    display_name VARCHAR(255),
	geocoding_location_id BIGINT,
	created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS news_provider (
    news_provider_id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    domain VARCHAR(255) NOT NULL,
    source_type VARCHAR(50) DEFAULT 'rss',
    is_active BOOLEAN DEFAULT TRUE,
    name INTEGER NOT NULL UNIQUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS news_category (
    news_category_id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    name_en VARCHAR(100),
    description TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS news_source (
    news_source_id SERIAL PRIMARY KEY,
    news_provider_id INTEGER,
    source_url VARCHAR(500) NOT NULL,
    frequency INTEGER,
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS map_news_item (
    map_news_item_id BIGSERIAL PRIMARY KEY,
    news_source_id INTEGER,
	provider VARCHAR(100) NOT NULL,
	geocoding_location_id BIGINT,
    title VARCHAR(255),
    description TEXT,
	source_url VARCHAR(500) NOT NULL,
    published_at TIMESTAMP,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    metadata JSONB
);

CREATE TABLE IF NOT EXISTS news_source_category (
    news_source_id INTEGER,
    news_category_id INTEGER,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (news_source_id, news_category_id)
);

CREATE TABLE IF NOT EXISTS map_news_item_category (
    map_news_item_id BIGINT,
    news_category_id INTEGER,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (map_news_item_id, news_category_id)
);

CREATE TABLE IF NOT EXISTS news_source_tracked_area (
    news_source_id INTEGER,
    news_tracked_area_id BIGINT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (news_source_id, news_tracked_area_id)
);

CREATE TABLE IF NOT EXISTS map_news_item_tracked_area (
    map_news_item_id BIGINT,
    news_tracked_area_id BIGINT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (map_news_item_id, news_tracked_area_id)
);




---- Create Foreign Key Constraints
ALTER TABLE news_tracked_area
ADD CONSTRAINT fk_news_tracked_area_geocoding_location
FOREIGN KEY (geocoding_location_id)
REFERENCES geocoding_location(geocoding_location_id);

ALTER TABLE news_source
ADD CONSTRAINT fk_news_source_provider
FOREIGN KEY (news_provider_id)
REFERENCES news_provider(news_provider_id) ON DELETE SET NULL;

ALTER TABLE map_news_item
ADD CONSTRAINT fk_map_news_item_source
FOREIGN KEY (news_source_id)
REFERENCES news_source(news_source_id) ON DELETE SET NULL;

ALTER TABLE map_news_item
ADD CONSTRAINT fk_map_news_item_location
FOREIGN KEY (geocoding_location_id)
REFERENCES geocoding_location(geocoding_location_id) ON DELETE SET NULL;

ALTER TABLE news_source_category
ADD CONSTRAINT fk_nsc_source
FOREIGN KEY (news_source_id)
REFERENCES news_source(news_source_id) ON DELETE CASCADE;

ALTER TABLE news_source_category
ADD CONSTRAINT fk_nsc_category
FOREIGN KEY (news_category_id)
REFERENCES news_category(news_category_id) ON DELETE CASCADE;

ALTER TABLE map_news_item_category
ADD CONSTRAINT fk_mnic_news
FOREIGN KEY (map_news_item_id)
REFERENCES map_news_item(map_news_item_id) ON DELETE CASCADE;

ALTER TABLE map_news_item_category
ADD CONSTRAINT fk_mnic_category
FOREIGN KEY (news_category_id)
REFERENCES news_category(news_category_id) ON DELETE CASCADE;

ALTER TABLE news_source_tracked_area
ADD CONSTRAINT fk_nsta_source
FOREIGN KEY (news_source_id)
REFERENCES news_source(news_source_id) ON DELETE CASCADE;

ALTER TABLE news_source_tracked_area
ADD CONSTRAINT fk_nsta_area
FOREIGN KEY (news_tracked_area_id)
REFERENCES news_tracked_area(news_tracked_area_id) ON DELETE CASCADE;

ALTER TABLE map_news_item_tracked_area
ADD CONSTRAINT fk_mnita_news
FOREIGN KEY (map_news_item_id)
REFERENCES map_news_item(map_news_item_id) ON DELETE CASCADE;

ALTER TABLE map_news_item_tracked_area
ADD CONSTRAINT fk_mnita_area
FOREIGN KEY (news_tracked_area_id)
REFERENCES news_tracked_area(news_tracked_area_id) ON DELETE CASCADE;



---- Create Index
-- geocoding_location
CREATE INDEX idx_geocoding_location_place_id ON geocoding_location(place_id);
CREATE INDEX idx_geocoding_location_lat_lon ON geocoding_location(latitude, longitude);
CREATE INDEX idx_geocoding_location_name ON geocoding_location(name);
CREATE INDEX idx_geocoding_location_display_name ON geocoding_location(display_name);

-- news_tracked_area
CREATE INDEX idx_news_tracked_area_name ON news_tracked_area(name);
CREATE INDEX idx_news_tracked_area_location_id ON news_tracked_area(geocoding_location_id);

-- news_provider
CREATE INDEX idx_news_provider_domain ON news_provider(domain);
CREATE INDEX idx_news_provider_name ON news_provider(name);

-- news_category
CREATE INDEX idx_news_category_name ON news_category(name);

-- news_source
CREATE INDEX idx_news_source_provider_id ON news_source(news_provider_id);
CREATE INDEX idx_news_source_url ON news_source(source_url);

-- map_news_item
CREATE INDEX idx_map_news_item_source_id ON map_news_item(news_source_id);
CREATE INDEX idx_map_news_item_location_id ON map_news_item(geocoding_location_id);
CREATE INDEX idx_map_news_item_published_at ON map_news_item(published_at);
CREATE INDEX idx_map_news_item_provider ON map_news_item(provider);

-- join tables
CREATE INDEX idx_nsc_source ON news_source_category(news_source_id);
CREATE INDEX idx_nsc_category ON news_source_category(news_category_id);

CREATE INDEX idx_mnic_news_item_id ON map_news_item_category(map_news_item_id);
CREATE INDEX idx_mnic_category_id ON map_news_item_category(news_category_id);

CREATE INDEX idx_nsta_source ON news_source_tracked_area(news_source_id);
CREATE INDEX idx_nsta_area ON news_source_tracked_area(news_tracked_area_id);

CREATE INDEX idx_mnita_news ON map_news_item_tracked_area(map_news_item_id);
CREATE INDEX idx_mnita_area ON map_news_item_tracked_area(news_tracked_area_id);

