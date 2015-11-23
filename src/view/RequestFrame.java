package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

/**
 * This class represents a frame that can be used to send requests and set their
 * headers and parameters
 */
public class RequestFrame extends JFrame {

	/**
	 * the default serial version UID
	 */
	private static final long serialVersionUID = 1L;

	// layout objects

	// upper panel objects
	private JPanel upperPanel;

	private JPanel methodJPanel;
	private ButtonGroup methodButtonGroup;
	private JRadioButton postJRadioButton;
	private JRadioButton getJRadioButton;

	private JPanel urlJPanel;
	private JLabel urlHintJLabel;
	private JTextField urlJTextField;

	private JButton sendJButton;

	// center panel objects
	private JPanel middlePanel;

	private JScrollPane headersJScrollPane;
	private JPanel headersViewPortJPanel;
	private ArrayList<KeyValuePanel> headersKeyValuePanels;

	private JScrollPane paramsJScrollPane;
	private JPanel paramsViewPortJPanel;
	private ArrayList<KeyValuePanel> paramsKeyValuePanels;

	// lower panel objects
	private JScrollPane lowerJScrollPane;
	private JPanel lowerPanel;

	private JTextArea responseJTextArea;

	// listener objects
	private OnSendClickListener listener;

	public RequestFrame() {
		super(Constants.WINDOW_TITLE);

		setLayout(new BorderLayout());
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(Constants.FRAME_WIDTH, Constants.FRAME_HEIGHT);
		setLocationRelativeTo(null);
		setResizable(false);

		// initialize the upper panel
		initUpperPanel();

		// initialize the middle panel
		initMiddlePanel();

		// initialize lower panel
		initLowerPanel();

		setVisible(true);

	}

	/**
	 * initializes the upper panel
	 */
	private void initUpperPanel() {
		upperPanel = new JPanel();
		upperPanel.setLayout(new BorderLayout());
		upperPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

		// initialize the method radio buttons
		initMethodPanel();

		// initialize the URL text field
		initURLPanel();

		// initialize the send button
		initSendButton();

		// add the upper panel to the frame
		add(upperPanel, BorderLayout.NORTH);
	}

	/**
	 * initializes the method radio buttons
	 */
	private void initMethodPanel() {
		methodJPanel = new JPanel();
		methodJPanel.setLayout(new BorderLayout());

		// set the titled border to the method panel
		Border border = BorderFactory.createLineBorder(Color.GRAY, 1, true);
		TitledBorder titledBorder = BorderFactory.createTitledBorder(border, Constants.METHOD, TitledBorder.LEFT, TitledBorder.TOP);
		methodJPanel.setBorder(titledBorder);

		methodButtonGroup = new ButtonGroup();

		postJRadioButton = new JRadioButton(Constants.POST);
		getJRadioButton = new JRadioButton(Constants.GET);

		// add the radio buttons to method button group
		methodButtonGroup.add(postJRadioButton);
		methodButtonGroup.add(getJRadioButton);

		getJRadioButton.setSelected(true);

		// add the radio buttons to the method panel
		methodJPanel.add(getJRadioButton, BorderLayout.NORTH);
		methodJPanel.add(postJRadioButton, BorderLayout.SOUTH);

		// add the method panel to the upper panel
		upperPanel.add(methodJPanel, BorderLayout.WEST);
	}

	/**
	 * initializes the URL text field and its label
	 */
	private void initURLPanel() {
		urlJPanel = new JPanel();
		urlJPanel.setLayout(new BorderLayout());
		urlJPanel.setBorder(new EmptyBorder(0, 10, 0, 10));

		urlHintJLabel = new JLabel(Constants.URL);
		urlJTextField = new JTextField();

		// add the label and text field to the panel
		urlJPanel.add(urlHintJLabel, BorderLayout.WEST);
		urlJPanel.add(urlJTextField, BorderLayout.CENTER);

		// add the label and the text field to the upper panel
		upperPanel.add(urlJPanel, BorderLayout.CENTER);
	}

	/**
	 * initializes the send button
	 */
	private void initSendButton() {
		sendJButton = new JButton();
		sendJButton.setText(Constants.SEND);
		sendJButton.addActionListener(onSendButtonClickListener);

		// add the send button to the panel
		upperPanel.add(sendJButton, BorderLayout.EAST);
	}

	/**
	 * initializes the middle panel
	 */
	private void initMiddlePanel() {
		middlePanel = new JPanel();
		middlePanel.setLayout(new GridLayout(1, 2));
		middlePanel.setBorder(new EmptyBorder(10, 10, 10, 10));

		// initialize headers scroll pane
		initHeadersScrollPane();

		// initialize parameters scroll pane
		initParamsScrollPane();

		add(middlePanel, BorderLayout.CENTER);
	}

	/**
	 * initializes the headers scroll pane
	 */
	private void initHeadersScrollPane() {
		headersViewPortJPanel = new JPanel();
		headersViewPortJPanel.setLayout(new BoxLayout(headersViewPortJPanel, BoxLayout.PAGE_AXIS));
		headersJScrollPane = new JScrollPane(headersViewPortJPanel);
		headersKeyValuePanels = new ArrayList<KeyValuePanel>();
		headersJScrollPane.setBackground(new Color(0, 0, 0, 0));

		// set the titled border to the header scroll pane
		Border border = BorderFactory.createLineBorder(Color.GRAY, 1, true);
		TitledBorder titledBorder = BorderFactory.createTitledBorder(border, Constants.HEADERS, TitledBorder.LEFT, TitledBorder.TOP);
		headersJScrollPane.setBorder(titledBorder);

		addHeaderKeyValuePanel();
		addHeaderKeyValuePanel();
		addHeaderKeyValuePanel();
		addHeaderKeyValuePanel();
		addHeaderKeyValuePanel();
		addHeaderKeyValuePanel();

		// add the headers scroll pane to the middle pane
		middlePanel.add(headersJScrollPane);
	}

