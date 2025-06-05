package apps.nooterapp.exception;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(UsernameNotFoundException.class)
    public ModelAndView handleUserNotFound(UsernameNotFoundException ex, Model model) {
        ModelAndView mav = new ModelAndView("error"); // "error.html" or "error.jsp"
        mav.addObject("errorMessage", ex.getMessage());
        return mav;
    }

    @ExceptionHandler(NullPointerException.class)
    public ModelAndView handleNullPointer(NullPointerException ex, Model model) {
        ModelAndView mav = new ModelAndView("error");
        mav.addObject("errorMessage", ex.getMessage());
        return mav;
    }
}
