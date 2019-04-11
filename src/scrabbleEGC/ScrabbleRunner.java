/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package scrabbleEGC;

import java.awt.Color; 
import java.awt.Graphics;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import scrabbleMVC.controller.ScrabbleController;
import scrabbleMVC.model.ScrabbleModel;
import scrabbleMVC.view.ScrabbleView;
/**
 *
 * @author Omar Verduga
 */
public class ScrabbleRunner extends JFrame {
 
    
	private ScrabbleView gameView;
        private ScrabbleController controller;
        private ScrabbleModel logic;
        
        
	/* Constructor usado para iniciar los componentes del patron MVC*/
	public ScrabbleRunner() {
            
		super();        
                setTitle("Scrabble EGC Multiplayer");
                setResizable(false);
               // try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); } catch (Exception e) { } //
                
                 gameView = new ScrabbleView( this );
		gameView.setBounds(0,0,900,850);
		add(gameView);
		
                
                /*Fijando MVC*/
                logic = ScrabbleModel.getInstance(); //no requerido, solo para exponer mas el diseño MVC
                controller = new ScrabbleController(gameView, logic);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(null);
		setSize(700, 750);
                setBackground(Color.black);
		
                
                
		
		
             
		setLocationRelativeTo(null);
		setVisible(true);
                gameView.setController(controller);
		repaint();
	}
     
        
	public static void main(String[] args){
		
             
          
          SwingUtilities.invokeLater(new Runnable()
		{
                        @Override
			public void run()
			{
				new ScrabbleRunner().setVisible(true);				
                            
			}
		});
	}

}

    
