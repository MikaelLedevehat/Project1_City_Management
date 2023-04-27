/*Copyright 2021 Google LLC
*
*Author: Mikael Le Devehat
*Use of this source code is governed by an MIT-style
*license that can be found in the LICENSE file or at
https://opensource.org/licenses/MIT.
*/

package com.customgraphicinterface.UI;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.WindowEvent;

public class CustomWindow implements IWindow{

	public class MainWindowAlreadyCreatedException extends RuntimeException{
		public MainWindowAlreadyCreatedException(String s, Throwable err){
			super(s, err);
		}

		public MainWindowAlreadyCreatedException(String s){
			super(s);
		}
	}

	public static Dimension DEFAULT_SIZE = new Dimension(1500,200);
	public static boolean DAFAULT_IS_MAIN = false;

	private static int _mainWindows = 0;

	private JFrame _windowFrame;
	private ICanvas _canvas;
	private boolean _isMain;

	public CustomWindow(boolean mainWindow, Dimension d) {

		incrementMainWindows(mainWindow);
		createWindow();
		setSize(d);
		createCanvas();
		createKeyBoardListener();
		initializeLookAndFeel();
		centerWindow();
		show();
	}

	private void centerWindow(){
		_windowFrame.setLocationRelativeTo(null);
	}

	private void incrementMainWindows(boolean b){
		if(b == true) _mainWindows++;
		_isMain = b;
	}

	public CustomWindow(boolean b){
		this(b, DEFAULT_SIZE);
	}

	public CustomWindow(){
		this(DAFAULT_IS_MAIN, DEFAULT_SIZE);
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

	private void initializeLookAndFeel() {
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
			ex.printStackTrace();
		}
	}

	public void setSize(int h, int l){
		_windowFrame.setSize(h, l);
	}

	public void setSize(Dimension d){
		if(d == null)_windowFrame.setSize(DEFAULT_SIZE);
		else _windowFrame.setSize(d);
	}

	private void createWindow() {
		_windowFrame = new JFrame();
		_windowFrame.setTitle("Project1 : City Management");
		_windowFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		_windowFrame.setSize(DEFAULT_SIZE);
		_windowFrame.addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent windowEvent) {
				if(_isMain == true) _mainWindows--;
				destroy();
				if(_mainWindows == 0) System.exit(0);
			}
		});
	}

	private void createCanvas() {
		_canvas = new CustomDrawingPanel();
		if (_windowFrame != null)
			_windowFrame.add(_canvas.getComponent());
		else {
			throw new NullPointerException("Error: Canvas not initialised");
		}
		_windowFrame.pack();
	}

	public void show() {
		_windowFrame.setVisible(true);
	}

	public void hide(){
		_windowFrame.setVisible(false);
	}

	public void close(){
		_windowFrame.dispatchEvent(new WindowEvent(_windowFrame, WindowEvent.WINDOW_CLOSING));
	}

	@Override
	public Component getComponent() {
		return _windowFrame;
	}


	public void destroy(){

	}
}
