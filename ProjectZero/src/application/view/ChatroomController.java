package application.view;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
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
import application.api.Groups;
import application.api.User;
import application.model.Group;
import application.model.Lableid;
import application.model.Message;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
/**
 * Controller-Klasse für den Chatroom
 * 
 * @author Dorsch, Deutsch, Penner, Kramer
 */
public class ChatroomController
{
	@FXML
	private JFXListView<Lableid> listgroup;
	@FXML
	private HBox hbox;
	@FXML
	private HBox vbox;
	@FXML
	private JFXButton btnfertig;
	@FXML
	private JFXTextArea taanzeige;
	@FXML
	private JFXTextArea taeingabe;
	@FXML
	private JFXButton btnsenden;
	private Pusher pusher;
	// private int chatroomID;
	private int gruppenID;

	/**
	 * Initialisierungen
	 */
	@FXML
	private void initialize()
	{
		btnsenden.setOnMouseClicked(new EventHandler<MouseEvent>()
		{

			@Override
			public void handle(MouseEvent event)
			{
				sendMessage();
			}
		});
		updateGrouplist();
		listgroup.setOnMouseClicked(new EventHandler<MouseEvent>()
		{

			@Override
			public void handle(MouseEvent event)
			{
				Lableid temp = listgroup.getSelectionModel().getSelectedItem();
				if (temp != null)
				{
					if (temp.istest())
					{
						hbox.setVisible(true);
						vbox.setVisible(false);
					}
					else
					{
						updatechat();
						hbox.setVisible(false);
						vbox.setVisible(true);
					}
				}

			}

		});
		btnfertig.setOnMouseClicked(new EventHandler<MouseEvent>()
		{

			@Override
			public void handle(MouseEvent event)
			{
				Lableid test = new Lableid(3);
				test.setText("Peter");
				addNewChat(test);
				listgroup.refresh();
				System.out.println("HALLOOO");
			}
		});
	}

	/**
	 * Diese Methode fügt ein Chat hinzu
	 * @param toadd
	 */
	private void addNewChat(Lableid toadd)
	{
		if (toadd != null && !toadd.istest())
		{
			int size = listgroup.getItems().size();
			listgroup.getItems().add(size - 1, toadd);
		}
	}

	/**
	 * Diese Methode initialisert den Pusher für den Chat
	 */
	private void updatechat()
	{
		Lableid temp = listgroup.getSelectionModel().getSelectedItem();
		if (temp != null)
		{
			gruppenID = temp.getGroupid();
			// chatroomID = Chat.getChatroomID(gruppenID);
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
			PrivateChannel channel = pusher.subscribePrivate("private-group-chat." + gruppenID);
			Connection con = pusher.getConnection();
			System.out.println(con.getState());
			System.out.println(channel.isSubscribed());
			String mess = getMessages();
			if (!mess.equals("Fehler"))
			{
				taanzeige.setText(mess);
			}
			channel.bind("App\\Events\\GroupMessageSent", new PrivateChannelEventListener()
			{
				@Override
				public void onSubscriptionSucceeded(String arg0)
				{
					System.out.println("Subscription succeded");
				}

				@Override
				public void onEvent(String arg0, String arg1, String arg2)
				{
					System.out.println("Event ausgelöst");
					taanzeige.setText(getMessages());
					taanzeige.setScrollTop(10000000);
				}

				@Override
				public void onAuthenticationFailure(String arg0, Exception e)
				{
					System.out.println("Failure: " + e.getMessage());
				}
			});

		}
		taanzeige.setText(getMessages());
	}

	/**
	 * Diese Methode empfängt Chat-Nachrichten
	 * @return output
	 */
	private String getMessages()
	{
		List<Message> messages = Chat.getGroupMessages(gruppenID);
		if (messages == null)
			return "Keine Nachrichten vorhanden!";
		String output = "";
		for (int i = 0; i < messages.size(); i++)
		{
			Message curMessage = messages.get(i);
			output += curMessage.getSender().getName() + ": " + curMessage.getMessage() + System.lineSeparator();
		}
		return output;
	}

	/**
	 * Diese Methode sendet Chat-Nachrichten
	 */
	private void sendMessage()
	{
		if (taeingabe.getText() != null && !taeingabe.getText().equals(""))
		{
			String message = taeingabe.getText();
			taeingabe.setText("");
			System.out.println(Chat.sendGroupMessage(gruppenID, message));
		}
	}

	/**
	 * Diese Methode aktualisiert die Gruppenliste
	 */
	private void updateGrouplist()
	{
		listgroup.getItems().clear();
		listgroup.getItems().add(new Lableid());
		listgroup.refresh();
		listgroup.setVisible(true);
		try
		{
			List<Group> groups = Groups.getUserGroups();
			for (Group g : groups)
			{
				Lableid lgroup = new Lableid(g.getID());
				lgroup.setText(g.getName());
				addNewChat(lgroup);
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

	}

}
