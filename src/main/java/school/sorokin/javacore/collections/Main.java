package school.sorokin.javacore.collections;

import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.Scanner;

public class Main {

    private static final String ENTER_NAME = "Введите имя контакта:";
    private static final String ENTER_PHONE = "Введите номер телефона:";
    private static final String ENTER_EMAIL = "Введите адрес электронной почты:";
    private static final String ENTER_GROUP = "Введите группу:";

    private static final ContactBook contactBook = new ContactBook();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        boolean exit = false;

        while(!exit) {
            System.out.println("Пожалуйста, выберете пункт меню: ");
            System.out.println("1. Добавить контакт\n" +
                    "2. Удалить контакт\n"+
                    "3. Посмотреть все контакты\n" +
                    "4. Найти контакт по имени\n" +
                    "5. Найти контакт по номеру\n" +
                    "6. Посмотреть контакты по группе\n" +
                    "0. Выход");
            try {
                int menuItem = scanner.nextInt();
                scanner.nextLine();
                switch (menuItem) {
                    case 1:
                        addContact();
                        break;
                    case 2:
                        removeContact();
                        break;
                    case 3:
                        showAllContacts();
                        break;
                    case 4:
                        findByName();
                        break;
                    case 5:
                        findByPhone();
                        break;
                    case 6:
                        showByGroup();
                        break;
                    case 0:
                        exit = true;
                        break;
                    default:
                        System.out.println("Такого пункта меню не существует. Попробуйте ещё раз");
                        break;
                }
            } catch (InputMismatchException ex) {
                System.out.println("Ожидалось целое число, но было введено нечто иное. Пожалуйста, попробуйте ещё раз");
                scanner.nextLine();
            }
        }
    }

    private static void addContact() {
        System.out.println(ENTER_NAME);
        String name = scanner.nextLine();
        System.out.println(ENTER_PHONE);
        String phone = scanner.nextLine();
        System.out.println(ENTER_EMAIL);
        String email = scanner.nextLine();
        System.out.println(ENTER_GROUP);
        String group = scanner.nextLine();

        try {
            contactBook.addContact(name, phone, email, group);
        } catch (ContactAlreadyExistException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private static void removeContact() {
        System.out.println(ENTER_NAME);
        String name = scanner.nextLine();
        System.out.println(ENTER_PHONE);
        String phone = scanner.nextLine();

        try {
            contactBook.removeContact(name, phone);
        } catch (ContactNotExistException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private static void showAllContacts() {
        contactBook.showAllContacts();
    }

    private static void findByName() {
        System.out.println(ENTER_NAME);
        String name = scanner.nextLine();
        var contacts = contactBook.findByName(name);
        Iterator iterator = contacts.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }

    private static void findByPhone() {
        System.out.println(ENTER_PHONE);
        String phone = scanner.nextLine();
        var contacts = contactBook.findByPhone(phone);
        Iterator iterator = contacts.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }

    private static void showByGroup() {
        System.out.println(ENTER_GROUP);
        String group = scanner.nextLine();
        contactBook.showGroupContacts(group);
    }
}
