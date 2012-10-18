package de.benjaminborbe.xmpp.connector;

import java.util.List;

public interface XmppConnector {

	void connect() throws XmppConnectorException;

	void disconnect() throws XmppConnectorException;

	void sendMessage(XmppUser user, String message) throws XmppConnectorException;

	List<XmppUser> getUsers();

	XmppUser getMe();
}
