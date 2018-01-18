package application.view;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;

import application.model.Lableid;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ChatroomController
{
	@FXML
	private JFXListView<Lableid> listgroup;
	@FXML
	private HBox hbox;
	@FXML
	private VBox vbox;
	@FXML
	private JFXButton btnfertig;
	@FXML
	private void initialize()
	{	
		listgroup.getItems().add(new Lableid());
		//addNewChat(new Lableid(2));
		listgroup.refresh();
		listgroup.setVisible(true);
		listgroup.setOnMouseClicked(new EventHandler<MouseEvent>()
		{

			@Override
			public void handle(MouseEvent event)
			{
				Lableid temp = listgroup.getSelectionModel().getSelectedItem();
				if(temp != null)
				{
					if(temp.istest())
					{
						hbox.setVisible(true);
						vbox.setVisible(false);
					}
					else
					{
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
	private void addNewChat(Lableid toadd)
	{
		if(toadd != null && !toadd.istest())
		{
			int size = listgroup.getItems().size();
			listgroup.getItems().add(size-1,toadd);
		}
	}
	
	
}
