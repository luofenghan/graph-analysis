package com.analysis.graph.web.library.repository;

import com.analysis.graph.common.constant.Encryption;
import com.analysis.graph.common.domain.dbo.Client;
import com.analysis.graph.common.domain.dbo.ClientExample;
import com.analysis.graph.common.repository.mapper.ClientMapper;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;

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
    public Client createClient(Client client) {
        sanitizeClient(client);
        String originMobile = client.getMobile();
        String encodedPassword = passwordEncoder.encode(client.getPassword());
        client.setPassword(encodedPassword);
        Client encryptedClient = encryptPropertiesForClient(client);
        encryptedClient.setId(null);
        encryptedClient.setCreatedTime(null);
        encryptedClient.setUpdatedTime(null);
        if (clientMapper.insertSelective(encryptedClient) != 1) {
            throw new RuntimeException("create client failed");
        } else {
            return getClientByMobile(originMobile).get();
        }
    }

    public Optional<Client> getClientById(Integer id) {
        return Optional.ofNullable(decryptPropertiesForClient(clientMapper.selectByPrimaryKey(id)));
    }

    public Optional<Client> getClientByMobile(String mobile) {
        ClientExample example = new ClientExample();

        String encryptMobile = Encryption.DATA_BASE.encryptWithSalt(mobile);
        example.createCriteria().andMobileEqualTo(encryptMobile);
        List<Client> dbResults = clientMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(dbResults)) {
            return Optional.empty();
        } else {
            return Optional.of(decryptPropertiesForClient(dbResults.get(0)));
        }
    }

    @Transactional
    public void updateClientSelectiveById(Client client) {
        client.setUpdatedTime(new DateTime().toDate());
        if (clientMapper.updateByPrimaryKeySelective(client) != 1) {
            throw new RuntimeException("updateClientSelectiveById failed");
        }
    }

    private void sanitizeClient(Client client) {
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
