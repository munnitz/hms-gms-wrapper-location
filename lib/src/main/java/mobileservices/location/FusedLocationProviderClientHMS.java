package mobileservices.location;

import android.app.PendingIntent;
import android.location.Location;
import android.os.Looper;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresPermission;

import mobileservices.tasks.Continuation;
import mobileservices.tasks.HMS.TaskHMS;
import mobileservices.tasks.Task;

public class FusedLocationProviderClientHMS implements FusedLocationProviderClient {

    private final com.huawei.hms.location.FusedLocationProviderClient fusedLocationProviderClient;

    public FusedLocationProviderClientHMS(@NonNull com.huawei.hms.location.FusedLocationProviderClient fusedLocationProviderClient) {
        this.fusedLocationProviderClient = fusedLocationProviderClient;
    }

    @Override
    public Task<Void> flushLocations() {
        return new TaskHMS<>(fusedLocationProviderClient.flushLocations()).continueWith(new ContinuationIdentity<Void>());
    }

    @RequiresPermission(
            anyOf = {"android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION"}
    )
    @Override
    public Task<Location> getLastLocation() {
        return new TaskHMS<>(fusedLocationProviderClient.getLastLocation()).continueWith(new ContinuationIdentity<Location>());
    }

    @Override
    public Task<LocationAvailability> getLocationAvailability() {
        return new TaskHMS<>(fusedLocationProviderClient.getLocationAvailability()).continueWith(new Continuation<com.huawei.hms.location.LocationAvailability, LocationAvailability>() {
            @Override
            public LocationAvailability then(@NonNull Task<com.huawei.hms.location.LocationAvailability> task) throws Exception {
                return new LocationAvailability(null, task.getResult());
            }
        }).continueWith(new ContinuationIdentity<LocationAvailability>());
    }

    @Override
    public Task<Void> removeLocationUpdates(PendingIntent callbackIntent) {
        return new TaskHMS<>(fusedLocationProviderClient.removeLocationUpdates(callbackIntent)).continueWith(new ContinuationIdentity<Void>());
    }

    @RequiresPermission(
            anyOf = {"android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION"}
    )
    @Override
    public Task<Void> removeLocationUpdates(final LocationCallback callback) {
        return new TaskHMS<>(
                fusedLocationProviderClient.removeLocationUpdates(
                        new com.huawei.hms.location.LocationCallback() {
                            @Override
                            public void onLocationAvailability(com.huawei.hms.location.LocationAvailability locationAvailability) {
                                callback.onLocationAvailability(new LocationAvailability(null, locationAvailability));
                            }

                            @Override
                            public void onLocationResult(com.huawei.hms.location.LocationResult locationResult) {
                                callback.onLocationResult(new LocationResult(null, locationResult));
                            }
                        }
                )
        ).continueWith(new ContinuationIdentity<Void>());
    }

    @RequiresPermission(
            anyOf = {"android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION"}
    )
    @Override
    public Task<Void> requestLocationUpdates(LocationRequest request, final LocationCallback callback, Looper looper) {
        return new TaskHMS<>(
                fusedLocationProviderClient.requestLocationUpdates(
                        request.hmsLocationRequest,
                        new com.huawei.hms.location.LocationCallback() {
                            @Override
                            public void onLocationAvailability(com.huawei.hms.location.LocationAvailability locationAvailability) {
                                callback.onLocationAvailability(new LocationAvailability(null, locationAvailability));
                            }

                            @Override
                            public void onLocationResult(com.huawei.hms.location.LocationResult locationResult) {
                                callback.onLocationResult(new LocationResult(null, locationResult));
                            }
                        },
                        looper
                )
        ).continueWith(new ContinuationIdentity<Void>());
    }

    @Override
    public Task<Void> requestLocationUpdates(LocationRequest request, PendingIntent callbackIntent) {
        return new TaskHMS<>(
                fusedLocationProviderClient.requestLocationUpdates(
                        request.hmsLocationRequest,
                        callbackIntent
                )
        ).continueWith(new ContinuationIdentity<Void>());
    }

    @RequiresPermission(
            anyOf = {"android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION"}
    )
    @Override
    public Task<Void> setMockLocation(Location mockLocation) {
        return new TaskHMS<>(fusedLocationProviderClient.setMockLocation(mockLocation)).continueWith(new ContinuationIdentity<Void>());
    }

    @RequiresPermission(
            anyOf = {"android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION"}
    )
    @Override
    public Task<Void> setMockMode(boolean isMockMode) {
        return new TaskHMS<>(fusedLocationProviderClient.setMockMode(isMockMode)).continueWith(new ContinuationIdentity<Void>());
    }
}
