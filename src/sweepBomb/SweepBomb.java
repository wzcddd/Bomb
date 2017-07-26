package sweepBomb;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class SweepBomb extends JPanel {
	private static final long serialVersionUID = 1L;

	public static BufferedImage background;
	public static BufferedImage scoreb;
	public static BufferedImage WALL1;
	public static BufferedImage WALL2;
	public static BufferedImage MARK;
	public static BufferedImage HERO;
	public static BufferedImage BOMB;
	public static BufferedImage LASER;
	public static BufferedImage B1;
	public static BufferedImage B2;
	public static BufferedImage B3;
	public static BufferedImage B4;
	public static BufferedImage B5;
	public static BufferedImage B6;
	public static BufferedImage B7;
	public static BufferedImage B8;
	public static BufferedImage S30;
	public static BufferedImage S50;
	public static BufferedImage S70;
	public static BufferedImage S30C;
	public static BufferedImage S50C;
	public static BufferedImage S70C;
	private Timer timer;

	private int state;
	public static final int START = 0;
	public static final int RUNNING = 1;
	public static final int GAME_OVER = 2;
	public static final int WIN = 3;

	private boolean trying = false;

	public static final int HH = 550;
	public static final int WW = 525;
	public static Cell[][] wall1 = new Cell[19][20];
	public static Cell[][] wall2 = new Cell[19][20];
	public static Cell laser = null;
	public static Cell[] bs = {};
	public static int X;
	public static int Y;
	public static int XT;
	public static int YT;
	// public static Hero hero=new Hero(0,0,null);
	public int[][] bombs = {};
	private static int bn;
	private static int mn;

	private static long timeb;
	private static int timel;
	private static int bombNum;
	private static int markNum;

	static {
		try {

			background = ImageIO.read(SweepBomb.class
					.getResource("background.png"));
			scoreb = ImageIO.read(SweepBomb.class.getResource("score.png"));
			WALL1 = ImageIO.read(SweepBomb.class.getResource("WALL1.png"));
			WALL2 = ImageIO.read(SweepBomb.class.getResource("WALL2.png"));
			MARK = ImageIO.read(SweepBomb.class.getResource("mark.png"));
			BOMB = ImageIO.read(SweepBomb.class.getResource("BOMB.png"));
			HERO = ImageIO.read(SweepBomb.class.getResource("HERO.png"));
			LASER = ImageIO.read(SweepBomb.class.getResource("laser.png"));
			B1 = ImageIO.read(SweepBomb.class.getResource("1.png"));
			B2 = ImageIO.read(SweepBomb.class.getResource("2.png"));
			B3 = ImageIO.read(SweepBomb.class.getResource("3.png"));
			B4 = ImageIO.read(SweepBomb.class.getResource("4.png"));
			B5 = ImageIO.read(SweepBomb.class.getResource("5.png"));
			B6 = ImageIO.read(SweepBomb.class.getResource("6.png"));
			B7 = ImageIO.read(SweepBomb.class.getResource("7.png"));
			B8 = ImageIO.read(SweepBomb.class.getResource("8.png"));
			S30 = ImageIO.read(SweepBomb.class.getResource("30.png"));
			S50 = ImageIO.read(SweepBomb.class.getResource("50.png"));
			S70 = ImageIO.read(SweepBomb.class.getResource("70.png"));
			S30C = ImageIO.read(SweepBomb.class.getResource("30C.png"));
			S50C = ImageIO.read(SweepBomb.class.getResource("50C.png"));
			S70C = ImageIO.read(SweepBomb.class.getResource("70C.png"));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		JFrame jf = new JFrame("Bomb");
		SweepBomb bo = new SweepBomb();
		jf.add(bo);
		jf.setSize(WW + 8, HH + 30 + 25);
		jf.setAlwaysOnTop(false);
		jf.setUndecorated(false);
		jf.setLocationRelativeTo(null);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.setResizable(false);
		jf.setVisible(true);
		bo.bombStart();
		bo.action();

	}

	private int tryingIndex = 0;

	public void action() {
		MouseAdapter l = new MouseAdapter() {
			public void mouseMoved(MouseEvent e) {
				X = e.getX() - 15;
				Y = e.getY() - 17 - 30;
				// hero.moveTo(X,Y);
			}

			public void mouseClicked(MouseEvent e) {
				int key = e.getButton();
				switch (state) {
				case START:
					switch (key) {
					case MouseEvent.BUTTON1:
						if (12 < X && X < 12 + 499 && 13 < Y && Y < 13 + 175)
							bombs = new int[30][2];
						else if (12 < X && X < 12 + 499 && 13 + 175 < Y
								&& Y < 13 + 175 + 175)
							bombs = new int[50][2];
						else if (12 < X && X < 12 + 499 && 13 + 175 + 175 < Y
								&& Y < 13 + 175 + 175 + 175)
							bombs = new int[70][2];
						bombStart();
						state = RUNNING;
						timeb = System.currentTimeMillis();
						break;
					}
					break;
				case RUNNING:
					switch (key) {
					case MouseEvent.BUTTON1:
						removeWall(X, Y);
						break;
					case MouseEvent.BUTTON2:
						tryBomb(X, Y);
						break;
					case MouseEvent.BUTTON3:
						markBomb(X, Y);
						break;
					}
					break;
				case GAME_OVER:
					switch (key) {
					case MouseEvent.BUTTON1:
						bombStart();
						state = START;
						timel = 0;
						bombNum = 0;
						laser = null;
						bs = new Cell[0];
						break;
					case MouseEvent.BUTTON3:
						System.exit(0);
						break;
					}
					break;
				case WIN:
					switch (key) {
					case MouseEvent.BUTTON1:
						bombStart();
						state = START;
						timel = 0;
						bombNum = 0;
						laser = null;
						bs = new Cell[0];
						break;
					case MouseEvent.BUTTON3:
						System.exit(0);
						break;
					}
					break;
				}
			}
		};
		this.addMouseListener(l);
		this.addMouseMotionListener(l);

		timer = new Timer();
		timer.schedule(new TimerTask() {
			public void run() {
				tryingIndex++;
				if (tryingIndex == 100)
					tryingIndex = 0;
				if (tryingIndex % 20 == 0)
					trying = false;
				if (laser != null) {
					laser.moveUp();
					if (laser.gety() < 15)
						laser = null;
				}
				if (bs.length > 0) {
					bangStep();
					if (bs[0].getx() < 0)
						bs = new Cell[0];
				}
				repaint();
			}
		}, 10, 10);
	}

	public void paint(Graphics g) {
		g.drawImage(background, 0, 30, null);
		g.drawImage(scoreb, 0, 0, null);
		g.translate(0, 30);
		paintWall1(g);
		paintWall2(g);
		// paintHero(g);
		paintScore(g);
		paintState(g);
		paintBang(g);
		paintLaser(g);
	}

	public void paintWall1(Graphics g) {
		for (int i = 0; i < wall1.length; i++)
			for (int j = 0; j < wall1[i].length; j++)
				g.drawImage(wall1[i][j].getImage(),
						wall1[i][j].getx() * 26 + 13,
						wall1[i][j].gety() * 26 + 14, null);
	}

	public void paintWall2(Graphics g) {
		if (trying)
			for (int i = XT / 26 - 1; i <= XT / 26 + 1; i++)
				for (int j = YT / 26 - 1; j <= YT / 26 + 1; j++) {
					if (!(i == XT / 26 && j == YT / 26) && i >= 0 && j >= 0
							&& i <= 18 && j <= 19
							&& wall2[i][j].getImage() == WALL2)
						wall2[i][j].setImage(HERO);
				}
		if (!trying)
			for (int i = XT / 26 - 1; i <= XT / 26 + 1; i++)
				for (int j = YT / 26 - 1; j <= YT / 26 + 1; j++) {
					if (!(i == XT / 26 && j == YT / 26) && i >= 0 && j >= 0
							&& i <= 18 && j <= 19
							&& wall2[i][j].getImage() == HERO)
						wall2[i][j].setImage(WALL2);
				}
		for (int i = 0; i < wall2.length; i++)
			for (int j = 0; j < wall2[i].length; j++)
				g.drawImage(wall2[i][j].getImage(),
						wall1[i][j].getx() * 26 + 13,
						wall2[i][j].gety() * 26 + 14, null);
	}

	public void removeWall(int x, int y) {
		if (x >= 0 && x <= 26 * 19 && y >= 0 && y <= 26 * 20
				&& wall2[x / 26][y / 26].getImage() != MARK) {
			wall2[x / 26][y / 26].setImage(null);
			if (wall1[x / 26][y / 26].getImage() == WALL1) {
				if (x / 26 - 1 >= 0
						&& wall2[x / 26 - 1][y / 26].getImage() != null
						&& wall2[x / 26 - 1][y / 26].getImage() != MARK)
					removeWall(x - 26, y);
				if (y / 26 - 1 >= 0
						&& wall2[x / 26][y / 26 - 1].getImage() != null
						&& wall2[x / 26][y / 26 - 1].getImage() != MARK)
					removeWall(x, y - 26);
				if (x / 26 + 1 <= 18
						&& wall2[x / 26 + 1][y / 26].getImage() != null
						&& wall2[x / 26 + 1][y / 26].getImage() != MARK)
					removeWall(x + 26, y);
				if (y / 26 + 1 <= 19
						&& wall2[x / 26][y / 26 + 1].getImage() != null
						&& wall2[x / 26][y / 26 + 1].getImage() != MARK)
					removeWall(x, y + 26);
				if (x / 26 - 1 >= 0 && y / 26 - 1 >= 0
						&& wall2[x / 26 - 1][y / 26 - 1].getImage() != null
						&& wall2[x / 26 - 1][y / 26 - 1].getImage() != MARK)
					removeWall(x - 26, y - 26);
				if (x / 26 + 1 <= 18 && y / 26 - 1 >= 0
						&& wall2[x / 26 + 1][y / 26 - 1].getImage() != null
						&& y / 26 - 1 >= 0
						&& wall2[x / 26 + 1][y / 26 - 1].getImage() != MARK)
					removeWall(x + 26, y - 26);
				if (x / 26 - 1 >= 0 && y / 26 + 1 <= 19
						&& wall2[x / 26 - 1][y / 26 + 1].getImage() != null
						&& wall2[x / 26 - 1][y / 26 + 1].getImage() != MARK)
					removeWall(x - 26, y + 26);
				if (x / 26 + 1 <= 18 && y / 26 + 1 <= 19
						&& wall2[x / 26 + 1][y / 26 + 1].getImage() != null
						&& wall2[x / 26 + 1][y / 26 + 1].getImage() != MARK)
					removeWall(x + 26, y + 26);
			} else if (wall1[x / 26][y / 26].getImage() == BOMB) {
				bang();
				state = GAME_OVER;
			} else if (isWin()) {
				laser = new Cell(14, HH - 15, LASER);
				bombNum = 0;
				state = WIN;
			}
		}
	}

	// public void paintHero(Graphics g){
	// for(int i=0;i<wall2.length;i++)
	// for(int j=0;j<wall2[i].length;j++){
	// if(wall2[i][j].getx()*26<=hero.getx()
	// &&wall2[i][j].getx()*26+26>hero.getx()
	// &&wall2[i][j].gety()*26<=hero.gety()
	// &&wall2[i][j].gety()*26+26>hero.gety())
	// g.drawImage(HERO,wall2[i][j].getx()*26+13,wall2[i][j].gety()*26+14,null);
	// }
	// }
	public void bombStart() {
		markNum = 0;
		int x;
		int y;
		for (int i = 0; i < bombs.length; i++) {
			do {
				x = (int) (Math.random() * 19);
				y = (int) (Math.random() * 20);
			} while (bombCheck(x, y));
			bombs[i][0] = x;
			bombs[i][1] = y;
		}
		for (int i = 0; i < wall1.length; i++)
			for (int j = 0; j < wall1[i].length; j++)
				wall1[i][j] = new Cell(i, j, WALL1);

		for (int i = 0; i < wall1.length; i++)
			for (int j = 0; j < wall1[i].length; j++) {
				bn = 0;
				for (int k = 0; k < bombs.length; k++)
					if (Math.sqrt((wall1[i][j].getx() - bombs[k][0])
							* (wall1[i][j].getx() - bombs[k][0])
							+ (wall1[i][j].gety() - bombs[k][1])
							* (wall1[i][j].gety() - bombs[k][1])) < 2
							&& !(wall1[i][j].getx() == bombs[k][0] && wall1[i][j]
									.gety() == bombs[k][1])) {
						bn++;
					}
				switch (bn) {
				case 1:
					wall1[i][j].setImage(B1);
					break;
				case 2:
					wall1[i][j].setImage(B2);
					break;
				case 3:
					wall1[i][j].setImage(B3);
					break;
				case 4:
					wall1[i][j].setImage(B4);
					break;
				case 5:
					wall1[i][j].setImage(B5);
					break;
				case 6:
					wall1[i][j].setImage(B6);
					break;
				case 7:
					wall1[i][j].setImage(B7);
					break;
				case 8:
					wall1[i][j].setImage(B8);
					break;
				}
			}
		for (int i = 0; i < wall1.length; i++)
			for (int j = 0; j < wall1[i].length; j++) {
				boolean flag = false;
				for (int k = 0; k < bombs.length; k++)
					if (bombs[k][0] == i && bombs[k][1] == j)
						flag = true;
				if (flag)
					wall1[i][j].setImage(BOMB);
			}

		for (int i = 0; i < wall2.length; i++)
			for (int j = 0; j < wall2[i].length; j++)
				wall2[i][j] = new Cell(i, j, WALL2);
	}

	public boolean bombCheck(int x, int y) {
		for (int i = 0; i < bombs.length; i++)
			if (bombs[i][0] == x && bombs[i][1] == y)
				return true;
		return false;
	}

	public void paintState(Graphics g) {
		switch (state) {
		case START:
			if (12 < X && X < 12 + 499 && 13 < Y && Y < 13 + 175)
				g.drawImage(S30C, 0 + 12, 0 + 13, null);
			else
				g.drawImage(S30, 0 + 12, 0 + 13, null);
			if (12 < X && X < 12 + 499 && 13 + 175 < Y && Y < 13 + 175 + 175)
				g.drawImage(S50C, 0 + 12, 175 + 13, null);
			else
				g.drawImage(S50, 0 + 12, 175 + 13, null);
			if (12 < X && X < 12 + 499 && 13 + 175 + 175 < Y
					&& Y < 13 + 175 + 175 + 175)
				g.drawImage(S70C, 0 + 12, 175 + 175 + 13, null);
			else
				g.drawImage(S70, 0 + 12, 175 + 175 + 13, null);
			break;
		case GAME_OVER:
			for (int i = 0; i < bombs.length; i++)
				if (wall2[bombs[i][0]][bombs[i][1]].getImage() != MARK)
					wall2[bombs[i][0]][bombs[i][1]].setImage(null);
			break;
		case WIN:
			for (int i = 0; i < bombs.length; i++)
				wall2[bombs[i][0]][bombs[i][1]].setImage(MARK);
			break;
		}
	}

	public boolean isWin() {
		for (int i = 0; i < wall2.length; i++)
			for (int j = 0; j < wall2[i].length; j++)
				if (wall1[i][j].getImage() != BOMB
						&& wall2[i][j].getImage() != null)
					return false;
		return true;
	}

	public void markBomb(int x, int y) {
		if (x >= 0 && x <= 26 * 19 && y >= 0 && y <= 26 * 20)
			if (wall2[x / 26][y / 26].getImage() != null)
				if (wall2[x / 26][y / 26].getImage() == WALL2
						&& markNum < bombs.length) {
					wall2[x / 26][y / 26].setImage(MARK);
					markNum++;
				} else if (wall2[x / 26][y / 26].getImage() == MARK) {
					wall2[x / 26][y / 26].setImage(WALL2);
					markNum--;
				}
	}

	public void tryBomb(int x, int y) {
		XT = x;
		YT = y;
		bn = 0;
		mn = 0;
		for (int i = x / 26 - 1; i <= x / 26 + 1; i++)
			for (int j = y / 26 - 1; j <= y / 26 + 1; j++)
				if (!(i == x / 26 && j == y / 26) && i >= 0 && j >= 0
						&& i <= 18 && j <= 19) {
					for (int k = 0; k < bombs.length; k++)
						if (wall1[i][j].getx() == bombs[k][0]
								&& wall1[i][j].gety() == bombs[k][1]) {
							bn++;
						}
					if (wall2[i][j].getImage() == MARK)
						mn++;
				}

		if (bn <= mn)
			for (int i = x / 26 - 1; i <= x / 26 + 1; i++)
				for (int j = y / 26 - 1; j <= y / 26 + 1; j++) {
					if (!(i == x / 26 && j == y / 26) && i >= 0 && j >= 0
							&& i <= 18 && j <= 19)
						removeWall(i * 26, j * 26);
				}
		else {
			trying = true;
			tryingIndex = 0;
		}
	}

	public void paintScore(Graphics g) {
		if (state == RUNNING) {
			bombNum = bombs.length - markNum;
			timel = (int) (System.currentTimeMillis() - timeb) / 1000;
		}
		Font font = new Font(Font.SANS_SERIF, Font.BOLD, 22);
		g.setColor(new Color(0xFF0000));
		g.setFont(font);
		g.drawString("时间：" + timel, 0, -5);
		g.drawString("雷数：" + bombNum, 200, -5);
	}

	public void paintBang(Graphics g) {
		if (bs.length > 0)
			for (int i = 0; i < bs.length; i++)
				g.drawImage(bs[i].getImage(), bs[i].getx(), bs[i].gety(), null);
	}

	public void paintLaser(Graphics g) {
		if (laser != null)
			g.drawImage(laser.getImage(), laser.getx(), laser.gety(), null);

	}

	public void laserMove() {
		laser.moveUp();
	}

	public void bang() {
		bs = new Cell[0];
		for (int i = -4; i < 5; i++)
			for (int j = -4; j < 5; j++) {
				if (!(i == 0 && j == 0) && Math.abs(i) + Math.abs(j) < 7) {
					bs = Arrays.copyOf(bs, bs.length + 1);
					bs[bs.length - 1] = new Cell(9 * 26 + 13, 10 * 26 + 14,
							HERO);
					bs[bs.length - 1].setSpeed(i, j);

				}
			}
	}

	public void bangStep() {
		for (int i = 0; i < bs.length; i++)
			bs[i].bang();
	}
}
