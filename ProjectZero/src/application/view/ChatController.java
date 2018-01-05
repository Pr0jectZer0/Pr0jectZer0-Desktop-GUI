package application.view;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import org.w3c.dom.events.EventTarget;
import org.w3c.dom.events.MouseEvent;
import org.w3c.dom.views.AbstractView;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.pusher.client.Authorizer;
import com.pusher.client.Pusher;
import com.pusher.client.PusherOptions;
import com.pusher.client.channel.Channel;
import com.pusher.client.channel.PrivateChannel;
import com.pusher.client.channel.PrivateChannelEventListener;
import com.pusher.client.channel.SubscriptionEventListener;
import com.pusher.client.connection.Connection;
import com.pusher.client.connection.ConnectionEventListener;
import com.pusher.client.connection.ConnectionState;
import com.pusher.client.connection.ConnectionStateChange;
import com.pusher.client.util.HttpAuthorizer;

import application.api.TimeCompare;
import application.api.User;
import application.model.HttpWebRequest;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;

public class ChatController
{

	@FXML
	private JFXTextArea tfanzeige;
	@FXML
	private JFXTextArea tfschreiben;
	@FXML
	private JFXButton btnsenden;
	String chatroom;
	String chatroomid = null;
	Pusher pusher;
	Calendar now = Calendar.getInstance();

	@FXML
	private void initialize()
	{
		System.out.println(User.getLoginToken());

		try
		{
			chatroom = HttpWebRequest.sendGetRequest("chatroom/" + 4 + "?token=" + User.getLoginToken());
			JsonParser jp = new JsonParser();
			JsonObject jo = (JsonObject) jp.parse(chatroom);
			chatroomid = jo.get("chatroom").toString();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

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
			public void onError(String arg0, String arg1, Exception arg2)
			{
				// TODO Auto-generated method stub

			}

			@Override
			public void onConnectionStateChange(ConnectionStateChange arg0)
			{
				System.out.println(arg0.getCurrentState());

			}
		}, ConnectionState.ALL);

		PrivateChannel channel = pusher.subscribePrivate("private-chat." + chatroomid);
		Connection con = pusher.getConnection();
		System.out.println(con.getState());
		System.out.println(channel.isSubscribed());
		String[][] para = { { "message", "Test" } };
		String mess = getNewMessages();
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
				String mess = getNewMessages();
				if (!mess.equals("Fehler"))
				{
					tfanzeige.setText(mess);
					tfanzeige.setScrollTop(10000000);
				}
			}

			@Override
			public void onAuthenticationFailure(String arg0, Exception arg1)
			{
				System.out.println("Failure");
			}

		});

		btnsenden.setOnMouseClicked(new EventHandler<Event>()
		{

			@Override
			public void handle(Event event)
			{
				if (tfschreiben.getText() != null && !tfschreiben.getText().equals(""))
				{
					para[0][1] = tfschreiben.getText();
					try
					{
						System.out.println(HttpWebRequest.sendPostRequest(
								"chatroom/" + chatroomid + "/messages" + "?token=" + User.getLoginToken(), para));
					}
					catch (IOException e)
					{
						e.printStackTrace();
					}
				}

			}

		});

	}

	public String getMessages()
	{
		try
		{
			String name = "";
			String mess = "";
			System.out.println(HttpWebRequest
					.sendGetRequest("chatroom/" + chatroomid + "/messages" + "?token=" + User.getLoginToken()));
			JsonObject message = (JsonObject) new JsonParser().parse(HttpWebRequest
					.sendGetRequest("chatroom/" + chatroomid + "/messages" + "?token=" + User.getLoginToken()));
			JsonArray messages = (JsonArray) message.get("message");
			Iterator<JsonElement> iterator = messages.iterator();
			while (iterator.hasNext())
			{
				JsonObject messobj = (JsonObject) iterator.next();
				JsonObject userobj = (JsonObject) messobj.get("user");
				name = userobj.get("name").toString();
				mess += name.replace("\"", "") + ": " + messobj.get("message").toString().replace("\"", "") + "\n";

			}
			return mess;
		}
		catch (IOException e)
		{
			e.printStackTrace();
			return "Fehler";
		}
	}

	public String getNewMessages()
	{
		try
		{
			String name = "";
			String mess = "";
			System.out.println(HttpWebRequest
					.sendGetRequest("chatroom/" + chatroomid + "/messages" + "?token=" + User.getLoginToken()));
			JsonObject message = (JsonObject) new JsonParser().parse(HttpWebRequest
					.sendGetRequest("chatroom/" + chatroomid + "/messages" + "?token=" + User.getLoginToken()));
			JsonArray messages = (JsonArray) message.get("message");
			Iterator<JsonElement> iterator = messages.iterator();
			while (iterator.hasNext())
			{
				JsonObject messobj = (JsonObject) iterator.next();
				JsonObject userobj = (JsonObject) messobj.get("user");
				String date = messobj.get("created_at").toString().replace("\"", "");
				DateTimeFormatter f = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
				LocalDateTime dateTime = LocalDateTime.from(f.parse(date));
				Calendar c2 = Calendar.getInstance();
				System.out.println(dateTime.getYear() + "" +dateTime.getMonthValue() + "" +dateTime.getDayOfMonth());
				// c2.set(dateTime.getYear(),dateTime.getMonthValue(),dateTime.getDayOfMonth(),dateTime.getHour(),dateTime.getMinute(),dateTime.getSecond());
				c2.set(Calendar.YEAR, dateTime.getYear());
				c2.set(Calendar.MONTH, dateTime.getMonthValue()-1);
				c2.set(Calendar.DATE, dateTime.getDayOfMonth());
				c2.set(Calendar.HOUR_OF_DAY, dateTime.getHour()+1);
				c2.set(Calendar.MINUTE, dateTime.getMinute());
				c2.set(Calendar.SECOND, dateTime.getSecond());
				if (TimeCompare.before(now, c2))
				{
					//System.out.println(now);
					//System.out.println(c2.toString());
					name = userobj.get("name").toString();
					mess += name.replace("\"", "") + ": " + messobj.get("message").toString().replace("\"", "") + "\n";
				}
				else

				{
					System.out.println("zu früh");
				}
			}
			return mess;
		}
		catch (IOException e)
		{
			e.printStackTrace();
			return "Fehler";
		}
	}

}
