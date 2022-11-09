package org.springframework.samples.petclinic.capacity;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;


@Controller
@RequestMapping("/capacities")
public class CapacityController {

    private final String CAPACITIES_LISTING_VIEW = "capacities/capacitiesListing";
    private final String CAPACITIES_CREATE_OR_UPDATE_CAPACITY_FORM = "capacities/createOrUpdateCapacityForm";

    private final CapacityService capacityService;

    @Autowired
    public CapacityController(CapacityService capacityService) {
        this.capacityService = capacityService;
    }

    @Transactional(readOnly = true)
    @GetMapping
    public ModelAndView showCapacities() {
        System.out.println("CapacityController.showCapacities");
        ModelAndView mav = new ModelAndView(CAPACITIES_LISTING_VIEW);
        mav.addObject("capacities", capacityService.getAllCapacities());
        return mav;
    }

    @Transactional
    @GetMapping("/{id}/delete")
    public ModelAndView deleteCapacity(@PathVariable("id") int id) {
        System.out.println("CapacityController.showCapacities");
        capacityService.deleteCapacityById(id);
        return showCapacities();
    }

    @Transactional(readOnly = true)
    @GetMapping("/{id}/edit")
    public ModelAndView updateCapacity(@PathVariable("id") int id) {
        ModelAndView mav = new ModelAndView(CAPACITIES_CREATE_OR_UPDATE_CAPACITY_FORM);
        mav.addObject("capacity", capacityService.getCapacityById(id));
        return mav;
    }

    @Transactional(readOnly = true)
    @PostMapping("/{id}/edit")
    public ModelAndView updateCapacity(@PathVariable("id") int id, @Valid Capacity capacity, BindingResult result) {
        if (result.hasErrors()) {
            return new ModelAndView(CAPACITIES_CREATE_OR_UPDATE_CAPACITY_FORM, result.getModel());
        }
        Capacity capacityToUpdate = capacityService.getCapacityById(id);
        BeanUtils.copyProperties(capacity, capacityToUpdate, "id");
        capacityService.saveCapacity(capacityToUpdate);
        ModelAndView mav = showCapacities();
        mav.addObject("message", "The capacity was updated successfully.");
        return mav;
    }

    @Transactional(readOnly = true)
    @GetMapping("/new")
    public ModelAndView createCapacity() {
        ModelAndView mav = new ModelAndView(CAPACITIES_CREATE_OR_UPDATE_CAPACITY_FORM);
        mav.addObject("capacity", new Capacity());
        return mav;
    }

    @Transactional
    @PostMapping("/new")
    public ModelAndView saveNewCapacity(@Valid Capacity capacity, BindingResult result) {
        if (result.hasErrors()) {
            return new ModelAndView(CAPACITIES_CREATE_OR_UPDATE_CAPACITY_FORM, result.getModel());
        }
        capacityService.saveCapacity(capacity);
        ModelAndView mav = showCapacities();
        mav.addObject("message", "The capacity was created successfully.");
        return mav;
    }
}
