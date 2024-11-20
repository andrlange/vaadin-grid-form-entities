package cool.cfapps.vaadinentity.config;

import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import org.springframework.context.annotation.Configuration;

@Theme(value = "vaadin-entity", variant = Lumo.DARK)
@Configuration
public class AppShellConfig implements AppShellConfigurator {
}
