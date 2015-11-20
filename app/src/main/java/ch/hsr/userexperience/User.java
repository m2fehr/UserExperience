package ch.hsr.userexperience;

/**
 * Created by Matthias on 13.11.15.
 * A User DTO
 */
public class User {

    private int _age;
    private int _gender;
    private int _location;
    private int _patience;
    private int _abo;
    private int _satisfaction;
    private int _aborted;

    public int get_age() {
        return _age;
    }

    public void set_age(int _age) {
        this._age = _age;
    }

    public int get_gender() {
        return _gender;
    }

    public void set_gender(int _gender) {
        this._gender = _gender;
    }

    public int get_location() {
        return _location;
    }

    public void set_location(int _location) {
        this._location = _location;
    }

    public int get_patience() {
        return _patience;
    }

    public void set_patience(int _patience) {
        this._patience = _patience;
    }

    public int get_abo() {
        return _abo;
    }

    public void set_abo(int _abo) {
        this._abo = _abo;
    }

    public int get_satisfaction() {
        return _satisfaction;
    }

    public void set_satisfaction(int _satisfaction) {
        this._satisfaction = _satisfaction;
    }

    public int get_aborted() {
        return _aborted;
    }

    public void set_aborted(int _aborted) {
        this._aborted = _aborted;
    }
}
