package io.abhishekpareek.app.chirp;

/**
 * Created by apareek on 2/1/16.
 */

class ListPerson {
    String name;

    public String getName() {
        return name;
    }

    public String getAge() {
        return age;
    }

    public int getPhotoId() {
        return photoId;
    }

    String age;
    int photoId;

    ListPerson(String name, String age, int photoId) {
        this.name = name;
        this.age = age;
        this.photoId = photoId;
    }


}

