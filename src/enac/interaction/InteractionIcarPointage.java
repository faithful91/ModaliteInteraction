/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package enac.interaction;

import fr.dgac.ivy.Ivy;
import fr.dgac.ivy.IvyClient;
import fr.dgac.ivy.IvyException;
import fr.dgac.ivy.IvyMessageListener;
import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Timer;

/**
 *
 * @author Hazem
 */
public class InteractionIcarPointage implements IvyMessageListener {
  int posx=0;
  int posy=0;
    String form="";
  private Ivy bus;
  String x;
  String y;
  Boolean isClicked=false;
    @Override
    public void receive(IvyClient ic, String[] strings) {
    }
  enum State 
  {
     INIT,FICAR,SPOIN,CREE
  }
    State sb;
    int delay = 4000; //milliseconds
    ActionListener task = new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
          try {
              getPalettePoint();
          } catch (IvyException ex) {
              Logger.getLogger(InteractionIcarPointage.class.getName()).log(Level.SEVERE, null, ex);
          }
          System.out.println("timer");
if (isClicked)
{
          try {
              getPalettePoint();
              isClicked=false;
          } catch (IvyException ex) {
              Logger.getLogger(InteractionIcarPointage.class.getName()).log(Level.SEVERE, null, ex);
          }
}
else {System.out.println("3afatlo"+form);
              try {
                  bus.sendMsg(form+" x="+posx+" y="+posy);
                  posx+=50;
                  posy+=50;
              } catch (IvyException ex) {
                  Logger.getLogger(InteractionIcarPointage.class.getName()).log(Level.SEVERE, null, ex);
              }
        t.stop();
     }
}
    };
    
      Timer t = new Timer(delay,task);
    
    public InteractionIcarPointage() throws IvyException, AWTException
    {   bus = new Ivy("IvyTranslater","",null);
         bus.start("127.255.255.255:2010");
        sb=sb.INIT;
        getSraMsg();
        
    }
     public void run() throws IvyException 
    {  
        switch (sb) 
        {
            case INIT:{getIcarForm();System.out.println("i habe form");}break;
            case FICAR:{t.start();System.out.println("i have point");  }break;
            case SPOIN:{t.stop();System.out.println("i create");createForm();}break;
            case CREE:{System.out.println("je suis creer");}break;
        }
    }
    
    public void TranslateIcarPointMsg() throws IvyException
           {getIcarForm();
           getPalettePoint();
           }
     
    
    public void getIcarForm() throws IvyException
       {
        bus.bindMsg("^ICAR (.*)",new IvyMessageListener() 
           {
          public void receive(IvyClient client, String[] args) 
                {   
                    System.out.println(args[0]);
                    switch(args[0])
                        {
                            case "Rectangle":form="Palette:CreerRectangle";break;
                            case "Ellipse":form="Palette:CreerEllipse";break;
                        }      
                sb=sb.FICAR;
              try {
                  run();
              } catch (IvyException ex) {
                  Logger.getLogger(InteractionIcarPointage.class.getName()).log(Level.SEVERE, null, ex);
              }
                }
           });            
       }  
           
          
    public void getPalettePoint() throws IvyException
       {
          bus.bindMsg("^Palette:MouseReleased x=(.*) y=(.*)",new IvyMessageListener() 
          {
                public void receive(IvyClient client, String[] args) 
                {    isClicked=true;
                     x=args[0];
                     y=args[1];
                    sb=sb.SPOIN;
                    try {
                        run();
                    } catch (IvyException ex) {
                        Logger.getLogger(InteractionIcarPointage.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } 
          });            
       }
    
    public void createForm()
        {
          try 
            {   System.out.println(form+" x="+x+" y="+y);
                bus.sendMsg(form+" x="+x+" y="+y);
                
                sb=sb.CREE;
                run();


            } catch (IvyException ex) 
                {
                    Logger.getLogger(ivyTranslater.class.getName()).log(Level.SEVERE, null, ex);
                }   
        }
        
  public void getSraMsg() throws IvyException, AWTException{
      Robot r=new Robot();
  bus.bindMsg("^sra5 Parsed=(.*) Confidence=.*",new IvyMessageListener() {
      // callback for "Bye" message
      public void receive(IvyClient client, String[] args) {
               System.out.println(args[0]);
 try {
     switch (args[0])
     {
            case "Action:deplacement Position:en bas" :
            {
                bus.sendMsg("Palette:DeplacerObjet nom=R4 y=2");
           
            
            }
break;
           
            case "Action:creeici" :r.mouseRelease(InputEvent.BUTTON1_MASK);
;break;
    
      } 
     } catch (IvyException ex) {
                       Logger.getLogger(ivyTranslater.class.getName()).log(Level.SEVERE, null, ex);
                   }
        
      }
    });
  }
   
}   
