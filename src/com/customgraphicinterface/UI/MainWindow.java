package com.customgraphicinterface.UI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.customgraphicinterface.pubsub.EventManager;
import com.customgraphicinterface.pubsub.EventManager.Channel;

import java.awt.event.WindowEvent;

public class MainWindow{

	private static MainWindow mwInstance;

	private JFrame windowFrame;
	private static CustomDrawingPanel canvas;
	private Timer timer;
	private Channel _updateChannel;

	public static MainWindow getInstance() {
		if (mwInstance == null) {
			mwInstance = new MainWindow();
			return mwInstance;
		} else {
			return mwInstance;
		}
	}

	public static CustomDrawingPanel getMainCanvas() {
		return canvas;
	}

	private MainWindow() {

		createMainWindow();
		createCanvas();
		createKeyBoardListener();
		_updateChannel = EventManager.getInstance().createChannel("update");
		initialize();
	}

	private void createKeyBoardListener() {
		/*KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher() {

            @Override
            public boolean dispatchKeyEvent(KeyEvent ke) {
                synchronized (IsKeyPressed.class) {
                    switch (ke.getID()) {
                    case KeyEvent.KEY_PRESSED:
                        if (ke.getKeyCode() == KeyEvent.VK_W) {
                            wPressed = true;
                        }
                        break;

                    case KeyEvent.KEY_RELEASED:
                        if (ke.getKeyCode() == KeyEvent.VK_W) {
                            wPressed = false;
                        }
                        break;
                    }
                    return false;
                }
            }
        });*/
	}

	public void run(){
		try {
			show();
		} catch (Exception e) {
			stopLoop();
			e.printStackTrace();
		}
	}

	private void initialize() {
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
			ex.printStackTrace();
		}
	}

	public void stopLoop() {
		if(timer != null) timer.stop();
	}
	
	private void startLoop() {
		if (timer == null)
			return;
		timer.setInitialDelay(0);
		timer.start();
	}

	public void setSize(int h, int l){
		windowFrame.setSize(h, l);
	}

	private void createMainWindow() {
		windowFrame = new JFrame();
		windowFrame.setTitle("Project1 : City Management");
		windowFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		windowFrame.setSize(1500, 1000);
		windowFrame.setLocationRelativeTo(null);
		windowFrame.addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent windowEvent) {
				if (timer != null)
					timer.stop();
				System.exit(0);
			}
		});

		timer = new Timer(16, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				canvas.getMainCamera().update();
				
				_updateChannel.sendNotification();

				canvas.repaint();
			}
		});
	}

	private void createCanvas() {
		
		canvas = new CustomDrawingPanel();
		if (windowFrame != null)
			windowFrame.add(canvas);
		else {
			throw new NullPointerException("Error: WindowFrame not initialised");
		}
		windowFrame.pack();
	}

	public void show() {
		startLoop();
		windowFrame.setVisible(true);
	}

	public void close(){
		windowFrame.dispatchEvent(new WindowEvent(windowFrame, WindowEvent.WINDOW_CLOSING));
	}

}
