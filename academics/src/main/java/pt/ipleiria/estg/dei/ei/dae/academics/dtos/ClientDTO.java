package pt.ipleiria.estg.dei.ei.dae.academics.dtos;

import pt.ipleiria.estg.dei.ei.dae.academics.entities.Client;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.Order;

import java.util.LinkedList;
import java.util.List;

public class ClientDTO extends UserDTO {

    public ClientDTO(String username, String password, String name, String email, String dtype) {
        super(username, password, name, email, dtype);
    }

    public ClientDTO() {
    }

    public static ClientDTO fromClient(Client client) {
        return new ClientDTO(
                client.getUsername(),
                client.getPassword(),
                client.getName(),
                client.getEmail(),
                client.getDtype()
        );
    }

    public static List<ClientDTO> fromClient(List<Client> clients) {
        return clients.stream().map(ClientDTO::fromClient).toList();
    }
}
