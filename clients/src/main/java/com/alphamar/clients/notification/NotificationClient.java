package com.alphamar.clients.notification;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        name = "notification-service",
        url = "${clients.notification.url}"
)
public interface NotificationClient {

    @PostMapping("notifications")
    void sendNotification(@RequestBody NotificationRequest notificationRequest);
}
