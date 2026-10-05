package com.hmenswear.fashionstore.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/** Trang lỗi H-Menswear thay Whitelabel và giữ exception thật trong console. */
@Controller
public class AppErrorController implements ErrorController {
    private static final Logger log = LoggerFactory.getLogger(AppErrorController.class);

    @RequestMapping("/error")
    public String error(HttpServletRequest request, Model model) {
        Object statusObj = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        Object throwableObj = request.getAttribute(RequestDispatcher.ERROR_EXCEPTION);
        Object messageObj = request.getAttribute(RequestDispatcher.ERROR_MESSAGE);
        Object uriObj = request.getAttribute(RequestDispatcher.ERROR_REQUEST_URI);

        int status = 500;
        if (statusObj != null) {
            try { status = Integer.parseInt(statusObj.toString()); } catch (NumberFormatException ignored) { status = 500; }
        }
        // Khi Thymeleaf lỗi sau lúc response đã bắt đầu ghi, container đôi lúc còn báo 200.
        // Nếu có exception thật thì giao diện phải hiển thị lỗi hệ thống, không hiển thị "200 OK" gây hiểu nhầm.
        if (throwableObj instanceof Throwable && status < 400) status = 500;

        String message = messageObj == null ? "" : String.valueOf(messageObj);
        if (message.isBlank()) {
            message = status == 404
                    ? "Trang bạn tìm kiếm không tồn tại hoặc đã được di chuyển."
                    : status == 403
                    ? "Bạn không có quyền truy cập chức năng này."
                    : "Đã xảy ra lỗi khi xử lý yêu cầu. Vui lòng thử lại.";
        }

        if (throwableObj instanceof Throwable t) {
            log.error("Lỗi khi xử lý {}", uriObj == null ? request.getRequestURI() : uriObj, t);
        }

        HttpStatus httpStatus = HttpStatus.resolve(status);
        model.addAttribute("statusCode", status);
        model.addAttribute("statusText", httpStatus == null ? "Lỗi hệ thống" : httpStatus.getReasonPhrase());
        model.addAttribute("errorMessage", message);
        return "error";
    }
}
