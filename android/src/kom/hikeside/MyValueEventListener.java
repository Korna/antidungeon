package kom.hikeside;

import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by Koma on 17.09.2017.
 */

public class MyValueEventListener implements ValueEventListener {
    private final DatabaseReference ref;
    private final TaskCompletionSource<DataSnapshot> taskSource;

    public MyValueEventListener(DatabaseReference ref, TaskCompletionSource<DataSnapshot> taskSource) {
        this.ref = ref;
        this.taskSource = taskSource;
    }

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        //snaps.put(ref, dataSnapshot);
        taskSource.setResult(dataSnapshot);
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {
        taskSource.setException(databaseError.toException());
    }
}
