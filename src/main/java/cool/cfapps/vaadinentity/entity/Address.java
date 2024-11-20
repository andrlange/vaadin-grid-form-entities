package cool.cfapps.vaadinentity.entity;

import cool.cfapps.vaadinentity.entity.base.BaseEntity;
import cool.cfapps.vaadinentity.entity.base.FormField;
import cool.cfapps.vaadinentity.entity.base.GridColumn;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Address implements BaseEntity {
    @GridColumn(header = "ID", order = 0)
    private Long addressId;

    @GridColumn(header = "Street", order = 1)
    @FormField(label = "Street", required = true, order = 1)
    private String street;

    @GridColumn(header = "City", order = 2)
    @FormField(label = "City", required = true, order = 2)
    private String city;

    @GridColumn(header = "Country", order = 3)
    @FormField(label = "Country", required = true, order = 3)
    private String country;

    @GridColumn(header = "Postal Code", order = 4)
    @FormField(label = "Postal Code", required = true, order = 4)
    private String postalCode;

    @GridColumn(header = "Demo Value", order = 5, sortable = false)
    public String demoValue(){
        return "Hello World!";  // Just for demonstration purpose
    }
}
