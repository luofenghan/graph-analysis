package com.analysis.graph.web.api.controller;

import com.analysis.graph.common.domain.dbo.Client;
import com.analysis.graph.web.library.repository.ClientRepository;
import com.analysis.graph.web.library.repository.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Created by cwc on 2017/4/6 0006.
 */
@RestController
@RequestMapping("/api/account")
public class AccountAPI {
    @Autowired
    @Lazy
    private ClientRepository clientRepository;

    @Resource
    private SessionRepository sessionRepository;

    @RequestMapping(method = RequestMethod.POST)
    public Client register(Client customer) {
        return clientRepository.saveClient(customer);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Client getClientById(@PathVariable Integer id) {
        Client client = clientRepository.getClientById(id);
        client.setPassword(null);
        return client;
    }

    @RequestMapping(method = RequestMethod.GET)
    public Client getCurrentClient() {
        Client client = sessionRepository.getCurrentOnlineClient();
        client.setPassword(null);
        return client;
    }
}
