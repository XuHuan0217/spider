package com.github.xuhuan0217.spider.entity;

/**
 * Book Entity
 */
public class BookEntity implements Comparable<BookEntity> {
    private String title;
    private double rating;
    private int ratingnum;
    private String author;
    private String press;
    private String date;
    private String price;

    public BookEntity(String title, double rating, int ratingnum, String author, String press, String date, String price) {
        this.title = title;
        this.rating = rating;
        this.ratingnum = ratingnum;
        this.author = author;
        this.press = press;
        this.date = date;
        this.price = price;
    }

    public BookEntity(){

    }

    public BookEntity(String... params){
        if(params.length != 7) throw new IllegalArgumentException("params is not in right format");
        this.title = params[0];
        this.rating = Double.parseDouble(params[1]);
        this.ratingnum = Integer.parseInt(params[2]);
        this.author = params[3];
        this.press = params[4];
        this.date = params[5];
        this.price = params[6];
    }



    public String toCsv(){
        return title+","+rating+","+ratingnum+","+author+","+press+","+date+","+price;
    }

    @Override
    public String toString() {
        return "BookEntity{" +
                "title='" + title + '\'' +
                ", rating=" + rating +
                ", rating num=" + ratingnum +
                ", author='" + author + '\'' +
                ", publication='" + press + '\'' +
                ", date='" + date + '\'' +
                ", price='" + price + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BookEntity that = (BookEntity) o;

        if (!title.equals(that.title)) return false;
        if (!author.equals(that.author)) return false;
        if (!press.equals(that.press)) return false;
        return date.equals(that.date);
    }

    @Override
    public int hashCode() {
        int result = title.hashCode();
        result = 31 * result + author.hashCode();
        result = 31 * result + press.hashCode();
        result = 31 * result + date.hashCode();
        return result;
    }

    public String getTitle() {
        return title;
    }

    public BookEntity setTitle(String title) {
        this.title = title;
        return this;
    }

    public double getRating() {
        return rating;
    }

    public BookEntity setRating(double rating) {
        this.rating = rating;
        return this;
    }

    public int getRatingnum() {
        return ratingnum;
    }

    public BookEntity setRatingnum(int ratingnum) {
        this.ratingnum = ratingnum;
        return this;
    }

    public String getAuthor() {
        return author;
    }

    public BookEntity setAuthor(String author) {
        this.author = author;
        return this;
    }

    public String getPublication() {
        return press;
    }

    public BookEntity setPublication(String publication) {
        this.press = publication;
        return this;
    }

    public String getDate() {
        return date;
    }

    public BookEntity setDate(String date) {
        this.date = date;
        return this;
    }

    public String getPrice() {
        return price;
    }

    public BookEntity setPrice(String price) {
        this.price = price;
        return this;
    }

    public int compareTo(BookEntity o) {
        if(rating>o.getRating()) return -1;
        else if(rating<o.getRating()) return 1;
        else return 0;
    }
}
