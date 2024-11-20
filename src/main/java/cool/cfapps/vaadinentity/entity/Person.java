package cool.cfapps.vaadinentity.entity;

import cool.cfapps.vaadinentity.entity.base.BaseEntity;
import cool.cfapps.vaadinentity.entity.base.FormField;
import cool.cfapps.vaadinentity.entity.base.GridColumn;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.Period;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Person implements BaseEntity {
    @GridColumn(header = "ID", order = 0)
    private Long personId;

    @GridColumn(header = "First Name", order = 1)
    @FormField(label = "First Name", required = true, order = 1)
    private String firstName;

    @GridColumn(header = "Last Name", order = 2)
    @FormField(label = "Last Name", required = true, order = 2)
    private String lastName;

    @GridColumn(header = "Email", order = 3, sortable = false)
    @FormField(label = "Email", required = true, order = 3)
    private String email;

    @GridColumn(header = "Is Active", order = 4)
    @FormField(label = "Is Active", required = true, order = 4)
    private Boolean isActive;

    @GridColumn(header = "Birthday", order = 5, dateTimeFormat = "dd.MM.yyyy")
    @FormField(label = "Birthday", required = true)
    private LocalDate birthday;

    @GridColumn(header = "Age", order = 6)
    public int getAge() {
        if (birthday == null) return 0;
        return Period.between(birthday.atStartOfDay().toLocalDate(), LocalDate.now()).getYears();
    }
}
