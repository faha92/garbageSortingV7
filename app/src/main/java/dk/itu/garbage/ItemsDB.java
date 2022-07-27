package dk.itu.garbage;
import android.content.Context;

import androidx.lifecycle.ViewModel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ItemsDB extends ViewModel {
  private static String where;
  private static final List<Item> values = new ArrayList<>();
  private ItemsDB(Context context) { fillItemsDB(context,"items.txt"); }


  public ItemsDB() {
    values.add(new Item("newspaper", "paper"));
    values.add(new Item("magazine", "paper"));
    values.add(new Item("milk carton", "food"));
    values.add(new Item("book", "paper"));
    values.add(new Item("shampoo bottle", "plastic"));
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

  public void fillItemsDB(Context context, String filename) {
    try {
      BufferedReader reader= new BufferedReader(
              new InputStreamReader(context.getAssets().open(filename)));
      String line= reader.readLine();
      while (line != null) {
        String[] gItem= line.split(",");
        addItem(gItem[0], gItem[1]);
        line= reader.readLine();
      }
    } catch (IOException e) {  // Error occurred when opening raw file for reading.
    }
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