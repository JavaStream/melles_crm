package com.javastream.melles_crm.controller;

import com.javastream.melles_crm.model.Client;
import com.javastream.melles_crm.model.ClientType;
import com.javastream.melles_crm.service.ClientService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/clients")
public class ClientController {

    private ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping
    public String allClients(Model model) {
        List<Client> clients = clientService.findAll();

        Client client = new Client();
        client.setClientType(ClientType.CLIENT);

        model.addAttribute("clients", clients);
        model.addAttribute("newClient", client);

        return "clients/clients";
    }

    @PostMapping("/add")
    public String addClient(@ModelAttribute("client") Client client, Model model) {
        clientService.save(client);

        return "redirect:/clients";
    }

    @GetMapping("/edit/{id}")
    public String editClientForm(@PathVariable("id") String id, Model model) {
        Client client = clientService.findById(id);

        model.addAttribute("client", client);

        return "clients/clientEdit";
    }

    @PostMapping("/update")
    public String updateClient(@ModelAttribute("client") Client client) {
        clientService.save(client);

        return "redirect:/clients";
    }

    @GetMapping("/delete/{id}")
    public String deleteClient(@PathVariable("id") String id) {
        clientService.deleteById(id);

        return "redirect:/clients";
    }
}