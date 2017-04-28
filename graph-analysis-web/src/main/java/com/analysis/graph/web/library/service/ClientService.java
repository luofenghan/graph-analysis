package com.analysis.graph.web.library.service;

import com.analysis.graph.web.library.repository.ClientRepository;
import com.analysis.graph.web.library.repository.SessionRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by cwc on 2017/4/22 0022.
 */
@Service
public class ClientService {
    @Resource
    private ClientRepository clientRepository;

    @Resource
    private SessionRepository sessionRepository;


}
