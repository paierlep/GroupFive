package at.sw2017.nodinero;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntroFragment;

public class WelcomeActivity extends AppIntro {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

       addSlide(AppIntroFragment.newInstance(getResources().getString(R.string.title_activity_welcome),
               getResources().getString(R.string.TextIntro1),
               R.drawable.money,
               Color.parseColor("#51e2b7")));

        addSlide(AppIntroFragment.newInstance(getResources().getString(R.string.title_activity_welcome2),
                getResources().getString(R.string.TextIntro2),
                R.drawable.create,
                Color.parseColor("#8c50e3")));

        showStatusBar(false);
        showSkipButton(false);
        setFadeAnimation();

        // TO DO: Permissions einholen
        //askForPermissions(new String[]{Manifest.permission.CAMERA}, 3);

    }

    @Override
    public void onDonePressed(){
        startActivity(new Intent(this, NoDineroActivity.class));
        finish();
    }

}
