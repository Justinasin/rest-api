package com.example.restapi.controller;

import com.example.restapi.service.IbanService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.restapi.model.Response;

@RestController
public class MainController {

    IbanService ibanService = new IbanService();

    @RequestMapping(value = "/check", method = RequestMethod.GET)
    public Response Sample(@RequestParam(value = "iban",
            defaultValue = "false") String iban) {
        Response response = new Response();
        response.setIban(iban);
        response.setCorrect(ibanService.checkIban(iban));
        return response;

    }


}
