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
                                                            ON DELETE CASCADE
);