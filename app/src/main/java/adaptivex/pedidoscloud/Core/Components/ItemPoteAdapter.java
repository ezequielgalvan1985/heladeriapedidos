package adaptivex.pedidoscloud.Core.Components;

/**
 * Created by egalvan on 10/3/2018.
 */
/*
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import adaptivex.pedidoscloud.R;


public class ItemPoteAdapter extends RecyclerView.Adapter<ItemPoteAdapter.ViewHolder> {

        private List<ItemPote> list;
        private Context mCtx;

        public CustomAdapter(List<ItemPote> list, Context mCtx) {
            this.list = list;
            this.mCtx = mCtx;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_items, parent, false);
            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(CustomAdapter.ViewHolder holder, int position) {
            MyList myList = list.get(position);
            holder.textViewHead.setText(myList.getHead());
            holder.textViewDesc.setText(myList.getDesc());

            holder.buttonViewOption.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    //will show popup menu here

                }
            });
        }


        @Override
        public int getItemCount() {
            return list.size();
        }


        public class ViewHolder extends RecyclerView.ViewHolder {

            public TextView textViewHead;
            public TextView textViewDesc;
            public TextView buttonViewOption;

            public ViewHolder(View itemView) {
                super(itemView);

                textViewHead = (TextView) itemView.findViewById(R.id.textViewHead);
                textViewDesc = (TextView) itemView.findViewById(R.id.textViewDesc);
                buttonViewOption = (TextView) itemView.findViewById(R.id.textViewOptions);
            }
        }
    }
}
*/