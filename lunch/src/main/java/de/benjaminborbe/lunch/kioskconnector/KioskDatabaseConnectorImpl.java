package de.benjaminborbe.lunch.kioskconnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.slf4j.Logger;

import com.google.inject.Inject;

import de.benjaminborbe.lunch.LunchConstants;

public class KioskDatabaseConnectorImpl implements KioskDatabaseConnector {

	private final Logger logger;

	@Inject
	public KioskDatabaseConnectorImpl(final Logger logger) {
		this.logger = logger;
	}

	@Override
	public KioskUserBean getCustomerNumber(final String prename, final String surname) throws KioskDatabaseConnectorException {
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			logger.trace("search customerNumber");

			connection = createConnection();

			statement = connection.prepareStatement("SELECT customer_no FROM customer WHERE prename = ? AND surname = ?");
			statement.setString(1, prename);
			statement.setString(2, surname);

			final ResultSet r = statement.executeQuery();
			logger.trace("executeQuery");
			while (r.next()) {
				logger.trace("found customerNumber");
				final KioskUserBean user = new KioskUserBean();
				user.setPrename(prename);
				user.setSurname(surname);
				user.setCustomerNumber(r.getString(1));
			}

			logger.debug("did not find customerNumber");
			return null;
		}
		catch (final SQLException e) {
			throw new KioskDatabaseConnectorException(e);
		}
		catch (final NamingException e) {
			throw new KioskDatabaseConnectorException(e);
		}
		catch (final InstantiationException e) {
			throw new KioskDatabaseConnectorException(e);
		}
		catch (final IllegalAccessException e) {
			throw new KioskDatabaseConnectorException(e);
		}
		catch (final ClassNotFoundException e) {
			throw new KioskDatabaseConnectorException(e);
		}
		finally {
			if (statement != null) {
				try {
					statement.close();
				}
				catch (final SQLException e) {
				}
			}
			if (connection != null) {
				try {
					connection.close();
				}
				catch (final SQLException e) {
				}
			}
		}
	}

	private Connection createConnection() throws InstantiationException, IllegalAccessException, ClassNotFoundException, NamingException, SQLException {
		Connection connection;
		logger.trace("load driver");
		Class.forName("com.mysql.jdbc.Driver").newInstance();

		logger.trace("InitialContext");
		final Context initialContext = new InitialContext();
		final Context componentBindings = (Context) initialContext.lookup("java:comp/env");
		final DataSource dataSource = (DataSource) componentBindings.lookup("jdbc/kiosk");
		logger.trace("getConnection");
		connection = dataSource.getConnection();
		return connection;
	}

	@Override
	public Collection<KioskUserBean> getBookingsForDay(final Calendar calendar) throws KioskDatabaseConnectorException {
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			final List<KioskUserBean> result = new ArrayList<KioskUserBean>();

			logger.trace("search customerNumber");

			connection = createConnection();

			final StringBuilder sb = new StringBuilder();
			sb.append("SELECT customer.prename, customer.surname, customer.customer_no ");
			sb.append("FROM customer ");
			sb.append("JOIN cart ON cart.customer_id = customer.id ");
			sb.append("JOIN cart_assignments ON cart_assignments.cart_id = cart.id ");
			sb.append("JOIN article ON article.id = cart_assignments.article_id ");
			sb.append(" WHERE 1=1 ");
			sb.append("AND article.ean = ? ");
			sb.append("AND DATE(FROM_UNIXTIME(cart.booked_time)) = DATE(NOW()) ");

			statement = connection.prepareStatement(sb.toString());
			statement.setString(1, LunchConstants.MITTAG_EAN);

			final ResultSet r = statement.executeQuery();
			logger.trace("executeQuery");
			while (r.next()) {
				final KioskUserBean user = new KioskUserBean();
				user.setPrename(r.getString(1));
				user.setSurname(r.getString(2));
				user.setCustomerNumber(r.getString(3));
				result.add(user);
			}

			return result;
		}
		catch (final SQLException e) {
			throw new KioskDatabaseConnectorException(e);
		}
		catch (final NamingException e) {
			throw new KioskDatabaseConnectorException(e);
		}
		catch (final InstantiationException e) {
			throw new KioskDatabaseConnectorException(e);
		}
		catch (final IllegalAccessException e) {
			throw new KioskDatabaseConnectorException(e);
		}
		catch (final ClassNotFoundException e) {
			throw new KioskDatabaseConnectorException(e);
		}
		finally {
			if (statement != null) {
				try {
					statement.close();
				}
				catch (final SQLException e) {
				}
			}
			if (connection != null) {
				try {
					connection.close();
				}
				catch (final SQLException e) {
				}
			}
		}
	}
}
