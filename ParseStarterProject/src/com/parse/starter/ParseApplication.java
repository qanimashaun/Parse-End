package com.parse.starter;

import android.app.Application;
import android.graphics.Point;
import android.location.Location;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseCrashReporting;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;


import java.util.List;

public class ParseApplication extends Application {

    private Point mCurrentPoint;

  @Override
  public void onCreate() {
    super.onCreate();

    // Initialize Crash Reporting.
    ParseCrashReporting.enable(this);

    // Enable Local Datastore.
    Parse.enableLocalDatastore(this);

    // Add your initialization code here
      Parse.initialize(this, "02hXcUEwVPYdQXSUURBbbbWJ15IDpRIClFEMuhtG", "SYulTaw9BT4zDs99kON3dt7cZBChEIjQj6KPMDmq");

//    ParseUser.enableAutomaticUser();
//    ParseACL defaultACL = new ParseACL();
//    // Optionally enable public read access.
//    defaultACL.setPublicReadAccess(true);
//    ParseACL.setDefaultACL(defaultACL, true);
//      try {
//          ParseUser.getCurrentUser().save();
//      } catch (ParseException e) { }
//      ParseObject Location = new ParseObject("Location");
//      Location.put("xcoordinate", 10);
//      Location.put("ycoordinate", 15);
//      Location.put("fbId", 2);
//      Location.saveInBackground();


      Point currentLoc = getLocation(1);

        /* oozOpp3Uju fbid=2
        * pKGLvJ7sSZ fbid=1
        * */
      updateLocation("oozOpp3Uju", 4, 4);



  }

    private void updateLocation(String id, final int xCoordinate, final int yCoordinate) {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Location");
        // Retrieve the object by id
        query.getInBackground(id, new GetCallback<ParseObject>() {
            public void done(ParseObject location, ParseException e) {
                if (e == null) {
                    // Now let's update it with some new data. In this case, only cheatMode and score
                    // will get sent to the Parse Cloud. playerName hasn't changed.
                    location.put("xcoordinate", xCoordinate);
                    location.put("ycoordinate", yCoordinate);
                    location.saveInBackground();
                }
            }
        });
    }

    private Point getLocation(int id) {

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Location");
        query.whereEqualTo("fbId",id);
        query.findInBackground(new FindCallback<ParseObject>() {

            public void done(List<ParseObject> object, ParseException e) {

                if (e == null) {
                    int a = (int) object.get(0).get("xcoordinate");
                    int b = (int) object.get(0).get("ycoordinate");
                    mCurrentPoint =new Point(a,b);
                    Toast.makeText(getApplicationContext(), "The locations are " + mCurrentPoint.x + " , "+mCurrentPoint.y, Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();

                }
            }
        });
        return mCurrentPoint;
    }
}
