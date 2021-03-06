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

package com.glavsoft.rfb.protocol.auth;

import com.glavsoft.exceptions.FatalException;
import com.glavsoft.exceptions.TransportException;
import com.glavsoft.exceptions.UnsupportedSecurityTypeException;
import com.glavsoft.rfb.CapabilityContainer;
import com.glavsoft.rfb.IPasswordRetriever;
import com.glavsoft.rfb.RfbCapabilityInfo;
import com.glavsoft.rfb.protocol.state.SecurityTypeState;
import com.glavsoft.transport.Reader;
import com.glavsoft.transport.Writer;

/**
 *
 */
public class TightAuthentication extends AuthHandler {

	@Override
	public SecurityType getType() {
		return SecurityType.TIGHT_AUTHENTICATION;
	}

	@Override
	public boolean authenticate(
		final Reader reader,
		final Writer writer,
		final CapabilityContainer authCaps,
		final IPasswordRetriever passwordRetriever
	) throws TransportException,
		FatalException, UnsupportedSecurityTypeException {
		initTunnelling(reader, writer);
		initAuthorization(reader, writer, authCaps, passwordRetriever);
		return true;
	}

	/**
	 * Negotiation of Tunneling Capabilities (protocol versions 3.7t, 3.8t)
	 * <p/>
	 * If the chosen security type is rfbSecTypeTight, the server sends a list of
	 * supported tunneling methods ("tunneling" refers to any additional layer of
	 * data transformation, such as encryption or external compression.)
	 * <p/>
	 * nTunnelTypes specifies the number of following rfbCapabilityInfo structures
	 * that list all supported tunneling methods in the order of preference.
	 * <p/>
	 * NOTE: If nTunnelTypes is 0, that tells the client that no tunneling can be
	 * used, and the client should not send a response requesting a tunneling
	 * method.
	 * <p/>
	 * typedef struct _rfbTunnelingCapsMsg {
	 * CARD32 nTunnelTypes;
	 * //followed by nTunnelTypes * rfbCapabilityInfo structures
	 * } rfbTunnelingCapsMsg;
	 * #define sz_rfbTunnelingCapsMsg 4
	 * ----------------------------------------------------------------------------
	 * Tunneling Method Request (protocol versions 3.7t, 3.8t)
	 * <p/>
	 * If the list of tunneling capabilities sent by the server was not empty, the
	 * client should reply with a 32-bit code specifying a particular tunneling
	 * method. The following code should be used for no tunneling.
	 * <p/>
	 * #define rfbNoTunneling 0
	 * #define sig_rfbNoTunneling "NOTUNNEL"
	 */
	private void initTunnelling(final Reader reader, final Writer writer) throws TransportException {
		final long tunnelsCount;
		tunnelsCount = reader.readUInt32();
		if (tunnelsCount > 0) {
			// for (int i = 0; i < tunnelsCount; ++i) {
			// final RfbCapabilityInfo rfbCapabilityInfo = new RfbCapabilityInfo(reader);
			// logger.debug(rfbCapabilityInfo.toString());
			// }
			writer.writeInt32(0); // NOTUNNEL
		}
	}

	/**
	 * Negotiation of Authentication Capabilities (protocol versions 3.7t, 3.8t)
	 * <p/>
	 * After setting up tunneling, the server sends a list of supported
	 * authentication schemes.
	 * <p/>
	 * nAuthTypes specifies the number of following rfbCapabilityInfo structures
	 * that list all supported authentication schemes in the order of preference.
	 * <p/>
	 * NOTE: If nAuthTypes is 0, that tells the client that no authentication is
	 * necessary, and the client should not send a response requesting an
	 * authentication scheme.
	 * <p/>
	 * typedef struct _rfbAuthenticationCapsMsg {
	 * CARD32 nAuthTypes;
	 * // followed by nAuthTypes * rfbCapabilityInfo structures
	 * } rfbAuthenticationCapsMsg;
	 * #define sz_rfbAuthenticationCapsMsg 4
	 *
	 * @param authCaps
	 * @param passwordRetriever
	 * @throws UnsupportedSecurityTypeException
	 *
	 * @throws TransportException
	 * @throws FatalException
	 */
	private void initAuthorization(final Reader reader, final Writer writer, final CapabilityContainer authCaps, final IPasswordRetriever passwordRetriever)
		throws UnsupportedSecurityTypeException, TransportException, FatalException {
		final int authCount;
		authCount = reader.readInt32();
		final byte[] cap = new byte[authCount];
		for (int i = 0; i < authCount; ++i) {
			final RfbCapabilityInfo rfbCapabilityInfo = new RfbCapabilityInfo(reader);
			cap[i] = (byte) rfbCapabilityInfo.getCode();
			// logger.debug(rfbCapabilityInfo.toString());
		}
		AuthHandler authHandler = null;
		if (authCount > 0) {
			authHandler = SecurityTypeState.selectAuthHandler(cap, authCaps);
			for (int i = 0; i < authCount; ++i) {
				if (authCaps.isSupported(cap[i])) {
					// sending back RFB capability code
					writer.writeInt32(cap[i]);
					break;
				}
			}
		} else {
			authHandler = SecurityType.getAuthHandlerById(SecurityType.NONE_AUTHENTICATION.getId());
		}
		// logger.info("Auth capability accepted: " + authHandler.getName());
		authHandler.authenticate(reader, writer, authCaps, passwordRetriever);
	}

}
