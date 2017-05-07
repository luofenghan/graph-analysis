package com.analysis.graph.web.library.repository;

import com.analysis.graph.common.constant.Encryption;
import com.analysis.graph.common.domain.dbo.Client;
import com.analysis.graph.common.domain.dbo.ClientExample;
import com.analysis.graph.common.repository.mapper.ClientMapper;
import com.google.common.base.Preconditions;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by cwc on 2017/4/6 0006.
 */
@Repository
@SuppressWarnings("all")
public class ClientRepository {
    private static final Logger logger = LoggerFactory.getLogger(ClientRepository.class);

    @Autowired
    private ClientMapper clientMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${graph.analysis.encryption.data.enable:true}")
    private boolean enableDataEncryption;

    @Transactional
    public Client saveClient(Client client) {
        sanitize(client);
        String originMobile = client.getMobile();
        String encodedPassword = passwordEncoder.encode(client.getPassword());
        client.setPassword(encodedPassword);
        Client encryptedClient = encryptPropertiesForClient(client);

        Date now = new DateTime().toDate();
        encryptedClient.setCreatedTime(now);
        encryptedClient.setUpdatedTime(now);
        clientMapper.insertSelective(encryptedClient);
        return getClientByMobile(originMobile);
    }

    public Client getClientById(Integer id) {
        Client client = clientMapper.selectByPrimaryKey(id);
        Preconditions.checkArgument(client != null, "can not find client by id:" + id);
        return decryptPropertiesForClient(client);
    }

    public Client getClientByMobile(String mobile) {
        ClientExample example = new ClientExample();

        String encryptMobile = Encryption.DATA_BASE.encryptWithSalt(mobile);
        example.createCriteria().andMobileEqualTo(encryptMobile);
        List<Client> dbResults = clientMapper.selectByExample(example);
        Preconditions.checkArgument(!dbResults.isEmpty(), "can not find client by mobile:" + mobile);
        return decryptPropertiesForClient(dbResults.get(0));
    }

    @Transactional
    public Client updateClient(Client client) {
        client.setUpdatedTime(new DateTime().toDate());
        clientMapper.updateByPrimaryKeySelective(client);
        return client;
    }

    private void sanitize(Client client) {
        if (client.getMobile() != null) {
            client.setMobile(client.getMobile().toLowerCase());
        }
    }

    private Client encryptPropertiesForClient(Client client) {
        if (client == null) {
            return null;
        }
        if (!enableDataEncryption) {
            return client;
        }
        Client encryptedClient = new Client();
        BeanUtils.copyProperties(client, encryptedClient);

        if (encryptedClient.getMobile() != null) {
            String encryptedMobile = Encryption.DATA_BASE.encryptWithSalt(encryptedClient.getMobile());
            encryptedClient.setMobile(encryptedMobile);
        }

        return encryptedClient;
    }

    private Client decryptPropertiesForClient(Client client) {
        if (client == null) {
            return null;
        }
        if (!enableDataEncryption) {
            return client;
        }
        Client decryptedClient = new Client();
        BeanUtils.copyProperties(client, decryptedClient);
        if (decryptedClient.getMobile() != null) {
            String decryptedMobile = Encryption.DATA_BASE.decryptWithSalt(decryptedClient.getMobile());
            decryptedClient.setMobile(decryptedMobile);
        }

        return decryptedClient;
    }
}
