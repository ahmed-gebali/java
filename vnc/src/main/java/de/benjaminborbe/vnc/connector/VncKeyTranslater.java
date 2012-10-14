package de.benjaminborbe.vnc.connector;

import java.util.HashMap;
import java.util.Map;

import com.glavsoft.utils.Keymap;
import com.google.inject.Inject;

import de.benjaminborbe.vnc.api.VncKey;

public class VncKeyTranslater {

	private final Map<VncKey, Integer> map = new HashMap<VncKey, Integer>();

	@Inject
	public VncKeyTranslater() {
		map.put(VncKey.K_0, Keymap.K_KP_0);
		map.put(VncKey.K_1, Keymap.K_KP_1);
		map.put(VncKey.K_2, Keymap.K_KP_2);
		map.put(VncKey.K_3, Keymap.K_KP_3);
		map.put(VncKey.K_4, Keymap.K_KP_4);
		map.put(VncKey.K_5, Keymap.K_KP_5);
		map.put(VncKey.K_6, Keymap.K_KP_6);
		map.put(VncKey.K_7, Keymap.K_KP_7);
		map.put(VncKey.K_8, Keymap.K_KP_8);
		map.put(VncKey.K_9, Keymap.K_KP_9);
		map.put(VncKey.K_ENTER, Keymap.K_ENTER);
	}

	public int translate(final VncKey vncKey) throws VncKeyTranslaterException {
		if (vncKey == null) {
			throw new VncKeyTranslaterException("can't translate null");
		}
		final String name = vncKey.name();
		if (name.indexOf("K_") == 0) {
			if (map.containsKey(vncKey)) {

				return map.get(vncKey);
			}
			else {
				throw new VncKeyTranslaterException("can't translate " + vncKey.name());
			}
		}
		else {
			final int c = name.charAt(0);
			return Keymap.unicode2keysym(c);
		}
	}
}