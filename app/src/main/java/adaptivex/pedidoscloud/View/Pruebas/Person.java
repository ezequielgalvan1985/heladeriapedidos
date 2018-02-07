package adaptivex.pedidoscloud.View.Pruebas;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ezequiel on 21/06/2016.
 */
class Person {
    String name;
    String age;
    int photoId;

    Person(String name, String age, int photoId) {
        this.name = name;
        this.age = age;
        this.photoId = photoId;
    }


private List<Person> persons;

    // This method creates an ArrayList that has three Person objects
// Checkout the project associated with this tutorial on Github if
// you want to use the same images.
    protected void initializeData(){
        persons = new ArrayList<>();

    }

    public List<Person> getPersons() {
        return persons;
    }
}