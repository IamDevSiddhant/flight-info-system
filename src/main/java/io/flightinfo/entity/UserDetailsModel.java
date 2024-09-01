package io.flightinfo.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "Flight_Info_Users")
public class UserDetailsModel {
    @Id
    private String  id;
    private String userName;
    private String userEmail;
    private String phoneNumber;
    private String role;
    private String passWord;
    private String address;
    private String city;
    private String state;
    private String country;
}
