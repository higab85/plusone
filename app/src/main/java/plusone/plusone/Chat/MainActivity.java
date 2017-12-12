package plusone.plusone.Chat;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import plusone.plusone.R;


public class MainActivity extends AppCompatActivity {

    static String eventID;
    static String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        eventID = this.getIntent().getStringExtra("eventID");
        username = this.getIntent().getStringExtra("username");

        setContentView(R.layout.activity_chat);


    }
}
