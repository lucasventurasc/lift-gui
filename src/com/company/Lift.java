/*
 * Created by JFormDesigner on Thu Jul 28 20:35:40 CEST 2022
 */

package com.company;

import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import java.util.concurrent.Callable;
import javax.swing.*;
import javax.swing.GroupLayout;

/**
 * @author unknown
 */
public class Lift extends JFrame implements LiftBaseInterface {

    private static Color DEFAULT_GRAY = new Color(204, 204, 204);
    private static String PANEL_CLOSE = "PANEL_CLOSE";
    private static String PANEL_OPEN = "PANEL_OPEN";
    private static String PANEL_OPENING_OR_CLOSING = "PANEL_OPENING_OR_CLOSING";
    
    private final LiftInternalInterface liftInternalInterface;
    private final LiftExternalInterfaceV2 liftExternalInterfaceV2;
    private final LiftExternalInterfaceV1 liftExternalInterfaceV1;

    private Integer defaultPanelWidth;
    private String panelState = "CLOSED";


    public Lift(LiftInternalInterface liftInternalInterface, LiftExternalInterfaceV1 liftExternalInterfaceV1, LiftExternalInterfaceV2 liftExternalInterfaceV2) {
        this.liftInternalInterface = liftInternalInterface;
        this.liftExternalInterfaceV1 = liftExternalInterfaceV1;
        this.liftExternalInterfaceV2 = liftExternalInterfaceV2;
        initComponents();
        defaultPanelWidth = panel1.getWidth();
        textField1.setText("G");
        hideInsideButtons();
    }

    private void hideInsideButtons() {
        Arrays.stream(panel4.getComponents()).forEach(t -> t.setVisible(false));
    }

    private void buttonClickUp(MouseEvent e) {
        changeButtonColorTo(e.getComponent(), Color.green);
        liftExternalInterfaceV2.callToGoUp();
    }

    private void buttonClickCall(MouseEvent e) {
        changeButtonColorTo(e.getComponent(), Color.green);
        liftExternalInterfaceV1.call();

    }

