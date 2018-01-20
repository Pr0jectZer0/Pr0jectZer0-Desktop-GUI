package application.view;

import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.pusher.client.Pusher;
import com.pusher.client.PusherOptions;
import com.pusher.client.channel.PrivateChannel;
import com.pusher.client.channel.PrivateChannelEventListener;
import com.pusher.client.connection.Connection;
import com.pusher.client.connection.ConnectionEventListener;
import com.pusher.client.connection.ConnectionState;
import com.pusher.client.connection.ConnectionStateChange;
import com.pusher.client.util.HttpAuthorizer;

import application.api.Chat;
import application.api.User;
import application.model.Message;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class ChatController
{

	@FXML
	private JFXTextArea tfanzeige;
	@FXML
	private JFXTextArea tfschreiben;
	@FXML
	private JFXButton btnsenden;
	int chatroomID;
	Pusher pusher;
	Calendar now = Calendar.getInstance();

	@FXML
	private void initialize()
	{
		int personID = FreundeslisteController.getFreundeslistecontroller().friendlist.getSelectionModel().getSelectedItem().getId();
		chatroomID = Chat.getChatroomID(personID);
		PusherOptions pusheroptions = new PusherOptions();
		pusheroptions.setCluster("eu");
		pusheroptions.setEncrypted(true);
		HttpAuthorizer authorizer = new HttpAuthorizer("https://pr0jectzer0.ml/broadcasting/auth");
		Map<String, String> header = new TreeMap<String, String>();
		header.put("Authorization", "Bearer " + User.getLoginToken());
		authorizer.setHeaders(header);
		pusheroptions.setAuthorizer(authorizer);
		pusher = new Pusher("60b89a1e182fd4635842", pusheroptions);
		pusher.connect(new ConnectionEventListener()
		{
			@Override
			public void onError(String arg0, String arg1, Exception e)
			{
				System.out.println(e.getMessage());

			}

			@Override
			public void onConnectionStateChange(ConnectionStateChange state)
			{
				System.out.println(state.getCurrentState());

			}
		}, ConnectionState.ALL);

		PrivateChannel channel = pusher.subscribePrivate("private-chat." + chatroomID);
		Connection con = pusher.getConnection();
		System.out.println(con.getState());
		System.out.println(channel.isSubscribed());
		String mess = getMessages();
		if (!mess.equals("Fehler"))
		{
			tfanzeige.setText(mess);
		}
		channel.bind("App\\Events\\MessageSent", new PrivateChannelEventListener()
		{

			@Override
			public void onSubscriptionSucceeded(String arg0)
			{
				System.out.println("Subscription succeded");
			}

			@Override
			public void onEvent(String arg0, String arg1, String arg2)
			{
				tfanzeige.setText(getMessages());
				tfanzeige.setScrollTop(10000000);
			}

			@Override
			public void onAuthenticationFailure(String arg0, Exception e)
			{
				System.out.println("Failure: " + e.getMessage());
			}

		});

		btnsenden.setOnMouseClicked(new EventHandler<Event>()
		{
			@Override
			public void handle(Event event)
			{
				if (tfschreiben.getText() != null && !tfschreiben.getText().equals(""))
				{
					String message = tfschreiben.getText();
					Chat.sendMessage(chatroomID, message);
				}

			}

		});
		btnsenden.setOnKeyPressed(new EventHandler<KeyEvent>()
		{
			@Override
			public void handle(KeyEvent event)
			{
				if(event.getCode() == KeyCode.ENTER)
				{
					if (tfschreiben.getText() != null && !tfschreiben.getText().isEmpty())
					{
						String message = tfschreiben.getText();
						Chat.sendMessage(chatroomID, message);
					}
				}
				
			}
		});

	}

	public String getMessages()
	{
		List<Message> messages = Chat.getMessages(chatroomID);
		if (messages == null)
			return "Keine Nachrichten vorhanden!";
		String output = "";
		for (int i = 0; i < messages.size(); i++) {
			Message curMessage = messages.get(i);
			output += curMessage.getSender().getName() + ": " + curMessage.getMessage();
		}
		return output;
	}
}