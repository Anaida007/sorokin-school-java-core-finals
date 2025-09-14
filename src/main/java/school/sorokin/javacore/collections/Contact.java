package school.sorokin.javacore.collections;

public class Contact {

    private String name;
    private String phone;
    private String email;
    private String group;

    public Contact(String name, String phone, String email, String group) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.group = group;
    }

    @Override
    public boolean equals(Object o) {
        if (o==null || !(o instanceof Contact)) {
            return false;
        }
        if (this == o) {
            return true;
        }
        Contact anotherContact = (Contact) o;
        return name.equals(anotherContact.name) && phone.equals(anotherContact.phone);
    }
    @Override
    public String toString() {
        return "Контакт из группы \"" + group + "\": " + name + ", " + phone + ", " + email;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }
}
