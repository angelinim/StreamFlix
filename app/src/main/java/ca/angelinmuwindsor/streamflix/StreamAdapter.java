package ca.angelinmuwindsor.streamflix;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;

public class StreamAdapter extends FirestoreRecyclerAdapter <StreamInfo, StreamAdapter.StreamHolder>{

    private OnItemClickListener listener;

    public StreamAdapter(@NonNull FirestoreRecyclerOptions<StreamInfo> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull StreamHolder holder, int position, @NonNull StreamInfo model) {
        holder.textViewTitle.setText(model.getStreamTitle());
        holder.textViewDescription.setText(model.getStreamDescription());
        holder.textViewLocation.setText(model.getStreamLocation().toString());
    }

    @NonNull
    @Override
    public StreamHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.stream_item,
                viewGroup, false);
        return new StreamHolder(v);
    }

    class StreamHolder extends RecyclerView.ViewHolder{
        TextView textViewTitle;
        TextView textViewDescription;
        TextView textViewLocation;

        public StreamHolder(View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.text_view_title);
            textViewDescription = itemView.findViewById(R.id.text_view_description);
            textViewLocation = itemView.findViewById(R.id.text_view_location);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && listener != null){
                        listener.onItemClick(getSnapshots().getSnapshot(position), position);
                    }
                }
            });

        }
    }

    public interface OnItemClickListener{
        void onItemClick(DocumentSnapshot documentSnapshot, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }
}
