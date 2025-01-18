package pt.ipleiria.estg.dei.ei.dae.academics.dtos;

import jakarta.validation.constraints.NotBlank;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.Employee;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.Volume;

import java.util.LinkedList;
import java.util.List;

public class EmployeeDTO {
    private String username;
    private String password;
    private String name;
    private String email;
    private List<VolumeDTO> volumes;
    private int warehouseId;

    public EmployeeDTO() {
       this.volumes = new LinkedList<>();
    }

    public EmployeeDTO(String username, String password, String name, String email, int warehouseId) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
        this.volumes = new LinkedList<>();
        this.warehouseId = warehouseId;
    }

    public static EmployeeDTO from(Employee employee) {
        return new EmployeeDTO(
                employee.getUsername(),
                employee.getPassword(),
                employee.getName(),
                employee.getEmail(),
                employee.getWarehouse().getId()
        );
    }

    public static List<EmployeeDTO> from(List<Employee> employees) {
        return employees.stream().map(EmployeeDTO::from).toList();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<VolumeDTO> getVolumes() {
        return new LinkedList<>(volumes);
    }

    public void setVolumes(List<VolumeDTO> volumes) {
        this.volumes = new LinkedList<>(volumes);
    }

    public int getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(int warehouseId) {
        this.warehouseId = warehouseId;
    }
}
