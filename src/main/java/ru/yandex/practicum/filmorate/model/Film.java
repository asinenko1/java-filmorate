package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.*;
import lombok.Data;
import ru.yandex.practicum.filmorate.Marker;

import java.time.LocalDate;

/**
 * Film.
 */
@Data
public class Film {

    private static final int MAX_DESCRIPTION_LENGTH = 200;
    private static final LocalDate FIRST_RELEASE_DATE = LocalDate.of(1895, 12, 28);

    @NotNull(groups = Marker.Update.class)
    @Positive (groups = Marker.Update.class)
    private Integer id;

    @NotBlank (groups = Marker.Create.class)
    private String name;


    @Size(max = MAX_DESCRIPTION_LENGTH, groups = {Marker.Create.class, Marker.Update.class})
    private String description;

    @NotNull(groups = Marker.Create.class)
    private LocalDate releaseDate;

    @NotNull(groups = Marker.Create.class)
    @Positive(groups = {Marker.Create.class, Marker.Update.class})
    private Integer duration;

    @AssertTrue(groups = {Marker.Create.class, Marker.Update.class})
    public boolean isReleaseDateValid() {
        return releaseDate == null || !releaseDate.isBefore((FIRST_RELEASE_DATE));
    }
}
