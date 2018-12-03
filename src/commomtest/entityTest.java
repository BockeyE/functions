package commomtest;


import lombok.Data;
import lombok.experimental.Accessors;

//@Data
//@Accessors(chain = true)
public class entityTest {
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "entityTest{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    private String id;
    private String name;
    private String address;
    private String phone;
    private String email;
}
