package Model;

public class User {

   private String name;
   private int age;

   public User(String name, int age) {
      this.name = name;
      this.age = age;
   }

   public User(User user) {
      name = user.name;
      age = user.age;
   }

   public String getName() {
      return name;
   }
   public int getAge() {
      return age;
   }
}
