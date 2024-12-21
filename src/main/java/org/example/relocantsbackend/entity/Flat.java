package org.example.relocantsbackend.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "flats") // Указываем имя таблицы
public class Flat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Автоинкрементный первичный ключ
    private Long id;

    @Column(nullable = false, length = 100) // Поле не может быть NULL, длина ограничена 100 символами
    private String title;

    @Column(nullable = false) // Поле не может быть NULL
    private int price;

    @Column(nullable = false, length = 50)
    private String city;

    @Column(nullable = false, length = 150)
    private String adress;

    @Column(nullable = false)
    private double area;

    @Column(nullable = false)
    private int num_of_rooms;

    @Column(nullable = false, length = 500) // Ссылка на квартиру, длина до 500 символов
    private String link;

    @Column(length = 500) // Ссылка на изображение, длина до 500 символов
    private String image_url;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public double getArea() {
        return area;
    }

    public void setArea(double area) {
        this.area = area;
    }

    public int getNum_of_rooms() {
        return num_of_rooms;
    }

    public void setNum_of_rooms(int num_of_rooms) {
        this.num_of_rooms = num_of_rooms;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }
}