    private void joinCatching(Thread t) {
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private Thread panelExecute(JPanel panel, String command) {
        Lift context = this;
        Thread t = new Thread() {
            private Boolean whileCondition(int currentWidth) {
                   if (command.equals(PANEL_CLOSE)) {
                       return currentWidth<defaultPanelWidth;
                   } else if (command.equals(PANEL_OPEN)) {
                       return currentWidth > 1;
                   } else {
                       return false;
                   }
            }

            @Override
            public void run() {
                int currentWidth = panel.getWidth();
                int currentHeight = panel.getHeight();

                while(whileCondition(currentWidth)) {
                    setPanelState(PANEL_OPENING_OR_CLOSING);
                    onPanelStateChange();
                    int millis = 10;
                    context.sleep(millis);

                    if (command.equals(PANEL_OPEN)) {
                        currentWidth = currentWidth - 4;
                    } else if (command.equals(PANEL_CLOSE)) {
                        currentWidth = currentWidth + 4;
                    }

                    panel.setSize(currentWidth, currentHeight);
                }
                setPanelState(command);
            }
        };
        t.start();
        return t;
    }

    private void setPanelState(String panelState) {
        this.panelState = panelState;
       onPanelStateChange();
    }

    private synchronized void onPanelStateChange() {
      if (panelState.equals(PANEL_OPENING_OR_CLOSING)) {
            disableAllButtons();
        }

      if (panelState.equals(PANEL_CLOSE)) {
          showInsideButtons();
      }

      if (panelState.equals(PANEL_OPEN)) {
          showOutsideButtons();
      }
    }

    private void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void buttonClickDown(MouseEvent e) {
        changeButtonColorTo(e.getComponent(), Color.green);
        liftExternalInterfaceV2.callToGoDown();
    }

    private void changeButtonColorTo(Component component, Color color) {
        clearAllButtonSelections();
        component.setForeground(color);
    }

    private void clearAllButtonSelections() {
        button1.setForeground(Color.BLACK);
        button2.setForeground(Color.BLACK);
        button3.setForeground(Color.BLACK);
    }

    private void disableAllButtons() {
        Arrays.stream(panel3.getComponents()).forEach(t -> t.setEnabled(false));
        Arrays.stream(panel4.getComponents()).forEach(t -> t.setEnabled(false));
    }

    private void enableAllButtons() {
        Arrays.stream(panel3.getComponents()).forEach(t -> t.setEnabled(true));
        Arrays.stream(panel4.getComponents()).forEach(t -> t.setEnabled(true));
    }

    private void showOutsideButtons() {
        enableAllButtons();
        Arrays.stream(panel3.getComponents()).forEach(t -> t.setVisible(true));
        Arrays.stream(panel4.getComponents()).forEach(t -> t.setVisible(false));
    }

    private void showInsideButtons() {
        enableAllButtons();
        Arrays.stream(panel3.getComponents()).forEach(t -> t.setVisible(false));
        Arrays.stream(panel4.getComponents()).forEach(t -> t.setVisible(true));
    }

    private void floor0MouseClicked(MouseEvent e) {
        liftInternalInterface.toggleFloor1();
    }

    private void floor1MouseClicked(MouseEvent e) {
        liftInternalInterface.toggleFloor2();
    }

    private void floor2MouseClicked(MouseEvent e) {
        liftInternalInterface.toggleFloor2();
    }

    private void floor3MouseClicked(MouseEvent e) {
        liftInternalInterface.toggleFloor3();
    }

    private void floor4MouseClicked(MouseEvent e) {
        liftInternalInterface.toggleFloor4();
    }

    private void floor5MouseClicked(MouseEvent e) {
        liftInternalInterface.toggleFloor5();
    }

    private void floor6MouseClicked(MouseEvent e) {
        liftInternalInterface.toggleFloor6();
    }

    private void floor7MouseClicked(MouseEvent e) {
        liftInternalInterface.toggleFloor7();
    }

    private void floor8MouseClicked(MouseEvent e) {
        liftInternalInterface.toggleFloor8();
    }

    private void floor9MouseClicked(MouseEvent e) {
        liftInternalInterface.toggleFloor9();
    }

    private void floor10MouseClicked(MouseEvent e) {
        liftInternalInterface.toggleFloor10();
    }

    private void floor11MouseClicked(MouseEvent e) {
        liftInternalInterface.toggleFloor11();
    }

    @Override
    public void displayFloor(String floor) {
        textField1.setText(floor);
    }

    @Override
    public void openDoor(Callable<Void> onDone) {
        executeDoorCommand(onDone, PANEL_OPEN);
    }

    @Override
    public void closeDoor(Callable<Void> onDone) {
        executeDoorCommand(onDone, PANEL_CLOSE);
    }

    private void executeDoorCommand(Callable<Void> onDone, String command) {
        Thread running = panelExecute(panel1, command);
        new Thread(() -> {
            joinCatching(running);
            try {
                onDone.call();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - Lucas
        textField1 = new JTextField();
        panel1 = new JPanel();
        panel3 = new JPanel();
        button2 = new JButton();
        button1 = new JButton();
        button3 = new JButton();
        panel4 = new JPanel();
        floor0 = new JButton();
        floor1 = new JButton();
        floor2 = new JButton();
        floor3 = new JButton();
        floor4 = new JButton();
        floor5 = new JButton();
        floor6 = new JButton();
        floor7 = new JButton();
        floor8 = new JButton();
        floor9 = new JButton();
        floor10 = new JButton();
        floor11 = new JButton();

        //======== this ========
        setBackground(new Color(204, 204, 255));
        Container contentPane = getContentPane();

        //---- textField1 ----
        textField1.setBackground(Color.black);
        textField1.setForeground(new Color(51, 153, 0));
        textField1.setEnabled(false);
        textField1.setEditable(false);
        textField1.setFont(new Font("Segoe UI", Font.PLAIN, 28));
        textField1.setText("0");
        textField1.setDisabledTextColor(new Color(0, 153, 51));
        textField1.setHorizontalAlignment(SwingConstants.CENTER);

        //======== panel1 ========
        {
            panel1.setBackground(new Color(204, 204, 204));
            panel1.setBorder (new javax. swing. border. CompoundBorder( new javax .swing .border .TitledBorder (new javax. swing. border. EmptyBorder
            ( 0, 0, 0, 0) , "JF\u006frm\u0044es\u0069gn\u0065r \u0045va\u006cua\u0074io\u006e", javax. swing. border. TitledBorder. CENTER, javax. swing. border
            . TitledBorder. BOTTOM, new java .awt .Font ("D\u0069al\u006fg" ,java .awt .Font .BOLD ,12 ), java. awt
            . Color. red) ,panel1. getBorder( )) ); panel1. addPropertyChangeListener (new java. beans. PropertyChangeListener( ){ @Override public void
            propertyChange (java .beans .PropertyChangeEvent e) {if ("\u0062or\u0064er" .equals (e .getPropertyName () )) throw new RuntimeException( )
            ; }} );
            panel1.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        }

        //======== panel3 ========
        {
            panel3.setLayout(new GridLayout(3, 1));

            //---- button2 ----
            button2.setText("/\\");
            button2.setBackground(new Color(204, 204, 204));
            button2.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    buttonClickUp(e);
                }
            });
            panel3.add(button2);

            //---- button1 ----
            button1.setText("||");
            button1.setBackground(new Color(204, 204, 204));
            button1.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    buttonClickCall(e);
                }
            });
            panel3.add(button1);

