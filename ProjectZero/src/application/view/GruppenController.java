package application.view;

import java.io.IOException;
import java.util.List;

import org.json.JSONException;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;

import application.api.Friends;
import application.api.Groups;
import application.model.Group;
import application.model.Lableid;
import application.model.User;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

public class GruppenController
{
	@FXML
	private JFXListView<Lableid> listfreunde;
	@FXML
	private JFXListView<Lableid> listgruppenmitglieder;
	@FXML
	private JFXListView<Lableid> gruppen;
	@FXML
	private JFXButton btnadd;
	@FXML
	private JFXButton btndelete;
	@FXML
	private Label lblgruppe;

	@FXML
	private void initialize()
	{
		updategroups();
		gruppen.setOnMouseClicked(new EventHandler<MouseEvent>()
		{

			@Override
			public void handle(MouseEvent event)
			{
				if (gruppen.getSelectionModel().getSelectedItem() != null)
				{
					updateGroupList();
				}
			}
		});
		btnadd.setOnMouseClicked(new EventHandler<MouseEvent>()
		{

			@Override
			public void handle(MouseEvent event)
			{
				inviteFriendToGroup();
			}
		});
		btndelete.setOnMouseClicked(new EventHandler<MouseEvent>()
		{

			@Override
			public void handle(MouseEvent event)
			{
				kickGroupMember();
			}
		});
	}

	private void updateGroupList()
	{
		listfreunde.getItems().clear();
		listgruppenmitglieder.getItems().clear();
		if (gruppen.getSelectionModel().getSelectedItem() != null)
		{
			try
			{
				List<User> friends = Friends.getFriends();
				for (User u : friends)
				{
					Lableid l = new Lableid(u.getId());
					l.setText(u.getName());
					listfreunde.getItems().add(l);
				}
				int gruppenid = gruppen.getSelectionModel().getSelectedItem().getGroupid();
				Group g = Groups.getGroupByID(gruppenid);
				lblgruppe.setText("Gruppe: " + g.getName());
				if (application.api.User.getUserID() == g.getAdmin().getId())
				{
					listfreunde.setDisable(false);
					btnadd.setDisable(false);
					btndelete.setDisable(false);
				}
				else
				{
					listfreunde.setDisable(true);
					btnadd.setDisable(true);
					btndelete.setDisable(true);
				}
				List<User> user = g.getMembers();
				for (User u : user)
				{
					Lableid ll = new Lableid(u.getId());
					ll.setText(u.getName());
					listgruppenmitglieder.getItems().add(ll);
				}
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}

		}
	}

	private void kickGroupMember()
	{
		if (listgruppenmitglieder.getSelectionModel().getSelectedItem() != null)
		{
			if (gruppen.getSelectionModel().getSelectedItems() != null)
			{
				int gruppenid = gruppen.getSelectionModel().getSelectedItem().getGroupid();
				try
				{
					Group g = Groups.getGroupByID(gruppenid);
					if (g.getAdmin().getId() == application.api.User.getUserID())
					{
						int tokick = listgruppenmitglieder.getSelectionModel().getSelectedItem().getGroupid();
						Groups.deleteUserFromGroup(tokick, gruppenid);
					}
					else
					{
						System.out.println("Keine Berechtigung zu kicken");
					}
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}

			}
		}
	}

	private void inviteFriendToGroup()
	{
		if (listfreunde.getSelectionModel().getSelectedItem() != null)
		{
			if (gruppen.getSelectionModel().getSelectedItems() != null)
			{
				int gruppenid = gruppen.getSelectionModel().getSelectedItem().getGroupid();
				try
				{
					Group g = Groups.getGroupByID(gruppenid);
					if (g.getAdmin().getId() == application.api.User.getUserID())
					{
						int toinvite = listfreunde.getSelectionModel().getSelectedItem().getGroupid();
						Groups.addUserToGroup(toinvite, gruppenid);
					}
					else
					{
						System.out.println("Keine Berechtigung Freunde einzuladen");
					}
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}

			}
		}
	}

	private void updategroups()
	{
		try
		{
			List<Group> gr = Groups.getUserGroups();
			for (Group g : gr)
			{
				Lableid l = new Lableid(g.getID());
				l.setText(g.getName());
				gruppen.getItems().add(l);
			}
		}
		catch (JSONException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

}
