package bul.nik.tldtesttask.exception;

public class RegionNotFoundException extends RuntimeException {
    public RegionNotFoundException(long regionId) {
        super(String.format("Region with id %d not found", regionId));
    }

}
