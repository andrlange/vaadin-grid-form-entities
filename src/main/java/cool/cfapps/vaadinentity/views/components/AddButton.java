package cool.cfapps.vaadinentity.views.components;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.icon.VaadinIcon;

public class AddButton extends Button {

    public AddButton(Runnable clickListener) {
        super(VaadinIcon.PLUS.create());
        addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_ICON);
        addClickListener(event -> clickListener.run());
        getStyle().setPadding("0");
        getStyle().setBorderRadius("50%");
        getStyle().setMargin("0.5em");
    }
}