            //---- button3 ----
            button3.setText("\\/");
            button3.setBackground(new Color(204, 204, 204));
            button3.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    buttonClickDown(e);
                }
            });
            panel3.add(button3);
        }

        //======== panel4 ========
        {
            panel4.setLayout(new GridLayout(6, 2));

            //---- floor0 ----
            floor0.setText("0");
            floor0.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    floor0MouseClicked(e);
                }
            });
            panel4.add(floor0);

            //---- floor1 ----
            floor1.setText("1");
            floor1.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    floor1MouseClicked(e);
                }
            });
            panel4.add(floor1);

            //---- floor2 ----
            floor2.setText("2");
            floor2.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    floor2MouseClicked(e);
                }
            });
            panel4.add(floor2);

            //---- floor3 ----
            floor3.setText("3");
            floor3.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    floor3MouseClicked(e);
                }
            });
            panel4.add(floor3);

            //---- floor4 ----
            floor4.setText("4");
            floor4.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    floor4MouseClicked(e);
                }
            });
            panel4.add(floor4);

            //---- floor5 ----
            floor5.setText("5");
            floor5.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    floor5MouseClicked(e);
                }
            });
            panel4.add(floor5);

            //---- floor6 ----
            floor6.setText("6");
            floor6.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    floor6MouseClicked(e);
                }
            });
            panel4.add(floor6);

            //---- floor7 ----
            floor7.setText("7");
            floor7.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    floor7MouseClicked(e);
                }
            });
            panel4.add(floor7);

            //---- floor8 ----
            floor8.setText("8");
            floor8.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    floor8MouseClicked(e);
                }
            });
            panel4.add(floor8);

            //---- floor9 ----
            floor9.setText("9");
            floor9.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    floor9MouseClicked(e);
                }
            });
            panel4.add(floor9);

            //---- floor10 ----
            floor10.setText("10");
            floor10.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    floor10MouseClicked(e);
                }
            });
            panel4.add(floor10);

            //---- floor11 ----
            floor11.setText("11");
            floor11.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    floor11MouseClicked(e);
                }
            });
            panel4.add(floor11);
        }

        GroupLayout contentPaneLayout = new GroupLayout(contentPane);
        contentPane.setLayout(contentPaneLayout);
        contentPaneLayout.setHorizontalGroup(
            contentPaneLayout.createParallelGroup()
                .addGroup(contentPaneLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(panel3, GroupLayout.PREFERRED_SIZE, 64, GroupLayout.PREFERRED_SIZE)
                    .addGroup(contentPaneLayout.createParallelGroup()
                        .addGroup(contentPaneLayout.createSequentialGroup()
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(panel1, GroupLayout.PREFERRED_SIZE, 296, GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(panel4, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE))
                        .addGroup(contentPaneLayout.createSequentialGroup()
                            .addGap(127, 127, 127)
                            .addComponent(textField1, GroupLayout.PREFERRED_SIZE, 66, GroupLayout.PREFERRED_SIZE)))
                    .addContainerGap(13, Short.MAX_VALUE))
        );
        contentPaneLayout.setVerticalGroup(
            contentPaneLayout.createParallelGroup()
                .addGroup(contentPaneLayout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(contentPaneLayout.createParallelGroup()
                        .addGroup(contentPaneLayout.createSequentialGroup()
                            .addGap(0, 0, Short.MAX_VALUE)
                            .addComponent(panel3, GroupLayout.PREFERRED_SIZE, 213, GroupLayout.PREFERRED_SIZE)
                            .addGap(159, 159, 159))
                        .addGroup(contentPaneLayout.createSequentialGroup()
                            .addComponent(textField1, GroupLayout.PREFERRED_SIZE, 65, GroupLayout.PREFERRED_SIZE)
                            .addGroup(contentPaneLayout.createParallelGroup()
                                .addGroup(contentPaneLayout.createSequentialGroup()
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(panel1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addContainerGap())
                                .addGroup(GroupLayout.Alignment.TRAILING, contentPaneLayout.createSequentialGroup()
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 86, Short.MAX_VALUE)
                                    .addComponent(panel4, GroupLayout.PREFERRED_SIZE, 270, GroupLayout.PREFERRED_SIZE)
                                    .addGap(126, 126, 126))))))
        );
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - Lucas
    private JTextField textField1;
    private JPanel panel1;
    private JPanel panel3;
    private JButton button2;
    private JButton button1;
    private JButton button3;
    private JPanel panel4;
    private JButton floor0;
    private JButton floor1;
    private JButton floor2;
    private JButton floor3;
    private JButton floor4;
    private JButton floor5;
    private JButton floor6;
    private JButton floor7;
    private JButton floor8;
    private JButton floor9;
    private JButton floor10;
    private JButton floor11;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
