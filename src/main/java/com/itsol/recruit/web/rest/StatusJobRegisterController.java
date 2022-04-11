package com.itsol.recruit.web.rest;

import com.itsol.recruit.core.Constants;
import com.itsol.recruit.entity.StatusJobRegister;
import com.itsol.recruit.repository.StatusJobRegisterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = Constants.Api.Path.PUBLIC)
public class StatusJobRegisterController {

    @Autowired
    private StatusJobRegisterRepository statusJobRegisterRepository;

    @GetMapping("/status-register-jobs")
    public List<StatusJobRegister> findAllStatusJobRegister() {
        return statusJobRegisterRepository.findAll();
    }

}