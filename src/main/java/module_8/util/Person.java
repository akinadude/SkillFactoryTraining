package module_8.util;

public class Person {
    private final Integer age;
    private final String name;

    public Person(String name, int age) {
        this.age = age;
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof Person)) return false;
        Person person = (Person) o;
        return name.equals(person.name) && age.equals(person.age);
    }

    @Override
    public int hashCode() {
        int result = 7;
        result = 31 * result + age;
        result = 31 * result + (name == null ? 0 : name.hashCode());

        return result;
    }
}