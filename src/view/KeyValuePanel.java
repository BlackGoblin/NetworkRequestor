package view;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.JTextField;

public class KeyValuePanel extends JPanel {

	/**
	 * the default serial version UID
	 */
	private static final long serialVersionUID = 1L;

	// layout objects
	private JTextField keyJTextField;
	private JTextField valueJTextField;

	public KeyValuePanel() {
		setLayout(new GridLayout(1, 2));

		keyJTextField = new JTextField();
		valueJTextField = new JTextField();

		add(keyJTextField);
		add(valueJTextField);

		setMaximumSize(new Dimension((int) getMaximumSize().getSize().getWidth(), 35));
	}

	public KeyValuePair getKeyValuePair() {
		return new KeyValuePair(keyJTextField.getText(), valueJTextField.getText());
	}

	/**
	 * a simple key value pair
	 */
	public static class KeyValuePair {
		private String key;
		private String value;

		public KeyValuePair(final String key, final String value) {
			this.key = key;
			this.value = value;
		}

		public String getKey() {
			return key;
		}

		public void setKey(String key) {
			this.key = key;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}

	}

}