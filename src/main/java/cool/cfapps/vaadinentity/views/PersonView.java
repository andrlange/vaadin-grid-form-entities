package cool.cfapps.vaadinentity.views;

import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import cool.cfapps.vaadinentity.entity.Person;
import cool.cfapps.vaadinentity.entity.base.GenericView;
import cool.cfapps.vaadinentity.service.DataService;

import java.util.List;

@PageTitle("PERSONS")
@Menu(order = 0, icon = "line-awesome/svg/home-solid.svg")
@Route(value = "", layout = MainLayout.class)
public class PersonView extends GenericView<Person> {

    private final DataService dataService;


    public PersonView(DataService dataService) {
        super(Person.class);
        this.dataService = dataService;
        refreshGrid();

        // Add Button for adding new person
        Div addButton = new Div();
        addButton.setClassName("circle-button-container");
        Avatar addAvatar = new Avatar("+");
        addAvatar.addClassName("circle-button");
        addButton.add(addAvatar);
        addButton.addClickListener(event -> addNew());
        gridContainer.addComponentAsFirst(addButton);
    }

    @Override
    protected void saveEntity(Person entity) {
        dataService.savePerson(entity);
    }

    @Override
    protected List<Person> loadEntities() {
        return dataService.findAllPersons();
    }

    @Override
    protected void deleteEntity(Person person) {
        dataService.deletePerson(person);
    }
}
