package cool.cfapps.vaadinentity.views.components;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.icon.VaadinIcon;

public class DeleteButton extends Button {

    private Runnable deleteListener;

    public DeleteButton() {
        super(VaadinIcon.TRASH.create());
        addThemeVariants(ButtonVariant.LUMO_ERROR);

        addClickListener(event -> {
            // Create confirmation dialog
            ConfirmDialog dialog = new ConfirmDialog();
            dialog.setHeader("Confirm Delete");
            dialog.setText("Are you sure you want to delete this item? This action cannot be undone.");

            dialog.setCancelable(true);
            dialog.setCancelText("Cancel");

            dialog.setConfirmText("Delete");
            dialog.setConfirmButtonTheme("error primary");

            dialog.addConfirmListener(confirmEvent -> {
                deleteListener.run();
            });
            dialog.open();
        });
        // hide the button by default
        setVisible(false);
    }

    public void setDeleteListener(Runnable deleteListener) {
        this.deleteListener = deleteListener;
    }
}
