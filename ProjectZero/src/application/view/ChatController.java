package application.view;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import org.w3c.dom.events.EventTarget;
import org.w3c.dom.events.MouseEvent;
import org.w3c.dom.views.AbstractView;

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
	@FXML
	private void initialize()
	{
		btnsenden.setOnMouseClicked(new EventHandler<Event>() {

			@Override
			public void handle(Event event) {
				System.out.println(User.getLoginToken());

				try
				{
					chatroom = HttpWebRequest.sendGetRequest("chatroom/" + 4 + "?token=" + User.getLoginToken());
					JsonParser jp = new JsonParser();
					JsonObject jo = (JsonObject)jp.parse(chatroom);
					chatroomid = jo.get("chatroom").toString();
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}


				PusherOptions pusheroptions = new PusherOptions();
				pusheroptions.setCluster("eu");
				pusheroptions.setEncrypted(true);
				pusheroptions.setHost("https://pr0jectzer0.ml");
				pusheroptions.setWsPort(80);
				HttpAuthorizer authorizer = new HttpAuthorizer("https://pr0jectzer0.ml/broadcasting/auth");
				Map<String, String> header = new TreeMap<String, String>();
				header.put("Authorization",
						"Bearer "+ User.getLoginToken() );
				authorizer.setHeaders(header);
				pusheroptions.setAuthorizer(authorizer);
				pusher = new Pusher("60b89a1e182fd4635842",pusheroptions);
				pusher.connect(new ConnectionEventListener() {
					
					@Override
					public void onError(String arg0, String arg1, Exception arg2) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void onConnectionStateChange(ConnectionStateChange arg0) {
						System.out.println(arg0.getCurrentState());
						
					}
				}, ConnectionState.ALL);

				PrivateChannel channel = pusher.subscribePrivate("private-chat."+ chatroomid);
				Connection con = pusher.getConnection();
				System.out.println(con.getState());
				System.out.println(channel.isSubscribed());
				String[][] para = {{"message","Test"}};
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
						try
						{	
							System.out.println(HttpWebRequest.sendGetRequest("chatroom/" + chatroomid+ "/messages"+"?token=" + User.getLoginToken()));
							System.out.println("Event löst aus");
						}
						catch (IOException e)
						{
							e.printStackTrace();
						}
						
					}

					@Override
					public void onAuthenticationFailure(String arg0, Exception arg1)
					{
						System.out.println("Failure");
					}
					

				});
				try
				{
					System.out.println(HttpWebRequest.sendPostRequest("chatroom/" + chatroomid +"/messages" +"?token=" + User.getLoginToken() , para));
				}
				catch (IOException e1)
				{
					e1.printStackTrace();
				}
			}

		});
	
		
	}

}

