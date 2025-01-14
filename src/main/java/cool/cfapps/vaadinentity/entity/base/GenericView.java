package cool.cfapps.vaadinentity.entity.base;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.component.HasValueAndElement;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.datetimepicker.DateTimePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import cool.cfapps.vaadinentity.views.components.DeleteButton;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public abstract class GenericView<T extends BaseEntity> extends VerticalLayout {
    private final Grid<T> grid;
    private final FormLayout form;
    private final Class<T> entityClass;
    private T selectedItem;
    private final Button save = new Button("Save");
    private final Button cancel = new Button("Cancel");
    private final DeleteButton delete = new DeleteButton();
    private final BeanValidationBinder<T> binder;
    private final Div editor = new Div();
    public final Div gridContainer = new Div();

    protected GenericView(Class<T> entityClass) {
        this.entityClass = entityClass;
        this.grid = new Grid<>(entityClass, false);
        this.form = new FormLayout();
        this.binder = new BeanValidationBinder<>(entityClass);

        setupLayout();
        setupGrid();
        setupForm();
        setupEventHandlers();
    }

    private void setupLayout() {
        setSizeFull();

        // Create a split layout for grid and form
        SplitLayout splitLayout = new SplitLayout();
        splitLayout.setSizeFull();
        form.getStyle().setMarginLeft("20px");
        editor.add(form);
        editor.setVisible(false);
        gridContainer.add(grid);
        splitLayout.addToPrimary(gridContainer);
        splitLayout.addToSecondary(editor);

        splitLayout.setSplitterPosition(80.0);


        add(splitLayout);
    }

    private void setupGrid() {
        grid.setSizeFull();

        List<GridColumnInfo> gridColumns = new ArrayList<>();

        // Add field-based columns
        Arrays.stream(entityClass.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(GridColumn.class))
                .forEach(field -> {
                    GridColumn annotation = field.getAnnotation(GridColumn.class);
                    gridColumns.add(new GridColumnInfo(
                            field.getName(),
                            annotation.header().isEmpty() ? field.getName() : annotation.header(),
                            annotation.order(),
                            annotation.sortable(),
                            field.getType(),
                            null,
                            annotation.dateTimeFormat(),
                            annotation.showAsComponent()
                    ));
                });

        // Add method-based columns
        Arrays.stream(entityClass.getDeclaredMethods())
                .filter(method -> method.isAnnotationPresent(GridColumn.class))
                .forEach(method -> {
                    GridColumn annotation = method.getAnnotation(GridColumn.class);
                    String propertyName = method.getName();
                    if (propertyName.startsWith("get") || propertyName.startsWith("is")) {
                        propertyName = propertyName.startsWith("get") ?
                                propertyName.substring(3, 4).toLowerCase() + propertyName.substring(4) :
                                propertyName.substring(2, 3).toLowerCase() + propertyName.substring(3);
                    }

                    gridColumns.add(new GridColumnInfo(
                            propertyName,
                            annotation.header().isEmpty() ? propertyName : annotation.header(),
                            annotation.order(),
                            annotation.sortable(),
                            method.getReturnType(),
                            method,
                            annotation.dateTimeFormat(),
                            annotation.showAsComponent()
                    ));
                });

        // Sort columns by order and add them to grid
        gridColumns.stream()
                .sorted(Comparator.comparingInt(GridColumnInfo::order))
                .forEach(columnInfo -> {
                    Grid.Column<T> column;

                    // Get value function that works for both methods and fields
                    ValueProvider<T, ?> valueProvider = item -> {
                        try {
                            Object value;
                            if (columnInfo.method() != null) {
                                value = columnInfo.method().invoke(item);
                            } else {
                                Field field = entityClass.getDeclaredField(columnInfo.propertyName());
                                field.setAccessible(true);
                                value = field.get(item);
                            }
                            return value;
                        } catch (Exception e) {
                            e.printStackTrace();
                            return null;
                        }
                    };

                    // Create appropriate column based on type and showAsComponent
                    if (columnInfo.showAsComponent() &&
                        (columnInfo.type() == Boolean.class || columnInfo.type() == boolean.class)) {
                        // Boolean component column
                        column = grid.addComponentColumn(item -> {
                            Boolean value = (Boolean) valueProvider.apply(item);
                            Checkbox checkbox = new Checkbox(value != null ? value : false);
                            checkbox.setReadOnly(true);
                            return checkbox;
                        });
                    } else if (isTemporalType(columnInfo.type()) && !columnInfo.dateTimeFormat().isEmpty()) {
                        // DateTime column with formatting
                        column = grid.addColumn(item -> {
                            Object value = valueProvider.apply(item);
                            return formatValue(value, columnInfo);
                        });
                    } else {
                        // Regular value column with potential formatting
                        column = grid.addColumn(item -> {
                            Object value = valueProvider.apply(item);
                            return formatValue(value, columnInfo);
                        });
                    }

                    // Configure column properties
                    column.setHeader(columnInfo.header())
                            .setSortable(columnInfo.sortable())
                            .setKey(columnInfo.propertyName());
                });

    }

    // Helper interface for value extraction
    @FunctionalInterface
    private interface ValueProvider<T, R> {
        R apply(T item);
    }


    private boolean isTemporalType(Class<?> type) {
        return LocalDateTime.class.isAssignableFrom(type) ||
               LocalDate.class.isAssignableFrom(type) ||
               Date.class.isAssignableFrom(type);
    }

    private Object formatValue(Object value, GridColumnInfo columnInfo) {
        if (value == null) return "";

        if (!columnInfo.dateTimeFormat().isEmpty() && isTemporalType(columnInfo.type())) {
            switch (value) {
                case LocalDateTime localDateTime -> {
                    return DateTimeFormatter
                            .ofPattern(columnInfo.dateTimeFormat())
                            .format(localDateTime);
                }
                case LocalDate localDate -> {
                    return DateTimeFormatter
                            .ofPattern(columnInfo.dateTimeFormat())
                            .format(localDate);
                }
                case Date date -> {
                    return new SimpleDateFormat(columnInfo.dateTimeFormat())
                            .format(date);
                }
                default -> {
                }
            }
        }
        return value;
    }

    private void setupForm() {
        // Automatically add form fields based on FormField annotations
        Arrays.stream(entityClass.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(FormField.class))
                .sorted(Comparator.comparingInt(field ->
                        field.getAnnotation(FormField.class).order()))
                .forEach(field -> {
                    FormField annotation = field.getAnnotation(FormField.class);
                    String label = annotation.label().isEmpty() ?
                            field.getName() : annotation.label();

                    Component component = createFormField(field);
                    form.addFormItem(component, label);

                    // Set required indicator on the component if it supports it
                    if (component instanceof HasValueAndElement) {
                        ((HasValueAndElement) component).setRequiredIndicatorVisible(
                                field.isAnnotationPresent(NotNull.class) ||
                                field.isAnnotationPresent(NotBlank.class)
                        );
                    }

                    setupFieldBinding(field, component);

                });

        delete.setVisible(false);  // Initially hidden until selection
        HorizontalLayout buttons = new HorizontalLayout(save, cancel, delete);
        form.add(buttons);
    }

    private void setupFieldBinding(Field field, Component component) {
        Class<?> type = field.getType();

        if (component instanceof NumberField numberField) {
            if (type == Integer.class || type == int.class) {
                binder.forField(numberField)
                        .withConverter(
                                // Convert Double to Integer when saving to bean
                                number -> number != null ? number.intValue() : null,
                                // Convert Integer to Double when reading from bean
                                integer -> integer != null ? integer.doubleValue() : null,
                                // Error message if conversion fails
                                "Please enter a valid number"
                        )
                        .bind(field.getName());
            } else if (type == Double.class || type == double.class) {
                binder.forField(numberField)
                        .bind(field.getName());
            } else if (type == Long.class || type == long.class) {
                binder.forField(numberField)
                        .withConverter(
                                number -> number != null ? number.longValue() : null,
                                longVal -> longVal != null ? longVal.doubleValue() : null,
                                "Please enter a valid number"
                        )
                        .bind(field.getName());
            }
        } else {
            // Handle other field types normally
            binder.forField((HasValue) component)
                    .bind(field.getName());
        }
    }

    private Component createFormField(Field field) {
        Class<?> type = field.getType();
        if (type == String.class) {
            return new TextField();
        } else if (type == Integer.class || type == int.class) {
            return new NumberField();
        } else if (type == Boolean.class || type == boolean.class) {
            return new Checkbox();
        } else if (type == LocalDateTime.class) {
            return new DateTimePicker();
        } else if (type == LocalDate.class) {
            return new DatePicker();
        }
        // Add more field types as needed
        return new TextField();
    }

    private void setupEventHandlers() {
        save.addClickListener(event -> {
            if (selectedItem != null && binder.writeBeanIfValid(selectedItem)) {
                saveEntity(selectedItem);
                refreshGrid();
                // Clear form after successful save
                clearForm();
            }
        });

        cancel.addClickListener(event -> {
            clearForm();
        });

        delete.setDeleteListener(() -> {
            deleteEntity(selectedItem);
            clearForm();
            refreshGrid();
            Notification.show("Item deleted", 3000, Notification.Position.TOP_CENTER);
        });

        grid.asSingleSelect().addValueChangeListener(event -> {
            selectedItem = event.getValue();
            editor.setVisible(selectedItem != null);
            delete.setVisible(selectedItem != null);
            binder.readBean(selectedItem);
        });
    }

    // Abstract methods to be implemented by specific views
    protected abstract void saveEntity(T entity);

    protected abstract List<T> loadEntities();

    protected abstract void deleteEntity(T entity);

    public void refreshGrid() {
        grid.setItems(loadEntities());
    }


    protected T createEmptyInstance() {
        try {
            return entityClass.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException("Could not create empty instance of " + entityClass.getSimpleName(), e);
        }
    }

    public void addNew() {
        editor.setVisible(true);
        // Create empty instance
        selectedItem = createEmptyInstance();
        // Clear and set the binder
        binder.readBean(selectedItem);
        // Optional: Scroll form into view or highlight it
        form.scrollIntoView();
    }

    private void clearForm() {
        selectedItem = null;
        binder.readBean(null);
        editor.setVisible(false);
        delete.setVisible(false);
    }

    private record GridColumnInfo(String propertyName, String header, int order, boolean sortable, Class<?> type,
                                  Method method, String dateTimeFormat, boolean showAsComponent) {
    }
}
