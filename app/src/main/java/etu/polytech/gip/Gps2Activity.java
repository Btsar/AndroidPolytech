package etu.polytech.gip;

import android.app.Activity;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


public class Gps2Activity extends Activity implements LocationListener {

    LocationManager locationManager;
    Location location;
    String fournisseur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria criteres = new Criteria();
        criteres.setAccuracy(Criteria.ACCURACY_FINE);
        criteres.setCostAllowed(true);
        criteres.setPowerRequirement(Criteria.POWER_HIGH);
        String fournisseur = locationManager.getBestProvider(criteres, true);

        try {
            locationManager.requestLocationUpdates(fournisseur,
                    10000,   // 10 sec
                    10, this);
        }
        catch (SecurityException e) {}
    }

    @Override
    public void onLocationChanged(Location location) {
        try {
            this.location = locationManager.getLastKnownLocation(fournisseur);
        } catch (SecurityException e) {}
    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        // TODO Auto-generated method stub

    }

    public Location getPosition () {
        try {
            Location localisation = this.locationManager.getLastKnownLocation(fournisseur);
            return localisation;
        }  catch (SecurityException e) {}
        return null;
    }

    public int getDistance (String latitudeIncident, String longitudeIncident) {
        Location location = this.getPosition();

        double latitude0 = convertRad(Double.parseDouble(latitudeIncident));
        double longitude0 = convertRad(Double.parseDouble(longitudeIncident));

        double latitude1 = convertRad(location.getLatitude());
        double longitude1 = convertRad(location.getLongitude());

        int R = 6378000; //Rayon de la terre en mètre



        int distance = (int) (R * (Math.PI/2 - Math.asin( Math.sin(latitude0) * Math.sin(latitude1) + Math.cos(longitude0 - longitude1) * Math.cos(longitude1) * Math.cos(longitude0))));
        return distance;
    }

    private double convertRad (double input){
        return (Math.PI * input)/180;
    }
}

