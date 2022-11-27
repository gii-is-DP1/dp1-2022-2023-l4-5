package org.springframework.samples.nt4h.achievement;

import org.springframework.beans.BeanUtils;
import org.springframework.samples.nt4h.user.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.transaction.Transactional;
import javax.validation.Valid;

@Controller
@RequestMapping("/achievement")
public class AchievementController {

    private final String ACHIEVEMENTS_LIST_VIEW ="/achievements/achievementsList";
    private final String ACHIEVEMENTS_FORM_VIEW ="/achievements/createOrUpdateAchievementsForm";
    private static final String PAGE_WELCOME = "redirect:/welcome";


    private AchievementService achievementService;

    @GetMapping()
    public ModelAndView showAllAchievements() {
        ModelAndView result = new ModelAndView(ACHIEVEMENTS_LIST_VIEW);
        result.addObject("achievement", achievementService.getAllAchievements());
        return result;
    }

    @Transactional()
    @GetMapping("/new")
    public ModelAndView createAchievement() {
        Achievement achievement = new Achievement();
        ModelAndView result = new ModelAndView(ACHIEVEMENTS_FORM_VIEW);
        result.addObject("achievement", achievement);
        return result;
    }

    @Transactional
    @PostMapping("/new")
    public ModelAndView saveAchievement(@Valid Achievement achievement, BindingResult br) {
        if(br.hasErrors())
            return new ModelAndView(ACHIEVEMENTS_FORM_VIEW, br.getModel());
        achievementService.save(achievement);
        ModelAndView result = showAllAchievements();
        result.addObject("message", "The achievement was created successfully");
        return result;
    }

    @Transactional
    @GetMapping("/{achievementId}/edit")
    public ModelAndView editAchievement(@PathVariable int achievementId) {
        Achievement achievement = achievementService.getAchievementById(achievementId);
        ModelAndView result = new ModelAndView(ACHIEVEMENTS_FORM_VIEW);
        result.addObject("achievement", achievement);
        return result;
    }

    @Transactional
    @PostMapping("/{achievementId}/edit")
    public ModelAndView saveAchievement(@PathVariable int achievementId,@Valid Achievement actualAchievement, BindingResult br){
        if(br.hasErrors())
            return new ModelAndView(ACHIEVEMENTS_FORM_VIEW, br.getModel());
        Achievement achievementToBeUpdated = achievementService.getAchievementById(achievementId);
        BeanUtils.copyProperties(actualAchievement, achievementToBeUpdated,"id");
        achievementService.save(achievementToBeUpdated);
        ModelAndView result = showAllAchievements();
        result.addObject("message", "The achievement was updated successfully");
        return result;
    }

    @GetMapping(value = "{achievementId}/delete")
    public ModelAndView deleteAchievement(@PathVariable int achievementId) {
        achievementService.deleteAchievementById(achievementId);
        ModelAndView result = showAllAchievements();
        result.addObject("message", "The achievement was deleted successfully");
        return result;
    }


}
