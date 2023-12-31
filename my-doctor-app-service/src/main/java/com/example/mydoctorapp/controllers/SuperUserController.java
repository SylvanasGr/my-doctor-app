package com.example.mydoctorapp.controllers;

import com.example.mydoctorapp.dto.AttachPrescriptionDTO;
import com.example.mydoctorapp.dto.DoctorViewDTO;
import com.example.mydoctorapp.services.DoctorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import static com.example.mydoctorapp.constants.Constants.PRESCRIPTIONS_TEMPLATE_VALUE;
import static com.example.mydoctorapp.constants.Constants.SUPER_USER_VIEW;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/super-user")
public class SuperUserController {

    private final DoctorService doctorService;

    @GetMapping("/view")
    public String doctorView(Model model, @AuthenticationPrincipal OidcUser user) {
        return doctorService.loginSuperUser(model, user);
    }

    @PostMapping("/user/add")
    public String addPatient(
            @RequestParam String citizenId,
            @RequestParam String doctorId,
            RedirectAttributes redirectAttributes) {
        doctorService.addPatient(citizenId, doctorId, redirectAttributes);
        return "redirect:/citizens/all";
    }

    @PostMapping("user/add/comment")
    public String addComment(@RequestBody DoctorViewDTO doctorViewDto, Model model) {
        doctorService.addComment(doctorViewDto, model);
        return SUPER_USER_VIEW;
    }

    @DeleteMapping("user/remove")
    public String removePatient(@RequestBody DoctorViewDTO doctorViewDto, RedirectAttributes redirectAttributes, Model model) {
        doctorService.removePatient(doctorViewDto, redirectAttributes, model);
        return SUPER_USER_VIEW;
    }

    @PostMapping("user/add/prescription")
    public String attachPrescription(@RequestBody @Valid AttachPrescriptionDTO attachPrescriptionDto) {
        doctorService.attachPrescriptions(attachPrescriptionDto);
        return PRESCRIPTIONS_TEMPLATE_VALUE;
    }

    @GetMapping("user/prescriptions")
    public String getPrescriptions(DoctorViewDTO doctorViewDto, Model model) {
        doctorService.getPrescriptions(doctorViewDto, model);
        return PRESCRIPTIONS_TEMPLATE_VALUE;
    }
}
