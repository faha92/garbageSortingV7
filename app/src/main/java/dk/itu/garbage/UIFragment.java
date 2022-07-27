package dk.itu.garbage;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

public class UIFragment extends Fragment {
  //GUI variables
  Button addItem, listItems,search;
  private TextView newWhat, newWhere;

  // Model: Database of items
  ItemsViewModel itemsDB;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

    super.onCreateView(inflater, container, savedInstanceState);
    final View v= inflater.inflate(R.layout.fragment_ui, container, false);

    //Text Fields
    newWhat=  v.findViewById(R.id.what_text);
    newWhere= v.findViewById(R.id.where_text);

    //Buttons
    listItems= v.findViewById(R.id.list);
    addItem= v.findViewById(R.id.add_button);
    search= v.findViewById(R.id.search_button);

    itemsDB= new ViewModelProvider(requireActivity()).get(ItemsViewModel.class);

    //Only show List button in Portrait mode
    if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
      listItems.setOnClickListener(view ->
          getActivity().
              getSupportFragmentManager()
              .beginTransaction()
              .replace(R.id.main_fragment,
                  new ListFragment()).commit());
    }



    // Look up garbage
    search.setOnClickListener(view -> {


      String garbageInput = newWhat.getText().toString();
      String result = ItemsDB.garbageLookup((garbageInput));
      newWhere.setText(result);

      Toast.makeText(getActivity(), newWhat.getText(),
              Toast.LENGTH_SHORT).show();

    });




    // adding a new thing
    addItem.setOnClickListener(view -> {
      String whatS= newWhat.getText().toString().trim();
      String whereS= newWhere.getText().toString().trim();
      if ((whatS.length() > 0) && (whereS.length() > 0)) {
        itemsDB.addItem(whatS, whereS);
        newWhat.setText("");
        newWhere.setText("");
      } else Toast.makeText(getActivity(), R.string.empty_toast, Toast.LENGTH_LONG).show();
    });



    return v;
  }


}

