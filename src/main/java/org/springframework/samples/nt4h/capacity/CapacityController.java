package org.springframework.samples.nt4h.capacity;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@Controller
@RequestMapping("/capacities")
public class CapacityController {

    // Servicio
    private final CapacityService capacityService;

    // Constantes
    private final String VIEW_CAPACITY_LIST = "capacities/capacitiesListing";
    private final String VIEW_CAPACITIES_CREATE_OR_UPDATE_FORM = "capacities/createOrUpdateCapacityForm";
    private final String PAGE_CAPACITY_LIST = "redirect:/capacities";

    @Autowired
    public CapacityController(CapacityService capacityService) {
        this.capacityService = capacityService;
    }

    @InitBinder
    public void setAllowedFields(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
    }


    // Obtener todas las capacidades.
    @GetMapping
    public String showCapacities(ModelMap model) {
        model.put("capacities", capacityService.getAllCapacities());
        return VIEW_CAPACITY_LIST;
    }

    // Crear una capacidad.
    @GetMapping("/new")
    public String createCapacity(ModelMap model) {
        model.put("capacity", new Capacity());
        return VIEW_CAPACITIES_CREATE_OR_UPDATE_FORM;
    }

    @PostMapping("/new")
    public String saveNewCapacity(@Valid Capacity capacity, BindingResult result, ModelMap model) {
        if (result.hasErrors()) {
            return VIEW_CAPACITIES_CREATE_OR_UPDATE_FORM;
        }
        capacityService.saveCapacity(capacity);
        model.put("message", "Capacity created successfully");
        return PAGE_CAPACITY_LIST;
    }

    // Editar una capacidad.
    @GetMapping("/{id}/edit")
    public String updateCapacity(@PathVariable("id") int id, ModelMap model) {
        model.put("capacity", capacityService.getCapacityById(id));
        return VIEW_CAPACITIES_CREATE_OR_UPDATE_FORM;
    }

    @PostMapping("/{id}/edit")
    public String updateCapacity(@PathVariable("id") int id, @Valid Capacity capacity, BindingResult result, ModelMap model) {
        if (result.hasErrors()) {
            return VIEW_CAPACITIES_CREATE_OR_UPDATE_FORM;
        }
        Capacity capacityToUpdate = capacityService.getCapacityById(id);
        BeanUtils.copyProperties(capacity, capacityToUpdate, "id");
        capacityService.saveCapacity(capacityToUpdate);
        model.put("message", "Capacity updated successfully");
        return PAGE_CAPACITY_LIST;
    }

    // Eliminar una capacidad.
    @GetMapping("/{id}/delete")
    public String deleteCapacity(@PathVariable("id") int id) {
        capacityService.deleteCapacityById(id);
        return VIEW_CAPACITY_LIST;
    }
}
