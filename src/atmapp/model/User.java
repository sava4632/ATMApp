package atmapp.model;

import java.io.Serializable;

/**
 *
 * @author Samuel
 */
public class User implements Serializable{

    private String id; // identificador del usuario
    private String nif;
    private String fullname; // nombre del usuario
    private String email; // correo electrónico del usuario
    private String phone; // contraseña del usuario

    //Vacio
    public User() {
    }

    //Usuario nuevo
    public User(String id, String nif, String fullname, String email, String phone) {
        this.id = id;
        this.nif = nif;
        this.fullname = fullname;
        this.email = email;
        this.phone = phone;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return id + "," + nif + "," + fullname + "," + email + "," + phone;
    }
}
