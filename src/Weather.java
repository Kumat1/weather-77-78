import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class Weather extends JFrame {
    private JLabel judul;

    public Weather() throws IOException {
        super("WEATHER");

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.CYAN);
        getContentPane().add(panel);

        JLabel judul = new JLabel("WEATHER FORECAST");
        judul.setHorizontalAlignment(SwingConstants.CENTER);
        judul.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        judul.setFont(new Font("Arial",Font.BOLD,22));
        panel.add(judul,BorderLayout.NORTH);

        JPanel panelButton = new JPanel(new BorderLayout());
        panel.add(panelButton,BorderLayout.SOUTH);

        JButton cek = new JButton("SEARCH");
        cek.setFont(new Font("Arial",Font.BOLD,12));
        cek.setBorder(BorderFactory.createEmptyBorder(5,2,5,2));
        panelButton.add(cek,BorderLayout.NORTH);

        JButton clear = new JButton("CLEAR");
        clear.setFont(new Font("Arial",Font.BOLD,12));
        clear.setBorder(BorderFactory.createEmptyBorder(5,2,5,2));
        panelButton.add(clear,BorderLayout.SOUTH);

        JPanel panelMain = new JPanel(new GridBagLayout());
        panelMain.setSize(350,250);
        panel.add(panelMain,BorderLayout.CENTER);

        GridBagConstraints c = new GridBagConstraints();
        c.weightx = 1;
        c.weighty = 1;
        c.gridwidth = 1;
        c.gridheight = 1;

        c.gridx = 0;
        c.gridy = 0;
        c.anchor = GridBagConstraints.LINE_END;
        JLabel namakota = new JLabel("CITY : ");
        namakota.setFont(new Font("Arial",Font.BOLD,12));
        panelMain.add(namakota,c);

        c.gridy = 1;
        JLabel suhu = new JLabel("TEMPERATURE : ");
        suhu.setFont(new Font("Arial",Font.BOLD,12));
        panelMain.add(suhu,c);

        c.gridy = 2;
        JLabel deskripsi = new JLabel("DESCRIPTION : ");
        deskripsi.setFont(new Font("Arial",Font.BOLD,12));
        panelMain.add(deskripsi,c);

        c.gridy = 3;
        JLabel humidity = new JLabel("HUMIDITY : ");
        humidity.setFont(new Font("Arial",Font.BOLD,12));
        panelMain.add(humidity,c);

        c.gridy = 4;
        JLabel visibility = new JLabel("VISIBILITY : ");
        visibility.setFont(new Font("Arial",Font.BOLD,12));
        panelMain.add(visibility,c);

        c.gridy = 5;
        JLabel pressure = new JLabel("PRESSURE : ");
        pressure.setFont(new Font("Arial",Font.BOLD,12));
        panelMain.add(pressure,c);

        c.gridx = 1;
        c.gridy = 0;
        c.anchor = GridBagConstraints.LINE_START;
        JTextField fieldkota = new JTextField(20);
        panelMain.add(fieldkota,c);

        c.gridy = 1;
        JTextField fieldsuhu = new JTextField(20);
        panelMain.add(fieldsuhu,c);
        fieldsuhu.setEditable(false);

        c.gridy = 2;
        JTextField fielddeskripsi = new JTextField(20);
        panelMain.add(fielddeskripsi,c);
        fielddeskripsi.setEditable(false);

        c.gridy = 3;
        JTextField fieldhumidity = new JTextField(20);
        panelMain.add(fieldhumidity,c);
        fieldhumidity.setEditable(false);

        c.gridy = 4;
        JTextField fieldvisibility = new JTextField(20);
        panelMain.add(fieldvisibility,c);
        fieldvisibility.setEditable(false);

        c.gridy = 5;
        JTextField fieldpressure = new JTextField(20);
        panelMain.add(fieldpressure,c);
        fieldpressure.setEditable(false);

        cek.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (fieldkota.getText().isEmpty()){
                    JOptionPane.showMessageDialog(null,"City Tidak Boleh Kosong","WARNING",JOptionPane.WARNING_MESSAGE);
                }
                else {
                    String kota = fieldkota.getText();

                    String api = "http://api.openweathermap.org/data/2.5/weather?q=" + kota + "&appid=b7ba0b30cc4aee57ba67dd21c5f04eb9";

                    URL url = null;
                    try {
                        url = new URL(api);
                    } catch (MalformedURLException e1) {
                        e1.printStackTrace();
                    }

                    try {
                        BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));

                        JSONParser parser = new JSONParser();

                        try {
                            Object o = parser.parse(reader);
                            JSONObject ob = (JSONObject) o;
                            String namakota = String.valueOf(ob.get("name"));
                            JSONObject main = (JSONObject) ob.get("main");
                            String temp = String.valueOf(main.get("temp"));
                            String humidity = String.valueOf(main.get("humidity"));
                            String visibility = String.valueOf(ob.get("visibility"));
                            String pressure = String.valueOf(main.get("pressure"));

                            JSONArray ar = (JSONArray) ob.get("weather");
                            for (Object weather : ar) {
                                JSONObject obj = (JSONObject) weather;

                                String desc = String.valueOf(obj.get("description"));

                                fielddeskripsi.setText(desc.toUpperCase());
                            }

                            fieldkota.setText(namakota.toUpperCase());
                            fieldkota.setEditable(false);
                            fieldsuhu.setText(temp.toString() + " F");
                            fieldhumidity.setText(humidity.toString());
                            fieldvisibility.setText(visibility.toString());
                            fieldpressure.setText(pressure.toString());

                        } catch (ParseException e1) {
                            e1.printStackTrace();
                        }

                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });

        clear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fieldkota.setText(null);
                fieldsuhu.setText(null);
                fielddeskripsi.setText(null);
                fieldhumidity.setText(null);
                fieldvisibility.setText(null);
                fieldpressure.setText(null);
                fieldkota.setEditable(true);
            }
        });

        setSize(300,320);
        setVisible(true);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public static void main(String[] args) throws IOException {
        Weather weather = new Weather();
    }
}
