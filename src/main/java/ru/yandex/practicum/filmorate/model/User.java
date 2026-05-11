package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.*;
import lombok.Data;
import ru.yandex.practicum.filmorate.Marker;

import java.time.LocalDate;

@Data
public class User {

    @NotNull(groups = Marker.Update.class)
    @Positive(groups = Marker.Update.class)
    private Integer id;

    @NotBlank(groups = Marker.Create.class)
    @Email(groups = {Marker.Create.class, Marker.Update.class})
    private String email;

    @NotBlank(groups = Marker.Create.class)
    private String login;

    @AssertTrue(groups = {Marker.Create.class, Marker.Update.class})
    public boolean isLoginValid() {
        return login == null || (!login.isBlank() && !login.contains(" "));
    }

    private String name;

    @NotNull(groups = Marker.Create.class)
    @PastOrPresent(groups = {Marker.Create.class, Marker.Update.class})
    private LocalDate birthday;
}
