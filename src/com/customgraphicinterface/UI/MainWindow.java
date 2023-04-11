package com.customgraphicinterface.UI;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import java.awt.event.WindowEvent;

public class MainWindow implements IWindow{

	private static MainWindow mwInstance;

	private JFrame windowFrame;
	private static ICanvas canvas;


	public static MainWindow getInstance() {
		if (mwInstance == null) {
			mwInstance = new MainWindow();
			return mwInstance;
		} else {
			return mwInstance;
		}
	}

	public static ICanvas getMainCanvas() {
		return canvas;
	}

	private MainWindow() {

		createMainWindow();
		createCanvas();
		createKeyBoardListener();
		initializeLookAndFeel();
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
			e.printStackTrace();
		}
	}

	private void initializeLookAndFeel() {
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
			ex.printStackTrace();
		}
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
				System.exit(0);
			}
		});
	}

	private void createCanvas() {
		canvas = new CustomDrawingPanel();
		if (windowFrame != null)
			windowFrame.add(canvas.getCanvasComponent());
		else {
			throw new NullPointerException("Error: Canvas not initialised");
		}
		windowFrame.pack();
	}

	public void show() {
		//startLoop();
		windowFrame.setVisible(true);
	}

	public void close(){
		windowFrame.dispatchEvent(new WindowEvent(windowFrame, WindowEvent.WINDOW_CLOSING));
	}

}
