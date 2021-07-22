package com.example.sbprafromwebmyself;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import javax.annotation.PostConstruct;


@Controller
public class DemoController {

    @Autowired
    UserRepository repos;

    //一欄画面（初期画面）への遷移
    @GetMapping
    public ModelAndView list() {
        List<User> list = repos.findAll();
        ModelAndView mav = new ModelAndView();
        mav.setViewName("list");
        mav.addObject("data", list);
        return mav;
    }

    //新規画面への遷移
    @GetMapping("/add")
    ModelAndView add() {
        ModelAndView mav = new ModelAndView();
        User data = new User();
        mav.setViewName("new");
        mav.addObject("formModel", data);
        return mav;
    }

    //編集画面への遷移
    @GetMapping("/edit")
    public ModelAndView edit(@RequestParam int id) {
        ModelAndView mav = new ModelAndView();
        User data = repos.findById(id);
        mav.addObject("formModel", data);
        mav.setViewName("new");
        return mav;
    }

    //更新処理
    @PostMapping
    @Transactional(readOnly = false)
    public ModelAndView sava(@ModelAttribute("formModel") User user) {
        repos.saveAndFlush(user);
        return new ModelAndView("redirect:user");
    }

    //削除処理
    @PostMapping("/delete")
    @Transactional(readOnly = false)
    public ModelAndView delete(@RequestParam int id) {
        repos.deleteById(id);
        return new ModelAndView("redirect:list");
    }

    //初期データ作成
    @PostConstruct
    public void init() {
        User user1 = new User();
        user1.setName("滋賀県　大好き太郎");
        user1.setAddress("琵琶湖");
        user1.setTel("090-xxxx-xxxx");
        repos.saveAndFlush(user1);


    }

}
