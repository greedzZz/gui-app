package common.content;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class SpaceMarine implements Comparable<SpaceMarine>, Serializable {
    private int id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private LocalDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private Integer health; //Поле не может быть null, Значение поля должно быть больше 0
    private AstartesCategory category; //Поле может быть null
    private Weapon weaponType; //Поле может быть null
    private MeleeWeapon meleeWeapon; //Поле может быть null
    private Chapter chapter; //Поле не может быть null
    private String owner;

    public SpaceMarine(String name,
                       Coordinates coordinates,
                       Integer health,
                       AstartesCategory category,
                       Weapon weaponType,
                       MeleeWeapon meleeWeapon,
                       Chapter chapter) throws IllegalArgumentException {
        if (name == null) {
            throw new IllegalArgumentException("Name cannot be empty word.");
        } else if (health == null || health <= 0) {
            throw new IllegalArgumentException("Health value cannot be empty word and must be greater than 0.");
        }
        try {
            this.name = name;
            this.coordinates = coordinates;
            this.health = health;
            this.category = category;
            this.weaponType = weaponType;
            this.meleeWeapon = meleeWeapon;
            this.chapter = chapter;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public SpaceMarine(Integer id,
                       String name,
                       Coordinates coordinates,
                       CharSequence creationDate,
                       Integer health,
                       AstartesCategory category,
                       Weapon weaponType,
                       MeleeWeapon meleeWeapon,
                       Chapter chapter,
                       String owner) throws IllegalArgumentException {
        if (name == null) {
            throw new IllegalArgumentException("Name cannot be empty word.");
        } else if (health == null || health <= 0) {
            throw new IllegalArgumentException("Health value cannot be empty word and must be greater than 0.");
        } else if (id == null) {
            throw new IllegalArgumentException("ID cannot be empty word.");
        } else if (id <= 0) {
            throw new IllegalArgumentException("ID value must be greater than 0");
        } else if (creationDate == null) {
            throw new IllegalArgumentException("Creation date cannot be empty word.");
        }
        try {
            LocalDateTime.parse(creationDate);
        } catch (Exception e) {
            throw new IllegalArgumentException("Creation date must be written according to ISO-8601 calendar system, such as 2007-12-03T10:15:30.");
        }
        try {
            this.id = id;
            this.name = name;
            this.coordinates = coordinates;
            this.creationDate = LocalDateTime.parse(creationDate);
            this.health = health;
            this.category = category;
            this.weaponType = weaponType;
            this.meleeWeapon = meleeWeapon;
            this.chapter = chapter;
            this.owner = owner;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public int compareTo(SpaceMarine sm) {
        return name.compareTo(sm.getName());
    }

    public int getID() {
        return this.id;
    }

    public void setID(int id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public int getCoordinateX() {
        return getCoordinates().getX();
    }

    public Integer getCoordinateY() {
        return getCoordinates().getY();
    }

    public String getCreationDate() {
        return creationDate.toString();
    }

    public LocalDateTime getCreationDateValue() {
        return this.creationDate;
    }

    public void setCreationDate() {
        this.creationDate = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
    }

    public Integer getHealth() {
        return this.health;
    }

    public AstartesCategory getCategory() {
        return category;
    }

    public Weapon getWeaponType() {
        return weaponType;
    }

    public MeleeWeapon getMeleeWeapon() {
        return meleeWeapon;
    }

    public Chapter getChapter() {
        return chapter;
    }

    public String getChapterName() {
        return chapter.getName();
    }

    public String getChapterWorld() {
        return chapter.getWorld();
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public SimpleIntegerProperty getIDProperty() {
        return new SimpleIntegerProperty(getID());
    }

    public SimpleStringProperty getNameProperty() {
        return new SimpleStringProperty(getName());
    }

    public SimpleIntegerProperty getXProperty() {
        return new SimpleIntegerProperty(getCoordinateX());
    }

    public SimpleIntegerProperty getYProperty() {
        return new SimpleIntegerProperty(getCoordinateY());
    }

    public SimpleIntegerProperty getHealthProperty() {
        return new SimpleIntegerProperty(getHealth());
    }

    public SimpleStringProperty getCategoryProperty() {
        if (category != null) return new SimpleStringProperty(getCategory().toString());
        else return new SimpleStringProperty(null);
    }

    public SimpleStringProperty getWeaponProperty() {
        if (weaponType != null) return new SimpleStringProperty(getWeaponType().toString());
        else return new SimpleStringProperty(null);
    }

    public SimpleStringProperty getMeleeProperty() {
        if (meleeWeapon != null) return new SimpleStringProperty(getMeleeWeapon().toString());
        else return new SimpleStringProperty(null);
    }

    public SimpleStringProperty getChapterNameProperty() {
        return new SimpleStringProperty(getChapterName());
    }

    public SimpleStringProperty getChapterWorldProperty() {
        return new SimpleStringProperty(getChapterWorld());
    }

    public SimpleStringProperty getOwnerProperty() {
        return new SimpleStringProperty(getOwner());
    }
}
