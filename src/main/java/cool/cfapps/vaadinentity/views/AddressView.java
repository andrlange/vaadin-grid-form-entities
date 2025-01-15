package cool.cfapps.vaadinentity.views;

import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import cool.cfapps.vaadinentity.entity.Address;
import cool.cfapps.vaadinentity.entity.base.GenericView;
import cool.cfapps.vaadinentity.service.DataService;
import cool.cfapps.vaadinentity.views.components.AddButton;

import java.util.List;

@PageTitle("ADDRESSES")
@Menu(order = 1, icon = "line-awesome/svg/boxes-solid.svg")
@Route(value = "addresses", layout = MainLayout.class)
public class AddressView extends GenericView<Address> {

    private final DataService dataService;

    public AddressView(DataService dataService) {
        super(Address.class);
        this.dataService = dataService;
        refreshGrid();
    }

    @Override
    protected void saveEntity(Address entity) {
        dataService.saveAddress(entity);
    }

    @Override
    protected List<Address> loadEntities() {
        return dataService.findAllAddresses();
    }

    @Override
    protected void deleteEntity(Address address) {
        dataService.deleteAddress(address);
    }
}


