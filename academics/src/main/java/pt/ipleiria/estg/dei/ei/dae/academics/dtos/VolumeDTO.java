package pt.ipleiria.estg.dei.ei.dae.academics.dtos;

import pt.ipleiria.estg.dei.ei.dae.academics.entities.Volume;

import java.util.List;

public class VolumeDTO {
    private int id;
    private int sensorId;
    private String employeeUsername;

    public VolumeDTO() {
    }

    public VolumeDTO(int id, int sensorId, String employeeUsername) {
        this.id = id;
        this.sensorId = sensorId;
        this.employeeUsername = employeeUsername;
    }

    public static VolumeDTO from(Volume volume) {
        return new VolumeDTO(
                volume.getId(),
                volume.getSensor().getId(),
                volume.getEmployee().getUsername()
        );
    }

    public static List<VolumeDTO> from(List<Volume> volumes) {
        return volumes.stream().map(VolumeDTO::from).toList();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSensorId() {
        return sensorId;
    }

    public void setSensorId(int sensorId) {
        this.sensorId = sensorId;
    }

    public String getEmployeeUsername() {
        return employeeUsername;
    }

    public void setEmployeeUsername(String employeeUsername) {
        this.employeeUsername = employeeUsername;
    }
}
