package dk.itu.garbage;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ListFragment extends Fragment {

  private Button backButton;
  // Model: Database of items
  ItemsViewModel itemsDB;

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    super.onCreateView(inflater, container, savedInstanceState);
    final View v= inflater.inflate(R.layout.fragment_list, container, false);
    backButton= v.findViewById(R.id.back_button);
    itemsDB= new ViewModelProvider(requireActivity()).get(ItemsViewModel.class);

    // Set up recyclerview
    RecyclerView itemList= v.findViewById(R.id.listItems);
    itemList.setLayoutManager(new LinearLayoutManager(getActivity()));
    ItemAdapter mAdapter= new ItemAdapter();
    itemList.setAdapter(mAdapter);

    itemsDB.getValue().observe(getActivity(), itemsDB -> mAdapter.notifyDataSetChanged());

    //Only show Back button in Portrait mode
    if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
      backButton.setOnClickListener(view ->
          getActivity()
              .getSupportFragmentManager()
              .beginTransaction()
              .replace(R.id.main_fragment,
                  new UIFragment()).commit());
    }
    return v;
  }

  private class ItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    private final TextView mWhatTextView, mWhereTextView, mNoView;

    public ItemHolder(View itemView) {
      super(itemView);
      mNoView= itemView.findViewById(R.id.item_no);
      mWhatTextView= itemView.findViewById(R.id.item_what);
      mWhereTextView= itemView.findViewById(R.id.item_where);
      itemView.setOnClickListener(this);
    }

    public void bind(Item item, int position){
      mNoView.setText(" "+position+" ");
      mWhatTextView.setText(item.getWhat());
      mWhereTextView.setText(item.getWhere());
    }
    @Override
    public void onClick(View v) {
      // Trick from https://stackoverflow.com/questions/5754887/accessing-view-inside-the-linearlayout-with-code
      String what= (String)((TextView)v.findViewById(R.id.item_what)).getText();
      //once we have a value for what, we can delete the item
      itemsDB.removeItem(what);
    }
  }

  private class ItemAdapter extends RecyclerView.Adapter<ItemHolder> {

    @Override
    public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
      LayoutInflater layoutInflater= LayoutInflater.from(getActivity());
      View v= layoutInflater.inflate(R.layout.one_row, parent, false);
      return new ItemHolder(v);
    }

    @Override
    public void onBindViewHolder(ItemHolder holder, int position) {
      Item item=  itemsDB.getList().get(position);
      holder.bind(item, position);
    }

    @Override
    public int getItemCount(){ return itemsDB.size(); }
  }
}