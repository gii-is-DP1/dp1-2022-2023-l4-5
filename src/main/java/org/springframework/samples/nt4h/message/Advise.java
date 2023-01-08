package org.springframework.samples.nt4h.message;

import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Component
public class Advise {

    public void getMessage(HttpSession session, ModelMap model) {
        Object message = session.getAttribute("message");
        Object messageType = session.getAttribute("messageType");
        if (message != null) {
            model.addAttribute("message", message);
            model.addAttribute("messageType", messageType);
            session.removeAttribute("message");
            session.removeAttribute("messageType");
        }
    }

    public void keepUrl(HttpSession session, HttpServletRequest request) {
        session.setAttribute("url", request.getRequestURI());
    }
}