	/**
	 * initializes the parameters scroll pane
	 */
	private void initParamsScrollPane() {
		paramsViewPortJPanel = new JPanel(new GridLayout());
		paramsViewPortJPanel.setLayout(new BoxLayout(paramsViewPortJPanel, BoxLayout.PAGE_AXIS));
		paramsJScrollPane = new JScrollPane(paramsViewPortJPanel);
		paramsKeyValuePanels = new ArrayList<KeyValuePanel>();
		paramsJScrollPane.setBackground(new Color(0, 0, 0, 0));

		// set the titled border to the header scroll pane
		Border border = BorderFactory.createLineBorder(Color.GRAY, 1, true);
		TitledBorder titledBorder = BorderFactory.createTitledBorder(border, Constants.PARAMS, TitledBorder.LEFT, TitledBorder.TOP);
		paramsJScrollPane.setBorder(titledBorder);

		addParamsKeyValuePanel();
		addParamsKeyValuePanel();
		addParamsKeyValuePanel();
		addParamsKeyValuePanel();
		addParamsKeyValuePanel();
		addParamsKeyValuePanel();

		// add the parameters scroll pane to the middle pane
		middlePanel.add(paramsJScrollPane);
	}

	/**
	 * adds a header key value panel
	 */
	private void addHeaderKeyValuePanel() {
		KeyValuePanel keyValuePanel = new KeyValuePanel();
		headersKeyValuePanels.add(keyValuePanel);
		headersViewPortJPanel.add(keyValuePanel);
	}

	/**
	 * adds a parameter key value panel
	 */
	private void addParamsKeyValuePanel() {
		KeyValuePanel keyValuePanel = new KeyValuePanel();
		paramsKeyValuePanels.add(keyValuePanel);
		paramsViewPortJPanel.add(keyValuePanel);
	}

	/**
	 * initializes the lower panel
	 */
	private void initLowerPanel() {
		lowerPanel = new JPanel();
		lowerJScrollPane = new JScrollPane(lowerPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		lowerPanel.setLayout(new BorderLayout());
		Border border = BorderFactory.createLineBorder(Color.GRAY, 1, true);
		TitledBorder titledBorder = BorderFactory.createTitledBorder(border, Constants.RESPONSE, TitledBorder.LEFT, TitledBorder.TOP);
		lowerPanel.setBorder(titledBorder);
		lowerJScrollPane.setPreferredSize(new Dimension((int) lowerJScrollPane.getPreferredSize().getWidth(), 200));

		// initialize response text area
		initResponseTextArea();

		// add lower panel to the frame
		add(lowerJScrollPane, BorderLayout.SOUTH);
	}

	/**
	 * initializes the response text area
	 */
	private void initResponseTextArea() {
		responseJTextArea = new JTextArea();
		responseJTextArea.setBorder(new EmptyBorder(10, 10, 10, 10));
		responseJTextArea.setLineWrap(true);

		// add the response text field to the lower panel
		lowerPanel.add(responseJTextArea, BorderLayout.CENTER);
	}

	/**
	 * sets a listener for the send button
	 * 
	 * @param onSendClickListener
	 *            the listener that gets called when the send button is clicked
	 */
	public void setOnSendClickListener(OnSendClickListener onSendClickListener) {
		this.listener = onSendClickListener;
	}

	/**
	 * displays a String as the response in the response text area
	 * 
	 * @param response
	 *            the response to be displayed
	 */
	public void setResult(String result) {
		responseJTextArea.append(result);
	}

	// on send button click listener
	private ActionListener onSendButtonClickListener = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			// clear the previous response
			responseJTextArea.setText(null);

			// on send button clicked
			if (listener != null) {
				Method method = null;

				// get the method from radio buttons
				if (getJRadioButton.isSelected()) {
					// the get radio button is selected
					method = Method.GET;
				} else {
					// the post radio button is selected
					method = Method.POST;
				}

				// get headers
				HashMap<String, String> headers = extractKeyValuePairs(headersKeyValuePanels);

				// get parameters
				HashMap<String, String> params = extractKeyValuePairs(paramsKeyValuePanels);

				// get the URL
				String url = urlJTextField.getText();

				listener.onClick(method, url, headers, params);
			}

		}
	};

	private HashMap<String, String> extractKeyValuePairs(ArrayList<KeyValuePanel> keyValuePanels) {
		if (keyValuePanels == null) {
			return null;
		}

		if (!(keyValuePanels.size() > 0)) {
			return null;
		}

		HashMap<String, String> keyValuePairs = new HashMap<String, String>();

		for (KeyValuePanel keyValuePanel : keyValuePanels) {
			if (keyValuePanel.getKeyValuePair().getKey().length() > 0 && keyValuePanel.getKeyValuePair().getValue().length() > 0) {
				keyValuePairs.put(keyValuePanel.getKeyValuePair().getKey(), keyValuePanel.getKeyValuePair().getValue());
			}
		}

		return keyValuePairs;
	}

	/**
	 * listener for send button
	 */
	public interface OnSendClickListener {
		void onClick(Method method, String url, HashMap<String, String> headers, HashMap<String, String> params);
	}

	/**
	 * the method that is chosen via method radio buttons
	 */
	public enum Method {
		POST, GET
	}

}