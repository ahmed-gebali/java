package de.benjaminborbe.gwt.gui.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import de.benjaminborbe.gwt.gui.client.message.DefaultMessages;

import javax.inject.Inject;

public class MainPanel extends Composite {

	@Inject
	public MainPanel(final DefaultMessages defaultMessages) {
		final Label title = new Label(defaultMessages.getTitle());
		final Button button = new Button(defaultMessages.getButtonText());
		button.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(final ClickEvent event) {
				Window.alert(defaultMessages.getWelcomeMessage());
			}
		});
		final VerticalPanel vp = new VerticalPanel();
		vp.add(title);
		vp.add(button);

		// All composites must call initWidget() in their constructors.
		initWidget(vp);
	}

}
