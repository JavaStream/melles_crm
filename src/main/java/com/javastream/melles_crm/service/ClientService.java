package com.javastream.melles_crm.service;

import com.javastream.melles_crm.model.Client;
import com.javastream.melles_crm.repo.ClientRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientService {

    private ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public List<Client> findAll() {
        List<Client> clients = clientRepository.findAll();
        List<Client> sortedClients = clients.stream().sorted(Comparator.comparing(Client::getId)).collect(Collectors.toList());
        return sortedClients;
    }

    public Client findById(String id) {
        return clientRepository.findById(Long.parseLong(id)).orElseThrow(IllegalStateException::new);
    }

    public void save(Client client) {
        clientRepository.save(client);
    }

    public void deleteById(String id)  {
       long clientId = Long.parseLong(id);
        clientRepository.deleteById(clientId);
    }
}
