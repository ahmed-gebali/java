// Copyright (C) 2010, 2011, 2012 GlavSoft LLC.
// All rights reserved.
//
//-------------------------------------------------------------------------
// This file is part of the TightVNC software.  Please visit our Web site:
//
//                       http://www.tightvnc.com/
//
// This program is free software; you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation; either version 2 of the License, or
// (at your option) any later version.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License along
// with this program; if not, write to the Free Software Foundation, Inc.,
// 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
//-------------------------------------------------------------------------
//

package com.glavsoft.viewer.swing.gui;

import com.glavsoft.viewer.swing.Utils;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

/**
 * Dialog to ask password
 */
@SuppressWarnings("serial")
public class PasswordDialog extends JDialog {

	private String password = "";

	private static final int PADDING = 4;

	private final JLabel messageLabel;

	public PasswordDialog(final Frame owner, final WindowListener onClose) {
		super(owner, "VNC Authentication", true);
		addWindowListener(onClose);
		final JPanel pane = new JPanel(new GridLayout(0, 1, PADDING, PADDING));
		add(pane);
		pane.setBorder(new EmptyBorder(PADDING, PADDING, PADDING, PADDING));

		messageLabel = new JLabel("Server requires VNC authentication");
		pane.add(messageLabel);

		final JPanel passwordPanel = new JPanel();
		passwordPanel.add(new JLabel("Password:"));
		final JPasswordField passwordField = new JPasswordField("", 20);
		passwordPanel.add(passwordField);
		pane.add(passwordPanel);

		final JPanel buttonPanel = new JPanel();
		final JButton loginButton = new JButton("Login");
		buttonPanel.add(loginButton);
		loginButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent e) {
				password = new String(passwordField.getPassword());
				setVisible(false);
			}
		});

		final JButton closeButton = new JButton("Cancel");
		buttonPanel.add(closeButton);
		closeButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent e) {
				password = null;
				setVisible(false);
				onClose.windowClosing(null);
			}
		});

		pane.add(buttonPanel);

		getRootPane().setDefaultButton(loginButton);
		Utils.decorateDialog(this);
		Utils.centerWindow(this);
		addWindowFocusListener(new WindowAdapter() {

			@Override
			public void windowGainedFocus(final WindowEvent e) {
				passwordField.requestFocusInWindow();
			}
		});
	}

	public void setServerHostName(final String serverHostName) {
		messageLabel.setText("Server '" + serverHostName + "' requires VNC authentication");
		pack();
	}

	public String getPassword() {
		return password;
	}

}
