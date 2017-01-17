package colormemory.test.com.colourmemory.activity;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import colormemory.test.com.colourmemory.R;
import colormemory.test.com.colourmemory.fragment.HomeFragment;

public class HomeActivity extends Activity {

    private HomeFragment mHomeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //setting the HomeFragment.
        mHomeFragment = HomeFragment.newInstance();
        FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.add(R.id.activity_home, mHomeFragment);
        fragmentTransaction.commit();
    }
}
