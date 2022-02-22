package com.whx.workbench.web.controller;

import com.whx.settings.domain.User;
import com.whx.utils.DateTimeUtil;
import com.whx.utils.UUIDUtil;
import com.whx.vo.PageVo;
import com.whx.workbench.domain.Clue;
import com.whx.workbench.service.ClueService;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/workbench/clue")
public class ClueController {
    @Resource
    ClueService clueService;

    @GetMapping("/save.do")
    @ResponseBody
    public boolean save(Clue clue, HttpSession session){
        clue.setId(UUIDUtil.getUUID());
        clue.setCreateTime(DateTimeUtil.getSysTime());
        clue.setCreateBy(((User)session.getAttribute("user")).getName());
        boolean flag=clueService.save(clue);
        return flag;
    }
    @PostMapping("/pageList.do")
    @ResponseBody
    public PageVo<Clue> pageList(Clue clue,Integer pageNo,Integer pageSize){
        PageVo<Clue> pageVo=clueService.pageList(clue,pageNo,pageSize);
        return pageVo;
    }

    @GetMapping("/detail.do")
    public String detail(String id, Model model){
        Clue clue=clueService.getById(id);
        model.addAttribute("c",clue);
        return "/workbench/clue/detail";
    }

    @PostMapping("/delete.do")
    @ResponseBody
    public boolean delete(String[] ids, Model model){
        boolean flag=clueService.delete(ids);
        return flag;
    }
    @GetMapping("/update.do")
    @ResponseBody
    public Clue update(String id){
        Clue clue=clueService.update(id);
        return clue;
    }

    @PostMapping("/updateClue.do")
    @ResponseBody
    public boolean updateClue(Clue clue,HttpSession session){
        System.out.println("执行updateClue.do方法");
        clue.setEditTime(DateTimeUtil.getSysTime());
        clue.setEditBy(((User)session.getAttribute("user")).getName());
        boolean flag=clueService.updateClue(clue);
        return flag;
    }


}
