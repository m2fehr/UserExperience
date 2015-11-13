package ch.hsr.userexperience;

/**
 * Created by Matthias on 13.11.15.
 * A User DTO
 */
public class User {

    private String _age;
    private String _gender;
    private String _location;
    private String _patience;
    private String _abo;
    private String _satisfaction;
    private String _aborted;


    public String get_age() {
        return _age;
    }

    public void set_age(String _age) {
        this._age = _age;
    }

    public String get_gender() {
        return _gender;
    }

    public void set_gender(String _gender) {
        this._gender = _gender;
    }

    public String get_location() {
        return _location;
    }

    public void set_location(String _location) {
        this._location = _location;
    }

    public String get_patience() {
        return _patience;
    }

    public void set_patience(String _patience) {
        this._patience = _patience;
    }

    public String get_abo() {
        return _abo;
    }

    public void set_abo(String _abo) {
        this._abo = _abo;
    }

    public String get_satisfaction() {
        return _satisfaction;
    }

    public void set_satisfaction(String _satisfaction) {
        this._satisfaction = _satisfaction;
    }

    public String get_aborted() {
        return _aborted;
    }

    public void set_aborted(String _aborted) {
        this._aborted = _aborted;
    }
}
