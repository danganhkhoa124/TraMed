-- Tạo bảng app_user
CREATE TABLE IF NOT EXISTS app_user (
                                        user_id UUID PRIMARY KEY,
                                        username VARCHAR(100) UNIQUE NOT NULL,
                                        password_hash VARCHAR(255) NOT NULL,
                                        full_name VARCHAR(255),
                                        active BOOLEAN DEFAULT TRUE,
                                        created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

-- Tạo bảng notification
CREATE TABLE IF NOT EXISTS notification (
                                            notification_id UUID PRIMARY KEY,
                                            logic_del_flg BOOLEAN DEFAULT FALSE
);

-- Tạo bảng notification_content
CREATE TABLE IF NOT EXISTS notification_content (
                                                    notification_content_id UUID PRIMARY KEY,
                                                    notification_id UUID NOT NULL,
                                                    content TEXT NOT NULL,
                                                    locale VARCHAR(10) NOT NULL,
                                                    created_date TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
                                                    created_by UUID NOT NULL,
                                                    update_date TIMESTAMP WITH TIME ZONE,
                                                    update_by UUID,
                                                    CONSTRAINT fk_notification
                                                        FOREIGN KEY (notification_id)
                                                            REFERENCES notification (notification_id)
                                                            ON DELETE CASCADE,
                                                    CONSTRAINT fk_notification_created_by
                                                        FOREIGN KEY (created_by)
                                                            REFERENCES app_user (user_id),
                                                    CONSTRAINT fk_notification_update_by
                                                        FOREIGN KEY (update_by)
                                                            REFERENCES app_user (user_id)
);