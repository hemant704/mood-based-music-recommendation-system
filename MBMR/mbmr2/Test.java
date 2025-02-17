import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import javax.swing.border.*;
import java.awt.Font;

public class Test extends JFrame {
    private JPanel mainPanel;
    private JComboBox<String> moodSelector;
    private JPanel songPanel;
    private JLabel titleLabel;
    private JPanel headerPanel;
    
    public Test() {
        setTitle("Mood Music Recommender");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 700);
        setLocationRelativeTo(null);
        
        // Initialize components
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(10, 10));
        mainPanel.setBackground(new Color(25, 25, 25));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Create header panel
        headerPanel = new JPanel();
        headerPanel.setLayout(new BorderLayout());
        headerPanel.setBackground(new Color(45, 45, 45));
        headerPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        // Create title
        titleLabel = new JLabel("Mood Music Recommender");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        headerPanel.add(titleLabel, BorderLayout.NORTH);
        
        // Create mood selector panel
        JPanel moodPanel = new JPanel(new FlowLayout());
        moodPanel.setBackground(new Color(45, 45, 45));
        
        JLabel moodLabel = new JLabel("Select your mood:");
        moodLabel.setForeground(Color.WHITE);
        moodLabel.setFont(new Font("Arial", Font.BOLD, 16));
        
        String[] moods = {"Happy", "Sad", "Energetic", "Relaxed", "Romantic"};
        moodSelector = new JComboBox<>(moods);
        moodSelector.setFont(new Font("Arial", Font.PLAIN, 14));
        moodSelector.setBackground(new Color(70, 70, 70));
        moodSelector.setForeground(Color.WHITE);
        
        moodPanel.add(moodLabel);
        moodPanel.add(moodSelector);
        headerPanel.add(moodPanel, BorderLayout.CENTER);
        
        // Create song panel
        songPanel = new JPanel();
        songPanel.setLayout(new BoxLayout(songPanel, BoxLayout.Y_AXIS));
        songPanel.setBackground(new Color(35, 35, 35));
        
        JScrollPane scrollPane = new JScrollPane(songPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        
        // Add action listener to mood selector
        moodSelector.addActionListener(e -> updateSongs());
        
        // Add components to main panel
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        
        add(mainPanel);
        
        // Initial song update
        updateSongs();
    }
    
    private void updateSongs() {
        songPanel.removeAll();
        String selectedMood = (String) moodSelector.getSelectedItem();
        
        // Add category labels
        JPanel categoryPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        categoryPanel.setBackground(new Color(35, 35, 35));
        JLabel englishLabel = new JLabel("English Songs");
        englishLabel.setFont(new Font("Arial", Font.BOLD, 16));
        englishLabel.setForeground(Color.WHITE);
        categoryPanel.add(englishLabel);
        songPanel.add(categoryPanel);
        
        String[][] songs = getSongsByMood(selectedMood, true); // English songs
        addSongsToPanel(songs);
        
        // Add Hindi songs category
        categoryPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        categoryPanel.setBackground(new Color(35, 35, 35));
        JLabel hindiLabel = new JLabel("Hindi Songs");
        hindiLabel.setFont(new Font("Arial", Font.BOLD, 16));
        hindiLabel.setForeground(Color.WHITE);
        categoryPanel.add(hindiLabel);
        songPanel.add(categoryPanel);
        
        songs = getSongsByMood(selectedMood, false); // Hindi songs
        addSongsToPanel(songs);
        
        songPanel.revalidate();
        songPanel.repaint();
    }
    
    private void addSongsToPanel(String[][] songs) {
        for (String[] song : songs) {
            JButton songButton = new JButton(song[0]);
            songButton.setAlignmentX(Component.LEFT_ALIGNMENT);
            songButton.setPreferredSize(new Dimension(500, 35));
            songButton.setMaximumSize(new Dimension(500, 35));
            songButton.setFont(new Font("Arial", Font.PLAIN, 12));
            songButton.setBackground(new Color(70, 70, 70));
            songButton.setForeground(Color.WHITE);
            songButton.setFocusPainted(false);
            songButton.setBorderPainted(false);
            
            songButton.addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent e) {
                    songButton.setBackground(new Color(100, 100, 100));
                }
                public void mouseExited(MouseEvent e) {
                    songButton.setBackground(new Color(70, 70, 70));
                }
            });
            
            songButton.addActionListener(e -> openWebPage(song[1]));
            songPanel.add(songButton);
            songPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        }
    }
    
    private String[][] getSongsByMood(String mood, boolean isEnglish) {
        if (isEnglish) {
            switch (mood) {
                case "Happy":
                    return new String[][] {
                        {"Pharrell Williams - Happy", "https://www.youtube.com/watch?v=ZbZSe6N_BXs"},
                        {"Can't Stop the Feeling - Justin Timberlake", "https://www.youtube.com/watch?v=ru0K8uYEZWw"},
                        {"Uptown Funk - Mark Ronson ft. Bruno Mars", "https://www.youtube.com/watch?v=OPf0YbXqDm0"},
                        {"I Gotta Feeling - The Black Eyed Peas", "https://www.youtube.com/watch?v=uSD4vsh1zDA"},
                        {"Walking on Sunshine - Katrina & The Waves", "https://www.youtube.com/watch?v=iPUmE-tne5U"},
                        {"Good Life - OneRepublic", "https://www.youtube.com/watch?v=jZhQOvvV45w"},
                        {"Shake It Off - Taylor Swift", "https://www.youtube.com/watch?v=nfWlot6h_JM"},
                        {"Best Day of My Life - American Authors", "https://www.youtube.com/watch?v=Y66j_BUCBMY"},
                        {"Don't Stop Believin' - Journey", "https://www.youtube.com/watch?v=1k8craCGpgs"},
                        {"Walking On Sunshine - Katrina & The Waves", "https://www.youtube.com/watch?v=iPUmE-tne5U"},
                        {"Celebration - Kool & The Gang", "https://www.youtube.com/watch?v=3GwjfUFyY6M"},
                        {"Don't Worry Be Happy - Bobby McFerrin", "https://www.youtube.com/watch?v=d-diB65scQU"},
                        {"Three Little Birds - Bob Marley", "https://www.youtube.com/watch?v=zaGUr6wzyT8"},
                    };

                case "Sad":
                    return new String[][] {
                        {"Someone Like You - Adele", "https://www.youtube.com/watch?v=hLQl3WQQoQ0"},
                        {"All By Myself - Celine Dion", "https://www.youtube.com/watch?v=NGrLb6W5YOM"},
                        {"Say Something - A Great Big World", "https://www.youtube.com/watch?v=-2U0Ivkn2Ds"},
                        {"Hello - Adele", "https://www.youtube.com/watch?v=YQHsXMglC9A"},
                        {"The Sound of Silence - Disturbed", "https://www.youtube.com/watch?v=u9Dg-g7t2l4"},
                        {"Yesterday - The Beatles", "https://www.youtube.com/watch?v=NrgmdOz227I"},
                        {"Nothing Compares 2 U - Sinéad O'Connor", "https://www.youtube.com/watch?v=0-EF60neguk"},
                        {"Mad World - Gary Jules", "https://www.youtube.com/watch?v=4N3N1MlvVc4"},
                        {"Hurt - Johnny Cash", "https://www.youtube.com/watch?v=8AHCfZTRGiI"},
                        {"Fix You - Coldplay", "https://www.youtube.com/watch?v=k4V3Mo61fJM"},
                        {"The Sound of Silence - Simon & Garfunkel", "https://www.youtube.com/watch?v=4fWyzwo1xg0"},
                        {"Skinny Love - Bon Iver", "https://www.youtube.com/watch?v=ssdgFoHLwnk"},
                        {"Stay With Me - Sam Smith", "https://www.youtube.com/watch?v=pB-5XG-DbAA"},
                    };

                case "Energetic":
                    return new String[][] {
                        {"Eye of the Tiger - Survivor", "https://www.youtube.com/watch?v=btPJPFnesV4"},
                        {"Stronger - Kanye West", "https://www.youtube.com/watch?v=PsO6ZnUZI0g"},
                        {"Can't Hold Us - Macklemore", "https://www.youtube.com/watch?v=2zNSgSzhBfM"},
                        {"Thunderstruck - AC/DC", "https://www.youtube.com/watch?v=v2AC41dglnM"},
                        {"Lose Yourself - Eminem", "https://www.youtube.com/watch?v=_Yhyp-_hX2s"},
                        {"We Will Rock You - Queen", "https://www.youtube.com/watch?v=-tJYN-eG1zk"},
                        {"Pump It - The Black Eyed Peas", "https://www.youtube.com/watch?v=ZaI2IlHwmgQ"},
                        {"Levels - Avicii", "https://www.youtube.com/watch?v=_ovdm2yX4MA"},
                        {"Till I Collapse - Eminem", "https://www.youtube.com/watch?v=ytQ5CYE1VZw"},
                        {"All I Do Is Win - DJ Khaled", "https://www.youtube.com/watch?v=GGXzlRoNtHU"},
                        {"Remember the Name - Fort Minor", "https://www.youtube.com/watch?v=VDvr08sCPOc"},
                        {"Hall of Fame - The Script", "https://www.youtube.com/watch?v=mk48xRzuNvA"},
                        {"Survivor - Destiny's Child", "https://www.youtube.com/watch?v=Wmc8bQoL-J0"},
                    };

                case "Relaxed":
                    return new String[][] {
                        {"River Flows in You - Yiruma", "https://www.youtube.com/watch?v=7maJOI3QMu0"},
                        {"Weightless - Marconi Union", "https://www.youtube.com/watch?v=UfcAVejslrU"},
                        {"Somewhere Over the Rainbow - Israel Kamakawiwo'ole", "https://www.youtube.com/watch?v=V1bFr2SWP1I"},
                        {"The Scientist - Coldplay", "https://www.youtube.com/watch?v=RB-RcX5DS5A"},
                        {"Chasing Cars - Snow Patrol", "https://www.youtube.com/watch?v=GemKqzILV4w"},
                        {"Say You Won't Let Go - James Arthur", "https://www.youtube.com/watch?v=0yW7w8F2TVA"},
                        {"Perfect - Ed Sheeran", "https://www.youtube.com/watch?v=2Vv-BfVoq4g"},
                        {"A Thousand Years - Christina Perri", "https://www.youtube.com/watch?v=rtOvBOTyX00"},
                        {"Make You Feel My Love - Adele", "https://www.youtube.com/watch?v=0put0_a--Ng"},
                        {"Hallelujah - Jeff Buckley", "https://www.youtube.com/watch?v=y8AWFf7EAc4"},
                        {"Let Her Go - Passenger", "https://www.youtube.com/watch?v=RBumgq5yVrA"},
                        {"I'm Yours - Jason Mraz", "https://www.youtube.com/watch?v=EkHTsc9PU2A"},
                        {"Thinking Out Loud - Ed Sheeran", "https://www.youtube.com/watch?v=lp-EO5I60KA"},
                    };

                case "Romantic":
                    return new String[][] {
                        {"All of Me - John Legend", "https://www.youtube.com/watch?v=450p7goxZqg"},
                        {"Just the Way You Are - Bruno Mars", "https://www.youtube.com/watch?v=LjhCEhWiKXk"},
                        {"Perfect - Ed Sheeran", "https://www.youtube.com/watch?v=2Vv-BfVoq4g"},
                        {"At Last - Etta James", "https://www.youtube.com/watch?v=S-cbOl96RFM"},
                        {"Can't Help Falling in Love - Elvis Presley", "https://www.youtube.com/watch?v=vGJTaP6anOU"},
                        {"Make You Feel My Love - Adele", "https://www.youtube.com/watch?v=0put0_a--Ng"},
                        {"Endless Love - Diana Ross & Lionel Richie", "https://www.youtube.com/watch?v=JM_R1R28kLM"},
                        {"Your Song - Elton John", "https://www.youtube.com/watch?v=GlPlfCy1urI"},
                        {"The Way You Look Tonight - Frank Sinatra", "https://www.youtube.com/watch?v=h9ZGKALMMuc"},
                        {"Everything - Michael Bublé", "https://www.youtube.com/watch?v=SPUJIbXN0WY"},
                        {"Amazed - Lonestar", "https://www.youtube.com/watch?v=x-skFgrV59A"},
                        {"I Will Always Love You - Whitney Houston", "https://www.youtube.com/watch?v=3JWTaaS7LdU"},
                        {"Thinking Out Loud - Ed Sheeran", "https://www.youtube.com/watch?v=lp-EO5I60KA"},
                    };
            }
        } else { // Hindi Songs
            switch (mood) {
                case "Happy":
                    return new String[][] {
                        {"Badtameez Dil - Yeh Jawaani Hai Deewani", "https://www.youtube.com/watch?v=II2EO3Nw4m0"},
                        {"London Thumakda - Queen", "https://www.youtube.com/watch?v=udra3Mfw2oo"},
                        {"Gallan Goodiyaan - Dil Dhadakne Do", "https://www.youtube.com/watch?v=jCEdTq3j-0U"},
                        {"Nagada Sang Dhol - Goliyon Ki Raasleela Ram-Leela", "https://www.youtube.com/watch?v=X_5_BLt76c0"},
                        {"Tune Maari Entriyaan - Gunday", "https://www.youtube.com/watch?v=Q2G-YQnqunQ"},
                        {"Desi Girl - Dostana", "https://www.youtube.com/watch?v=RX7TA3ezjHc"},
                        {"Balam Pichkari - Yeh Jawaani Hai Deewani", "https://www.youtube.com/watch?v=0WtRNGubWGA"},
                        {"Ainvayi Ainvayi - Band Baaja Baaraat", "https://www.youtube.com/watch?v=pElk1ShPrcE"},
                        {"Chammak Challo - Ra.One", "https://www.youtube.com/watch?v=yP9KiFTyBks"},
                        {"Dard-E-Disco - Om Shanti Om", "https://www.youtube.com/watch?v=HZm046OFp90"},
                        {"Dhoom Machale - Dhoom", "https://www.youtube.com/watch?v=_JmZPGpX7n4"},
                        {"Mauja Hi Mauja - Jab We Met", "https://www.youtube.com/watch?v=HZm046OFp90"},
                        {"Rang Barse - Silsila", "https://www.youtube.com/watch?v=V7IwR6v7D-M"},
                    };

                case "Sad":
                    return new String[][] {
                        {"Channa Mereya - Ae Dil Hai Mushkil", "https://www.youtube.com/watch?v=284Ov7ysmfA"},
                        {"Tum Hi Ho - Aashiqui 2", "https://www.youtube.com/watch?v=IJq0yyWug1k"},
                        {"Agar Tum Saath Ho - Tamasha", "https://www.youtube.com/watch?v=sK7riqg2mr4"},
                        {"Judaai - Badlapur", "https://www.youtube.com/watch?v=S-ZpqwGpntw"},
                        {"Kabira - Yeh Jawaani Hai Deewani", "https://www.youtube.com/watch?v=jHNNMj5bNQw"},
                        {"Ae Dil Hai Mushkil - Ae Dil Hai Mushkil", "https://www.youtube.com/watch?v=6FURuLYrR_Q"},
                        {"Tadap Tadap - Hum Dil De Chuke Sanam", "https://www.youtube.com/watch?v=hpp_5mzpBwA"},
                        {"Tujhe Bhula Diya - Anjaana Anjaani", "https://www.youtube.com/watch?v=pqRbJh6M_YE"},
                        {"Kal Ho Naa Ho - Kal Ho Naa Ho", "https://www.youtube.com/watch?v=g0eO74UmRBs"},
                        {"Tere Naam - Tere Naam", "https://www.youtube.com/watch?v=P8PWN1OmZOA"},
                        {"Luka Chuppi - Rang De Basanti", "https://www.youtube.com/watch?v=_ikZtcgALBM"},
                        {"Maa - Taare Zameen Par", "https://www.youtube.com/watch?v=pOK08cRwE6c"},
                        {"Phir Bhi Tumko Chahunga - Half Girlfriend", "https://www.youtube.com/watch?v=_iktURk0X-A"},
                    };

                // ... Continue with other moods (Energetic, Relaxed, Romantic) for Hindi songs
                case "Energetic":
                    return new String[][] {
                        {"Malhari - Bajirao Mastani", "https://www.youtube.com/watch?v=l_MyUGq7pgs"},
                        {"Dhoom Machale Dhoom - Dhoom 3", "https://www.youtube.com/watch?v=11_aneYcRqs"},
                        {"Zingaat - Dhadak", "https://www.youtube.com/watch?v=RYihwKty83Q"},
                        {"Kar Gayi Chull - Kapoor & Sons", "https://www.youtube.com/watch?v=NTHz9ephYTw"},
                        {"Dhan Te Nan - Kaminey", "https://www.youtube.com/watch?v=KRJpFYxfYrI"},
                        {"Sadda Haq - Rockstar", "https://www.youtube.com/watch?v=p9DQINKZxWE"},
                        {"Dhoom Again - Dhoom 2", "https://www.youtube.com/watch?v=2uUmHTgT65I"},
                        {"Jumme Ki Raat - Kick", "https://www.youtube.com/watch?v=Qn1RYQzUx4Y"},
                        {"Chak De India - Chak De India", "https://www.youtube.com/watch?v=6a0-dSMWm5g"},
                        {"Desi Boyz - Desi Boyz", "https://www.youtube.com/watch?v=RBICqE_ZR7c"},
                        {"Tattad Tattad - Goliyon Ki Raasleela Ram-Leela", "https://www.youtube.com/watch?v=sIooFGRBZJY"},
                        {"Gandi Baat - R... Rajkumar", "https://www.youtube.com/watch?v=vvLBXO89vfM"},
                        {"Lungi Dance - Chennai Express", "https://www.youtube.com/watch?v=2kWLyInyjOE"},
                    };

                case "Relaxed":
                    return new String[][] {
                        {"Tum Se Hi - Jab We Met", "https://www.youtube.com/watch?v=mt9xg0mmt28"},
                        {"Iktara - Wake Up Sid", "https://www.youtube.com/watch?v=fSS_R91Nimw"},
                        {"Kun Faya Kun - Rockstar", "https://www.youtube.com/watch?v=T94PHkuydcw"},
                        {"Jeena Jeena - Badlapur", "https://www.youtube.com/watch?v=vA86QFrXoho"},
                        {"Tum Hi Ho Bandhu - Cocktail", "https://www.youtube.com/watch?v=o1RducJbUdc"},
                        {"Phir Se Ud Chala - Rockstar", "https://www.youtube.com/watch?v=2mWaqsC3U7k"},
                        {"Abhi Mujh Mein Kahin - Agneepath", "https://www.youtube.com/watch?v=oWKgpB2zpgw"},
                        {"Tere Bina - Guru", "https://www.youtube.com/watch?v=9JDzlhW3XTM"},
                        {"Sajdaa - My Name Is Khan", "https://www.youtube.com/watch?v=zfABYXP_NSA"},
                        {"Tu Hi Re - Bombay", "https://www.youtube.com/watch?v=pwGUvxIyZ0E"},
                        {"Khaabon Ke Parindey - Zindagi Na Milegi Dobara", "https://www.youtube.com/watch?v=R0XjwtP_iTY"},
                        {"Tere Naina - My Name Is Khan", "https://www.youtube.com/watch?v=2OEKnLpqIt0"},
                        {"Kabhi Alvida Naa Kehna - KANK", "https://www.youtube.com/watch?v=lN1m7zLBbSU"},
                    };

                case "Romantic":
                    return new String[][] {
                        {"Tum Hi Ho - Aashiqui 2", "https://www.youtube.com/watch?v=IJq0yyWug1k"},
                        {"Gerua - Dilwale", "https://www.youtube.com/watch?v=AEIVhBS6baE"},
                        {"Hawayein - Jab Harry Met Sejal", "https://www.youtube.com/watch?v=cYOB941gyXI"},
                        {"Tere Sang Yaara - Rustom", "https://www.youtube.com/watch?v=E8rpY2FwKkY"},
                        {"Pehli Nazar Mein - Race", "https://www.youtube.com/watch?v=BadBAMnPX0I"},
                        {"Mere Naam Tu - Zero", "https://www.youtube.com/watch?v=X1dXeUxZLrA"},
                        {"Tera Ban Jaunga - Kabir Singh", "https://www.youtube.com/watch?v=mQiiw7uRngk"},
                        {"Pehli Dafa - Atif Aslam", "https://www.youtube.com/watch?v=SxTYjptEzZs"},
                        {"Kuch Kuch Hota Hai - KKHH", "https://www.youtube.com/watch?v=S9DzJnhDxbs"},
                        {"Tere Liye - Veer Zaara", "https://www.youtube.com/watch?v=Jb6G0SZVVlE"},
                        {"Main Agar Kahoon - Om Shanti Om", "https://www.youtube.com/watch?v=DAYszemgPxc"},
                        {"Tujhe Dekha To - DDLJ", "https://www.youtube.com/watch?v=cNV5hLSa9H8"},
                        {"Mere Haath Mein - Fanaa", "https://www.youtube.com/watch?v=qJt5mHYZ_nk"},
                    };
            }
        }
        return new String[][]{};
    }
    
    private void openWebPage(String url) {
        try {
            Desktop.getDesktop().browse(new URI(url));
        } catch (IOException | URISyntaxException e) {
            JOptionPane.showMessageDialog(this, 
                "Error opening link: " + e.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Test().setVisible(true);
        });
    }
}
