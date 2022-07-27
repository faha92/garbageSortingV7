package dk.itu.garbage;
import androidx.lifecycle.ViewModel;
import java.util.ArrayList;
import java.util.List;

public class ItemsDB extends ViewModel {
  private static String where;
  private static final List<Item> values = new ArrayList<>();


  public ItemsDB() {
    values.add(new Item("coffee", "Irma"));
    values.add(new Item("carrots", "Netto"));
    values.add(new Item("milk", "Netto"));
    values.add(new Item("bread", "bakery"));
    values.add(new Item("butter", "Irma"));
  }

  public void addItem(String what, String where) {
    values.add(new Item(what, where));
  }

  public void removeItem(String what) {
    for (Item t : values) {
      if (t.getWhat().equals(what)) {
        values.remove(t);
        break;
      }
    }
  }

  public int size() {
    return values.size();
  }

  public String listItems() {
    StringBuilder r = new StringBuilder();
    for (Item i : values) r.append("Buy ").append(i.toString()).append("\n");
    return r.toString();
  }


  public static String garbageLookup(String garbage) {


    for (Item i : values) {

      if (i.getWhat().contains(garbage)) {
        where = i.getWhere();

        break;
      }

    }
    return garbage + " should be placed in: " + where;}


  public List<Item> getValues() {  return values;  }
}