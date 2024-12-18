package org.example.relocantsbackend.exception;


public class NotFoundException extends RuntimeException {

    public NotFoundException() {
        super("Entity not found");
    }

    //Не найдена одна сущность типа name по ключу key
    public NotFoundException(String name, Object key) {
        super("Entity" + name + " (" + key + ") not found.");
    }

    //Не найдено много сущностей типа type
    public NotFoundException(String name) {
        super("No entites of" + name + "were found.");
    }

}

