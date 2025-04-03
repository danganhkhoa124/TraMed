-- Tạo bảng notification
CREATE TABLE IF NOT EXISTS notification (
                                            notification_id UUID PRIMARY KEY,
                                            created_date TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
                                            created_by UUID NOT NULL
);

-- Tạo bảng notification_content
CREATE TABLE IF NOT EXISTS notification_content (
                                                    notification_content_id UUID PRIMARY KEY,
                                                    notification_id UUID NOT NULL,
                                                    content TEXT NOT NULL,
                                                    locale VARCHAR(10) NOT NULL,
                                                    created_date TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
                                                    created_by UUID NOT NULL,
                                                    CONSTRAINT fk_notification
                                                        FOREIGN KEY (notification_id)
                                                            REFERENCES notification (notification_id)
                                                            ON DELETE CASCADE
);