import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

public class MyFrame extends JFrame {

    private static JTextField genderText;
    private static JTextField dobText;


    public MyFrame() {
        setTitle("Covid-19");
        setSize(580, 410);


        JPanel cardPanel = new JPanel();
        CardLayout cardLayout = new CardLayout();
        cardPanel.setLayout(cardLayout);


        JPanel panel1 = new JPanel();
        JLabel genderLabel = new JLabel("Gender :");
        JLabel dobLabel = new JLabel("Birthday :");
        JLabel genderTLabel = new JLabel("male or female");
        JLabel dobTLabel = new JLabel("\"yyyy-mm-dd \"");

        genderText = new JTextField(20);
        dobText = new JTextField(20);

        JButton jButton = new JButton("Get Vaccine");
        ImageIcon gifIcon2 = new ImageIcon("src/vaccine.jpg");  // เปลี่ยนเป็นพาธของไฟล์ GIF
        JLabel gifLabel2 = new JLabel(gifIcon2);

        panel1.setLayout(null);


        genderLabel.setBounds(90, 150, 120, 25);
        genderLabel.setForeground(Color.black);
        Font font = new Font("Arial", Font.BOLD, 20);
        genderLabel.setFont(font);

        panel1.add(genderLabel);

        genderText.setBounds(230, 150, 150, 25);
        panel1.add(genderText);

        dobLabel.setBounds(90, 180, 120, 25);
        dobLabel.setForeground(Color.black);
        dobLabel.setFont(font);
        panel1.add(dobLabel);


        genderTLabel.setBounds(400, 150, 300, 25);
        genderTLabel.setForeground(Color.red);
        genderTLabel.setFont(font);
        panel1.add(genderTLabel);

        dobText.setBounds(230, 180, 150, 25);
        panel1.add(dobText);
        jButton.setBounds(130, 230, 120, 25);

        dobTLabel.setBounds(400, 180, 300, 25);
        dobTLabel.setForeground(Color.red);
        dobTLabel.setFont(font);
        panel1.add(dobTLabel);
        panel1.add(jButton);

        jButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                getVaccineThread gvt = new getVaccineThread();
                gvt.run(genderText, dobText);

                new Thread(() -> {
                    try {
                        main(new String[]{});
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }).start();
            }
        });

        JButton closeButton = getExitButton();
        panel1.add(closeButton);
        gifLabel2.setBounds(0, 0, 580, 382);
        panel1.add(gifLabel2);


        cardPanel.add(panel1, "Page 1");

        getContentPane().add(cardPanel);


    }

    private static JButton getExitButton() {
        JButton closeButton = new JButton("Exit");

        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int choice = showCustomDialog();
                if (choice == JOptionPane.YES_OPTION) {
                    System.exit(0);
                } else if (choice == JOptionPane.NO_OPTION) {
                    System.out.println("You clicked No");
                }
            }
        });
        closeButton.setBounds(270, 230, 120, 25);
        return closeButton;
    }

    private static int showCustomDialog() {
        String[] options = {"Yes", "No"};
        return JOptionPane.showOptionDialog(
                null,
                "Do you want to exit the application?",
                "Exit",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]
        );
    }

    class getVaccineThread extends Thread {
        public void run(JTextField genderText, JTextField dobText) {
            String gender = genderText.getText();
            String dob = dobText.getText();
            if (gender.equals("male") || gender.equals("female")) {
                if (!(dobText.equals(""))) {
                    vaccine(gender, dob);
                } else {
                    JOptionPane.showMessageDialog(null, "Please input Date of brith \"yyyy-MM-dd\" to get Vaccine");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Please Input Gender \"male\" or \"female\" ");
            }
        }
    }

    void printInfo(String gender, String dobStr, LocalDate startVaccine, LocalDate endVaccine) {
        JOptionPane.showMessageDialog(null, "You are " + gender
                + "\nEligible Flag : Y\n" +
                "Service Start Date : " + startVaccine + "\n" +
                "Service End Date : " + endVaccine);
    }

    void printInfo(String gender, String dobStr) {
        JOptionPane.showMessageDialog(null, "You are " + gender + "\nEligible Flag : N\n" +
                "Service Start Date : Null \n" +
                "Service End Date : Null");
    }

    void vaccine(String gender, String dobStr) {

        MyFrame frame = new MyFrame();

        try {

            LocalDate dob = LocalDate.parse(dobStr, DateTimeFormatter.ofPattern("yyyy-MM-dd"));


            LocalDate startVaccine = LocalDate.parse("2021-06-01");
            LocalDate endVaccine = LocalDate.parse("2021-08-31");


            Period startAge = Period.between(dob, startVaccine);
            Period endAge = Period.between(dob, endVaccine);

            LocalDate sixM = dob.plusMonths(6);
            LocalDate twoY = dob.plusYears(2);
            LocalDate sixtyFiveY = dob.plusYears(65);


            System.out.println("วันที่เกิด : " + dob);

            System.out.println("วันที่เริ่มรับวัคซีน : " + startVaccine);
            System.out.println("อายุครบ 6 เดือน : " + sixM);
            System.out.println("อายุครบ 2 ปี : " + twoY);
            System.out.println("อายุครบ 66 ปี : " + sixtyFiveY);

            System.out.println("วันที่เริ่มฉีด ฉันอายุุ ุ: " + startAge.getYears() + " ปี, " + startAge.getMonths() + " เดือน, " + startAge.getDays() + " วัน");
            System.out.println("วันสุดท้าย ฉันอายุ ุ: " + endAge.getYears() + " ปี, " + endAge.getMonths() + " เดือน, " + endAge.getDays() + " วัน");

            if (startAge.getYears() >= 65) {
                System.out.println("Eligible Flag : Y");
                System.out.println("จัดอยู่ในกลุ่มอายุ เกิน 65 ตั้งเเต่เเรก");
                System.out.println("วันที่เริ่มรับวัคซีน : " + startVaccine);
                System.out.println("วันที่สุดท้าย : " + endVaccine);
                frame.printInfo(gender, dobStr, startVaccine, endVaccine);
            } else if (endAge.getYears() >= 65) {
                System.out.println("Eligible Flag : Y");
                System.out.println("จัดอยู่ในกลุ่มอายุ เกิน 65 ตอนช่วงฉีด");
                System.out.println("วันที่เริ่มรับวัคซีน : " + sixtyFiveY);
                System.out.println("วันที่สุดท้าย : " + endVaccine);
                frame.printInfo(gender, dobStr, sixtyFiveY, endVaccine);
            } else if (startAge.getYears() == 0 && startAge.getMonths() >= 6) {
                System.out.println("Eligible Flag : Y");
                System.out.println("จัดอยู่ในกลุ่มอายุ 6 เดือน ถึง 2 ปี");
                System.out.println("วันที่เริ่มรับวัคซีน : " + startVaccine);
                System.out.println("วันที่สุดท้าย : " + endVaccine);
                frame.printInfo(gender, dobStr, startVaccine, endVaccine);
            } else if (startAge.getYears() >= 1 && (startAge.getYears() <= 2 && startAge.getMonths() == 0 && startAge.getDays() == 0)) {
                if (endAge.getYears() >= 1 && (endAge.getYears() <= 2 && endAge.getMonths() == 0 && endAge.getDays() == 0)) {
                    System.out.println("Eligible Flag : Y");
                    System.out.println("จัดอยู่ในกลุ่มอายุ 6 เดือน ถึง 2 ปี");
                    System.out.println("วันที่เริ่มรับวัคซีน : " + startVaccine);
                    System.out.println("วันที่สุดท้าย : " + endVaccine);
                    frame.printInfo(gender, dobStr, startVaccine, endVaccine);
                } else {
                    System.out.println("Eligible Flag : Y");
                    System.out.println("จัดอยู่ในกลุ่มอายุ วันเเรกตามเกณ เเต่อายุเกินก่อนถึงวันสุดท้าย");
                    System.out.println("วันที่เริ่มรับวัคซีน : " + startVaccine);
                    System.out.println("วันที่สุดท้าย : " + twoY);
                    frame.printInfo(gender, dobStr, startVaccine, twoY);
                }
            } else if (endAge.getYears() == 0 && endAge.getMonths() >= 6) {
                System.out.println("Eligible Flag : Y");
                System.out.println("จัดอยู่ในกลุ่มอายุวันเริ่มฉีดไม่ถึง พึ่งถึงช่วงฉีด");
                System.out.println("วันที่เริ่มรับวัคซีน : " + sixM);
                System.out.println("วันที่สุดท้าย : " + endVaccine);
                frame.printInfo(gender, dobStr, sixM, endVaccine);
            } else {
                System.out.println("Eligible Flag : N");
                System.out.println("คุณไม่เข้าตามเกณการรับวัคซีน อายุของคุณ คือ ");
                System.out.println("วันที่เริ่มฉีด ฉันอายุุ ุ: " + startAge.getYears() + " ปี, " + startAge.getMonths() + " เดือน, " + startAge.getDays() + " วัน");
                System.out.println("วันสุดท้าย ฉันอายุ ุ: " + endAge.getYears() + " ปี, " + endAge.getMonths() + " เดือน, " + endAge.getDays() + " วัน");
                frame.printInfo(gender, dobStr);
            }
        } catch (Exception e) {
            System.out.println("please input Date of brith \"yyyy-MM-dd\" to get Vaccine");
            JOptionPane.showMessageDialog(null, "Please input Date of brith \"yyyy-MM-dd\" to get Vaccine");
        }

    }

    public static void main(String[] args) throws IOException {
        MyFrame frame = new MyFrame();
        SwingUtilities.invokeLater(() -> {
            frame.setVisible(true);
        });
    }
}

