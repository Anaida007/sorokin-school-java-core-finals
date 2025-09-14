package school.sorokin.javacore.collections;

import java.util.*;
import java.util.stream.Collectors;

public class ContactBook {

    /** Контакты в порядке добавления */
    private final List<Contact> list = new ArrayList<>();

    /** Сет для проверки уникальности контактов */
    private final Set<Contact> set = new HashSet<>();

    /** Группировка контактов по категориям */
    private final Map<String, List<Contact>> map = new HashMap<>();

    public void addContact(String name, String phone, String email, String group) throws ContactAlreadyExistException {
        Contact newContact = new Contact(name, phone, email, group);
        int uniqContactsNumber = set.size();
        set.add(newContact);
        if (uniqContactsNumber != set.size()) {
            list.add(newContact);
            if (map.containsKey(group)) {
                map.get(group).add(newContact);
            } else {
                List<Contact> newGroup = new ArrayList<>();
                newGroup.add(newContact);
                map.put(group, newGroup);
            }
        }
        throw new ContactAlreadyExistException("Такой контакт уже существует");
    }

    public void removeContact(String name, String phone) throws ContactNotExistException {
        Contact toRemove = new Contact(name, phone, null, null);
        if (!list.remove(toRemove)) {
            throw new ContactNotExistException("Такого контакта не существует");
        }
        set.remove(toRemove);
        var marIterator = map.entrySet().iterator();
        while (marIterator.hasNext()) {
            var element = marIterator.next();
            element.getValue().remove(toRemove);
        }
    }

    public void showAllContacts() {
        Iterator<Contact> iterator = list.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }

    public void showGroupContacts(String group) {
        Iterator<Contact> iterator = map.getOrDefault(group, List.of()).iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }

    public List<Contact> findByName(String name) {
        return set.stream().filter(c->c.getName().equals(name)).collect(Collectors.toList());
    }

    public List<Contact> findByPhone(String phone) {
        return set.stream().filter(c->c.getPhone().equals(phone)).collect(Collectors.toList());
    }

}
