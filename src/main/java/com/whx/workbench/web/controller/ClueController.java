package com.whx.workbench.web.controller;

import com.whx.settings.domain.User;
import com.whx.utils.DateTimeUtil;
import com.whx.utils.UUIDUtil;
import com.whx.vo.PageVo;
import com.whx.workbench.domain.Activity;
import com.whx.workbench.domain.Clue;
import com.whx.workbench.domain.ClueRemark;
import com.whx.workbench.domain.Tran;
import com.whx.workbench.service.ClueService;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.rmi.activation.ActivationID;
import java.util.List;

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

        String ownerId=clueService.getOwnerIdBy(id);
        model.addAttribute("ownerId",ownerId);
        return "workbench/clue/detail";
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

    @GetMapping("/showActivityList.do")
    @ResponseBody
    public List<Activity> remarkList(String id){
        List<Activity> list=clueService.remarkList(id);
        return list;
    }

    @GetMapping("/getActivityListByNameAndNotByClueId.do")
    @ResponseBody
    public List<Activity> getActivityListByNameAndNotByClueId(String aname,String clueId){
        List<Activity> list=clueService.getActivityListByNameAndNotByClueId(aname,clueId);
        return list;
    }


    @PostMapping("/bundActivity.do")
    @ResponseBody
    public boolean bundActivity(String[] ids,String clueId){
        boolean flag=clueService.bundActivity(ids,clueId);
        return flag;
    }
    @GetMapping("/unbund.do")
    @ResponseBody
    public boolean unbund(String activityId,String clueId){
        boolean flag=clueService.unbund(activityId,clueId);
        return flag;
    }
    @GetMapping("delete.do")
    public String deleteCAR(String id){
        boolean flag=clueService.deleteCAR(id);
        return "workbench/clue/index";
    }
    @PostMapping("/update.do")
    @ResponseBody
    public boolean update(Clue clue,HttpSession session){
        //boolean flag=clueService.update(clue);
        clue.setEditTime(DateTimeUtil.getSysTime());
        clue.setEditBy(((User)session.getAttribute("user")).getName());
        boolean flag = clueService.updateClue(clue);
        return flag;
    }

    @GetMapping("/remarkList.do")
    @ResponseBody
    public List<ClueRemark> showRemarkList(String clueId){
        List<ClueRemark> list=clueService.showRemarkList(clueId);
        return list;
    }

    @GetMapping("/deleteRemark.do")
    @ResponseBody
    public boolean deleteRemark(String id){
        boolean flag=clueService.deleteRemark(id);
        return flag;
    }

    @GetMapping("/saveRemark.do")
    @ResponseBody
    public ClueRemark saveRemark(ClueRemark clueRemark,HttpSession session){
        clueRemark.setId(UUIDUtil.getUUID());
        clueRemark.setCreateBy(((User)session.getAttribute("user")).getName());
        clueRemark.setCreateTime(DateTimeUtil.getSysTime());
        clueRemark.setEditFlag("0");
        boolean flag=clueService.saveRemark(clueRemark);
        return clueRemark;
    }

    @GetMapping("/convert.do")
    @ResponseBody
    public ModelAndView saveRemark(String id,String ownerId,ModelAndView mv){
        Clue c=clueService.getById(id);
        mv.addObject("c",c);
        mv.addObject("ownerId",ownerId);
        mv.setViewName("workbench/clue/convert");
        return mv;
    }

    @GetMapping("/getActivityListByName.do")
    @ResponseBody
    public List<Activity> getActivityListByName(String aName){
        List<Activity> list=clueService.getActivityListByName(aName);
        return list;
    }


    /**
     *
     * @param flag
     * @param tran 除了前端传来的内容外，还要自己赋值id，customerId，contactsId，createBy，createTime
     * @param mv
     * @return
     */
    @PostMapping("/convertCLue.do")
    @ResponseBody
    public ModelAndView convertCLue(String flag, String clueId,Tran tran,ModelAndView mv,HttpSession session){
        String msg="转换成功";
        User user= (User) session.getAttribute("user");
        try {
            boolean key=clueService.convertCLue(flag,clueId,tran,user);
        } catch (Exception e) {
            e.printStackTrace();
            msg=e.getMessage();
        }
        mv.setViewName("workbench/clue/index");
        return mv;
    }

}
