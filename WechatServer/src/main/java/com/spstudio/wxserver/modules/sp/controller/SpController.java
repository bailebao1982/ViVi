package com.spstudio.wxserver.modules.sp.controller;

import com.sun.javafx.sg.prism.NGShape;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by Soul on 2017/3/8.
 */
@Controller
@RequestMapping("/serviceprovider")
public class SpController {
    @RequestMapping(value = "/regieter/{employee_id}",
            method = RequestMethod.GET)
    public ModelAndView getRegieterPage(@PathVariable String employee_id) {
        ModelAndView mav = new ModelAndView();

        return mav;
    }

    @RequestMapping(value = "/regieter/{employee_id}",
            method = RequestMethod.POST)
    public ModelAndView registerEmployee(@PathVariable String employee_id) {
        ModelAndView mav = new ModelAndView();
        return mav;
    }

    @RequestMapping(value = "/workorder/{employee_id}",
            method = RequestMethod.POST)
    public ModelAndView createWorkOrder(@PathVariable String employee_id) {
        ModelAndView mav = new ModelAndView();
        return mav;
    }

    @RequestMapping(value = "/myworkorders/{employee_id}",
            method = RequestMethod.GET)
    public ModelAndView getMyWorkOrders(@PathVariable String employee_id) {
        ModelAndView mav = new ModelAndView();
        return mav;
    }

}
