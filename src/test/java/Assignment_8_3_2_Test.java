import org.junit.jupiter.api.Test;

import module_8.Assignment_8_3_2;
import module_8.util.Person;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Assignment_8_3_2_Test {

    @Test
    public final void test1() {
        Assignment_8_3_2 hashMap = new Assignment_8_3_2();
        Person p1 = new Person("Vasya", 123);

        hashMap.insert(p1, 21);
        assertEquals(hashMap.get(p1), 21);

        hashMap.remove(p1);
        assertEquals(hashMap.get(p1), 0);
    }

    @Test
    public final void test2() {
        Assignment_8_3_2 hashMap = new Assignment_8_3_2();
        Person p1 = new Person("Vasya", 123);
        Person p2 = new Person("Petya", 321);

        hashMap.insert(p1, 21);
        hashMap.insert(p2, 27);
        assertEquals(21, hashMap.get(p1));
        assertEquals(27, hashMap.get(p2));

        hashMap.remove(p1);
        hashMap.remove(p2);
        assertEquals(0, hashMap.get(p1));
        assertEquals(0, hashMap.get(p2));
    }

    @Test
    public final void test3() {
        Assignment_8_3_2 hashMap = new Assignment_8_3_2();
        Person p1 = new Person("Vasya", 123);
        Person p2 = new Person("Petya", 321);

        hashMap.insert(p1, 21);
        hashMap.insert(p1, 22);
        assertEquals(hashMap.get(p1), 22);

        hashMap.remove(p1);
        hashMap.remove(p2);
        assertEquals(0, hashMap.get(p1));
        assertEquals(0, hashMap.get(p2));
    }

    @Test
    public final void extending_hash_map_test() {
        Assignment_8_3_2 hashMap = new Assignment_8_3_2();
        Person p1 = new Person("Vasya", 10);
        Person p2 = new Person("Petya", 11);
        Person p3 = new Person("Katya", 12);
        Person p4 = new Person("Sasha", 13);
        Person p5 = new Person("Kolya", 14);
        Person p6 = new Person("Vitya", 15);
        Person p7 = new Person("Seva", 16);
        Person p8 = new Person("Angelina", 17);
        Person p9 = new Person("Elvira", 18);
        Person p10 = new Person("Nick", 19);

        hashMap.insert(p1, 21);
        hashMap.insert(p2, 22);
        hashMap.insert(p3, 23);
        hashMap.insert(p4, 24);
        hashMap.insert(p5, 25);
        hashMap.insert(p6, 26);
        hashMap.insert(p7, 27);
        hashMap.insert(p8, 28);
        hashMap.insert(p9, 29);
        hashMap.insert(p10, 30);

        assertEquals(hashMap.get(p1), 21);
        assertEquals(hashMap.get(p2), 22);
        assertEquals(hashMap.get(p8), 28);
        assertEquals(hashMap.get(p9), 29);
        assertEquals(hashMap.get(p10), 30);

        hashMap.remove(p1);
        hashMap.remove(p2);
        hashMap.remove(p3);
        hashMap.remove(p4);
        hashMap.remove(p5);
        hashMap.remove(p6);
        hashMap.remove(p7);
        hashMap.remove(p8);
        hashMap.remove(p9);
        hashMap.remove(p10);
        assertEquals(0, hashMap.get(p1));
        assertEquals(0, hashMap.get(p2));
        assertEquals(0, hashMap.get(p8));
        assertEquals(0, hashMap.get(p9));
        assertEquals(0, hashMap.get(p10));
    }
}
