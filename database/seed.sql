-- Thêm dữ liệu mẫu vào bảng notification
INSERT INTO notification (notification_id, logic_del_flg, created_by) VALUES
                                                           ('11111111-1111-1111-1111-111111111111', false, '22222222-2222-2222-2222-222222222222'),
                                                           ('33333333-3333-3333-3333-333333333333', true, '22222222-2222-2222-2222-222222222222'),
                                                           ('44444444-4444-4444-4444-444444444444', false, '22222222-2222-2222-2222-222222222222')
ON CONFLICT (notification_id) DO NOTHING;

-- Thêm dữ liệu mẫu vào bảng notification_content
INSERT INTO notification_content (notification_content_id, notification_id, content, locale, created_by) VALUES
                                                                                                             ('66666666-6666-6666-6666-666666666666', '11111111-1111-1111-1111-111111111111', 'Thông báo hệ thống', 'vi-VN', '22222222-2222-2222-2222-222222222222'),
                                                                                                             ('77777777-7777-7777-7777-777777777777', '11111111-1111-1111-1111-111111111111', 'System notification', 'en-US', '22222222-2222-2222-2222-222222222222'),
                                                                                                             ('88888888-8888-8888-8888-888888888888', '33333333-3333-3333-3333-333333333333', 'Cập nhật phiên bản mới', 'vi-VN', '22222222-2222-2222-2222-222222222222'),
                                                                                                             ('99999999-9999-9999-9999-999999999999', '33333333-3333-3333-3333-333333333333', 'New version update', 'en-US', '22222222-2222-2222-2222-222222222222'),
                                                                                                             ('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa', '44444444-4444-4444-4444-444444444444', 'Bảo trì hệ thống', 'vi-VN', '22222222-2222-2222-2222-222222222222'),
                                                                                                             ('bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb', '44444444-4444-4444-4444-444444444444', 'System maintenance', 'en-US', '22222222-2222-2222-2222-222222222222')
ON CONFLICT (notification_content_id) DO NOTHING;